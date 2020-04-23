<template>
  <view class ="container">
    <!--titre de l'onglet + détails du challenge [title, category, description]    -->
    <text class="title">Validation du défi :</text>

    <!--<text class="subTitle">{{this.defiTitle}}</text>--> <!-- récupérer le nom du challenge -->

    <!--<text-input class = "challenge-title" v-model= "response.name"/>
    <text-input class = "challenge-cat" v-model= "response.category"/>
    <text-input class = "challenge-desc" v-model= "response.description"/>-->
    <text class = "challenge-title">{{currentChallenge.name}}</text>
    <text class = "challenge-cat">{{currentChallenge.category}}</text>
    <text class = "challenge-desc">{{currentChallenge.description}}</text>


   <!--  Boutons pour importer ou prendre une photo + on les récupère pour les poster  -->
   <button
    :on-press="() => pickImage()"
    title="Importer une image depuis l'appareil"
    />
    <button
    :on-press="() => takePhoto()"
    title="Prendre une photo avec l'appareil"
    />

    <!-- On affiche l'image qui a été sélectionnée   -->
    <!--<image :source="require('/home/colbois/Documents/Lauzhack/Coronhackathon/front/assets/images/phone_black_192x192.png')"/> -->

    
    
    <!-- Box "rajouter une description" + on la récupère pour la poster  -->
    <text class="box">Ajouter une description : </text>
     <text-input class = "insideBox" v-model= "review"/>
    <!-- On confirme la validation + on poste les détails de la validation   -->
    <button :on-press="challengeValidation"
    title="Confirmer"
    color="#841584"

    accessibility-label="Confirmer la validation du défi"/>
</view>
</template>


<script>
import * as ImagePicker from 'expo-image-picker';
import * as Permissions from 'expo-permissions';
import { Alert } from 'react-native';
import {API} from '../../api.js';
import React from 'react';
import {Text} from 'react-native';

export default {
    data:   function() {
      return {
          defiTitle : "Nom du défi",
          uploading: false,
          image:null,
          imageBack:null,
          response:{
            userId:1,
            challengeId:1,
            commentary:"",
            picture:null,
          },
          review:"blabla",
          currentChallenge:{
            name:"nom",
            category:"cuisine",
            description:"croissant",
          },
          //challengeId:null,
          //userId:null,
      }
    },
    methods:{
      
      start: async function(){
        const self=this;
      API({ //on suppose qu'on a déjà l'id
           method: 'get',
           url: '/api/getChallenge?id=3', //ATTENTION vérifier si l'API n'a pas changé et mettre getChallenge/3 à la place
          }).then(function(response){
          console.log(response)
          self.currentChallenge.name = response.data.name; //contacter backend pour la structure de response
          self.currentChallenge.category = response.data.category;
          self.currentChallenge.description = response.data.description;
        })
      },
      //tout sauf l'image puis uid et cid dans l'url et que l'image
      challengeValidation1: async function() {
          var bodyFormData = new FormData();
          bodyFormData.append('userId',this.userId);
          bodyFormData.append('challengeId', this.challengeId);
          bodyFormData.append('commentary',this.review);
          bodyFormData.append('picture',""); //à changer
          //valider le défi?
          API({
            method: 'post',
            url : '/api/completeChallenge',
            data : bodyFormData,
            headers: {'Content-Type':'multipart/form-data-'}
          }).then(function(reponse){
            //try{
             console.log(response)
            //}catch(e){
            // Alert.alert('Oupsi');
            //}

          })
        },
      challengeValidation2: async function() {
          this.image;
          //valider le défi?
          API({
            method: 'post',
            url : '/api/uploadImage/68-3', //user id et challenge id su
            data : bodyFormData,
            headers: {'Content-Type':'image/jpeg'}
          }).then(function(reponse){
            //try{
             console.log(response)
            //}catch(e){
            // Alert.alert('Oupsi');
            //}

          })
        },
        challengeValidation: async function() {
          challengeValidation1();
          challengeValidation2();
        },
        //ca marchera pas faut afficher la photo avant de la passer au serveur
        // getImageBack: async function() {
        //   const self=this;
        // API({ //on suppose qu'on a déjà l'id
        //    method: 'get',
        //    url: '/api/getChallenge?id=3', //changer la fonction!!
        //   }).then(function(response){
        //   console.log(response)
        //   self.imageBack = response; //contacter backend pour la structure de response
        // })
        // },
        pickImage :  async function () {
            const {
              status: cameraRollPerm
            } = await Permissions.askAsync(Permissions.CAMERA_ROLL);

            // only if user allows permission to camera roll
            if (cameraRollPerm === 'granted') {
              let pickerResult = await ImagePicker.launchImageLibraryAsync({
                allowsEditing: true,
                aspect: [4, 3],
              });
              this.handleImagePicked(pickerResult);
          }
        },
        takePhoto : async function () {
           const {
             status: cameraPerm
           } = await Permissions.askAsync(Permissions.CAMERA);

           const {
             status: cameraRollPerm
           } = await Permissions.askAsync(Permissions.CAMERA_ROLL);

           // only if user allows permission to camera AND camera roll
           if (cameraPerm === 'granted' && cameraRollPerm === 'granted') {
             let pickerResult = await ImagePicker.launchCameraAsync({
               allowsEditing: true,
               aspect: [4, 3],
             });
             let uploadResponse, uploadResult;
             this.uploading = true;
             try {
                 if (!pickerResult.cancelled) {
                     //Send POST to server
                    uploadResponse = await uploadImageAsync(pickerResult.uri);
                    // uploadResult = await uploadResponse.json();
                    // this.image: uploadResult.location
                }
               } catch (e) {
                  console.log({ uploadResponse });
                 // console.log({ uploadResult });
                 console.log({ e });
                 Alert.alert('Upload failed, sorry :(');

               } finally {
                   this.uploading = false;
               }
             }
         },
        handleImagePicked : async function(pickerResult) {
            let uploadResponse, uploadResult;

            try {
             // this.uploading = true;

              if (!pickerResult.cancelled) {
                uploadResponse = await uploadImageAsync(pickerResult.uri);
                 uploadResult = await uploadResponse.json();
                 image = uploadResult.location
              }
            } catch (e) {
              console.log({ uploadResponse });
              console.log({ uploadResult });
              console.log({ e });
              Alert.alert('Upload failed, sorry :(');
            } finally {
             // this.uploading= false;
            }
          },
          // convertImage : async function(callback) {
          //   var 

          // },
        // postPhoto: async function() {
        //   var bodyFormData = new FormData();
        //   bodyFormData.append('image',this.image);
        //   API({
        //     method: 'post',
        //     url : '/postImage', //à vérifier avec la team backend
        //     data : bodyFormData,
        //     headers: {'Content-Type':'multipart/form-data-'}
        //   }).then(function(reponse)){
        //     console.log(reponse)
        //   }
        // }, 
        // addReview: async function() {
        //   var bodyFormData = new FormData();
        //   bodyFormData.append('review',this.review);
        //   API({
        //     method: 'post',
        //     url : '/postReview', //à discuter avec la team backend
        //     data : bodyFormData,
        //     headers: {'Content-Type':'multipart/form-data-'}
        //   }).then(function(reponse)){
        //     console.log(response)
        //   }
        // }, 
        // challengeValidation: async function() {
        //   var bodyFormData = new FormData();
        //   bodyFormData.append('userId',this.userId);
        //   bodyFormData.append('challengeId', this.challengeId);
        //   bodyFormData.append('commentary',this.review);
        //   bodyFormData.append('picture', this.image);
        //   //valider le défi?
        //   API({
        //     method: 'post',
        //     url : '/api/completeChallenge', //à discuter avec la team backend
        //     data : bodyFormData,
        //     headers: {'Content-Type':'multipart/form-data-'}
        //   }).then(function(reponse)){
        //     //try{
        //      console.log(response)
        //     //}catch(e){
        //     // Alert.alert('Oupsi');
        //     //}

        //   }
        // },
        // takePhoto : async function () {
        //    const {
        //      status: cameraPerm
        //    } = await Permissions.askAsync(Permissions.CAMERA);

        //    const {
        //      status: cameraRollPerm
        //    } = await Permissions.askAsync(Permissions.CAMERA_ROLL);

        //    // only if user allows permission to camera AND camera roll
        //    if (cameraPerm === 'granted' && cameraRollPerm === 'granted') {
        //      let pickerResult = await ImagePicker.launchCameraAsync({
        //        allowsEditing: true,
        //        aspect: [4, 3],
        //      });
        //      let uploadResponse, uploadResult;
        //      this.uploading = true;
        //      try {
        //          if (!pickerResult.cancelled) {
        //              //Send POST to server
        //             uploadResponse = await uploadImageAsync(pickerResult.uri);
        //             // uploadResult = await uploadResponse.json();
        //             // this.image: uploadResult.location
        //         }
        //        } catch (e) {
        //           console.log({ uploadResponse });
        //          // console.log({ uploadResult });
        //          console.log({ e });
        //          Alert.alert('Upload failed, sorry :(');

        //        } finally {
        //            this.uploading = false;
        //        }
        //      }
        //  },
        //  handleImagePicked : async function(pickerResult) {
        //     let uploadResponse, uploadResult;

        //     try {
        //       this.uploading = true;

        //       if (!pickerResult.cancelled) {
        //         uploadResponse = await uploadImageAsync(pickerResult.uri);
        //         // uploadResult = await uploadResponse.json();
        //         // image = uploadResult.location
        //       }
        //     } catch (e) {
        //       console.log({ uploadResponse });
        //       console.log({ uploadResult });
        //       console.log({ e });
        //       Alert.alert('Upload failed, sorry :(');
        //     } finally {
        //       this.uploading= false;
        //     }
        //   }

    },

    mounted: function(){
       this.start();
    }
}


async function uploadImageAsync(uri){
    Alert.alert('uploading')
    return 0;
  // let apiUrl = 'https://file-upload-example-backend-dkhqoilqqn.now.sh/upload';
  //
  // // Note:
  // // Uncomment this if you want to experiment with local server
  // //
  // // if (Constants.isDevice) {
  // //   apiUrl = `https://your-ngrok-subdomain.ngrok.io/upload`;
  // // } else {
  // //   apiUrl = `http://localhost:3000/upload`
  // // }
  //
  // let uriParts = uri.split('.');
  // let fileType = uriParts[uriParts.length - 1];
  //
  // let formData = new FormData();
  // formData.append('photo', {
  //   uri,
  //   name: `photo.${fileType}`,
  //   type: `image/${fileType}`,
  // });
  //
  // let options = {
  //   method: 'POST',
  //   body: formData,
  //   headers: {
  //     Accept: 'application/json',
  //     'Content-Type': 'multipart/form-data',
  //   },
  // };
  //
  // return fetch(apiUrl, options);
}
</script>
<!--><style>
.container {
  align-items: center;
  justify-content: center;
  flex: 1;
}
.heading {
  font-size: 30px;
  font-weight: bold;
  color: darkolivegreen;
  margin: 20px;
}
</style><-->
