<template>
    <view class="real-container">
        <view class="container">
            
            <view class ="main">
                <view class="categories">
                        <view class="category" v-for="(category, index) in categories" :key="index">
                            <touchable-opacity class="single-element-container" :on-press="() => goToCategory(category.id)">
                                <image class="icon"
                                  :source="{uri: baseURL + '/static/image/png?path=' + category.logo}"
                                />
                                <text class="name">{{category.name}}</text>
                            </touchable-opacity>
                        </view>
                </view>
            </view>
        </view>
  </view>
</template>

<style>

/* .header{
    width: 100%;
    height: 10%;
    justify-content: center;
    align-items: center;
    background-color: #3d9d84;
} */

.title{
    font-size: 40;
    font-weight: 200;
    /* margin-left: 50; */
}

.icon {
  height:80;
  width:80;
}

.main {
    justify-content: center;
    align-items: center;
    /* height:90%; */
    height:100%;
}
.categories{
    padding-top: 50;
    /* padding-bottom: 50px; */
    flex-direction: row;
    justify-content: space-around;
    align-items: stretch;
    align-content: center;
    flex-wrap: wrap;
    max-width: 90%;
    height:90%;
    /* background-color: grey; */
}
.single-element-container{
    justify-content: center;
    margin: 10px;
    margin-bottom:70;
    width: 80;
    /* background-color: grey; */
}

.name{
    justify-content: center;
    align-items: center;
    text-align: center;
    font-weight:200;
    font-size:15;
    color:#2c3c74;
}

.container {
    width:100%;
}



</style>

<script>
import {request, baseURL} from '../../api.js';
import { Alert } from 'react-native';
import axios from "axios";
import styles from "../../palette.js"

export default {
    props: {
      navigation: {
        type: Object
      }
    },
  data: function() {
    return {
        styles: styles,
        baseURL:baseURL,
        categories: [
            // {
            //     name: "Hardcodé 1",
            // },
            // {
            //     name: "Hardcodé 2",
            // },
        ]
    }
  },
  methods: {
    fetch () {
     console.log("Fetch !")
        const self = this;
       request({
        method: 'get',
        url: '/api/allCategories',
        }).then(function(response){
            self.categories = response.data;
            console.log('Fetched all categories')

        }).catch(function(error){
            console.log(error)
        })
    },
    setLogos(){

    },
    goToCategory(catId) {
      this.navigation.navigate("ChallengesByCategory", {categoryId:catId})
    },

  },
  mounted: function() {
      this.fetch();
  }
};
</script>
