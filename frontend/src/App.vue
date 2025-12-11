<template>
  <div class="app">
    <Header />     <!-- 상단 고정 -->
    <Main />       <!-- 페이지 변경은 Main 내부에서만 일어남! 여기 안에서 routerview 바뀜  -->
  </div>
</template>

<script setup>
import Header from '@/components/common/Header.vue'
import Main from '@/components/common/Main.vue' 
</script>

<style scoped>
.app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}
</style>