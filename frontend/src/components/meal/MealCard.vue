<!-- MealCard는 껍데기(디자인)를 제공하고 내용은 slot을 통해 부모가 넣는 구조 -->
<template>
  <div class="meal-card p-4 rounded-4 ">
    <slot></slot>
  </div>
</template>

<script setup></script>

<style scoped>
.meal-card {
  background-color: #faf7f5;
}
</style>