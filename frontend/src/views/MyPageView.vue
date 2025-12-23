<template>
  <div class="mypage-container">
    <h2 class="text-center fw-bold mb-4">마이 페이지</h2>
    <div class="mypage-content">
      <div class="user-profile-card">
        <div class="icon-wrapper profile-main">
        <img src="@/assets/profile-icon.png" alt="프로필" class="custom-icon" />

        </div>

        <div class="user-info">
          <p class="nickname">{{ authStore.user?.nickname }} 님</p>
          <p class="point-balance">{{ authStore.user?.rewardPoint.toLocaleString() ?? 0 }} P</p>
          <button class="edit-btn" @click="goToEditProfile">회원정보 수정</button>
        </div>
      </div>

      <div class="divider"></div>

      <nav class="mypage-nav">
        <a @click="router.push('/point-shop')" class="nav-item">
          <div class="icon-wrapper">
            <img src="@/assets/pointshop-icon.png" alt="포인트샵" class="custom-icon" />
          </div>
          <span class="nav-text">포인트샵</span>
        </a>

        <a @click="router.push('/point-history')" class="nav-item">
          <div class="icon-wrapper">
            <img src="@/assets/pointhistory-icon.png" alt="내역" class="custom-icon" />
          </div>
          <span class="nav-text">포인트 교환 내역</span>
        </a>

        <a @click="goToInquiry" class="nav-item">
          <div class="icon-wrapper">
            <img src="@/assets/inquiry-icon.png" alt="문의" class="custom-icon" />
          </div>
          <span class="nav-text">문의하기</span>
        </a>
      </nav>

      <div class="bottom-links">
        <button class="deactivate-link">회원 탈퇴</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'

const router = useRouter()
const authStore = useAuthStore()

onMounted(() => {
  // 컴포넌트가 마운트될 때 최신 사용자 정보를 가져옵니다.
  authStore.fetchUser()
  // 디버깅: 마이페이지가 마운트될 때 authStore.user의 실제 값을 확인합니다.
  console.log('User object in MyPageView on mount:', authStore.user)
})

// 사용자 정보가 없으면, 홈으로 이동
if (!authStore.isAuthenticated) {
  router.replace('/login')
}

const goToEditProfile = () => {
  alert('회원정보 수정 페이지로 이동')
  // router.push('/mypage/edit');
}

const goToInquiry = () => {
  alert('MealLog@naver.com 으로 문의부탁드립니다.')
  // router.push('/inquiry');
}
</script>

<style scoped>


.icon-wrapper {
  width: 60px;
  height: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 20px;
  flex-shrink: 0;
}

.icon-wrapper.profile-main {
  width: 70px;
  height: 70px;
  margin-left: 10px;
  margin-right: 25px;
}

.mypage-content {
  max-width: 500px;
  width: 100%;
  background-color: #fff;
  padding: 40px;
  border-radius: 20px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
}

.mypage-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 100vh;
  padding: 60px 20px;
  background-color: #faf7f4;
}


.user-profile-card {
  display: flex;
  align-items: center;
  padding: 10px 0;
}

.custom-icon {
  width: 100%;
  height: 100%;
  object-fit: contain;
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
  margin-bottom: 10px;
}

.edit-btn {
  background: none;
  border: none;
  /* color: #888; */
  font-size: 0.9em;
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

/* 네비게이션 */
.mypage-nav {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.nav-item {
  display: flex;
  align-items: center;
  padding: 15px 10px;
  text-decoration: none;
  font-size: 1.1em;
  cursor: pointer;
  transition: background-color 0.2s;
}
.nav-item:hover {
  background-color: #fafafa;
  border-radius: 5px;
}

  .nav-text {
  font-size: 1.05rem;
  font-weight: 500;
  color: #444;
}


/* 하단 탈퇴 링크 */
.bottom-links {
  margin-top: 50px;
  display: flex;
  justify-content: center;
}

.deactivate-link {
  background: none;
  border: none;
  /* color: #999; */
  cursor: pointer;
  font-size: 1em;
  text-decoration: none;
}
</style>
