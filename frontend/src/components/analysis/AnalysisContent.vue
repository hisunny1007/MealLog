<template>
    <div v-if="meals.length > 0">
      <h2 class="text-center fw-bold mb-5">{{ formattedDate }} 데일리 분석</h2>

      <div class="row g-4 mb-4">
        <div class="col-md-4">
          <div class="card p-4 rounded-4 shadow-sm border-0 h-100 border-top border-4 border-brown">
            <p class="text-muted mb-2 font-weight-bold">나의 목표</p>
            <h4 class="fw-bold mb-0">
              {{ user.exerciseGoal === 'DIET' ? '체중 감량 🔥' : '근육 증가 💪' }}
            </h4>
          </div>
        </div>

        <div class="col-md-4">
          <div class="card p-4 rounded-4 shadow-sm border-0 h-100 border-top border-4 border-brown">
            <p class="text-muted mb-2 font-weight-bold">오늘 섭취 칼로리</p>
            <h4 class="fw-bold mb-0 text-brown">{{ totalCalories }} kcal</h4>
          </div>
        </div>

        <div class="col-md-4">
          <div class="card p-4 rounded-4 shadow-sm border-0 h-100 border-top border-4 border-brown">
            <p class="text-muted mb-2 font-weight-bold">권장 칼로리</p>
            <h4 class="fw-bold mb-0">{{ recommendedCalories }} kcal</h4>
          </div>
        </div>
      </div>

      <div class="card p-4 rounded-4 shadow-sm border-0 mb-5 bg-white">
        <div class="d-flex align-items-center">
          <div class="me-3 fs-2">📢</div>
          <div>
            <p class="fw-bold mb-1 fs-5 text-dark">{{ feedbackMessage.line1 }}</p>
            <p class="text-muted mb-0 fs-6">{{ feedbackMessage.line2 }}</p>
          </div>
        </div>
      </div>

      <div class="row g-4 mb-5">
        <div class="col-lg-6">
          <div class="card p-4 rounded-4 shadow-sm border-0 h-100">
            <h5 class="fw-bold mb-4">📊 영양 성분 비율</h5>

            <div class="chart-section my-4">
              <DoughnutChart :ratio="macroRatio" />
            </div>

            <div class="macro-feedback mt-auto p-4 bg-light rounded-4">
              <p class="fw-bold mb-2 text-dark fs-5">{{ macroFeedback.line1 }}</p>
              <p class="text-muted mb-0 fs-6 lh-base">{{ macroFeedback.line2 }}</p>
            </div>
          </div>
        </div>

        <div class="col-lg-6">
          <div class="card p-4 rounded-4 shadow-sm border-0 h-100">
            <div class="d-flex justify-content-between align-items-center mb-4">
              <h5 class="fw-bold mb-0">🤖 AI 분석 리포트</h5>
              <button
                class="btn btn-brown btn-sm px-4 rounded-pill"
                :disabled="aiLoading"
                @click="fetchAnalysis"
              >
                {{ aiLoading ? '분석 중...' : '새로고침' }}
              </button>
            </div>

            <div v-if="aiLoading" class="text-center py-5">
              <div class="spinner-border text-brown mb-3" style="width: 3rem; height: 3rem"></div>
              <p class="text-muted fs-6">AI가 식단을 꼼꼼히 분석하고 있어요!</p>
            </div>

            <p v-if="aiError" class="text-danger text-center py-3 fs-6">
              {{ aiError }}
            </p>

            <div v-if="analysisResult && !aiLoading" class="analysis-result-content">
              <div class="mb-4 p-4 rounded-4 bg-brown-light shadow-none">
                <p class="mb-0 lh-lg text-dark fs-6" style="white-space: pre-wrap">
                  {{ analysisResult.analysis }}
                </p>
              </div>

              <div>
                <h6 class="fw-bold mb-3 fs-5">🥗 오늘의 추천 음식</h6>
                <div class="recommend-list d-flex flex-wrap gap-2">
                  <span
                    v-for="(food, idx) in analysisResult.recommendations"
                    :key="idx"
                    class="badge bg-white text-dark border border-2 px-3 py-2 rounded-pill fw-bold fs-6"
                  >
                    {{ food }}
                  </span>
                </div>
              </div>
            </div>

            <div v-if="!analysisResult && !aiLoading && !aiError" class="text-center my-auto py-5">
              <div class="mb-3 fs-1">📜</div>
              <p class="text-muted fs-6 mb-4">
                오늘의 식단 정보를 바탕으로<br />전문적인 피드백을 받아보세요.
              </p>
              <button
                class="btn btn-outline-brown rounded-pill px-5 py-2 fw-bold"
                @click="fetchAnalysis"
              >
                분석 시작하기
              </button>
            </div>
          </div>
        </div>
      </div>
    </div></template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import DoughnutChart from '@/components/daily/DoughnutChart.vue'
import mealApi from '@/api/mealApi'
import { useAuthStore } from '@/stores/authStore'
import { storeToRefs } from 'pinia'
import aiApi from '@/api/aiApi'

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
const authStore = useAuthStore()
const { user } = storeToRefs(authStore)

// 3. 오늘 식단 데이터
const meals = ref([]) // meals -ref

// 오늘 식단 불러오기
const fetchMeals = async () => {
  if (!user.value) return

  try {
    const result = await mealApi.getMealsByDate(date)
    // 백엔드가 List<Dto>를 바로 내려주므로 data 자체가 배열
    meals.value = result ?? []
  } catch (e) {
    console.error('식단 조회 실패', e)
    meals.value = []
  }
}

onMounted(fetchMeals)

// 식단 ai 분석
const analysisResult = ref(null)
const aiLoading = ref(false)
const aiError = ref(null)

const fetchAnalysis = async () => {
  if (meals.value.length === 0) return

  try {
    aiLoading.value = true
    aiError.value = null
    const result = await aiApi.analyzeMeals(meals.value)
    analysisResult.value = result
  } catch (e) {
    console.error(e)
    aiError.value = 'AI 분석에 실패했어요. 잠시 후 다시 시도해 주세요!'
  } finally {
    aiLoading.value = false
  }
}

// ▼ 계산 로직
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
  if (total === 0) return { carbs: 0, protein: 0, fat: 0 }
  return {
    carbs: Math.round((carbKcal / total) * 100),
    protein: Math.round((proteinKcal / total) * 100),
    fat: Math.round((fatKcal / total) * 100),
  }
})

// 권장칼로리 = BMR X 활동계수
// 최종 권장 칼로리 = BMR X 활동계수 + 목표 보정치 (다이어트 or 근육증가)
// 다이어트	-300 ~ -500 kcal
// 근육 증가	+300 ~ +500 kcal

// 기초대사량(BMR) 계산
const calcBmr = (user) => {
  if (user.gender === 'M') return 10 * user.weight + 6.25 * user.height - 5 * user.age + 5
  else return 10 * user.weight + 6.25 * user.height - 5 * user.age - 161
}
// 활동계수 - 운동빈도 매핑
const activityFactorMap = { NONE: 1.2, LOW: 1.375, MEDIUM: 1.55, HIGH: 1.725, VERY_HIGH: 1.9 }
// 목표 보정치 - 운동목표 매핑

const goalAdjustmentMap = { DIET: -400, MUSCLE: +400 }

// 최종 권장 칼로리
const recommendedCalories = computed(() => {
  if (!user.value) return 0
  const bmr = calcBmr(user.value)
  const base = bmr * activityFactorMap[user.value.exerciseFrequency]
  const adjustment = goalAdjustmentMap[user.value.exerciseGoal] ?? 0
  return Math.round(base + adjustment)
})

const feedbackMessage = computed(() => {
  if (!user.value) return { line1: '', line2: '' }
  const difference = totalCalories.value - recommendedCalories.value
  const line1 =
    difference > 0
      ? `오늘은 권장량보다 ${difference}kcal 더 먹었어요.`
      : `오늘은 권장량보다 ${Math.abs(difference)}kcal 덜 먹었어요.`
  let line2 =
    user.value.exerciseGoal === 'DIET'
      ? difference > 0
        ? '다이어트 목표에 비해 조금 높아요'
        : '다이어트 목표에 잘 맞는 섭취량이에요 👍'
      : difference < 0
        ? '근육 증가를 위해 조금 더 드셔도 좋아요'
        : '근육 증가에 적절한 섭취량이에요 💪'
  return { line1, line2 }
})

const macroFeedback = computed(() => {
  if (!user.value) return { line1: '', line2: '' }
  const { carbs, protein, fat } = macroRatio.value
  let line1 = `탄수화물 ${carbs}%, 단백질 ${protein}%, 지방 ${fat}%`
  let line2 = ''
  if (user.value.exerciseGoal === 'DIET') {
    line2 =
      protein < 25
        ? '단백질 섭취를 조금 늘려보세요 🥚'
        : carbs > 55
          ? '탄수화물 비중이 높아요 🍚'
          : '균형 잡힌 비율이에요 👍'
  } else {
    line2 =
      protein < 30
        ? '단백질을 더 보충해 주세요 💪'
        : fat < 20
          ? '지방도 에너지원이에요 🥑'
          : '적절한 비율이에요 🔥'
  }
  return { line1, line2 }
})

</script>
