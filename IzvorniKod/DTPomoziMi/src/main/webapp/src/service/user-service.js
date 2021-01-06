import http from "../http-common";
import jwt_decode from "jwt-decode";

const getCookie = (cname) => {
  var name = cname + "=";
  var decodedCookie = decodeURIComponent(document.cookie);
  var ca = decodedCookie.split(";");
  for (var i = 0; i < ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) === " ") {
      c = c.substring(1);
    }
    if (c.indexOf(name) === 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
};

class UserContext {
    constructor(id, roles) {
        this.id = id; // string id
        this.roles = roles; // string array 
    }
}

class UserService {
  getUserContext() {
    const token = getCookie("AUTH_COOKIE");
    if(token === undefined || token === null || token === "") return null;
    const decoded = jwt_decode(token);
    if(decoded === undefined || decoded === null) return null;
    return new UserContext(decoded.id, decoded.roles);
  }

  getUser(id) {
    return http.get("./users/" + id, {});
  }

  getUserStatistics(id) {
    return http.get("./users/statistics/" + id, {});
  }

  getAuthored(id) {
    return http.get("./requests/authored/" + id, {});
  }

  blockUser(id, en) {
    return http.post("./users/blockUnblock/" + id +"?enabled=" + en, {});
  }

  blockRequest(id) {
    return http.patch("./requests/blockUnblock/" + id + "?enabled=false", {});
  }

  unblockRequest(id) {
    return http.patch("./requests/blockUnblock/" + id + "?enabled=true", {});
  }

  deleteRequest(id) {
    return http.delete("./requests/" + id, {});
  }

  updateRequest(id, updatedRequest) {
    return http.put("./requests/" + id, updatedRequest);
  }

  getChainOfTrust(id) {
    return http.get("./users/chainOfTrust/" + id, {});
  }

  getNotifications(id) {
    return http.get("/notifications/user/" + id , {});
  }

  setReadNotifs(id){
    return http.patch("/notifications/" + id, {});
  }

}

export default new UserService();

/* ALL LINKS 
"self": {
            "href": "http://localhost:8080/api/requests/7"
        },
        "create": {
            "href": "http://localhost:8080/api/requests/",
            "type": "post"
        },
        "one": {
            "href": "http://localhost:8080/api/requests/7",
            "type": "get"
        },
        "update": {
            "href": "http://localhost:8080/api/requests/7",
            "type": "put"
        },
        "delete": {
            "href": "http://localhost:8080/api/requests/7",
            "type": "delete"
        },
        "block": {
            "href": "http://localhost:8080/api/requests/block/7",
            "type": "patch"
        },
        "pick": {
            "href": "http://localhost:8080/api/requests/pickForExecution/7",
            "type": "patch"
        },
        "markExecuted": {
            "href": "http://localhost:8080/api/requests/markExecuted/7",
            "type": "patch"
        },
        "backoff": {
            "href": "http://localhost:8080/api/requests/backOff/7",
            "type": "patch"
        },
        "active": {
            "href": "http://localhost:8080/api/requests/active{?radius}",
            "type": "get",
            "templated": true
        },
        "authored": {
            "href": "http://localhost:8080/api/requests/authored/7",
            "type": "get"
        }*/
