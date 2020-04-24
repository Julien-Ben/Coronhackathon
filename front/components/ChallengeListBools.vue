<template>
  <scroll-view class = "myScrollView">
    <view class = "element-border" v-for="(challenge, index) in challengesAndBool.Challenge" :key="index">
      <touchable-opacity class = "element-container" :on-press="() => goToChallenge(challenge)">
        <view class ="challenge-text" v-if="challengesAndBool.Completed[index]">
          <text class = "challenge-title">{{challenge.name}}</text> 
          <text class = "challenge-desc">{{challenge.description}}</text>
        </view>
        <view class ="challenge-text" v-else>
          <text class = "challenge-title-done">{{challenge.name + " (validé)"}}</text> 
          <text class = "challenge-desc-done">{{challenge.description}}</text>
        </view>
        <view class="empty-container" v-if="challengesAndBool.Completed[index]">
          <image class = "challenge-icon" :source="{uri: baseURL + '/static/image/jpg?path=' + challenge.imgPath}"/>
          <!--TODO : Mettre l'image uploadée lors de la validation ?-->
        </view>
        <view v-else>
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

.challenge-title-done {
  font-size: 20;
  color: #4e4e4e;
  padding-bottom:10;
}

.challenge-desc-done {
  color:#4e4e4e;
}

.challenge-icon {
  width: 60;
  height: 60;
  border-radius: 25;
}

.challenge-icon-done {
  width: 60;
  height: 60;
  border-radius: 35;
}
</style>

<script>
import {baseURL} from '../api.js';

export default {
    props: {
        challengesAndBool: Object,
        goToChallenge: Function,
    },
    data: function() {
        return {
            baseURL: baseURL,
        }
    },
    mounted: function (){
    }
}
</script>