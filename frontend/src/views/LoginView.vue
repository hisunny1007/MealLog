<template>
  <div class="login-container">
    <h1>Meal Log</h1>
    <p>오늘 먹은 것부터 시작해볼까요?</p>

    <form @submit.prevent="handleLogin" class="login-form">
      <div class="input-group">
        <input type="email" placeholder="이메일" id="email" v-model="email" required />
      </div>
      <div class="input-group">
        <input type="password" placeholder="비밀번호" id="password" v-model="password" required />
      </div>

      <button type="submit" class="btn btn-login">로그인</button>
      <button type="button" @click="router.push('/signup')" class="btn btn-signup">회원가입</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '@/api/authApi'
import { useAuthStore } from '@/stores/authStore'

const email = ref('')
const password = ref('')
const router = useRouter()
const authStore = useAuthStore()

const handleLogin = async () => {
  try {
    const credentials = { email: email.value, password: password.value }
    const response = await login(credentials)

    const token = response.data.accessToken

    authStore.setLogin(token)
    await authStore.fetchUser()

    alert('로그인 성공!')
    router.push('/') // 홈 화면으로 이동
  } catch (error) {
    console.error('로그인 실패:', error)
    alert('로그인 실패: 이메일 또는 비밀번호를 확인해주세요.')
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 50px 20px;
  min-height: 100vh;
  background-color: #f8f8f8;
}

h1 {
  font-size: 2.5em;
  margin-bottom: 5px;
  color: #4b2e1e;
}

p {
  color: #4b2e1e;
}

.login-form {
  display: flex;
  width: 300px;
  flex-direction: column;
  gap: 15px;
}

.input-group {
  text-align: left;
  margin-bottom: 0;
}
.input-group label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
  /* color: #444; */
}
.input-group input {
  width: 100%;
  padding: 12px 10px;
  border: 1px solid #ccc;
  border-radius: 6px;
  box-sizing: border-box;
}
.btn-login {
  background-color: #5c4533;
  color: white;
  padding: 12px;
}
.btn-signup {
  background-color: #a99a8d;
  color: white;
  margin-top: 10px;
  padding: 12px;
}
</style>
