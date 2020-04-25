<template>
<scroll-view>
  <view class="container">

    <!-- Demande d'ami en cour -->
    <view class ="request-container" v-if="existFriendRequests">
      <text class="title" :style=styles.titlePrimaryColor>Demandes d'amis</text>
      <view class= "request-for" v-for="(request, index) in friendRequests" :key="index">
        <view class="request-elem" :style=styles.lightPrimaryColor>
          <text class="request-user" :style=styles.primaryTextColor>{{request.username}}</text>
          <view class="request-answer">
            <touchable-opacity class="request-accept" :on-press="(index) => answerRequest(request.id, true)">
              <text :style=styles.secondaryTextColor>Accepter</text>
            </touchable-opacity>
            <touchable-opacity class="request-decline" :on-press="(index) => declineRequest(request.id)">
              <text :style=styles.secondaryTextColor>Refuser</text>
            </touchable-opacity>
          </view>
          <!-- <AntDesign name="check" size="28" color="green"/> 
          <Entypo name="cross" size="28" color="red"/> -->
        </view>
      </view> 
      <view class="sep" :style=styles.separatorColor></view>
    </view>

    <!--  Rechercher des amis -->
    <view class = "search-container">
      <text class="title" :style=styles.titlePrimaryColor>Chercher des amis</text>
      <view class="input-container">
        <text-input class = "input-text" 
        placeholder="Envoie une invitation à un ami !" 
        v-model= "searchPseudo"
        placeholderTextColor="grey"
        autoCapitalize="none"
        />
      </view>
      <view class="search-feedback">
        <text class="search-feedback-text" v-if="searchSuccess" :style=styles.titlePrimaryColor>Invitation envoyée</text>
        <text class="search-feedback-text" v-if="searchUnknown" :style=styles.titlePrimaryColor>Ce pseudo n'existe pas ...</text>
        <text class="search-feedback-text" v-if="searchAlreadyFriend" :style=styles.titlePrimaryColor>Vous êtes déjà amis !</text>
        <text class="search-feedback-text" v-if="searchAlreadyAsked" :style=styles.titlePrimaryColor>Une invitation a déjà été envoyée</text>
      </view>
      <touchable-opacity class="search-btn-container" :style=styles.defaultPrimaryColor :on-press="sendFriendRequest">
        <text class="search-btn-text" :style=styles.textPrimaryColor>Demander en ami</text>
      </touchable-opacity>

      <view v-if="loading" class="loadingIndicatorContainer">
            <activity-indicator v-if="loading" size="large" color="black"/>
        </view>
      <view class="sep" :style=styles.separatorColor></view>
    </view>

    <!-- Liste des amis -->
    <view class ="friends-container" v-if="existsFriends" >
      <view class="friends-title-container">
        <text class="title" :style=styles.titlePrimaryColor>Liste des amis</text>
      </view>
      <view class= "friends-for" :style=styles.separatorColor>
        <view  v-for="(friend, index) in friends" :key="index">
          <touchable-opacity :on-press="() => goToFriendProfile(friend.userId)">
          <view class="friend-elem" :style=styles.lightPrimaryColor>
            <text class="friend-name" :style=styles.titlePrimaryColor>{{friend.username}}</text>
            <text class="friend-score" :style=styles.titlePrimaryColor>{{friend.nbChall}}</text>
          </view>
          </touchable-opacity>
        </view>
      </view>
    </view>
  
  </view>
</scroll-view>
</template>


<script>
import {request} from '../../../api.js';
import React from 'react';
import { Alert } from 'react-native';
import styles from "../../../palette.js"
import { Entypo, AntDesign } from '@expo/vector-icons';

export default {
    props: {
        navigation: {
          type: Object
      }
  },
  components: {
    
  },
    data:   function() {
      return {
        styles:styles,
        searchPseudo: '',
        existFriendRequests: false,
        existsFriends: false,
        loading:false,
        friendRequests: [],
        friends :[],
        searchSuccess:false,
        searchUnknown:false,
        searchAlreadyFriend:false,
        searchAlreadyAsked:false,
      }
    },
    methods:{
      getFriendRequests(){ 
        const self = this;
       request({
        method: 'get',
        url: '/api/getFriendRequests'
        }).then(function(response){
          self.friendRequests = response.data
          self.existFriendRequests = self.friendRequests.length > 0
        })
      },

      declineRequest(userId){
        Alert.alert("Es-tu sûr de vouloir refuser cette demande d'ami ?",'',[
                    {text: 'Annuler'},
                    {text:'Refuser', onPress:() => this.answerRequest(userId,false)}
                ])
      },

      answerRequest(userId, accept){
        //Remove the friend request from the screen
        this.friendRequests = this.friendRequests.filter(function(request) {
          return request.id != userId;
        });
        if(this.friendRequests.length == 0){
          this.existFriendRequests = false
        }
        var bodyFormData = new FormData();
        bodyFormData.append('userId', userId);
        const self = this;
        // POST the answer here
        request({
        method: 'post',
        url: '/api/' + (accept ? 'accept' : 'refuse') +'FriendRequest', //post to the right url depending on the answer
        data: bodyFormData,
        headers: {'Content-Type': 'multipart/form-data' }
        }).then(function(response){
          if(accept){
            self.getFriends();  
          } 
        }).catch(function(error){
          console.log(error) 
        })
      },

      sendFriendRequest(){
        this.loading = true
        var bodyFormData = new FormData();
        bodyFormData.append('username', this.searchPseudo);
        const self = this;
        // POST the answer here
        request({
        method: 'post',
        url: '/api/friendRequest',
        data: bodyFormData,
        headers: {'Content-Type': 'multipart/form-data' }
        }).then(function(response){
          console.log(response.data)
          self.loading = false
          self.searchPseudo = ""

          self.searchUnknown = false;
          self.searchAlreadyFriend = response.data.includes("already friends")
          self.searchAlreadyAsked = response.data.includes("already asked")
          self.searchSuccess= !(self.searchAlreadyFriend || self.searchAlreadyAsked);
          
          //Only refresh if the searched username is in the friend requests or the friend list
          if(self.friendRequests.filter(request => request.username === self.searchPseudo)){
            self.getFriendRequests();
            }
          if(self.friends.filter(friend => friend.username === self.searchPseudo)){
            self.getFriends();
          }

        }).catch(function(error){
          self.searchUnknown = true;
          self.searchSuccess= false;
          self.searchAlreadyFriend = false;
          self.searchAlreadyAsked = false;
          self.loading = false
          self.searchPseudo = ""

        })
      },

      getFriends(){
        const self = this;
        request({
        method: 'get',
        url: '/api/getFriends'
        }).then(function(response){
          self.friends = response.data
          self.existsFriends = self.friends.length > 0
        })
      },

      goToFriendProfile(friendId){
        this.navigation.navigate("FriendProfile",{friendId:friendId});
      }

    },
    mounted: function() {
      this.getFriends()
      this.getFriendRequests();
    }
  }
</script>


<style>
.container {
  background-color: white;
}
.request-container{
  width:100%;
 align-items: center;
}
.title {
  font-size: 25px;
  font-weight: bold;
  text-align: center;
  padding:20;
}

.request-for{
  width:80%;
}

.request-elem{
  justify-content: space-between;
  text-align: center;
  flex-direction: row;
  margin-bottom:10;
  /* border-color: #3d9d84; */
  /* border-width:2; */
  padding:10;
  
}

.request-answer{
  flex-direction: row;
}

.request-accept{
  color:white;
}
.request-decline{
  padding-left:10;
}

.sep{
  width: 60%;
  align-items: center;
  border-bottom-width: 2;
  margin: 20;
  margin-bottom:0;

}

.search-container{
  width:100%;
  justify-content: center;
  align-items: center;
}

.search-feedback{
  margin-bottom:20;
}

.search-feedback-text{
  font-size: 18px;
  font-weight: bold;
  text-align: center;
  
}

.input-container{
  width:80%;
  min-height:30;
  border-color: #3d9d84;
  border-radius:5;
  border-width: 1;
  padding:10;
  margin-bottom:20;
}

.input-text {
  font-size: 18;
  text-align: left;
}

.search-btn-container{
  border-radius: 25;
  margin-bottom: 10;
}

.search-btn-text{
  font-size: 20;
    padding-top: 10;
    padding-bottom: 10;
    padding-left: 20;
    padding-right: 20;
}

.friends-container{
  width:100%;
  align-items: center;
}

.friends-for{
  width:85%;
  border-width: 2;
  border-radius:5;
  border-color:lightgrey;
  justify-content: center;
  align-items: center;
  padding:10;
  padding-top:20;
}

.friend-elem{
  flex-direction: row;
  justify-content: space-between;
  min-width: 100%;
  max-width:100%;
  border-radius:5;
  margin-bottom:10;
  padding:10;
}

.friend-score{
  padding-left:10;
}


</style>
