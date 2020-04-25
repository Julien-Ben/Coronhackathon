<template>
  <scroll-view class = "myScrollView">
    <view v-for="(challenge, index) in challengesAndBool.Challenge" :key="index">
      <view class = "element-border" v-if="!challengesAndBool.Completed[index]">
        <touchable-opacity class = "element-container" :on-press="() => goToChallenge(challenge)">
          <view class ="challenge-text">
            <text class = "challenge-title">{{challenge.name}}</text> 
            <text class = "challenge-desc">{{challenge.description}}</text>
          </view>
          <view class="empty-container">
            <image class = "challenge-icon" :source="{uri: baseURL + '/static/image/jpg?path=' + challenge.imgPath}"/>
          </view>
        </touchable-opacity>
      </view> 
      <view class = "element-border-done" :style="styles.lightPrimaryColor" v-else>
        <touchable-opacity class = "element-container-done" :style="styles.lightPrimaryColor" :on-press="() => goToChallenge(challenge)">
          <view class ="challenge-text">
            <text class = "challenge-title-done">{{challenge.name}}</text> 
            <text class = "challenge-desc-done">{{challenge.description}}</text>
          </view>
          <view class="empty">
            <image class = "challenge-icon" :source="{uri: baseURL + '/static/image/jpg?path=' + challenge.imgPath}"/>
            <view class="check-container">
              <image class = "check-icon" :source="require('../assets/images/challengescreen/check.png')"/>
            </view>
          </view>
        </touchable-opacity>
      </view> 
    </view>
  </scroll-view>
</template>

<style >
.element-border {
  background-color: white;
  border-bottom-width: 1;
  border-color: gray;
  width: 100%;
  padding: 15;
}

.element-container {
  background-color: white;
  width: 100%;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}

.element-border-done {
  border-bottom-width: 1;
  border-color: gray;
  width: 100%;
  padding: 15;
}

.element-container-done {
  width: 100%;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}

.check-container {
  width: 100%;
  justify-content: space-evenly;
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
  color: #3d9d84;
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

.check-icon {
  width: 20;
  height: 20;
  border-radius: 10;
}
</style>

<script>
import {baseURL} from '../api.js';
import styles from "../palette.js";

export default {
    props: {
        challengesAndBool: Object,
        goToChallenge: Function,
    },
    data: function() {
        return {
            baseURL: baseURL,
            styles: styles,
        }
    },
    mounted: function (){
    }
}
</script>
