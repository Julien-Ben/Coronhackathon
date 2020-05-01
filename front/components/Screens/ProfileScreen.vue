<!-- PROFILE SCREEN -->
<template>
    <view class ="container">
        <view v-if="loading" class="loadingIndicatorContainer">
            <activity-indicator v-if="loading" size="large" color="black"/>
        </view>
        <scroll-view v-if="!loading" class="top">
            <view class ="header">
                <text class="title">{{username}}</text>
                <view class="sep"></view>
            </view>
            <view >
                <text class= "heading">{{nbAchieved}} Défis relevés parmi {{nbAll}} défis disponibles!</text>
                <view class="cat-container" v-for="(category,index) in res" :key="index">
                    <text  v-if="category.comp!=0" class = "heading">{{category.catName}} : {{category.comp}} / {{category.all}}</text>
                    <view class="icon-container" v-if="category.challenges.length > 0" >
                        <!-- v-for="challenge in category.challenges" :key="challenge.id" -->
                            <touchable-opacity class="badge-container" :on-press="() => showChallengeDescription(category.challenges[0])">
                                <image class="badge"
                                    :source="{uri: baseURL + '/static/image/png?path=' + category.logo}"
                                />
                                <text class="description"> Badge obtenu pour la réalisation du challenge : {{category.challenges[0].name}}</text>
                            </touchable-opacity>
                    </view>
                </view>
                
                
                
            </view>
            <view class="completed-container">
                    <view class="sep"></view>
                    <text class="completed-title" :style=styles.titlePrimaryColor>Défis réalisés:</text>
                    <ChallengeCompletedList :challenges="completedChallenges" :goToChallenge="goToChallenge" />
                </view>
        </scroll-view>
    </view>
</template>


<script>

import {request, baseURL} from '../../api.js';
import { Alert } from 'react-native';
import ChallengeCompletedList from '../ChallengeCompletedList';
import styles from '../../palette';
import axios from "axios";
    export default {
        props: {
            navigation: {
                type: Object
            }
        },
        components:{
            ChallengeCompletedList: ChallengeCompletedList,
        },
        data: function() {
            return{
                styles:styles,
                baseURL: baseURL,
                cats: [],
                res: [],
                username: '',
                nbAchieved: 0,
                nbAll: 0,
                loading: true,
                completedChallenges:[],
            };
        },
        methods: {
            showChallengeDescription: function(challenge) {
                Alert.alert('' + challenge.name, '' + challenge.description, 
                [
                    {text: 'Ok'},
                    {text:'En savoir plus!', onPress:() => this.navigation.navigate("ChallengeDetail", {challengeId:challenge.id})}
                ])
            },

            fetch : function(){
                const self = this
                request({
                    method: 'GET',
                    url: '/api/userProfile'
                }).then(function(ansName){
                    self.username = ansName.data.username
                    request({
                        method: 'GET',
                        url: '/api/allCategories'
                    }).then(function(categories){
                        self.cats = categories.data
                        self.getCatInfo(0);

                    }).catch(function(err){
                        console.log("could not fetch the categories"+ err)
                    })
                }).catch(function(err){
                    console.log("Impossible to catch the name"+ err)
                })
            },

            getCatInfo: function(index){
                const self = this
                request({ 
                    method: 'GET',
                    url: "/api/getChallengeByCategoryName/"+self.cats[index].name
                }).then(function(allChallengeName){
                    const allChallofCat = allChallengeName.data.length
                    self.nbAll += allChallofCat
                    request({
                        method: 'GET',
                        url: "/api/getMyCompletedByCat/"+self.cats[index].id
                    }).then(function(completed){
                        const nbComplet = completed.data.length
                        self.nbAchieved += nbComplet
                        
                        if(self.cats[index] != undefined){
                            if(self.cats != undefined && index == self.cats.length){
                                console.log("everything loading at index : " + index)
                                laoded = true;
                             }
                            self.res.push({
                                catName: self.cats[index].name,
                                comp: nbComplet,
                                all : allChallofCat,
                                challenges:completed.data,
                                logo:self.cats[index].logo
                            })
                        }
                        if(index < self.cats.length - 1){
                            self.getCatInfo(index + 1)
                        } else{
                            self.loading = false
                        }
                    }).catch(function(err){
                        console.log(err)
                    })
                })
            },

            getCompletedChallenge(){
                const self = this
                request({ 
                    method: 'GET',
                    url: "/api/getMyCompleted",
                }).then(function(response){
                    // console.log(response)
                    self.completedChallenges = response.data
                }).catch(function(error){
                    console.log(error.response.error.status)
                })
            },
            
            goToChallenge(challenge){
                this.navigation.navigate("ChallengeDetail", {challengeId:challenge.id})
            }
        },

        mounted: function(){
            this.fetch()
            this.getCompletedChallenge()
        }


    }
</script>


<style>


.loadingIndicatorContainer {
    position: absolute;
    top: 50%;
    right: 35%;
    width: 100;
    height: 100;
    align-items: center;
    flex:1;
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
.container {
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 100%;
}
.icon-container{
    justify-content: center;
    align-items: center;
    flex-direction: row;
}

.badge-container{
    flex-direction: row;
    max-width: 90%;
}
.badge {
    height: 50;
    width: 50;
}

.description{
    /* font-weight: 100; */
    width: 70%;
}

.heading {

  font-size: 20;
  font-weight: bold;
  margin: 20;
  text-align: center;
}

.info-container{
    margin-top: 10;
    width: 100%;
    border-radius: 10;
    align-items: center;
    flex-direction: row;
}

.completed-container{
    justify-content: center;
    align-items: center;
}

.completed-title{

  font-size: 25;
  margin: 20;
  text-align: center;
}

</style>
