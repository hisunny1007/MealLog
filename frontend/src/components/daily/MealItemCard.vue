<template>
  <div class="card meal-card">
    <img :src="meal.imageUrl" class="meal-img" />
    <div class="card-body">
      <h5>{{ meal.foodName }}</h5>
      <p>{{ meal.calories }} kcal</p>
      <p>{{ meal.carbs }} g</p>
      <p>{{ meal.protein }} g</p>
      <p>{{ meal.fat }} g</p>
    </div>
  </div>
</template>

<script setup>
defineProps({
  meal: Object,
})
</script>

<style scoped></style>
