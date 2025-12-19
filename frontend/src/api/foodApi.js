import api from './axios'

const ENDPOINT = '/foods'

const foodApi = {
  // 자동완성 검색 api
  searchAutoComplete: async (query) => {
    const response = await api.get(`${ENDPOINT}/auto-complete`, {
      params: { query },
    })
    return response.data
  },

  // 음식 상세 검색 api
  searchFood: async (query) => {
    const response = await api.get(`${ENDPOINT}/search`, {
      params: { query },
    })
    return response.data
  },

  // 음식 상세 조회 (ID로)
  getFoodDetail: async (foodId) => {
    const response = await api.get(`${ENDPOINT}/${foodId}`)
    return response.data
  },

  // 커스텀 음식 등록 api
  createCustomFood: async (data) => {
    const response = await api.post(`${ENDPOINT}`, data)
    return response.data
  },
}

export default foodApi
