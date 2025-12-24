<template>
  <div v-if="isOpen" class="modal-overlay" @click.self="$emit('close')">
    <div class="modal-content">
      <div class="modal-body">
        <h3 class="modal-title">{{ title }}</h3>
        <p class="modal-text">{{ message }}</p>
      </div>

      <div class="modal-footer-btns">
        <button v-if="type === 'confirm'" class="btn-cancel" @click="$emit('close')">취소</button>

        <button class="btn-confirm" @click="$emit('confirm')">확인</button>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  isOpen: Boolean,
  title: String,
  message: String,
  // confirm 버튼 2개, alert 버튼 1개
  type: {
    type: String,
    default: 'confirm',
  },
})

defineEmits(['confirm', 'close'])
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 2000;
}
.modal-content {
  background: white;
  padding: 2rem;
  border-radius: 1.5rem;
  width: 320px;
  text-align: center;
  border: none;
}
.modal-title {
  font-size: 1.25rem;
  font-weight: 700;
  color: #4a3f35;
  margin-bottom: 1rem;
}
.modal-text {
  color: #666;
  margin-bottom: 1.5rem;
  line-height: 1.5;
}

.modal-footer-btns {
  display: flex;
  gap: 10px;
  justify-content: center;
}

.btn-confirm {
  background: #4a3f35;
  color: white;
  border: none;
  padding: 0.5rem 1.5rem;
  border-radius: 10px;
  flex: 1;
}
.btn-cancel {
  background: #eee;
  color: #666;
  border: none;
  padding: 0.5rem 1.5rem;
  border-radius: 10px;
  flex: 1;
}
</style>
