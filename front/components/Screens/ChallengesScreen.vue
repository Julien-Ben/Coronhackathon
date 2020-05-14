<template>
  <view class="container">
      <view>
        <ChallengeList :challengesAndBool="challenges" :goToChallenge="goToChallenge"/>
      </view>
  </view>
</template>

<style>

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
      challenges: {},
    }
  },

  methods: {
    fetch : function() {
      const self = this;
      request({
        method: 'get',
        url: '/api/getChallengesBool'
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
