import http from "../http-common";

class LoginService {
    login(formdata) {
  
      return http.post("/upload", formdata, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        onUploadProgress,
      });
    }
  
  }
  
  export default new LoginService();