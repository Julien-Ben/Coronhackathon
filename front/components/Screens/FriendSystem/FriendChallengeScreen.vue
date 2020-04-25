<template>
<scroll-view>
  <view class ="container">
    <!--titre de l'onglet + détails du challenge [title, category, description]    -->
    <view class ="header">
      <text class="title">{{challenge.title}}</text>
      <view class="sep"></view>
    </view>
    <view class="main">
      <view class="image-container">
          <image class='image'
            :source="{uri: baseURL + '/static/image/jpg?path=' + challenge.image}"
            :style=styles.separatorColor
          />
        </view>

      <view class="desc-container">
        <text class="desc">{{challenge.desc}}</text>
      </view>

      <view class="sep"></view>

      <view class ="user-container">
        <text class="user-title">Réalisé par {{user.username}}</text> 
        <view class="comment-container" v-if="user.comment.length > 0">
          <text class="comment-title">{{user.username}} a commenté:</text>
          <view class="comment-border" :style=styles.separatorColor>
          <text class="comment" >{{user.comment}}</text>
          </view>
        </view>
        <view class="image-container" v-if="user.image != null && user.image.length > 0">
          <image class='image'
            :source="{uri: baseURL + '/static/image/jpg?path=' + user.image}"
            :style=styles.separatorColor
          />
        </view>
        <image/>
      </view>
    </view>
</view>
</scroll-view>
</template>

<style>

.container {
  background-color: white;
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

.main{
  width: 100%;
  justify-content: center;
  align-items: center;
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
    max-width: 70%;
    justify-content: center;
    align-items: center;
    padding: 10;
    padding-top:30;
    margin-bottom:30;
}
.desc{
    font-size:20;
    color : #4e4e4e;
    /* text-align: center; */
}

.user-container{
  margin-top:30;
  justify-content: center;
  align-items: center;
}

.user-title{
  font-size:25;
  color : #3d9d84;
  width:80%;
  text-align: center;
  margin-bottom: 20;
}

.comment-container{
  min-width:80%;
}

.comment-title{
  font-size:20;
  color : #3d9d84;
  width:80%;
  text-align: center;
  margin-bottom: 10;
}

.comment-border{
  min-width:80%;
  border-radius:5;
  border-width:1;
  padding: 10;
}

.comment{
  font-size:18;
  color : #4e4e4e; 
}

</style>


<script>
import {request, baseURL} from '../../../api.js';
import styles from "../../../palette.js"

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
         baseURL:baseURL,
          styles: styles,
          user:{
            username: this.navigation.state.params.username,
            userId: this.navigation.state.params.userId,
            comment: "",
            image:"",
          },
          challenge:{
            id: this.navigation.state.params.challengeId,
            title:"",
            image:"",
            desc:"",
          }
      }
    },
    methods:{

      //get the challenge name
      getChallenge(){
        const self=this;
        request({ //on suppose qu'on a déjà l'id
            method: 'get',
            url: '/api/getChallenge/'+this.navigation.state.params.challengeId,
            }).then(function(response){
              self.challenge.title = response.data.name; 
              self.challenge.desc = response.data.description;
              self.challenge.image = response.data.imgPath;
          })
      },

      getDataCompleted(){
        const self = this
        request({ 
          method: 'GET',
          url: "/api/getDataCompleted/"+ this.navigation.state.params.userId + "/" +self.challenge.id, 
        }).then(function(response){
          if(response.data !== []){
            self.user.comment = response.data[0]
            if(response.data.length > 1){
              self.user.image= response.data[1]
            }
          }
        }).catch(function(error){
          console.log('Failure...')
          console.log(error.response.data.status);

        })
      },
    },
    mounted: function() {
      this.getChallenge();
      this.getDataCompleted();
      console.log(this.user)
      console.log(this.user.image)
    }
  }
</script>
