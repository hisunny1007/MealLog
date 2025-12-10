<!-- 이게 메인임!! 여기 컴포넌트 갈아끼면 됨!! 그 header제외하고 아래 메인임  -->
 <!-- router-view만 Main.vue에서 받아서 렌더되게 하면 끝임 -->
<template>
  <main class="main-container">
    <router-view />
  </main>
</template>

<script setup></script>

<style scoped>
.main-container {
  flex: 1;
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 24px;
  box-sizing: border-box;
}
</style>