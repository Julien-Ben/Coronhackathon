<template>
  <view class="container"> 
    <scroll-view class="scroll">
      <view class ="header">
      
      <text class="title">{{titre}}</text>
      <view class="sep"></view>
      </view>

      <view class="main">
        <view class="image-container">
          <image class='image'
            :source="{uri: baseURL + '/static/image/jpg?path=' + imageChallenge}"
          />
        </view>
        <view class="desc-container">
          <text class="desc">{{description}}</text>
        </view>
        <touchable-opacity class="container-btn" :on-press="accessChallengeValidation" :style=styles.defaultPrimaryColor>
        <text class="validation-btn" :style=styles.textPrimaryColor>Valider ce challenge</text>
        </touchable-opacity>
        
      </view>
      <view class="sep-container">
        <view class="sep"></view>
      </view>
      <view class="comment-title-container">
        <text class="comment-title">Commentaires des autres utilisateurs:</text>
      </view>

      <view class="comments-container">
        <view class="comments" v-for="(commentaire, index) in commentaires" :key="index">
          <view class="comment-container">
            <text class="user">{{commentaire[0]}}</text>
            <text class="comment">{{commentaire[1]}}</text>
          </view>
        </view>
      </view>
    </scroll-view>
  </view>
</template>


<style>
.container {
  align-items: center;
  justify-content: center;
  /* flex: 1; */
  width:100%;
  height:100%;
}

.scroll {
  width:100%;
  
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
  max-width:90%;
  text-align: center;
}

.main{
  align-items: center;
  /* background-color: gray; */
}

.image-container{
  width: 100%;
  min-height: 30;
  align-items: center;
  margin-bottom:30;
  margin-top:30;
}
.image{
    width : 200;
    height: 200;
    border-width: 3;
    border-color: white;
    border-radius: 15;
}

.desc-container{
    max-width: 70%;
    justify-content: center;
    align-items: center;
    padding: 10;
    padding-top:30;
    margin-bottom:30;
}
.desc{
    font-size:20;
    color : #4e4e4e;
    /* text-align: center; */
}

.container-btn {
  border-radius: 25;
  margin-bottom:30;
}
.validation-btn{
    font-size: 20;
    padding-top: 10;
    padding-bottom: 10;
    padding-left: 20;
    padding-right: 20;
    
}

.sep-container{
  width:100%;
  align-items: center;
  padding: 10;
  padding-bottom: 30;
}



.comment-title-container{
  width:100%;
  padding:10;
  align-items: center;
}

.comment-title{
  font-size:25;
  color : #3d9d84;
  width:80%;
}

.comments-container{
  width:100%;
  align-items: center;
}
.comments{
  width:100%;
  padding-bottom:100;
  align-items: center;
}

.comment-container{
  border-bottom-width: 1;
  border-color:gray;
  width:100%;
  padding:30;
  
}
.user{
  font-size: 20;
  color : #3d9d84;
  margin-bottom:5;
}
.comment{
  color :#4e4e4e;
  font-size: 20;
  /* padding-left:30; */
}

</style>


<script>
import {request, baseURL} from '../../api.js';
import { Alert } from 'react-native';
import axios from "axios";
import styles from "../../palette.js"

export default {
  props: {
      navigation: {
        type: Object
      }
    },
  data: function() {
    return {
        styles: styles,
        titre:'',
        description:'',
        categoryId:'',
        imageCategory:'',
        imageChallenge:'',
        commentaires:[],
        baseURL:baseURL,
    }
  },
  methods: {

    accessChallengeValidation : function(){
        this.navigation.navigate("Validation", {challengeId:this.navigation.state.params.challengeId})
    },

    fetch : function() {
      const self = this;
       request({
        method: 'get',
        url: '/api/getChallenge/'+this.navigation.state.params.challengeId
        }).then(function(response){
          self.titre = response.data.name;
          self.description = response.data.description;
          self.categoryId =response.data.categoryId;
          self.imageChallenge= response.data.imgPath;
          self.getComments();
        }).catch(function(error){
          console.log(error);
        })
    },

    getComments : function(){
      const self = this;
       request({
        method: 'get',
        url: '/api/getCommentsOfChallenge/'+this.navigation.state.params.challengeId
        }).then(function(response){
          self.commentaires =response.data;
          self.getImageCategory();
        }).catch(function(error){
          console.log(error);
        })
    },

    getImageCategory : function(){
      const self = this;
       request({
        method: 'get',
        url: '/api/getCategory/'+self.categoryId
        }).then(function(response){
          self.imageCategory =response.data.logo;
          // console.log(response)
        }).catch(function(error){
          console.log(error);
        })
    }


  },
  mounted : function(){
      this.fetch();
  }
};

</script>
