E<template>
  <view class="container">
    <view class ="topbar">
      <view>
      <text class="heading">Défis</text>
      <text>Bienvenue sur la liste des défis</text>
      </view>
      <!-- <touchable-opacity class = "corona-touchable" :on-press="() => goToCorona()">
        <image class = "corona-icon" :source="require('../../assets/images/challengescreen/virus-lab-scientist-biology-cell-medical-512.png')"/>
      </touchable-opacity> -->
    </view>
    <scroll-view class = "myScrollView">
      <view class = "element-border" v-for="(challenge, index) in challenges" :key="index">
        <touchable-opacity class = "element-container" :on-press="() => goToChallenge(challenge)">
          <view>
          <text class = "challenge-title">{{challenge.title}}</text>
          <text class = "challenge-desc">{{challenge.description}}</text>
          </view>
          <view>
          <image class = "challenge-icon" :source="require('../../assets/images/challengescreen/phone_black_192x192.png')"/>
          </view>
        </touchable-opacity>
      </view>
    </scroll-view>
  </view>
</template>
<style>

.topbar {
  height : 15%;
  justify-content: center;
  align-items: center;
  background-color: #FFC107;
  flex-direction: row;
  justify-content: space-around;
}

.container {
  background-color: #ffecb3;
  flex: 1;
}

.corona-icon {
  width: 40;
  height: 40;
}

.corona-touchable {
  text-align: right;
}

.heading {
  font-size: 30px;
  font-weight: bold;
  color: #212121;
}

.element-border {
  border-bottom-width: 1;
  border-color: gray;
  width: 100%;
  padding: 15;
}

.element-container {
  width: 90%;
  flex-direction: row;
}

.challenge-title {
  font-size: 20;
  color: #9E9E9E;
}

.challenge-description {
  font-size : 10;
}

.challenge-icon {
  width: 40;
  height: 40;
  border-radius: 25;
}
</style>

<script>
import {API} from '../../api.js';
import React from 'react';
import {Text} from 'react-native';
export default {
  props: {
      navigation: {
        type: Object
      }
    },
  data: function() {
    return {
        challenges: [
           {
            id: 1,
            title : 'Prendre des nouvelles de ses voisins',
            description : "En ce moment, certains n'ont pas la chance d'avoir des proches près d'eux ...",
            image : 'https://www.materialui.co/materialIcons/communication/phone_black_192x192.png'
          },
           {
            id: 2,
            title : 'Faire une mousse au chocolat',
            description : "Des oeufs, une tablette de chocolat dessert? Pourquoi ne pas essayer de reproduire chez vous ...",
            image : 'https://icons-for-free.com/iconfiles/png/512/cake+48px-131987943060752100.png'
          },
           {
            id: 3,
            title : 'Un troisième challenge',
            description : "Lorem ipsum je n'ai pas d'idée, de toute façons personne ne lira cette description. PERSONNE LIS TES PAVEEEES",
            image : ''
          },
          {
            id: 1,
            title : 'Prendre des nouvelles de ses voisins',
            description : "En ce moment, certains n'ont pas la chance d'avoir des proches près d'eux ...",
            image : 'https://www.materialui.co/materialIcons/communication/phone_black_192x192.png'
          },
           {
            id: 2,
            title : 'Faire une mousse au chocolat',
            description : "Des oeufs, une tablette de chocolat dessert? Pourquoi ne pas essayer de reproduire chez vous ...",
            image : 'https://icons-for-free.com/iconfiles/png/512/cake+48px-131987943060752100.png'
          },
           {
            id: 3,
            title : 'Un troisième challenge',
            description : "Lorem ipsum je n'ai pas d'idée, de toute façons personne ne lira cette description. PERSONNE LIS TES PAVEEEES",
            image : ''
          },
          {
            id: 1,
            title : 'Prendre des nouvelles de ses voisins',
            description : "En ce moment, certains n'ont pas la chance d'avoir des proches près d'eux ...",
            image : 'https://www.materialui.co/materialIcons/communication/phone_black_192x192.png'
          },
           {
            id: 2,
            title : 'Faire une mousse au chocolat',
            description : "Des oeufs, une tablette de chocolat dessert? Pourquoi ne pas essayer de reproduire chez vous ...",
            image : 'https://icons-for-free.com/iconfiles/png/512/cake+48px-131987943060752100.png'
          },
           {
            id: 3,
            title : 'Un troisième challenge',
            description : "Lorem ipsum je n'ai pas d'idée, de toute façons personne ne lira cette description. PERSONNE LIS TES PAVEEEES",
            image : ''
          },
          {
            id: 1,
            title : 'Prendre des nouvelles de ses voisins',
            description : "En ce moment, certains n'ont pas la chance d'avoir des proches près d'eux ...",
            image : 'https://www.materialui.co/materialIcons/communication/phone_black_192x192.png'
          },
           {
            id: 2,
            title : 'Faire une mousse au chocolat',
            description : "Des oeufs, une tablette de chocolat dessert? Pourquoi ne pas essayer de reproduire chez vous ...",
            image : 'https://icons-for-free.com/iconfiles/png/512/cake+48px-131987943060752100.png'
          },
           {
            id: 3,
            title : 'Un troisième challenge',
            description : "Lorem ipsum je n'ai pas d'idée, de toute façons personne ne lira cette description. PERSONNE LIS TES PAVEEEES",
            image : ''
          },
          {
            id: 1,
            title : 'Prendre des nouvelles de ses voisins',
            description : "En ce moment, certains n'ont pas la chance d'avoir des proches près d'eux ...",
            image : 'https://www.materialui.co/materialIcons/communication/phone_black_192x192.png'
          },
           {
            id: 2,
            title : 'Faire une mousse au chocolat',
            description : "Des oeufs, une tablette de chocolat dessert? Pourquoi ne pas essayer de reproduire chez vous ...",
            image : 'https://icons-for-free.com/iconfiles/png/512/cake+48px-131987943060752100.png'
          },
           {
            id: 3,
            title : 'Un troisième challenge',
            description : "Lorem ipsum je n'ai pas d'idée, de toute façons personne ne lira cette description. PERSONNE LIS TES PAVEEEES",
            image : ''
          },
          {
            id: 1,
            title : 'Prendre des nouvelles de ses voisins',
            description : "En ce moment, certains n'ont pas la chance d'avoir des proches près d'eux ...",
            image : 'https://www.materialui.co/materialIcons/communication/phone_black_192x192.png'
          },
           {
            id: 2,
            title : 'Faire une mousse au chocolat',
            description : "Des oeufs, une tablette de chocolat dessert? Pourquoi ne pas essayer de reproduire chez vous ...",
            image : 'https://icons-for-free.com/iconfiles/png/512/cake+48px-131987943060752100.png'
          },
           {
            id: 3,
            title : 'Un troisième challenge',
            description : "Lorem ipsum je n'ai pas d'idée, de toute façons personne ne lira cette description. PERSONNE LIS TES PAVEEEES",
            image : ''
          },
          {
            id: 1,
            title : 'Prendre des nouvelles de ses voisins',
            description : "En ce moment, certains n'ont pas la chance d'avoir des proches près d'eux ...",
            image : 'https://www.materialui.co/materialIcons/communication/phone_black_192x192.png'
          },
           {
            id: 2,
            title : 'Faire une mousse au chocolat',
            description : "Des oeufs, une tablette de chocolat dessert? Pourquoi ne pas essayer de reproduire chez vous ...",
            image : 'https://icons-for-free.com/iconfiles/png/512/cake+48px-131987943060752100.png'
          },
           {
            id: 3,
            title : 'Un troisième challenge',
            description : "Lorem ipsum je n'ai pas d'idée, de toute façons personne ne lira cette description. PERSONNE LIS TES PAVEEEES",
            image : ''
          },
          {
            id: 1,
            title : 'Prendre des nouvelles de ses voisins',
            description : "En ce moment, certains n'ont pas la chance d'avoir des proches près d'eux ...",
            image : 'https://www.materialui.co/materialIcons/communication/phone_black_192x192.png'
          },
           {
            id: 2,
            title : 'Faire une mousse au chocolat',
            description : "Des oeufs, une tablette de chocolat dessert? Pourquoi ne pas essayer de reproduire chez vous ...",
            image : 'https://icons-for-free.com/iconfiles/png/512/cake+48px-131987943060752100.png'
          },
           {
            id: 3,
            title : 'Un troisième challenge',
            description : "Lorem ipsum je n'ai pas d'idée, de toute façons personne ne lira cette description. PERSONNE LIS TES PAVEEEES",
            image : ''
          },
        ]
    }
  },

  methods: {
    renderList : function(item) {
      return(
        <View>
        <Text>{item.item.title}</Text>
        <Text>{item.item.description}</Text>
        </View>
      )
    },

    fetch : function() {
      const self = this;
       API({
        method: 'get',
        url: '/api/allChallenges'
        }).then(function(response){
          //console.log(response)
          self.challenges = response.data
        })
    },

    goToChallenge : function(challenge) {
      this.navigation.navigate("ChallengeDetail", {challengeId:challenge.id})
    },

    goToCorona : function() {
      this.navigation.navigate("Covid19")
    }
  },
  mounted: function() {
    this.fetch();
  }
};
</script>
