<template>
  <div class="card add-card" @click="$emit('click')">
    <p>식단을 추가해 보세요!</p>
    <div class="plus">+</div>
  </div>
</template>

<script setup></script>

<style scoped>
.add-card {
  width: 654px;
  height: 259px;
  left: 517px;
  top: 224px;
  background-color: var(--card-bg);
  border: 2px dash var(--border);
  border-radius: 54px;
  padding: 40px;
  text-align: center;
  cursor: pointer;
}
.plus {
  font-size: 32px;
}
</style>
