import * as React from 'react';
import { Button, Image, View, TextInput,TouchableOpacity, Text ,StyleSheet} from 'react-native';
import * as ImagePicker from 'expo-image-picker';
import Constants from 'expo-constants';
import * as Permissions from 'expo-permissions';
import {request} from '../api.js';
import palette from "../palette.js"

export default class ImagePickerExample extends React.Component {
  state = {
    image: null,
  };

  render() {
    let { image } = this.state;

    return (
      <View style={styles.main}>
        <View style={styles.descContainer}>
          <Text style={styles.inputTitle}></Text>
          <View style={styles.inputContainer}>
            <TextInput style={styles.inputText}  
              placeholder="Commente ce que tu as réalisé !" multiline
              placeholderTextColor="grey"
              textAlignVertical="top"
            />
          </View>
        </View>
        <View style={styles.pickerContainer}>
          <View style={styles.picker}>
            <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center'}}>
              <TouchableOpacity style={[styles.containerBtn,palette.defaultPrimaryColor]}  onPress={this._pickImage}> 
                <Text style={[styles.validationBtn, palette.textPrimaryColor]}>Ajouter une photo</Text>
              </TouchableOpacity>
              {image && <Image source={{ uri: image }} style={[styles.image,palette.separatorColor]} />}
            </View>
          </View>
        </View>
        <TouchableOpacity style={[palette.defaultPrimaryColor, styles.containerBtn]} onPress={this._uploadImage}> 
          <Text  style={[palette.textPrimaryColor, styles.validationBtn]}>Valider !</Text>
        </TouchableOpacity>
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


const styles = StyleSheet.create({
    
  main :{
    alignItems: 'center',
    height:'80%',
  },

  descContainer :{
    alignItems: 'center',
    width:'100%',
    // minHeight:200,
    // maxHeight:200,
    margin: 20,
    paddingBottom: 10,
  },

  inputTitle:{
    fontSize:20,
    color : '#3d9d84',
  },

  inputContainer :{
    minWidth:'90%',
    minHeight:30,
    maxHeight:170,
    borderColor: '#3d9d84',
    borderRadius:5,
    borderWidth: 1,
    padding:10,
  },

  inputText :{
    fontSize: 18,
    textAlign: 'left',
  },

  pickerContainer  :{
    minHeight: 100,
    width:'100%',
    fontSize: 20,
    marginBottom: 20,
  },

  image :{
    borderWidth:3,
    borderRadius:5,
    width: 200, 
    height: 200,
  },

  containerBtn :{
    borderRadius: 25,
    marginBottom:30,
  },

  validationBtn :{
      fontSize: 20,
      paddingTop: 10,
      paddingBottom: 10,
      paddingLeft: 20,
      paddingRight: 20,
  },
});