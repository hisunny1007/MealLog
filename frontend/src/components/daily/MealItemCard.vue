<template>
  <div class="meal-card-layout border rounded-4 p-3">
    <section class="meal-left">
      <img
        v-if="meal.imageUrl"
        :src="getImageUrl(meal.imageUrl)"
        class="meal-image meal-layout mb-3"
        alt="식단 이미지"
      />
    </section>

    <h5 class="fw-bold">{{ meal.foodName }}</h5>

    <div class="info-card-layout text-muted small">
      <p>{{ meal.calories }} kcal</p>
      <p>{{ meal.carbs }} g</p>
      <p>{{ meal.protein }} g</p>
      <p>{{ meal.fat }} g</p>
      <p class="mt-2">{{ meal.memo }}</p>
    </div>

  </div>
</template>

<script setup>
defineProps({
  meal: Object,
})

const getImageUrl = (filename) => {
  console.log('사진이름', filename)

  if (!filename) return '/default-meal.png' // 기본 이미지
  return `http://localhost:8080/api/v1/uploads/${filename}`
}
</script>

<style scoped>
.meal-card-layout {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 24px;
}

.meal-image {
  /* width: 100%;
  height: 160px; */
  object-fit: cover;
  border-radius: 12px;
}

</style>
