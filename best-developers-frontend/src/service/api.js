import axios from "axios";
import { getToken } from "./auth-session-manager";

const api = axios.create({
  baseURL: process.env.REACT_APP_API_DEVELOPERS_URL
});

api.interceptors.request.use(async config => {
  const token = getToken();

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export const genericErrorValidator = (error) => {
  if (error.response)
    throw error.response.data.description;
  if (error.request)
    throw 'Unavailable server. Try again later.'
  else
    throw error;
}

export default api;