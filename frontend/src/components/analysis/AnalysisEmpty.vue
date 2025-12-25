<template>
  <div
    class="analysis-page analysis-empty d-flex flex-column justify-content-center align-items-center text-center"
  >
    <p class="empty-title mb-2">아직 오늘의 식단 기록이 없어요 🍽️</p>
    <p class="empty-desc mb-4">식단을 기록하면 분석 리포트를 확인할 수 있어요!</p>

    <RouterLink :to="`/meals/create/${date}`" class="btn-brown rounded-pill px-5 py-2">
      식단 기록하러 가기
    </RouterLink>
  </div>
</template>

<script setup>
defineProps({ date: String })
</script>

<style>
.analysis-empty {
  min-height: 60vh;
}
.empty-title {
  font-size: 1.3rem;
  font-weight: 700;
  color: #a57c5e;
}
.btn-brown {
  background: #a57c5e;
  color: #fff;
  border: none;
  text-decoration: none;
  display: inline-block;
}
.btn-brown:hover {
  background: #8e6a50;
  color: #fff;
  text-decoration: none;
}
.btn-outline-brown {
  border: 1px solid #a57c5e;
  color: #a57c5e;
  background: transparent;
}
.btn-outline-brown:hover {
  background: #a57c5e;
  color: #fff;
}
</style>
