<template>
  <div class="container my-5">
    <!-- 날짜 -->
    <h2 class="text-center fw-bold mb-4">{{ formattedDate }} 식단</h2>

    <!-- 라벨 -->
    <!-- 자식한테 상태 넘김 -->
    <MealTimeLabel :meals="mealStatus" />

    <hr />

    <!-- 카드 (slot 사용) -->
    <MealCard class="mt-4">
      <MealForm :date="selectedDate" :key="formKey" @submit="createMeal" />
    </MealCard>
  </div>
</template>

<script setup>
import mealApi from '@/api/meal'
import MealCard from '@/components/meal/MealCard.vue'
import MealForm from '@/components/meal/MealForm.vue'
import MealTimeLabel from '@/components/meal/MealTimeLabel.vue'
import { computed, onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'

// URL에서 받은 원본 날짜 (API용)
const route = useRoute() // url에서 값 꺼냄
const selectedDate = route.query.date // "2025-12-16"

// 화면 표시용 날짜 포맷
const formattedDate = computed(() => {
  const date = new Date(selectedDate)

  const year = date.getFullYear()
  // console.log(date.getMonth())
  const month = date.getMonth() + 1 // 0부터 시작해서 +1 필요
  const day = date.getDate()

  return `${year}년 ${month}월 ${day}일`
})

// 식단 상태
const meals = ref([]) // // 식단 목록 담을 상태 -useState랑 같은 거임
const selectedMealType = ref(null)
const loading = ref(false)

const formKey = ref(0)

const router = useRouter()

// 식단 생성
const createMeal = async (formData) => {
  try {
    console.log('보내는 데이터', formData)
    console.log('토큰', localStorage.getItem('accessToken'))
    // 식단 등록
    await mealApi.createMeal({
      date: selectedDate,
      ...formData,
    })

    // 바로 해당 날짜의 DailyView로 이동
    router.push({
      name: 'MealDaily',
      params: { date: selectedDate },
    })
  } catch (e) {
    console.error('식단 등록 실패', e)
  }
}

const loadMeals = async () => {
  loading.value = true
  try {
    const response = await mealApi.getMealsByDate(selectedDate)
    meals.value = response.data
    console.log('식단 데이터:', meals.value)
  } catch (e) {
    console.error('식단 조회 실패', e)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  if (selectedDate) {
    loadMeals()
  }
})

// 라벨 상태 계산
const mealStatus = computed(() => [
  {
    type: 'BREAKFAST',
    label: '아침',
    hasMeal: meals.value.some((m) => m.mealType === 'BREAKFAST'),
  },
  {
    type: 'LUNCH',
    label: '점심',
    hasMeal: meals.value.some((m) => m.mealType === 'LUNCH'),
  },
  {
    type: 'DINNER',
    label: '저녁',
    hasMeal: meals.value.some((m) => m.mealType === 'DINNER'),
  },
])

// 라벨 클릭 처리
const handleSelectMealType = (type) => {
  console.log('라벨 클릭됨:', type)

  const target = mealStatus.value.find((m) => m.type === type)

  if (target.hasMeal) return // 이미 있으면 막기
  selectedMealType.value = type
}

// 저장 후 갱신
const reloadMeals = async () => {
  selectedMealType.value = null
  await loadMeals()
}
</script>
