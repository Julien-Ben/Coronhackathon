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

            <view class="container">
                <text class= "heading">{{nbAchieved}} Défis relevés parmi {{nbAll}} défis disponibles!</text>
                
                <view class="cat-container" v-for="(category,index) in res" :key="index">
                    
                    <text  v-if="category.all!=0" class = "heading">{{category.catName}} : {{category.comp}} / {{category.all}}</text>

                    <view class="icon-container" v-for="challenge in category.challenges" :key="challenge.id">
                            <touchable-opacity :on-press="() => showChallengeDescription(challenge)">
                                <image class="badge"
                                      :source="{uri: baseURL + '/static/image/png?path=' + category.logo}"
                                    />
                            </touchable-opacity>
                            <text class="description"> Badge obtenu pour la réalisation du challenge : {{challenge.name}}</text>

                    </view>
                </view>
            </view>

        </scroll-view>
    </view>
</template>

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
            return{
                baseURL: baseURL,
                cats: [],
                res: [],
                username: 'John Doe',
                nbAchieved: 0,
                nbAll: 0,
                loading: true

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
                        if(self.cats.length - 1 == index){
                            self.loading = false
                        }
                        if(index < self.cats.length){
                            self.getCatInfo(index + 1)
                        }
                    }).catch(function(err){
                        console.log(err)
                    })
                })

            }


        },

        mounted: function(){
            this.fetch()
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
/* .loadingCase{
    background-color: #B0B0B0;
    width: 100px;
    height: 100px;
    position: relative;
} */
/* .loading{
    position: absolute;
    left:0;
    bottom:0;

} */
/* .header {
  height : 20%;
  justify-content: center;
  align-items: center;
  background-color: #3d9d84;
  color:white;
  flex-direction: row;
  justify-content: space-around;
}
.title {
  font-size: 40px;
  font-weight: 200;
  color: white;
} */

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
  /* background-color: #b2ebcc; */
  flex: 1;
}
.cat-container{
    align-items: center;
    justify-content: center;
}
.icon-container{
    justify-content: center;
    align-items: center;
    flex-direction: row;
}
.badge {
    height: 30;
    width: 30;
}
.top{
    /* align-items: center; */
    /* background-color: #b2ebcc; */
}

.description{
    font-weight: 100;
    width: 70%;
}



.heading {
  font-size: 20;
  font-weight: bold;
  color: black;
  margin: 20;
}

.info-container{
    margin-top: 10;
    width: 100%;
    border-radius: 10;
    align-items: center;
    flex-direction: row;
}

</style>
