<template>
  <view class="container">
    <!-- <view class ="header">
  <view class="container" :style=styles.lightPrimaryColor>
    <view class ="header" :style=styles.defaultPrimaryColor>
      <view>
      <text class="title" :style=styles.textPrimaryColor>Listes des Défis</text>
      </view>
    </view> -->
     <ChallengeList :challenges="challenges" :goToChallenge="goToChallenge"/>
  </view>
</template>

<style>

/* .header {
  height : 10%;
  justify-content: center;
  align-items: center;
  background-color: #3d9d84;
  color:white;
  flex-direction: row;
  justify-content: space-around;
} */

.header {
  height : 10%;
  justify-content: center;
  align-items: center;
  flex-direction: row;
}
.title {
  font-size: 40px;
  font-weight: 200;
}
.container {
  flex: 1;
}
</style>

<script>
import {request, baseURL} from '../../api.js';
import ChallengeList from '../ChallengeList';
import styles from '../../palette.js'

export default {
  props: {
      navigation: {
        type: Object
      }
    },
  components:{
    ChallengeList:ChallengeList,
  },
  data: function() {
    return {
      styles: styles,
      baseURL: baseURL,
      challenges: [
          {
          id: 1,
          title : 'Prendre des nouvelles de ses voisins',
          description : "En ce moment, certains n'ont pas la chance d'avoir des proches près d'eux ...",
          image : 'https://www.materialui.co/materialIcons/communication/phone_black_192x192.png'
        },
          {
          id: 2,
          title : 'Faire une mousse au chocolat',
          description : "Des oeufs, une tablette de chocolat dessert? Pourquoi ne pas essayer de reproduire chez vous ...",
          image : 'https://icons-for-free.com/iconfiles/png/512/cake+48px-131987943060752100.png'
        },
          {
          id: 3,
          title : 'Un troisième challenge',
          description : "Lorem ipsum je n'ai pas d'idée, de toute façons personne ne lira cette description. PERSONNE LIS TES PAVEEEES",
          image : ''
        },
      ]
    }
  },

  methods: {
    fetch : function() {
      const self = this;
       request({
        method: 'get',
        url: '/api/allChallenges'
        }).then(function(response){
          self.challenges = response.data
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
  }
};
</script>
