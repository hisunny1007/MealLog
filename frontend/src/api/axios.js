import axios from "axios";


// vite.config.js 프록시 추가
const api = axios.create({
  baseURL: '/api/v1',
});

// 요청 인터셉터 추가 (로그인 이후 요청)
// api.interceptors.request.use((config) => {
//   const token = store.getState().auth.token;
//   if (token) {
//     config.headers.Authorization = `Bearer ${token}`;
//   }
//   return config;
// });

export default api;
