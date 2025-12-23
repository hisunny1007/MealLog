<template>
  <div class="add-card-wrapper" @click="$emit('click')">
    <div class="d-flex align-items-center p-4">
      <div class="add-circle me-3">+</div>
      <div class="text-muted fw-medium">{{ label }} 식단을 기록해 보세요!</div>
    </div>
  </div>
</template>

<script setup>
defineProps({ label: String })
</script>

<style scoped>
.add-card-wrapper {
  background-color: rgba(165, 124, 94, 0.02);
  border: 1px dashed #e2d1c3;
  border-radius: 1rem;
  cursor: pointer;
  transition: all 0.2s;
}
.add-card-wrapper:hover {
  background-color: rgba(165, 124, 94, 0.05);
  border-color: #a57c5e;
  transform: translateX(5px);
}
.add-circle {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: white;
  border: 1px solid #e2d1c3;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #a57c5e;
  font-size: 20px;
  font-weight: bold;
}
</style>