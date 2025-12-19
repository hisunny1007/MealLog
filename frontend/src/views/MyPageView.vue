<template>
  <div class="mypage-container">
    <div class="mypage-content">
      <h1>마이 페이지</h1>

      <div class="user-profile-card">
        <div class="profile-icon">👤</div>

        <div class="user-info">
          <p class="nickname">{{ authStore.user?.nickname }} 님</p>
          <p class="point-balance">{{ authStore.user?.currentPoint ?? 0 }} P</p>
          <button class="edit-btn" @click="goToEditProfile">회원정보 수정</button>
        </div>
      </div>

      <div class="divider"></div>

      <nav class="mypage-nav">
        <a @click="router.push('/pointshop')" class="nav-item">
          <i class="icon">🛒</i>
          <span>포인트샵</span>
        </a>
        <a @click="router.push('/pointhistory')" class="nav-item">
          <i class="icon">🪙</i>
          <span>포인트 교환 내역</span>
        </a>
        <a @click="goToInquiry" class="nav-item">
          <i class="icon">❓</i>
          <span>문의하기</span>
        </a>
      </nav>

      <div class="bottom-links">
        <button class="deactivate-link">회원 탈퇴</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'

const router = useRouter()
const authStore = useAuthStore()

// 사용자 정보가 없으면, 홈으로 이동
if (!authStore.isAuthenticated) {
  router.replace('/login')
}

const goToEditProfile = () => {
  alert('회원정보 수정 페이지로 이동')
  // router.push('/mypage/edit');
}

const goToInquiry = () => {
  alert('문의하기 페이지로 이동')
  // router.push('/inquiry');
}

const handleLogout = () => {
  authStore.setLogout()
  alert('로그아웃 되었습니다.')
  router.push('/')
}
</script>

<style scoped>
.mypage-container {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  min-height: 100vh;
  padding: 50px 20px;
  background-color: #f8f8f8;
}

.mypage-content {
  max-width: 600px;
  width: 100%;
  background-color: #fff;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

h1 {
  font-size: 2em;
  text-align: center;
  margin-bottom: 40px;
  color: #4b2e1e;
}

.user-profile-card {
  display: flex;
  align-items: flex-start;
  padding: 10px;
  margin-bottom: 20px;
}

.profile-icon {
  font-size: 40px;
  background-color: #e0e0e0;
  border-radius: 50%;
  width: 70px;
  height: 70px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 25px;
  flex-shrink: 0;
}

.user-info {
  display: flex;
  flex-direction: column;
  text-align: left;
  flex-grow: 1;
}

.nickname {
  font-size: 1.3em;
  font-weight: bold;
  margin-bottom: 5px;
}

.point-balance {
  font-size: 1.2em;
  color: #8d6e63;
  margin-bottom: 10px;
}

.edit-btn {
  background: none;
  border: none;
  color: #888;
  font-size: 0.9em;
  text-decoration: underline;
  cursor: pointer;
  text-align: left;
  padding: 0;
}

.divider {
  border: 0;
  height: 1px;
  background: #e0e0e0;
  margin: 30px 0;
}

.mypage-nav {
  display: flex;
  flex-direction: column;
  gap: 10px;
  color: #4b2e1e;
}

.nav-item {
  display: flex;
  align-items: center;
  padding: 15px 10px;
  text-decoration: none;
  color: #333;
  font-size: 1.1em;
  cursor: pointer;
  transition: background-color 0.2s;
}

.nav-item:hover {
  background-color: #fafafa;
  border-radius: 5px;
}

.icon {
  font-size: 1.5em;
  margin-right: 25px;
  width: 30px;
  text-align: center;
}

.bottom-links {
  text-align: center;
  margin-top: 40px;
  display: flex;
  justify-content: center;
  gap: 20px;
}

.logout-link,
.deactivate-link {
  background: none;
  border: none;
  color: #999;
  cursor: pointer;
  font-size: 1em;
  text-decoration: none;
}
</style>
