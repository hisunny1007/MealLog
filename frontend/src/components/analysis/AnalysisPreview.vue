<template>
  <div class="analysis-page container py-5">
    <!-- 비로그인 + 분석 미리보기-->
    <div v-if="!isLoggedIn" class="analysis-wrapper position-relative">
      <!-- 오늘의 분석 (더미데이터활용) -->
      <div class="blur-content">
        <h2 class="text-center fw-bold mb-5">{{ formattedDate }} 데일리 분석</h2>

        <div class="row g-4 mb-4">
          <div class="col-md-4">
            <div
              class="card p-4 rounded-4 shadow-sm border-0 h-100 border-top border-4 border-brown"
            >
              <p class="text-muted mb-2 font-weight-bold">나의 목표</p>
              <h4 class="fw-bold mb-0">체중 감량 🔥</h4>
            </div>
          </div>

          <div class="col-md-4">
            <div
              class="card p-4 rounded-4 shadow-sm border-0 h-100 border-top border-4 border-brown"
            >
              <p class="text-muted mb-2 font-weight-bold">오늘 섭취 칼로리</p>
              <h4 class="fw-bold mb-0 text-brown">1850 kcal</h4>
            </div>
          </div>

          <div class="col-md-4">
            <div
              class="card p-4 rounded-4 shadow-sm border-0 h-100 border-top border-4 border-brown"
            >
              <p class="text-muted mb-2 font-weight-bold">권장 칼로리</p>
              <h4 class="fw-bold mb-0">2000 kcal</h4>
            </div>
          </div>
        </div>

        <div class="row g-4 mb-5">
          <div class="col-lg-6">
            <div class="card p-4 rounded-4 shadow-sm border-0 h-100">
              <h5 class="fw-bold mb-4">📊 영양 성분 비율</h5>
              <div class="chart-section my-4">
                <DoughnutChart :ratio="dummyMacroRatio" />
              </div>
            </div>
          </div>

          <div class="col-lg-6">
            <div class="card p-4 rounded-4 shadow-sm border-0 h-100">
              <h5 class="fw-bold mb-4">🤖 AI 분석 리포트</h5>
              <div class="p-4 rounded-4 bg-brown-light">
                <p class="mb-0" style="white-space: pre-wrap">
                  {{ dummyAnalysisResult.analysis }}
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 오버레이 -->
      <div class="analysis-overlay">
        <div class="overlay-box text-center">
          <h4 class="fw-bold mb-3">🍽️ 식단 분석을 받아보세요!</h4>
          <p class="text-muted mb-4">
            식단을 기록하면<br />
            AI가 영양 밸런스를 분석해드려요.
          </p>
          <RouterLink to="/login" class="btn-brown rounded-pill px-5 py-2">
            로그인하고 분석받기
          </RouterLink>
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
import aiApi from '@/api/aiApi'

// 라우트에서 날짜 받기
const route = useRoute()
const date = route.params.date

const formattedDate = computed(() => {
  const rawDate = new Date(date)
  return `${rawDate.getFullYear()}년 ${rawDate.getMonth() + 1}월 ${rawDate.getDate()}일`
})

// 로그인 유저 정보
const authStore = useAuthStore()
const { user } = storeToRefs(authStore)
const isLoggedIn = computed(() => !!user.value)

// 더미 데이터
const dummyMacroRatio = { carbs: 45, protein: 30, fat: 25 }

const dummyAnalysisResult = {
  analysis:
    '오늘은 비교적 균형 잡힌 식단이에요.\n단백질과 지방 비율이 안정적이에요.\n채소 섭취를 조금 더 늘려보세요!',
}

// 3. 오늘 식단 데이터
const meals = ref([])

// 오늘 식단 불러오기
const fetchMeals = async () => {
  if (!user.value) return
  const result = await mealApi.getMealsByDate(date)
  meals.value = result ?? []
}

onMounted(fetchMeals)
</script>

<style>
.blur-content {
  filter: blur(3px);
  pointer-events: none;
  user-select: none;
}

.analysis-overlay {
  position: absolute;
  inset: 0;
  background: rgba(255, 255, 255, 0.35);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 10;
}

.overlay-box {
  background: white;
  padding: 40px;
  border-radius: 24px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
}
</style>
