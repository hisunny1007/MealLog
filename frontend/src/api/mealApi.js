import api from './axios'

const ENDPOINT = '/meals'

const mealApi = {
  // 식단 생성 api
  createMeal: async (formData) => {
    const response = await api.post(`${ENDPOINT}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data', // 이미지 함께 등록 (data : JSON / image : 파일)
      },
    })
    return response.data
  },

  // 특정 날짜 전체 식단 조회 api
  getMealsByDate: async (date) => {
    const response = await api.get(`${ENDPOINT}/date/${date}`)
    return response.data
  },

  // 식단 단일 조회 api (수정/상세용)
  getMealDetail: async (mealId) => {
    const response = await api.get(`${ENDPOINT}/${mealId}`)
    return response.data
  },

  // 식단 수정 api
  updateMeal: async (mealId, formData) => {
    // 수정할 때 기본 식단 정보는 가지고 와야 함
    const response = await api.put(`${ENDPOINT}/${mealId}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
    return response.data
  },

  // 식단 삭제 api
  deleteMeal: async (mealId) => {
    const response = await api.delete(`${ENDPOINT}/${mealId}`)
    return response.data
  },

  // 캘린더 요약용 조회 api
  // params: { year, month } -> URL 쿼리 문자열로 자동 변환 (?year=2025&month=12)
  getCalendarSummary: async (year, month) => {
    const response = await api.get(`${ENDPOINT}/calendar`, {
      params: { year, month },
    })
    return response.data
  },
}

export default mealApi
