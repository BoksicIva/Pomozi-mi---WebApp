import http from "../http-common";

class RequestService {


    sendRequest(req){
      return http.post("/requests", req, {});
    }

    getRequests(radius){
      return http.get("/requests/active?radius=" + radius + "&page=0&size=400000", {});
    }

    sendExecution(id){
      return http.patch("/requests/pickForExecution/" + id, {});
    }
    deleteRequest(id){
        return http.delete("/requests/" + id, {});
    }
  
  }
  
  export default new RequestService();