<template>
  <nav class="navbar custom-navbar">
    <div class="container d-flex justify-content-between align-items-center">
      <!-- 메인 로고 - 클릭시 홈으로 -->
      <router-link to="/" class="navbar-brand d-flex align-items-center">
        <img src="@/assets/logo.png" alt="logo" class="logo" />
      </router-link>

      <!-- 오른쪽 메뉴 -->
      <div class="d-flex gap-2">
        <!-- 로그인 상태 -->
        <template v-if="authStore.isAuthenticated">
          <router-link to="/point-shop" class="btn btn-brown-outline"> 포인트샵 </router-link>
          <router-link to="/mypage" class="btn btn-brown-outline"> 마이페이지 </router-link>
          <button @click="handleLogout" class="btn btn-brown">로그아웃</button>
        </template>

        <!-- 비로그인 상태 -->
        <template v-else>
          <router-link to="/login" class="btn btn-brown"> 로그인 </router-link>
        </template>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'
import { toast } from 'vue3-toastify'

const authStore = useAuthStore()
const router = useRouter()

const handleLogout = () => {
  authStore.setLogout()
  toast.info('로그아웃 되었습니다.')
  router.push('/')
}
</script>

<style scoped>

.custom-navbar {
  background: #ffffff;
  padding: 14px 0;
  box-shadow: 0 4px 12px rgba(75, 46, 30, 0.06);
}

.logo {
  width: 80px;
}

/* 메인 버튼 */
.btn-brown {
  background: var(--main-brown);
  color: #fff;
  border: none;
  font-weight: 600;
  padding: 6px 16px;
  border-radius: 999px;
  transition: all 0.15s ease;
}

.btn-brown:hover {
  background: #3f2619;
  color: #fff;
}

.btn-brown:active {
  transform: scale(0.96);
}

/* 아웃라인 버튼 */
.btn-brown-outline {
  background: transparent;
  color: var(--main-brown);
  border: 1px solid rgba(75, 46, 30, 0.4);
  font-weight: 600;
  padding: 6px 16px;
  border-radius: 999px;
  transition: all 0.15s ease;
}

.btn-brown-outline:hover {
  background: rgba(75, 46, 30, 0.06);
}

/* 버튼 기본 포커스 제거 */
.btn:focus {
  box-shadow: none;
}
</style>
