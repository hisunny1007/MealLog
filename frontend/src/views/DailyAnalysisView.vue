<template>
  <div class="analysis-page container py-4">
    <!-- 제목 -->
    <h2 class="text-center fw-bold mb-4">{{ formattedDate }} 데일리 분석</h2>
    <!-- 요약 카드 -->
    <div class="row g-3 mb-4">
      <div class="col-md-6">
        <div class="card p-3 rounded-4">
          <p class="text-muted mb-1">총 섭취 칼로리</p>
          <h4 class="fw-bold">{{ totalCalories }} kcal</h4>
        </div>
      </div>

      <div class="col-md-6">
        <div class="card p-3 rounded-4">
          <p class="text-muted mb-1">권장 칼로리</p>
          <h4 class="fw-bold">{{ recommendedCalories }} kcal</h4>
        </div>
      </div>
    </div>

    <!-- 텍스트 -->
    <div>
      <p>오늘은 권장량보다 {{ calories }} 먹었어요.</p>
      <p>{{ exerciseGoal }} 목표에 맞는 섭취량을 확인해 보세요!</p>
    </div>

    <!-- 식단 없을 때 -->
    <div v-if="meals.length === 0" class="text-center text-muted py-5">
      오늘 기록된 식단이 없습니다.
    </div>

    <!-- 탄단지 그래프 -->
    <div v-else class="card p-4 rounded-4">
      <h5 class="fw-bold mb-3">탄 · 단 · 지 비율</h5>
      <DoughnutChart :ratio="macroRatio" />
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import DoughnutChart from '@/components/daily/DoughnutChart.vue'
import mealApi from '@/api/mealApi'
import { useAuthStore } from '@/stores/authStore'
import { storeToRefs } from 'pinia'

// 1. 라우트에서 날짜 받기
const route = useRoute()
const date = route.params.date

const formattedDate = computed(() => {
  const rawDate = new Date(date)
  const year = rawDate.getFullYear()
  const month = rawDate.getMonth() + 1
  const day = rawDate.getDate()
  return `${year}년 ${month}월 ${day}일`
})

// 2. 로그인 유저 정보 (authStore에서 가져옴)
// const authStore = useAuthStore()
// const { user } = storeToRefs(authStore)

// 2임시 유저 데이터
const user = {
  height: 165,
  weight: 58,
  age: 25,
  gender: 'F',
  exerciseFrequency: 'HIGH',
}

// 3. 오늘 식단 데이터
const meals = ref([])

// 오늘 식단 불러오기
const fetchMeals = async () => {
  // console.log('사람 정보', user.value)
  // console.log('나이몇살', user.value?.age)
  // if (!user.value) return
  // try {
  //   const response = await mealApi.getMealsByDate(date)
  //   meals.value = response.data
  //   console.log('식단 데이터', meals.value)
  // } catch (e) {
  //   console.error('식단 조회 실패', e)
  // }
  meals.value = [
    { calories: 450, carbs: 55, protein: 20, fat: 15 },
    { calories: 700, carbs: 80, protein: 35, fat: 25 },
    { calories: 380, carbs: 45, protein: 25, fat: 10 },
  ]
}

onMounted(fetchMeals)

// 계산 로직
// 총 섭취 칼로리
const totalCalories = computed(() => meals.value.reduce((sum, meal) => sum + meal.calories, 0))

// 탄단지 합계
const totalMacros = computed(() =>
  meals.value.reduce(
    (acc, meal) => {
      acc.carbs += meal.carbs
      acc.protein += meal.protein
      acc.fat += meal.fat
      return acc
    },
    { carbs: 0, protein: 0, fat: 0 },
  ),
)

// 탄단지 비율
const macroRatio = computed(() => {
  const carbKcal = totalMacros.value.carbs * 4
  const proteinKcal = totalMacros.value.protein * 4
  const fatKcal = totalMacros.value.fat * 9

  const total = carbKcal + proteinKcal + fatKcal
  if (total === 0) {
    return { carbs: 0, protein: 0, fat: 0 }
  }

  return {
    carbs: Math.round((carbKcal / total) * 100),
    protein: Math.round((proteinKcal / total) * 100),
    fat: Math.round((fatKcal / total) * 100),
  }
})

// 기초대사량 계산
const calcBmr = (user) => {
  if (user.gender === 'M') {
    return 10 * user.weight + 6.25 * user.height - 5 * user.age + 5
  } else {
    return 10 * user.weight + 6.25 * user.height - 5 * user.age - 161
  }
}

// 운동 빈도(활동계수) 매핑
const activityFactorMap = {
  NONE: 1.2,
  LOW: 1.375,
  MEDIUM: 1.55,
  HIGH: 1.725,
  VERY_HIGH: 1.9,
}

// 권장칼로리 계산
const recommendedCalories = computed(() => {
  // if (!user.value) return 0
  const bmr = calcBmr(user)
  const factor = activityFactorMap[user.exerciseFrequency]
  return Math.round(bmr * factor)
})
</script>

<style scoped></style>
