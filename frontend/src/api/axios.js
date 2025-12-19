import { useAuthStore } from '@/stores/authStore'
import axios from 'axios'

const api = axios.create({
  baseURL: '/api/v1',
})

// 요청 인터셉터
api.interceptors.request.use(
  (config) => {
    const authStore = useAuthStore()
    const token = authStore.token

    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    } else {
    }

    return config
  },
  (error) => Promise.reject(error),
)

// 응답 인터셉터 (401 확인용)
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      console.error('인증 실패')
    }
    return Promise.reject(error)
  },
)

export default api
