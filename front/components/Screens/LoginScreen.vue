<template>
    <view class="real-container">
      <view class ="container">
        <text class="title" :style="styles.myred">CONSVID la TÊTE</text>
        
        <text class="text-container">Pseudo : </text>
        <text-input class="input-container" placeholder="username" v-model="username"/>
        <text class="text-container">Mot de passe :</text>
        <text-input class="input-container" placeholder="password" secure-text-entry v-model="password"/>
        <text class="login-fail" v-if="loginFail">Mauvaise combinaison</text>
        <text class="login-fail" v-else></text>
        <view class="login-container"> 
          <touchable-opacity :on-press="login">
           <text  class="login-btn">Se connecter</text>
          </touchable-opacity>
          <touchable-opacity :on-press="goToRegister">
           <text  class="login-btn" >S'enregistrer</text>
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
        styles: Stylesheet,
        loading: false,
        loginFail: false,
    }
  },
  methods: {
    login () {
        this.loading = true
        var bodyFormData = new FormData();
        bodyFormData.append('username', this.username);
        bodyFormData.append('password', this.password);
        const self = this;
        request({
        method: 'post',
        url: '/login',
        data: bodyFormData,
        headers: {'Content-Type': 'multipart/form-data' }
        }).then(function(response){
           //console.log(response)
          if(response != undefined && response.status == 200){
              self.navigation.navigate("Défis")
              self.loading = false
              self.loginFail = false
          } else{
          }
        }).catch(function(error){
          self.loading = false
          self.loginFail = true
          console.log(error)

        })
    },
    goToRegister() {
      this.navigation.navigate("RegisterScreen")
    }
  },
  mounted: function() {
      this.username = 'Julien Le Vig'
      this.password = '1234'
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

.login-fail{
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
