<template>
  <nav class="navbar navbar-light bg-white shadow-sm py-3">
    <div class="container d-flex justify-content-between align-items-center">
      <!-- 메인 로고 - 클릭시 홈으로 -->
      <router-link to="/" class="navbar-brand d-flex align-items-center">
        <img src="@/assets/logo.png" alt="logo" class="me-2" style="width: 80px" />
      </router-link>

      <!-- 오른쪽 메뉴 -->
      <div class="d-flex gap-3">
        <!-- 로그인 상태일 때 -->
        <template v-if="authStore.isAuthenticated">
          <router-link to="/mypage" class="btn btn-outline-primary"> 마이페이지 </router-link>
          <button @click="handleLogout" class="btn btn-primary">로그아웃</button>
        </template>

        <!-- 로그아웃 상태일 때 -->
        <template v-else>
          <router-link to="/login" class="btn btn-primary"> 로그인 </router-link>
        </template>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'

const authStore = useAuthStore()
const router = useRouter()

const handleLogout = () => {
  authStore.setLogout()
  alert('로그아웃 되었습니다.')
  router.push('/')
}
</script>

<style scoped></style>
