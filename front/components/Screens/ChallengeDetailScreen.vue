<template>
  <view class="container">

    <scroll-view class="scroll">
      <view class="header">
        <view class="logo-container">
        <image class='logo'
          :source="{uri: baseURL + '/static/image/jpg?path=resources/white_logo_entraide.png'}"
        />
        </view>
        <text class="title">{{titre}}</text>
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
        <touchable-opacity class="container-btn" :on-press="accessChallengeValidation">
        <text class="validation-btn">Valider ce challenge</text>
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
            <text class="comment">{{commentaire}}</text>
          </view>
          <view class="comment-container">
            <text class="comment">Vous savez, moi je ne crois pas qu'il y ait de bonnes 
              ou de mauvaises situations. Moi si je devais résumer ma vie, aujourd'hui, 
              avec vous, je dirais que c'est d'abord des rencontres, des gens qui m'ont 
              tendu la main, peut-être à un moment où je ne pouvais pas, où j'étais seul 
              chez moi, et c'est assez curieux de se dire que les hasards, les rencontres 
              forgent une destinée, parce que quand on a le goût de la chose, quand on a 
              le goût de la chose bien faite, le beau geste, parfois on ne trouve pas 
              l'interlocuteur en face, je dirais le miroir qui vous aide à avancer ; alors 
              ce n'est pas mon cas comme je le disais là, puisque moi au contraire j'ai pu 
              et je dis merci à la vie, je lui dis merci, je chante la vie, je danse la vie, 
              je ne suis qu'amour, et finalement quand beaucoup de gens aujourd'hui me disent : 
              Mais comment fais-tu pour avoir cette humanité ? Et bah je leur réponds très 
              simplement, je leur dis : c'est ce goût de l'amour, ce goût donc qui m'a 
              poussé, aujourd'hui, à entreprendre une construction mécanique mais demain, 
              qui sait, peut-être, simplement à me mettre au service de la communauté, à 
              faire le don, le don de soi...
            </text>
          </view>
          <view class="comment-container">
            <text class="comment">Sa va?</text>
          </view>
        </view>
      </view>
    </scroll-view>
    <view class="footer"></view>
  </view>
</template>


<style>
.container {
  background-color: #b2ebcc;
  align-items: center;
  justify-content: center;
  /* flex: 1; */
  width:100%;
  height:100%;
}

.scroll {
  width:100%;
  
}

.header{
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  width: 80%;
  height:90;
  background-color: #3d9d84;
  border-bottom-right-radius: 15;
  /* margin-bottom:30; */
}

.logo-container{
  width:30%;
  align-items: center;
}

.logo{
    /* transform: rotate(30deg); */
    width : 80;
    height : 80;
}

.title{
    color : white ; /*#1d3060*/
    font-size:30;
    font-weight: 200;
    width: 70%;
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
    border-top-width: 2; 
    border-color:#3d9d84;
    max-width: 80%;
    justify-content: center;
    align-items: center;
    padding: 10;
    padding-top:30;
    margin-bottom:30;
}
.desc{
    color : #2c3c74;
    font-size:20;
    /* text-align: center; */
}

.container-btn {
  border-radius: 25;
  background-color:#3d9d84;
  margin-bottom:30;
}
.validation-btn{
    font-size: 20;
    color: white;
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

.sep{
 width:80%;
 border-top-width: 2;
 border-color:#3d9d84;
}

.comment-title-container{
  width:100%;
  padding:10;
  align-items: center;
}

.comment-title{
  font-size:25;
  color : #2c3c74;
  width:80%;
}

.comments-container{
  width:100%;
  align-items: center;
}
.comments{
  width:90%;
  padding-bottom:100;
  align-items: center;
}

.comment-container{
  border-bottom-width: 1;
  border-color:#3d9d84;
  width:100%;
  padding:20;
  
}

.comment{
  color :#1d3060;
  font-size: 20;

}

.footer{
  background-color:white;
  height:10%;
  width:100%;
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
          console.log(response.data)
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
