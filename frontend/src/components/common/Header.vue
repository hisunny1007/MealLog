<template>
  <nav class="navbar navbar-light bg-white shadow-sm py-3">
    <div class="container d-flex justify-content-between align-items-center">

      <!-- 메인 로고 - 클릭시 홈으로 -->
      <router-link to="/" class="navbar-brand d-flex align-items-center">
        <img 
          src="@/assets/logo.png" 
          alt="logo" 
          class="me-2" 
          style="width: 120px;"
        />
      </router-link>

      <!-- 오른쪽 메뉴 - 로그인했을 시 변경 필요-->
      <div class="d-flex gap-3">
        <router-link to="/mypage" class="btn btn-outline-primary">
          마이페이지
        </router-link>

        <router-link to="/login" class="btn btn-primary">
          로그인
        </router-link>

      </div>

    </div>
  </nav>
</template>

<script setup>
import logo from "@/assets/logo.png"
</script>

<style scoped>
</style>