import axios from "axios";

 export const API = axios.create({
  baseURL: 'http://192.168.1.17:8080', //Set your IPV4 here
  withCredentials: true,
})

API.interceptors.response.use(function (response) {
    return response
  }, function (error) {
    if(!error.response){
      console.log("No response from server")
      return
    }
    if (error.response.data.status === 401) {
      console.log('access denied, disconnecting user')
      //todo redirect the user to the login page with the router
    }
    return Promise.reject(error)
  })

  export default API