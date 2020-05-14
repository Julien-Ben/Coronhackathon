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
    <view v-else>
      <ChallengeList v-bind:challengesAndBool="challenges" v-bind:goToChallenge="goToChallenge"/>
    </view>
  </view>
</template>

<style>
.container {
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
  margin-top:20;
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
      challenges:{},
      baseURL: baseURL,
      categoryInfo: {
        name:'',
      },
    }
  },

  methods: {
    fetch : function() {
      const self = this;
       request({
        method: 'get',
        url: '/api/getChallengeByCategoryBool/'+self.navigation.state.params.categoryId
      }).then(function(response){
        self.challenges = response.data;
        self.empty = self.challenges.Challenge.length == 0;
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
