<template>
  <view class="container">
    <view class ="header">
      <text class="title">{{categoryInfo.name}}</text>
      <view class="sep"></view>
    </view>
    <view class="empty-container" v-if="empty">
      <image class="sad" :source="require('../../assets/images/categoryByScreen/sad.png')"/>
      <text class="empty-text">Il n'y a pas encore de défi dans cette catégorie.</text>
    </view>
    <ChallengeList v-bind:challenges="challenges" v-bind:goToChallenge="goToChallenge"/>
      </view>
</template>

<style>
.container {
    /* background-color: #b2ebcc; */
    width:100%;
    flex:1;
}
.empty-container{
  align-items: center;
  justify-content: center;
  height: 70%;
}

.sad{
  width:80;
  height:80;
  margin-bottom: 20;
}

.empty-text{
  text-align: center;
  width: 50%;
}
.header {
  justify-content: center;
  align-items: center;
  margin-bottom: 20;
}

.sep{
  width: 80%;
  align-items: center;
  border-color:#3d9d84;
  border-bottom-width: 2;
  padding-bottom: 20;
  
  
}
.title {
  font-size: 40px;
  font-weight: 200;
  color:#3d9d84;
  /* background-color: white; */
  margin-top:20;
  /* color: white; */
}
</style>

<script>
import ChallengeList from '../ChallengeList';

import {request, baseURL} from '../../api.js';
export default {
  props: {
      navigation: {
        type: Object
      }
    },
  components: {
    ChallengeList: ChallengeList,
  },
    
  data: function() {
    return {
      empty:false,
      baseURL: baseURL,
        categoryInfo: {
          name:'',
        },
        challenges: [
          //  {
          //   id: 1,
          //   title : 'Prendre des nouvelles de ses voisins',
          //   description : "En ce moment, certains n'ont pas la chance d'avoir des proches près d'eux ...",
          //   image : 'https://www.materialui.co/materialIcons/communication/phone_black_192x192.png'
          // },
          //  {
          //   id: 2,
          //   title : 'Faire une mousse au chocolat',
          //   description : "Des oeufs, une tablette de chocolat dessert? Pourquoi ne pas essayer de reproduire chez vous ...",
          //   image : 'https://icons-for-free.com/iconfiles/png/512/cake+48px-131987943060752100.png'
          // },
          //  {
          //   id: 3,
          //   title : 'Un troisième challenge',
          //   description : "Lorem ipsum je n'ai pas d'idée, de toute façons personne ne lira cette description. PERSONNE LIS TES PAVEEEES",
          //   image : ''
          // },
        ]
    }
  },

  methods: {
    fetch : function() {
      const self = this;
       request({
        method: 'get',
        url: '/api/getChallengeByCategory/'+self.navigation.state.params.categoryId
        }).then(function(response){
          //console.log(response)
          self.challenges = response.data
          self.empty = self.challenges.length == 0
        }).catch(function(error){
          console.log(error);
        })
    },

    fetchCatInfo : function() {
      const self = this;
      request({
        method: 'get',
        url: '/api/getCategory/'+self.navigation.state.params.categoryId
        }).then(function(response){
          //console.log(response)
          self.categoryInfo = response.data
        }).catch(function(error){
          console.log(error);
        })
    },

    goToChallenge : function(challenge) {
      this.navigation.navigate("ChallengeDetail", {challengeId:challenge.id})
    },

    goToCorona : function() {
      this.navigation.navigate("Covid19")
    }
  },
  mounted: function() {
    this.fetch();
    this.fetchCatInfo();
  }
};
</script>
