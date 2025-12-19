<!-- 오늘의 식단 기록 페이지
MealDailyView.vue          (페이지 / 데이터 책임)
 ├─ MealTimeSection.vue    (아침/점심/저녁 한 섹션)
 │   ├─ MealItemCard.vue   (식단 카드)
 │   └─ MealAddCard.vue    (추가 버튼 카드)
 ├─ ProductRecommendation.vue    (상품 추천) -->
<template>
  <section class="meal-time-section">
    <header class="section-header">
      <h4 class="fw-bold mb-3">{{ title }}</h4>
    </header>

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

<style scoped>
.meal-time-section {
  margin-bottom: 24px;
}

/* 상단 */
.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.section-header h4 {
  font-weight: 700;
  color: var(--main-brown);
}

/* ✅ 점수 원 */
.circle-score {
  width: 28px;
  height: 28px;
  border-radius: 50%;
  background-color: var(--bg-main);
  border: 1px solid var(--border);
  font-size: 0.8rem;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
