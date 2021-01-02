import http from "../http-common";

class LoginService {
    login(formdata) {
  
      return http.post("/auth/login", formdata, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        
      });
    }

    getCSRF() {
      return http.get("/getCsrf", {});
    }

    register(formdata) {
  
      return http.post("/auth/registration", formdata, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        
      });
    }

    notPer(){
      return http.post("/notPermitted", {});
    }

    logout(){
      return http.post("../logout", {});
    }

    getUsers(){
      return http.get("/users" , {});
    }

    getSortedUsers(condition){
      return http.get("/users?sort=" + condition + ",asc" , {});
    }

    sendRequest(req){
      return http.post("/requests", req, {});
    }

    getRequests(radius){
      return http.get("/requests/active?radius=" + radius, {});
    }
  
  }
  
  export default new LoginService();