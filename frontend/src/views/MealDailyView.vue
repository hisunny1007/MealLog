<template>
  <h2 class="text-center fw-bold mb-4">{{ formattedDate }} 식단 기록</h2>
  <button class="btn btn-outline-secondary mb-4" @click="goToAnalysis">데일리 레포트</button>

  <div class="meal-daily-layout">
    <!-- 왼쪽 -->
    <section class="meal-left">
      <MealTimeSection
        v-for="section in mealSections"
        :key="section.type"
        :meal="section.meal"
        :label="section.label"
        :mealType="section.type"
        :date="date"
        @add="goToCreate"
      />
    </section>

    <!-- 오른쪽 -->
    <ProductRecommendation />
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import mealApi from '@/api/mealApi'

import MealTimeSection from '@/components/daily/MealTimeSection.vue'
import ProductRecommendation from '@/components/daily/ProductRecommendation.vue'

const route = useRoute()
const router = useRouter()
const date = route.params.date

const formattedDate = computed(() => {
  const rawDate = new Date(date)
  const year = rawDate.getFullYear()
  const month = rawDate.getMonth() + 1
  const day = rawDate.getDate()
  return `${year}년 ${month}월 ${day}일`
})

const meals = ref([])

onMounted(async () => {
  try {
    const result = await mealApi.getMealsByDate(date)

    meals.value = result ?? []
  } catch (error) {
    console.error('식단 조회 실패:', error)
    meals.value = []
  }
})

const mealSections = computed(() => [
  {
    type: 'BREAKFAST',
    label: '아침',
    meal: meals.value.find((m) => m.mealType === 'BREAKFAST'),
  },
  {
    type: 'LUNCH',
    label: '점심',
    meal: meals.value.find((m) => m.mealType === 'LUNCH'),
  },
  {
    type: 'DINNER',
    label: '저녁',
    meal: meals.value.find((m) => m.mealType === 'DINNER'),
  },
])

const goToCreate = (mealType) => {
  router.push({
    name: 'MealCreate',
    query: { date, mealType },
  })
}

const goToAnalysis = () => {
  router.push({
    name: 'MealDailyAnalysis',
    params: { date: date },
  })
}
</script>

<style scoped>
.meal-daily-layout {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 24px;
}
</style>
