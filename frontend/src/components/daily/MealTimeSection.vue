<!-- 오늘의 식단 기록 페이지
MealDailyView.vue          (페이지 / 데이터 책임)
 ├─ MealTimeSection.vue    (아침/점심/저녁 한 섹션)
 │   ├─ MealItemCard.vue   (식단 카드)
 │   └─ MealAddCard.vue    (추가 버튼 카드)
 ├─ ProductRecommendation.vue    (상품 추천) -->
<template>
  <div class="meal-section-wrapper mb-5 position-relative">
    <div class="timeline-dot"></div>

    <div class="ms-4">
      <div class="d-flex align-items-center mb-3">
        <span class="meal-label-badge">{{ label }}</span>
      </div>

      <div class="content-area">
        <MealItemCard v-if="meal" :meal="meal" />
        <MealAddCard v-else @click="$emit('add', mealType)" :label="label" />
      </div>
    </div>
  </div>
</template>

<script setup>
import MealItemCard from './MealItemCard.vue'
import MealAddCard from './MealAddCard.vue'

defineProps({
  label: String,
  meal: Object,
  mealType: String,
  isLast: Boolean
})
</script>

<style scoped>
.meal-section-wrapper {
  position: relative;
}

.timeline-dot {
  position: absolute;
  left: -9px;
  top: 5px;
  width: 16px;
  height: 16px;
  background-color: #a57c5e;
  border: 3px solid #fff;
  border-radius: 50%;
  z-index: 1;
  box-shadow: 0 0 0 3px rgba(165, 124, 94, 0.1);
}

.meal-label-badge {
  background-color: #a57c5e;
  color: white;
  padding: 4px 14px;
  border-radius: 20px;
  font-size: 0.9rem;
  font-weight: 600;
  box-shadow: 0 4px 6px rgba(165, 124, 94, 0.2);
}

.content-area {
  transition: all 0.3s ease;
}
</style>