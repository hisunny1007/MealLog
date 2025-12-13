<!-- props를 받는 자식 컴포넌트 -->

<template>
  <div class="d-flex justify-content-center gap-4">

    <div
      v-for="meal in meals"
      :key="meal.type"
      class="d-flex align-items-center gap-2 meal-label"
      :class="{ active: meal.hasMeal }"
    >
      <!-- 동그라미 -->
      <div
        class="circle"
        :class="meal.hasMeal ? 'filled' : 'empty'"
      ></div>

      <!-- 텍스트 -->
      <span class="fw-semibold">
        {{ meal.label }}
      </span>
    </div>

  </div>
</template>

<script setup>
defineProps({
  meals: {
    type: Array,
    required: true,
  }
});
</script>

<style scoped>
.circle {
  width: 20px;
  height: 20px;
  border-radius: 50%;
}

/* 식단 있음 */
.circle.filled {
  background-color: #a58a6d;
}

/* 식단 없음 */
.circle.empty {
  border: 2px solid #a58a6d;
}

.meal-label {
  opacity: 0.7;
}

.meal-label.active {
  opacity: 1;
}
</style>
