<template>
  <view class="container">

    <scroll-view>

      <image
        class='hexagone'
        :source="{uri: 'http://192.168.1.117:8080/api/image?' + imageCategory}"
      />
    <image
        class='licorne'
        :source="{uri: 'http://192.168.1.117:8080/api/image?' + imageChallenge}"
      />

      <text class="titre">{{titre}}</text>

      <text class="description">{{description}}</text>

      <button :on-press="accessChallengeValidation"
        title="Valider ce challenge"
        color="#841584"

        accessibility-label="Accéder à la validation du défi"/>

      <view class="commentaires" v-for="(commentaire, index) in commentaires" :key="index">
        <text>{{commentaire}}</text></view>

      </scroll-view>

  </view>
</template>


<style>
.container {
  background-color: #65D498;
  align-items: center;
  justify-content: center;
  flex: 1;
}
.fleche{
    transform: rotate(180deg);
    width : 70;
    height : 70;
    position :relative;
    top : -160px;
    left : -170px;


}

.hexagone{
    transform: rotate(30deg);
    width : 80;
    height : 80;
    position :relative;
    top : -10px;
    left : -130px;

}
.licorne{
    width : 250;
    height : 150;
    position :relative;
    top : -140px;
}
.titre{
    color : #1d3060;
    font-size:25;
    position :relative;
    top : -370px;
    left : 50px;
}



.description{
    color : #1d3060;
    font-size:20;
    top : -150px;
    left : -20px;
}

.commentaires{
  color :#1d3060;
}

</style>


<script>
import {API} from '../../api.js';
import { Alert } from 'react-native';
import axios from "axios";

export default {
  props: {
      navigation: {
        type: Object
      }
    },
  data: function() {
    return {
        titre:'',
        description:'',
        categoryId:'',
        imageCategory:'',
        imageChallenge:'',
        commentaires:[]
    }
  },
  methods: {

    accessChallengeValidation : function(){
        this.navigation.navigate("Validation", {challengeId:this.navigation.state.params.challengeId})
    },

    fetch : function() {
      const self = this;
       API({
        method: 'get',
        url: '/api/getChallenge/'+this.navigation.state.params.challengeId
        }).then(function(response){
          console.log(response.data);
          self.titre = response.data.name;
          self.description = response.data.description;
          self.categoryId =response.data.categoryId;
          //self.imageChallenge= response.data.???;
          self.getComments();
        }).catch(function(error){
          console.log(error);
        })
    },

    getComments : function(){
      const self = this;
       API({
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
       API({
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
