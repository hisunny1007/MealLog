<template>
  <div class="black-bg" v-if="isOpen">
    <div class="white-bg">
      <p>{{ message }}</p>

      <div class="btn-group">
        <button @click="$emit('confirm')">확인</button>
        <button @click="$emit('close')">취소</button>
      </div>
    </div>
  </div>
</template>

<script setup>
// 부모로부터 받을 데이터 정의
defineProps({
  isOpen: Boolean,
  message: String,
})

// 부모에게 보낼 신호(이벤트) 정의
defineEmits(['close', 'confirm'])
</script>

<style scoped>
.black-bg {
  width: 100%;
}
</style>
