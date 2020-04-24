<template>
<scroll-view>
  <view class ="container">
    <!--titre de l'onglet + détails du challenge [title, category, description]    -->
    <view class ="header">
      <text class="title">{{challenge}}</text>
      <view class="sep"></view>
    </view>
    <!-- Call react-native object consiting of the input, image picker and validation button -->
    <ValidationMain :challengeId="challengeId" />
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

</style>


<script>
import {request} from '../../api.js';
import ValidationMain from '../ValidationMain';
import styles from "../../palette.js"

export default {
    props: {
        navigation: {
          type: Object
      }
  },
  components: {
    ValidationMain
  },
    data:   function() {
      return {
          challengeId: this.navigation.state.params.challengeId,
          styles: styles,
          challenge:"",
      }
    },
    methods:{

      //get the challenge name
      start: function(){
        const self=this;
      request({ //on suppose qu'on a déjà l'id
           method: 'get',
           url: '/api/getChallenge/'+this.navigation.state.params.challengeId,
          }).then(function(response){
            self.challenge = response.data.name; 
        })
      },
    },
    mounted: function() {
      this.start();
    }
  }
</script>
