<template>
  <view class="container">

    <scroll-view>

      <image
        class='hexagone'
        :source="{uri: baseURL + '/static/image/jpg?path=' + imageCategory}"
      />
      <image
        class='licorne'
        :source="{uri: baseURL + '/static/image/jpg?path=' + imageChallenge}"
      />

      <text class="titre">{{titre}}</text>
      <text class="description">{{description}}</text>

      <button :on-press="accessChallengeValidation"
        title="Valider ce challenge"
        color="#841584"
        accessibility-label="Accéder à la validation du défi"
      />

      <view class="commentaires" v-for="(commentaire, index) in commentaires" :key="index">
        <text>{{commentaire}}</text>
      </view>
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

.hexagone{
    transform: rotate(30deg);
    width : 80;
    height : 80;
}
.licorne{
    width : 250;
    height : 150;
}
.titre{
    color : #1d3060;
    font-size:25;
}

.description{
    color : #1d3060;
    font-size:20;
}

.commentaires{
  color :#1d3060;
}

</style>


<script>
import {request, baseURL} from '../../api.js';
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
          console.log(response.data);
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
