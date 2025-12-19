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
import mealApi from '@/api/mealApi'
import MealCard from '@/components/meal/MealCard.vue'
import MealForm from '@/components/meal/MealForm.vue'
import MealTimeLabel from '@/components/meal/MealTimeLabel.vue'
import { computed, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'

// URL에서 받은 원본 날짜 (API용)
const route = useRoute() // url에서 값 꺼냄
const router = useRouter()

const selectedDate = computed(() => route.query.date) //
// const selectedDate = route.query.date // "2025-12-16"

// 화면 표시용 날짜 포맷
const formattedDate = computed(() => {
  // computed 사용해서 .value
  if (!selectedDate.value) return ''

  // const date = new Date(selectedDate)
  const date = new Date(selectedDate.value) // 

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

// 식단 조회
const loadMeals = async () => {
  if (!selectedDate.value) return //

  loading.value = true
  try {
    const response = await mealApi.getMealsByDate(selectedDate.value)
    meals.value = response.data || []
  } catch (e) {
    meals.value = [] // 에러 시 빈 배열로 초기화
  } finally {
    loading.value = false
  }
}

// 이건 처음 실행 시에만 진행
// onMounted(() => {
//   if (selectedDate) {
//     loadMeals()
//   }
// })

// 날짜 변경 감지
watch(
  () => route.query.date,
  () => {
    loadMeals()
  },
  { immediate: true }, // 처음 진입 시에 실행됨
)

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

// 식단 등록
const createMeal = async (formData) => {
  try {
    const isDuplicate = meals.value.some((meal) => meal.mealType === formData.mealType)

    if (isDuplicate) {
      alert(`${getMealTypeLabel(formData.mealType)} 식단이 이미 등록되어 있습니다!`)
      return
    }

    // multipart 전송을 위한 FormData 생성
    const multipartForm = new FormData()

    // JSON -> data
    multipartForm.append(
      'data',
      new Blob(
        [
          JSON.stringify({
            mealType: formData.mealType,
            foodId: formData.foodId,
            foodName: formData.foodName,
            calories: formData.calories,
            carbs: formData.carbs,
            protein: formData.protein,
            fat: formData.fat,
            score: formData.score,
            memo: formData.memo,
            date: formData.date,
          }),
        ],
        { type: 'application/json' },
      ),
    )

    // 이미지 -> image
    if (formData.imageFile) {
      multipartForm.append('image', formData.imageFile)
    }

    await mealApi.createMeal(multipartForm)

    // 바로 해당 날짜의 DailyView로 이동
    router.push({
      name: 'MealDaily',
      params: { date: selectedDate.value },
    })
  } catch (e) {
    console.error('식단 등록 실패', e)
  }
}

// 시간대 한글 변환
const getMealTypeLabel = (type) => {
  const labels = {
    BREAKFAST: '아침',
    LUNCH: '점심',
    DINNER: '저녁',
  }
  return labels[type] || type
}
</script>
