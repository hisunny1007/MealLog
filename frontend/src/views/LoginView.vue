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
import { toast } from 'vue3-toastify'

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

    toast.success('로그인 성공!')
    router.push('/') // 홈 화면으로 이동
  } catch (error) {
    console.error('로그인 실패:', error)
    toast.info('로그인 실패: 이메일 또는 비밀번호를 확인해주세요.')
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 80px 20px;

}

h1 {
  font-size: 2.5em;
  margin-bottom: 5px;
  color: var(--main-brown);
}

p {
  color: var(--main-brown);
}

.login-form {
  display: flex;
  width: 400px;
  display: flex;
  flex-direction: column;
  gap: 25px;
}

.input-group {
  display: flex;
  flex-direction: column;
  text-align: left;
}

.input-group label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}
.input-group input {

  width: 100%;
  padding: 10px 5px;
  border: none;
  border-bottom: 1px solid #ccc;
  background: transparent;
  font-size: 1rem;
  outline: none;
  transition: border-color 0.3s ease;
  border-radius: 0;
}

.input-group input:focus {
  border-bottom-color: #5d4037;
}

.btn-login {
  background-color: var(--main-brown);
  color: white;
  padding: 12px;
  border: none;
  border-radius: 6px;
}

.btn-login:hover {
  background-color: #ede0d4;
}
.btn-signup {
  background-color: var(--border);
  color: white;
  margin-top: 10px;
  padding: 12px;
  border: none;
  border-radius: 6px;
}

.btn-signup:hover {
  background-color: #ede0d4;
}
</style>
