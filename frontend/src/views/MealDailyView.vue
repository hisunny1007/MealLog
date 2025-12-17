<template>
  <h2 class="text-center fw-bold mb-4">오늘의 식단 기록</h2>
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
import mealApi from '@/api/meal'

import MealTimeSection from '@/components/daily/MealTimeSection.vue'
import ProductRecommendation from '@/components/daily/ProductRecommendation.vue'

const route = useRoute()
const router = useRouter()
const date = route.params.date

const meals = ref([])

onMounted(async () => {
  meals.value = await mealApi.getMealsByDate(date)
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
</script>

<style scoped>
.meal-daily-layout {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 24px;
}
</style>
