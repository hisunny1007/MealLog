// meal.js
import api from "./axios";

const ENDPOINT = '/meals'

const mealApi = {
  // 식단 생성 api
  createMeal : async(data) => {
    const response = await api.post(`${ENDPOINT}`, data);
    console.log(response.data);
    return response.data;
  }
}