import axios from "axios";
const {REACT_APP_BASE_URL} = process.env;

export default axios.create({
  withCredentials: true,
  xsrfCookieName: 'X-CSRF-COOKIE', // default,
  xsrfHeaderName: 'X-CSRF-TOKEN',
  baseURL: REACT_APP_BASE_URL,
  headers: {
    "Content-type": "application/json"
  }
});
