import api from './axios'

const ENDPOINT = '/ai'

const aiApi = {
  // 데일리 리포트 ai 식단 분석 api
  analyzeMeals: async (meals) => {
    const response = await api.post(`${ENDPOINT}/analyze`, meals)
    return response.data
  },
}

export default aiApi
