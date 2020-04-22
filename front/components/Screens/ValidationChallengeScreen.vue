<template>
<scroll-view>
  <view class ="container">
    <!--titre de l'onglet + détails du challenge [title, category, description]    -->
    <view class ="header">
      <text class="title">{{challenge}}</text>
      <view class="sep"></view>
    </view>

    <!-- Box "rajouter une description" + on la récupère pour la poster  -->
    <view class="main">
    <view class="desc-container">
      <text class="input-title"></text>
      <view class="input-container">
        <text-input class = "input-text" 
        placeholder="Commente ce que tu as réalisé !" 
        v-model= "review"
        multiline
        placeholderTextColor="grey"
        textAlignVertical="top"
        maxLength=500/></text-input>
      </view>
    </view>
    <view class="picker-container">
      <view class="picker">
        <ImagePicker/>
      </view>
    </view>
    <!-- On confirme la validation + on poste les détails de la validation   -->
    <touchable-opacity class="container-btn" :on-press="challengeValidation" :style=styles.defaultPrimaryColor>
      <text class="validation-btn" :style=styles.textPrimaryColor>Valider !</text>
    </touchable-opacity>

    </view>
</view>
</scroll-view>
</template>

<style>

.container {
  background-color: white;
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
  /* justify-content: center;  */
  align-items: center;
  height:80%;
  /* background-color: lightgrey; */
}
.desc-container{
  align-items: center;
  width:100%;
  min-height:200;
  max-height:200;
  margin: 20;
  /* background-color: brown; */
}

.input-title{
  font-size:20;
  color : #3d9d84;
}

.input-container{
  width:90%;
  min-height:30;
  border-color: #3d9d84;
  border-radius:5;
  border-width: 1;
  padding:10;
}

.input-text {
  font-size: 18;
  text-align: left;
}

.picker-container  {
  min-height: 250;
  width:100%;
  font-size: 20;
  margin-bottom: 20;
  /* background-color:burlywood; */
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

</style>


<script>
import { Alert } from 'react-native';
import {request} from '../../api.js';
import React from 'react';
import {Text} from 'react-native';
import ImagePicker from '../ImagePicker';
import styles from "../../palette.js"

export default {
    props: {
        navigation: {
          type: Object
      }
  },
  components: {
    ImagePicker,
  },
    data:   function() {
      return {
          styles: styles,
          response:{
            commentary:"",
            picture:null,
          },
          review:"",
          challenge:"nom",
      }
    },
    methods:{

      start: function(){
        const self=this;
      request({ //on suppose qu'on a déjà l'id
           method: 'get',
           url: '/api/getChallenge/'+this.navigation.state.params.challengeId,
          }).then(function(response){

          self.challenge = response.data.name; 
        })
      },
      //tout sauf l'image puis uid et cid dans l'url et que l'image
      challengeValidation: function() {

          var bodyFormData = new FormData();
          bodyFormData.append('challengeId', this.navigation.state.params.challengeId);
          bodyFormData.append('commentary',this.review);
          bodyFormData.append('picture',""); //à changer
          request({
            method: 'post',
            url : '/api/completeMyChallenge',
            data : bodyFormData,
            headers: {'Content-Type':'multipart/form-data'}
        }).then(function(response){
          }).catch(function(error){
            console.log(error)
          })
        },
    },
    mounted: function() {
      this.start();
    }
  }
</script>
