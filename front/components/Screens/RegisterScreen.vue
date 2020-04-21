<template>
    <view class="real-container">
      <view class ="container">
        <text class="title" :style="styles.myred">CONSVID la TÊTE</text>
        
        <text class="text-container">Choisis ton pseudo : </text>
        <text-input class="input-container" placeholder="pseudo" v-model="username"/>
        <text class="text-container">Mot de passe :</text>
        <text-input class="input-container" placeholder="T<f~M4*@@e)Zq8~" secure-text-entry v-model="password"/>
        <text class="text-container">Vérifie ton mot de passe :</text>
        <text-input class="input-container" placeholder="T<f~M4*@@e)Zq8~" secure-text-entry v-model="verification"/>
        <text class="verification-fail" v-if="password != verification">Les mots de passes sont différents!</text>
        <text class="login-fail" v-else></text>
        <view class="login-container">
          <touchable-opacity :on-press="register">
           <text  class="login-btn" >Créer un compte</text>
           </touchable-opacity>
           <touchable-opacity :on-press="goToLogin">
           <text  class="login-btn">Retour</text>
           </touchable-opacity>
        </view>
      </view>
       <view class="loading-container">
            <activity-indicator v-if="loading" size="large" color="black"/>
        </view>
  </view>
</template>

<script>
import {request, baseURL} from '../../api.js';
import { Alert } from 'react-native';
import Stylesheet from '../../palette.js'

export default {
    props: {
      navigation: {
        type: Object
      }
    },
  data: function() {
    return {
        username:'',
        password:'',
        verification:'',
        styles: Stylesheet,
        loading: false,
    }
  },
  methods: {
    register () {
        this.loading = true
        var bodyFormData = new FormData();
        bodyFormData.append('username', this.username);
        bodyFormData.append('hashPwd', this.password);
        bodyFormData.append('hashPwd2', this.verification);
        const self = this;
        request({
        method: 'post',
        url: '/register', //A modifier ?
        data: bodyFormData,
        headers: {'Content-Type': 'multipart/form-data' }
        }).then(function(response){
           console.log(response)
          if(response != undefined && response.status == 200){
              self.navigation.navigate("Défis")
              self.loading = false
          } else{
          }
        }).catch(function(error){
          self.loading = false
          console.log(error)

        })
    },
    goToLogin() {
      this.navigation.navigate("Home")
    }
  },
  mounted: function() {
  }
};
</script>

<style>
.real-container {
  align-items: center;
  justify-content: center;
  flex:1;
}
.container {
  width:80%;
}

.title{
  font-size: 40;
  font-weight: 100;
  margin-bottom: 100;
}

.loading-container {
    width: 100;
    height: 100;
    align-items: center;
    justify-content: center;
    margin-top:20;
}

.text-container{
  font-size: 22;
}
.input-container {
  border-style: solid;
  border-color: #FFFFFF;
  border-bottom-color: #888888;
  border-width: 2;
  font-size: 22;
  padding: 10;
  margin-bottom: 20;
}

.verification-fail{
  width:100%;
  text-align: center;
  min-height:30;
  font-size:20;
  color:rgb(248, 69, 108);
}

.login-container{
  margin-top: 10;
  width: 100%;
  border-radius: 10;
  /* justify-content: space-between; */
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.login-btn {
  padding: 20;
  margin-bottom:20;
  font-size: 22;
  background-color: #EEAAEE;
  color:white;
}



</style>
