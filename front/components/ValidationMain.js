import * as React from 'react';
import { ActivityIndicator, Image, View, TextInput,TouchableOpacity, Text ,StyleSheet} from 'react-native';
import * as ImagePicker from 'expo-image-picker';
import Constants from 'expo-constants';
import * as Permissions from 'expo-permissions';
import {request, baseURL} from '../api.js';
import palette from "../palette.js"

export default class ImagePickerExample extends React.Component {
  state = {
    previousImage:null,
    image: null,
    commentary: "", // 'comment' is a keyword /!\
    animating:false, // loading icon animation
  };

  render() {
    let { image } = this.state;
    const animating = this.state.animating; //cannot call this.state in View 
    const previousComment = this.state.commentary;
    return (
      <View style={styles.main}>
        {/* text input for th euser's comment*/}
        <View style={styles.descContainer}>
          <View style={styles.inputContainer}>
            <TextInput style={styles.inputText}  
              placeholder="Commente ce que tu as réalisé !" multiline
              defaultValue={previousComment}
              placeholderTextColor="grey"
              textAlignVertical="top"
              onChangeText={this.handleComment}
            />
          </View>
        </View>
        {/* The image picker*/}
        <View style={styles.pickerContainer}>
          <View style={styles.picker}>
            <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center'}}>
              <TouchableOpacity style={[styles.containerBtn,palette.defaultPrimaryColor]}  onPress={this._pickImage}> 
                <Text style={[styles.validationBtn, palette.textPrimaryColor]}>Ajouter une photo</Text>
              </TouchableOpacity>
              {image && <Image source={{ uri: image.uri }} style={[styles.image,palette.separatorColor]} />}
            </View>
          </View>
        </View>
        {/* Submit button*/}
        <TouchableOpacity style={[palette.defaultPrimaryColor, styles.containerBtn]} onPress={this._submitValidation}> 
          <Text  style={[palette.textPrimaryColor, styles.validationBtn]}>Valider !</Text>
        </TouchableOpacity>
        <ActivityIndicator
               animating = {animating}
               color = '#3d9d84'
               size = "large"
        />
      </View>
    );
  }

  handleComment = (text) => {
    this.setState({ commentary: text })
 }

  //When the screen is mounted ask for permission
  componentDidMount() {
    this.isAlreadyCompleted();
    this.getPermissionAsync();
  }

  isAlreadyCompleted = async () =>{
    const self = this
    request({ 
      method: 'GET',
      url: "/api/getDataCompleted/"+this.props.challengeId, 
    }).then(function(response){
      console.log('Success !')
      console.log(response.data);
      if(response.data !== []){
        self.setState({commentary: response.data[0]})
        if(response.data.length > 1){
          self.setState({image:{uri: baseURL + '/static/image/jpg?path=' +response.data[1]}})
        }
      }
    }).catch(function(error){
      console.log('Failure...')
      console.log(error.response.data.status);

    })
  };

  getPermissionAsync = async () => {
      const { status } = await Permissions.askAsync(Permissions.CAMERA_ROLL);
      if (status !== 'granted') {
        alert('Nous avons besoin de ta permission pour ajouter une image!');
      }
  };

  //Choose image in gallery, display it and store its uri + base64 representation
  _pickImage = async () => {
    const { status } = await Permissions.askAsync(Permissions.CAMERA_ROLL);
      if (status !== 'granted') {
        alert('Nous avons besoin de ta permission pour ajouter une image!');
        return
      }
    try {
      let result = await ImagePicker.launchImageLibraryAsync({
        mediaTypes: ImagePicker.MediaTypeOptions.Images,
        allowsEditing: true,
        aspect: [2, 2],
        quality: 0.5,
        base64:true,
      });
      if (!result.cancelled) {
        this.setState({ image: result });
      }
    } catch (E) {
      console.log(E);
    }
  };

  //Post the comment and the image base64 
  _submitValidation = async () => {
    this.setState({animating: true })
    let bodyFormData = new FormData();
    bodyFormData.append('challengeId', this.props.challengeId);
    bodyFormData.append('commentary',this.state.commentary);
    console.log(this.state.commentary);
    if(this.state.image != null){
      let uriParts = this.state.image.uri.split('.');
      let fileType = uriParts[uriParts.length - 1];
      fileType = ['jpg', 'png'].includes(fileType) ? fileType : 'jpg';
      
      bodyFormData.append('imgBase64',this.state.image.base64);
      bodyFormData.append('imgFormat', fileType); 
    } else{
      bodyFormData.append('imgBase64',"");
      bodyFormData.append('imgFormat', ""); 
    }
    const self = this;
    request({
      method: 'post',
      url : '/api/completeMyChallenge',
      data : bodyFormData,
      headers: {'Content-Type':'multipart/form-data'}
    }).then(function(response){
      self.setState({animating: false})
      
    }).catch(function(error){
      self.setState({animating: false})
      console.log("Error while posting validation!")
      console.log(error.response.data.status)
      console.log(error.response.data.error)
      console.log(error.response.data.message)
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