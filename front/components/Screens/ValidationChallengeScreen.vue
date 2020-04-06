<template>
  <view class ="container">
    <!--titre de l'onglet + détails du challenge [title, category, description]    -->

    <view class = "topbar">
      <text class="heading">Validation du défi :</text>
      <text class = "challenge-title">{{currentChallenge.name}}</text>
      <text class = "challenge-cat">{{currentChallenge.category}}</text>
      <text class = "challenge-desc">{{currentChallenge.description}}</text>
    </view>

    <!--<text-input class = "challenge-title" v-model= "response.name"/>
    <text-input class = "challenge-cat" v-model= "response.category"/>
    <text-input class = "challenge-desc" v-model= "response.description"/>-->

    <!-- Box "rajouter une description" + on la récupère pour la poster  -->
    <text class="box">Ajouter une description : </text>
     <text-input class = "insideBox" placeholder="Votre description" v-model= "review"/>
    <!-- On confirme la validation + on poste les détails de la validation   -->
    <button :on-press="challengeValidation"
    title="Confirmer"
    color="#841584"

    accessibility-label="Confirmer la validation du défi"/>
</view>
</template>

<style>
.topbar {
  height : 15%;
  justify-content: center;
  align-items: center;
  background-color: #FFC107;
}

.container {
  background-color: #ffecb3;
  flex: 1;
}

.heading {
  font-size: 20px;
  font-weight: bold;
  color: #212121;
}

.insideBox {
  background-color: cornsilk;
  height: 20%;
  text-align: left;
}
</style>


<script>
import { Alert } from 'react-native';
import {API} from '../../api.js';
import React from 'react';
import {Text} from 'react-native';

export default {
    props: {
        navigation: {
          type: Object
      }
  },
    data:   function() {
      return {
          imagePickerDisplay: "../../assets/images/challengescreen/virus-lab-scientist-biology-cell-medical-512.png",
          defiTitle : "Nom du défi",
          response:{
            commentary:"",
            picture:null,
          },
          review:"",
          currentChallenge:{
            name:"nom",
            category:"cuisine",
            description:"croissant",
          },
      }
    },
    methods:{

      start: function(){
        const self=this;
      API({ //on suppose qu'on a déjà l'id
           method: 'get',
           url: '/api/getChallenge/'+this.navigation.state.params.challengeId,
          }).then(function(response){

          self.currentChallenge.name = response.data.name; //contacter backend pour la structure de response
          self.currentChallenge.category = response.data.category;
          self.currentChallenge.description = response.data.description;
        })
      },
      //tout sauf l'image puis uid et cid dans l'url et que l'image
      challengeValidation: function() {

          var bodyFormData = new FormData();
          bodyFormData.append('challengeId', this.navigation.state.params.challengeId);
          bodyFormData.append('commentary',this.review);
          bodyFormData.append('picture',""); //à changer
          console.log(bodyFormData)
          API({
            method: 'post',
            url : '/api/completeMyChallenge',
            data : bodyFormData,
            headers: {'Content-Type':'multipart/form-data'}
        }).then(function(response){
             console.log(response)
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
