<template>
    <scroll-view class = "myScrollView">
          <view class = "element-border" v-for="(challenge, index) in challenges" :key="index">
            <touchable-opacity class = "element-container" :on-press="() => goToChallenge(challenge)">
              <view class ="challenge-text">
              <text class = "challenge-title">{{challenge.name}}</text> 
              <text class = "challenge-desc">{{challenge.description}}</text>
              </view>
              <view>
              <image class = "challenge-icon" :source="{uri: baseURL + '/static/image/jpg?path=' + challenge.imgPath}"/>
              </view>
            </touchable-opacity>
          </view>
    </scroll-view>
</template>

<style >
.element-border {
  border-bottom-width: 1;
  border-color: gray;
  width: 100%;
  padding: 15;
}

.element-container {
  width: 100%;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}

.challenge-text{
  /* background-color: grey; */
  width:80%;
  
  
}
.challenge-title {
  font-size: 20;
  color: #3d9d84;
  padding-bottom:10;
}

.challenge-desc {
  color:#4e4e4e;
}

.challenge-icon {
  width: 60;
  height: 60;
  border-radius: 25;
}
</style>

<script>
import {baseURL} from '../api.js';

export default {
    props: {
        challenges: Array,
        goToChallenge: Function,
    },
    data: function() {
        return {
            baseURL: baseURL,
        }
    },
    methods: {
        // goToChallenge : this.goToChallenge
    },
    mounted: function (){

    }
}

</script>