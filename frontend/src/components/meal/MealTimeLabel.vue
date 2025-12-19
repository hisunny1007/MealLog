<!-- props를 받는 자식 컴포넌트 -->
<template>
  <div class="d-flex justify-content-center gap-4">
    <div
      v-for="meal in meals"
      :key="meal.type"
      class="meal-label d-flex align-items-center gap-2"
    >
      <!-- @click="handleClickMeal(meal)" -->
      <!-- 상태 점 -->
      <div class="circle" :class="{ filled: meal.hasMeal }"></div>

      <span class="fw-semibold">
        {{ meal.label }}
      </span>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  meals: {
    type: Array,
    required: true,
  },
})

</script>

<style scoped>
.circle {
  width: 16px;
  height: 16px;
  border-radius: 50%;
  flex-shrink: 0;
}

/* 식단 있음 */
.circle.filled {
  background-color: #a58a6d;
}

/* 식단 없음 */
.circle:not(.filled) {
  border: 2px solid #a58a6d;
  background-color: #ffffff;
}

.meal-label {
  cursor: pointer;
}

.meal-label.disabled {
  cursor: not-allowed;
  opacity: 0.4;
}
</style>
