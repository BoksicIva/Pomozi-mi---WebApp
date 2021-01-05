import http from "../http-common";

class RatingService {
  rateUser(id, rating) {
    return http.post("./ratings/" + id, rating);
  }
}

export default new RatingService();
