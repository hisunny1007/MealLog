<template>
  <div class="create-page container py-5">
    <!-- 날짜 -->
    <div class="page-title text-center mb-5">
      <h2 class="fw-bold mb-4">{{ formattedDate }} 식단</h2>
    </div>

    <!-- 라벨 -->
    <!-- 자식한테 상태 넘김 -->
    <MealTimeLabel :meals="mealStatus" />

    <hr />

    <!-- 카드 (slot 사용) -->
    <MealCard class="mt-4">
      <MealForm
        :date="selectedDate"
        :mode="mode"
        :mealId="mealId"
        @submit="createMeal"
        @update="updateMeal"
      />
    </MealCard>
  </div>
</template>

<script setup>
import mealApi from '@/api/mealApi'
import MealCard from '@/components/meal/MealCard.vue'
import MealForm from '@/components/meal/MealForm.vue'
import MealTimeLabel from '@/components/meal/MealTimeLabel.vue'
import { useAuthStore } from '@/stores/authStore'
import { computed, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { toast } from 'vue3-toastify'

const authStore = useAuthStore()

// URL에서 받은 원본 날짜 (API용)
const route = useRoute() // url에서 값 꺼냄
const router = useRouter()

// form에 넘겨야 함
// const mode
const mode = route.props?.mode ?? route.meta?.mode ?? 'create'

const mealId = route.params.mealId

const selectedDate = computed(() => route.params.date) //params 변경

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
// const formKey = ref(0)

// 식단 조회
const loadMeals = async () => {
  if (!selectedDate.value) return //

  loading.value = true
  try {
    const result = await mealApi.getMealsByDate(selectedDate.value)
    meals.value = result ?? []
  } catch (e) {
    meals.value = [] // 에러 시 빈 배열로 초기화
  } finally {
    loading.value = false
  }
}

// 날짜 변경 감지
watch(
  () => route.params.date,
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
  if (!authStore.isAuthenticated) {
    toast.info('회원가입 후 이용이 가능합니다!')
    router.push('/signup')
    return
  }

  try {
    const isDuplicate = meals.value.some((meal) => meal.mealType === formData.mealType)

    if (isDuplicate) {
      toast.error(`${getMealTypeLabel(formData.mealType)} 식단이 이미 등록되어 있습니다!`)
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

    const response = await mealApi.createMeal(multipartForm)

    // 포인트 최신 업데이트
    const point = response.currentTotalPoint
    authStore.updateUserPoints(point)
    toast.success('🎉 식단이 기록되었습니다. 100 포인트가 적립되었어요!')

    // 바로 해당 날짜의 DailyView로 이동
    setTimeout(() => {
      router.push({
        name: 'MealDaily',
        params: { date: selectedDate.value },
      })
    }, 1000)
  } catch (e) {
    toast.error('식단 등록 중 오류가 발생했습니다.')
  }
}

// 식단 수정
const updateMeal = async (updateFormData) => {
  // 수정도  JSON -> data 변환 필요
  try {
    const multipartForm = new FormData()

    // JSON -> data
    multipartForm.append(
      'data',
      new Blob(
        [
          JSON.stringify({
            mealType: updateFormData.mealType,
            foodId: updateFormData.foodId,
            foodName: updateFormData.foodName,
            calories: updateFormData.calories,
            carbs: updateFormData.carbs,
            protein: updateFormData.protein,
            fat: updateFormData.fat,
            score: updateFormData.score,
            memo: updateFormData.memo,
            date: updateFormData.date || selectedDate.value,
          }),
        ],
        { type: 'application/json' },
      ),
    )

    // 새 이미지 잇다면 추가
    if (updateFormData.imageFile) {
      multipartForm.append('image', updateFormData.imageFile)
    }

    await mealApi.updateMeal(mealId, multipartForm)
    toast.success('✏️ 식단이 수정되었습니다.')

    setTimeout(() => {
      router.push({
        name: 'MealDaily',
        params: { date: selectedDate.value },
      })
    }, 1000)
  } catch (e) {
    toast.error('수정 중 오류가 발생했습니다.')
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

<style scoped>
.meal-daily-page {
  max-width: 1100px;
  margin: 0 auto;
}
</style>
