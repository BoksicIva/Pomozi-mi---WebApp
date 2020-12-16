import axios from "axios";
const {REACT_APP_BASE_URL} = process.env;

export default axios.create({
  withCredentials: true,
  baseURL: REACT_APP_BASE_URL,
  headers: {
    "Content-type": "application/json"
  }
});
