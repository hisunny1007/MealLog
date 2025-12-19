import axios from "axios";
import { useAuthStore } from "@/stores/auth"; 

// vite.config.js 프록시 추가
const api = axios.create({
  baseURL: '/api/v1',
});


api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  
 
  console.log('Attaching token to request header:', token);

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}, (error) => {
  return Promise.reject(error);
});

export default api;
