import * as React from 'react';
import { Button, Image, View } from 'react-native';
import * as ImagePicker from 'expo-image-picker';
import Constants from 'expo-constants';
import * as Permissions from 'expo-permissions';
import {request} from '../api.js';

export default class ImagePickerExample extends React.Component {
  state = {
    image: null,
  };

  render() {
    let { image } = this.state;

    return (
      <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center'}}>
        <Button title="Ajouter une photo" onPress={this._pickImage}/>
        {image && <Image source={{ uri: image }} style={{ width: 200, height: 200 }} />}
        <Button title="Valider !" onPress={this._uploadImage}/>
      </View>
      
    );
  }

  componentDidMount() {
    this.getPermissionAsync();
  }

  getPermissionAsync = async () => {
    if (Constants.platform.ios) {
      const { status } = await Permissions.askAsync(Permissions.CAMERA_ROLL);
      if (status !== 'granted') {
        alert('Nous avons besoin de ta permission pour ajouter une image!');
      }
    }
  };

  _pickImage = async () => {
    try {
      let result = await ImagePicker.launchImageLibraryAsync({
        mediaTypes: ImagePicker.MediaTypeOptions.Images,
        allowsEditing: true,
        aspect: [4, 3],
        quality: 1,
      });
      if (!result.cancelled) {
        this.setState({ image: result.uri });
      }
      // console.log(result);
    } catch (E) {
      console.log(E);
    }
  };

 _uploadImage = async () => {
    if(this.state.image == null){
      console.log(this.props.challengeId)
      return
    }
    // console.log(this.state.image)
    let uriParts = this.state.image.split('.');
    let fileType = uriParts[uriParts.length - 1];
    fileType = ['jpg', 'png'].includes(fileType) ? fileType : 'jpg';
    let formData = new FormData();

    formData.append('photo', {
      uri: this.state.image,
      name: `photo.${fileType}`,
      type: `image/${fileType}`,
    });

    request({
      method: 'post',
      url : '/api/uploadCompletedImage/'+fileType+'/'+this.props.challengeId,
      data : bodyFormData,
      headers: {'Content-Type':'multipart/form-data'}
    }).then(function(response){
      console.log("Got here then!")
      console.log(response)

      this._submitValidation(response.path) // ??
    }).catch(function(error){
      console.log("Got here catch!")
      console.log(error.response)
    })

    // let options = {
    //   method: 'POST',
    //   body: formData,
    //   headers: {
    //     Accept: 'application/json',
    //     'Content-Type': 'multipart/form-data',
    //   },
    // };

    // return fetch(apiUrl, options);
    // }

  }

  _submitValidation = async (imagePath) => {
    
    let bodyFormData = new FormData();
    bodyFormData.append('challengeId', this.props.challengeId);
    bodyFormData.append('commentary',"Commentaire constructif"); //this.review
    bodyFormData.append('picture',""); //this.state.image

    request({
      method: 'post',
      url : '/api/completeMyChallenge',
      data : bodyFormData,
      headers: {'Content-Type':'multipart/form-data'}
    }).then(function(response){
      console.log("Got here then!")
      console.log(response)
    }).catch(function(error){
      console.log("Got here catch!")
      console.log(error.response)
    })
  }
}