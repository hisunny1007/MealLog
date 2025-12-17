import axios from 'axios'

// vite.config.js 프록시 추가
const api = axios.create({
  baseURL: '/api/v1',
})

// 요청 인터셉터 추가 (로그인 이후 요청)
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('accessToken')
    console.log('🔐 axios interceptor token =', token)

    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    } else {
      console.error('❌ 인증 실패 (JWT 없음 또는 만료)')
    }

    return config
  },
  (error) => Promise.reject(error),
)

/**
 * 응답 인터셉터 (401 확인용)
 */
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      console.error('❌ 인증 실패 (JWT 없음 또는 만료)')
    }
    return Promise.reject(error)
  },
)

export default api
