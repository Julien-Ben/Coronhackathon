<!-- PROFILE SCREEN -->
<template>
        <scroll-view class="top">
                <view class="name-container">
                    <text class="title">{{username}}</text>
                </view>
            <view class="container">
                <text class= "heading">{{nbAchieved}} Défis relevés parmi {{nbAll}} défis disponibles!</text>
                <view class="cat-container" v-for="(cat,index) in res" :key="index">
                    <text  v-if="cat.all!=0" class = "heading">{{cat.catName}} : {{cat.comp}} / {{cat.all}}</text>
                    <view class="icon-container" v-for="challenge in cat.challenges" :key="challenge.id">
                            <image class = "badge" :source="require('../../assets/images/profilescreen/coeur_fonce.png')"/>
                            <text class="desc"> Badge obtenu pour la réalisation du challenge : {{challenge.name}} : {{challenge.description}}</text>
                    </view>
                </view>
            </view>

        </scroll-view>
</template>

<script>
import {request} from '../../api.js';
import { Alert } from 'react-native';
import axios from "axios";
    export default {
        data: function() {
            return{
                cats: [],
                res: [],
                username: 'John Doe',
                nbAchieved: 0,
                nbAll: 0,

            };
        },
        methods: {
            onPressButton () {
                alert(this.username + '\'s photo')
            },

            showChall (challenge) {
                alert(this.username + ' a complété le défi : ' + challenge.name , challenge.description)
            },


            fetch : function(){
                const self = this
                // console.log("fetching the data")
                request({
                    method: 'GET',
                    url: '/api/userProfile'
                }).then(function(ansName){
                    self.username = ansName.data.username
                    request({
                        method: 'GET',
                        url: '/api/allCategories'
                    }).then(function(categories){
                        console.log(categories)
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
                            self.res.push({
                                catName: self.cats[index].name,
                                comp: nbComplet,
                                all : allChallofCat,
                                challenges:completed.data,
                                pic:self.cats[index].logo
                            })
                        }
                        if(index < self.cats.length){
                            self.getCatInfo(index + 1)
                        } else {
                            // console.log("done extracting the info");
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
.cat-container{
    align-items: center;
    justify-content: center;
}
.name-container{
    align-items: center;
    justify-content: center;
    background-color: orange;
}
.real-container {
    align-items: center;
    justify-content: center;
    flex:1;
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

.title{

    font-size: 70;
    font-weight: 200;
    margin-bottom: 100;
    align-items: center;

}
.desc{
    font-weight: 100;
    width: 70%;
}

.root{
    align-items: center;
}
.container {
  align-items: center;
  justify-content: center;
  flex: 1;
}
.heading {
  font-size: 20;
  font-weight: bold;
  color: #980740;
  margin: 20;
}

.container2 {
    align-items: center;
    justify-content: center;
    flex: 1;
}
.info-container{
    margin-top: 10;
    width: 100%;
    border-radius: 10;
    align-items: center;
    flex-direction: row;
}

</style>
