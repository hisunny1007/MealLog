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
    
    // ✅ accessToken으로 수정!
    const token = response.data.accessToken  // ← token이 아니라 accessToken
    const user = response.data.user || { email: email.value } // user 없으면 임시로 이메일 저장
    
    console.log('🟣 [로그인] 추출한 token:', token)
    console.log('🟣 [로그인] 추출한 user:', user)

    if (!token) {
      console.error('❌ 토큰이 undefined입니다!')
      alert('서버 응답에 토큰이 없습니다.')
      return
    }

    authStore.setLogin(token, user)
    
    console.log('🟣 [로그인] authStore.token:', authStore.token)
    console.log('🟣 [로그인] localStorage token:', localStorage.getItem('token'))

    alert('로그인 성공!')
    router.push('/')
  } catch (error) {
    console.error('❌ [로그인] 실패:', error)
    alert('로그인 실패: 이메일 또는 비밀번호를 확인해주세요.')
  }
}
</script>
<!-- <script setup>
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

    // 1. 토큰 저장 및 전역 상태 업데이트
    const token = response.data.token
    const user = response.data.user

    authStore.setLogin(token, user)
    // setAuthHeader(token) // axios 인스턴스에 토큰 추가

    alert('로그인 성공!')
    router.push('/') // 홈 화면으로 이동
  } catch (error) {
    console.error('로그인 실패:', error)
    alert('로그인 실패: 이메일 또는 비밀번호를 확인해주세요.')
  }
}
</script> -->

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
  color: #444;
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
