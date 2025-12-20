<template>
  <div class="analysis-page container py-4">
    <!-- 식단 없을 때 -->
    <div
      v-if="meals.length === 0"
      class="analysis-empty d-flex flex-column justify-content-center align-items-center text-center"
    >
      <p class="empty-title mb-2">아직 오늘의 식단 기록이 없어요 🍽️</p>
      <p class="empty-desc mb-4">식단을 기록하면 오늘의 분석 리포트를 확인할 수 있어요!</p>

      <RouterLink :to="`/meals/create/${date}`" class="btn btn-brown rounded-pill px-5 py-2">
        식단 기록하러 가기
      </RouterLink>
    </div>
    <!-- 식단 있을 때만 분석 보여줌 -->
    <div v-else>
      <div>
        <!-- 제목 -->
        <h2 class="text-center fw-bold mb-4">{{ formattedDate }} 데일리 분석</h2>
      </div>
      <!-- 요약 카드 -->
      <div class="row g-3 mb-4">
        <div class="col-md-6">
          <div class="card p-3 rounded-4">
            <p class="text-muted mb-1">오늘 섭취 칼로리</p>
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

      <!-- 운동목표 반영 피드백 문구 -->
      <div class="feedback-text">
        <p>{{ feedbackMessage.line1 }}</p>
        <p class="text-muted">{{ feedbackMessage.line2 }}</p>
      </div>

      <!-- 탄단지 그래프 -->
      <div class="card p-4 rounded-4">
        <h5 class="fw-bold mb-3">탄 · 단 · 지 비율</h5>
        <DoughnutChart :ratio="macroRatio" />

        <!-- 탄단지 피드백 문구 -->
        <div class="macro-feedback mt-3">
          <p class="fw-semibold">{{ macroFeedback.line1 }}</p>
          <p class="text-muted">{{ macroFeedback.line2 }}</p>
        </div>
      </div>
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
const authStore = useAuthStore()
const { user } = storeToRefs(authStore)

// 3. 오늘 식단 데이터
const meals = ref([])

// 오늘 식단 불러오기
const fetchMeals = async () => {
  if (!user.value) return

  try {
    const response = await mealApi.getMealsByDate(date)

    // 백엔드가 List<Dto>를 바로 내려주므로 data 자체가 배열
    meals.value = response.data ?? []
  } catch (e) {
    console.error('식단 조회 실패', e)
    meals.value = []
  }
}

onMounted(fetchMeals)

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
  if (total === 0) {
    return { carbs: 0, protein: 0, fat: 0 }
  }

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
  if (user.gender === 'M') {
    return 10 * user.weight + 6.25 * user.height - 5 * user.age + 5
  } else {
    return 10 * user.weight + 6.25 * user.height - 5 * user.age - 161
  }
}

// 활동계수 - 운동빈도 매핑
const activityFactorMap = {
  NONE: 1.2,
  LOW: 1.375,
  MEDIUM: 1.55,
  HIGH: 1.725,
  VERY_HIGH: 1.9,
}

// 목표 보정치 - 운동목표 매핑
const goalAdjustmentMap = {
  DIET: -400,
  MUSCLE: +400,
}

// 최종 권장 칼로리
const recommendedCalories = computed(() => {
  if (!user.value) return 0

  const bmr = calcBmr(user.value)
  const activityFactor = activityFactorMap[user.value.exerciseFrequency]

  const base = bmr * activityFactor
  const adjustment = goalAdjustmentMap[user.value.exerciseGoal] ?? 0

  return Math.round(base + adjustment)
})

// 운동목표 반영 피드백 문구
const feedbackMessage = computed(() => {
  if (!user.value) {
    return {
      line1: '',
      line2: '',
    }
  }

  //권장 대비 초과-부족 칼로리 계산
  const difference = totalCalories.value - recommendedCalories.value

  const line1 =
    difference > 0
      ? `오늘은 권장량보다 ${difference}kcal 더 먹었어요.`
      : `오늘은 권장량보다 ${Math.abs(difference)}kcal 덜 먹었어요.`

  let line2 = '' // 재할당해야돼서 let으로

  if (user.value.exerciseGoal === 'DIET') {
    line2 =
      difference > 0
        ? '다이어트 목표에 비해 섭취량이 조금 높아요'
        : '다이어트 목표에 잘 맞는 섭취량이에요 👍'
  }

  if (user.value.exerciseGoal === 'MUSCLE') {
    line2 =
      difference < 0
        ? '근육 증가를 위해 조금 더 드셔도 좋아요'
        : '근육 증가에 적절한 섭취량이에요 💪'
  }

  return { line1, line2 } // 객체로 반환
})

// 탄단지 평가 문구
const macroFeedback = computed(() => {
  if (!user.value) {
    return { line1: '', line2: '' }
  }

  const { carbs, protein, fat } = macroRatio.value

  let line1 = ''
  let line2 = ''

  // 공통 1줄 요약
  line1 = `탄수화물 ${carbs}%, 단백질 ${protein}%, 지방 ${fat}% 섭취했어요.`

  // 목표별 평가
  if (user.value.exerciseGoal === 'DIET') {
    if (protein < 25) {
      line2 = '다이어트를 위해 단백질 섭취를 조금 늘려보세요 🥚'
    } else if (carbs > 55) {
      line2 = '탄수화물 비중이 높아요. 조금만 줄여도 좋아요 🍚'
    } else {
      line2 = '다이어트에 잘 맞는 균형 잡힌 비율이에요 👍'
    }
  }

  if (user.value.exerciseGoal === 'MUSCLE') {
    if (protein < 30) {
      line2 = '근육 증가를 위해 단백질을 더 보충해 주세요 💪'
    } else if (fat < 20) {
      line2 = '지방도 에너지원이에요. 너무 낮지 않게 유지해요 🥑'
    } else {
      line2 = '근육 증가에 적절한 탄단지 비율이에요 🔥'
    }
  }

  return { line1, line2 }
})
</script>

<style scoped>
.analysis-empty {
  min-height: 60vh;
}

.empty-title {
  font-size: 1.3rem;
  font-weight: 700;
  color: var(--main-brown);
}

.empty-desc {
  font-size: 1rem;
  color: #7a6658;
  line-height: 1.6;
}

.btn-brown {
  background: var(--main-brown);
  color: #fff;
  font-weight: 600;
  border: none;
  transition: all 0.15s ease;
}

.btn-brown:hover {
  background: var(--brown-50);
  color: #fff;
}

.btn-brown:active {
  transform: scale(0.96);
}
</style>
