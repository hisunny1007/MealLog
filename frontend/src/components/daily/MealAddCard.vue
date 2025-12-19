<template>
  <div class="card-soft add-card" @click="$emit('click')">
    <p>식단을 추가해 보세요!</p>
    <div class="plus">+</div>
  </div>
</template>

<script setup></script>

<style scoped>
.add-card {
  padding: 40px;
  text-align: center;
  cursor: pointer;
}
.plus {
  font-size: 32px;
  color: var(--main-brown);
}
</style>
