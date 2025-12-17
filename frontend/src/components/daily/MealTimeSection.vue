<!-- 오늘의 식단 기록 페이지
MealDailyView.vue          (페이지 / 데이터 책임)
 ├─ MealTimeSection.vue    (아침/점심/저녁 한 섹션)
 │   ├─ MealItemCard.vue   (식단 카드)
 │   └─ MealAddCard.vue    (추가 버튼 카드)
 ├─ ProductRecommendation.vue    (상품 추천) -->
<template>
  <section class="mb-4">
    <h4 class="fw-bold mb-3">{{ title }}</h4>

    <MealItemCard v-if="meal" :meal="meal" />
    <MealAddCard v-else @click="$emit('add', mealType)" />
  </section>
</template>

<script setup>
import MealItemCard from './MealItemCard.vue'
import MealAddCard from './MealAddCard.vue'

defineProps({
  title: String,
  meal: Object,
  mealType: String,
  date: String,
})
</script>
