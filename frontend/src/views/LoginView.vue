<template>
  <div class="login-container">

    <h1>Meal Log</h1>
    <p>오늘 먹은 것부터 시작해볼까요?</p>

    <form @submit.prevent="handleLogin" class="login-form">
      <div class="input-group">
        <label for="email">이메일</label>
        <input type="email" id="email" v-model="email" required>
      </div>
      <div class="input-group">
        <label for="password">비밀번호</label>
        <input type="password" id="password" v-model="password" required>
      </div>

      <button type="submit" class="btn btn-login">로그인</button>
      <button type="button" @click="router.push('/signup')" class="btn btn-signup">회원가입</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { login, setAuthHeader } from '@/api/auth';
import { useAuthStore } from '@/stores/auth';

const email = ref('');
const password = ref('');
const router = useRouter();
const authStore = useAuthStore();

const handleLogin = async () => {
  try {
    const credentials = { email: email.value, password: password.value };
    const response = await login(credentials);

    // 1. 토큰 저장 및 전역 상태 업데이트
    const token = response.data.token;
    const user = response.data.user;

    authStore.setLogin(token, user);
    setAuthHeader(token); // axios 인스턴스에 토큰 추가

    alert('로그인 성공!');
    router.push('/'); // 홈 화면으로 이동
  } catch (error) {
    console.error('로그인 실패:', error);
    alert('로그인 실패: 이메일 또는 비밀번호를 확인해주세요.');
  }
};
</script>

<style scoped>
/* UI 시안에 맞춘 스타일 적용 필요 */
.login-container { max-width: 400px; margin: 50px auto; text-align: center; }
.logo-area { margin-bottom: 20px; }
.logo { width: 100px; }
.input-group { margin-bottom: 15px; text-align: left; }
.input-group label { display: block; margin-bottom: 5px; }
.input-group input { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; }
.btn-login { background-color: #6474F2; color: white; }
.btn-signup { background-color: #A9A9A9; color: white; margin-top: 10px; }
</style>


