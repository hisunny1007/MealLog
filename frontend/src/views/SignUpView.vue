<template>
  <div class="signup-container">
    <h1>가입하기</h1>

    <form v-if="step === 1" @submit.prevent="handleSignupStep1" class="signup-form">
      <div class="input-group">
        <input
          type="email"
          placeholder="이메일 입력"
          id="email"
          v-model="step1Data.email"
          required
        />
      </div>
      <div class="input-group">
        <input
          type="password"
          placeholder="비밀번호 입력"
          id="password"
          v-model="step1Data.password"
          required
        />
        <small v-if="step1Data.password" class="error-msg">8 ~ 16자리를 입력해 주세요.</small>
      </div>
      <div class="input-group">
        <input
          type="password"
          placeholder="비밀번호 재입력"
          id="confirm-password"
          v-model="confirmPassword"
          required
        />
        <p
          v-if="step1Data.password && confirmPassword && step1Data.password !== confirmPassword"
          class="error-msg"
        >
          비밀번호가 일치하지 않습니다.
        </p>
      </div>
      <div class="input-group">
        <input
          type="text"
          placeholder="닉네임"
          id="nickname"
          v-model="step1Data.nickname"
          required
        />
        <small v-if="step1Data.nickname" class="error-msg">2 ~ 12자를 입력해 주세요.</small>
      </div>

      <button type="submit" :disabled="step1Data.password !== confirmPassword" class="btn btn-next">
        다음
      </button>
    </form>

    <form v-else @submit.prevent="handleSignupStep2" class="signup-form">
      <div class="form-section align-start">
        <label>성별</label>
        <div class="radio-group">
          <button
            type="button"
            :class="{ selected: step2Data.gender === 'M' }"
            @click="step2Data.gender = 'M'"
          >
            남
          </button>
          <button
            type="button"
            :class="{ selected: step2Data.gender === 'F' }"
            @click="step2Data.gender = 'F'"
          >
            여
          </button>
        </div>
      </div>

      <div class="form-section align-start">
        <label>운동 목표</label>
        <div class="radio-group goal-group">
          <button
            type="button"
            :class="{ selected: step2Data.exerciseGoal === 'DIET' }"
            @click="step2Data.exerciseGoal = 'DIET'"
          >
            다이어트
          </button>
          <button
            type="button"
            :class="{ selected: step2Data.exerciseGoal === 'MUSCLE' }"
            @click="step2Data.exerciseGoal = 'MUSCLE'"
          >
            근육 증가
          </button>
        </div>
      </div>

      <div class="form-section frequency-section">
        <div class="frequency-row">
          <label>주 운동 빈도</label>
          <div class="frequency-buttons">
            <div class="radio-group frequency-group">
              <button
                v-for="n in 5"
                :key="n"
                type="button"
                :class="{ selected: step2Data.exerciseFrequency === n.toString() }"
                @click="step2Data.exerciseFrequency = n.toString()"
              >
                {{ n }}
              </button>
            </div>
          </div>
        </div>

        <div class="frequency-labels">
          <span class="label-count">횟수</span>
          <span class="label-under">이하</span>
          <span class="label-over">이상</span>
        </div>
      </div>

      <div class="input-group-inline last-input-group">
        <div class="input-wrapper">
          <input
            type="text"
            inputmode="numeric"
            placeholder="키"
            v-model.number="step2Data.height"
            required
          />
        </div>
        <div class="input-wrapper">
          <input
            type="text"
            inputmode="numeric"
            placeholder="몸무게"
            v-model.number="step2Data.weight"
            required
          />
        </div>
        <div class="input-wrapper">
          <input
            type="text"
            inputmode="numeric"
            placeholder="나이"
            v-model.number="step2Data.age"
            required
          />
        </div>
      </div>

      <button type="submit" class="btn btn-complete">회원가입</button>
    </form>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { signup, signupComplete } from '@/api/authApi'

const router = useRouter()

// Step 1: 상태 관리
const step = ref(1) // 1 또는 2
const userId = ref(null) // 1단계 성공 후 백엔드에서 받은 사용자 ID
const confirmPassword = ref('')

const step1Data = reactive({
  email: '',
  password: '',
  nickname: '',
})

// Step 2: 상태 관리
const step2Data = reactive({
  gender: '',
  age: '',
  height: '',
  weight: '',
  exerciseGoal: '',
  exerciseFrequency: '',
})

// **TODO 3: 회원가입 1단계 API 연동 로직 구현**
const handleSignupStep1 = async () => {
  if (step1Data.password !== confirmPassword.value) {
    alert('비밀번호가 일치하지 않습니다.')
    return
  }

  try {
    const response = await signup(step1Data)

    // 1단계 성공 시, 백엔드에서 사용자 ID를 반환
    userId.value = response.data.userId

    alert('회원가입 1단계 성공! 추가 정보를 입력해주세요.')
    step.value = 2 // 다음 단계로 이동
  } catch (error) {
    console.error('회원가입 1단계 실패:', error)
    alert('회원가입 실패: 이미 존재하는 이메일입니다.')
  }
}

// TODO 3: 회원가입 2단계 API 연동 로직 구현
const handleSignupStep2 = async () => {
  if (!userId.value) {
    alert('잘못된 접근입니다. 다시 시작해주세요.')
    router.push('/signup')
    return
  }

  try {
    await signupComplete(userId.value, step2Data)

    alert('회원가입이 완료되었습니다. 로그인해 주세요.')
    router.push('/login') // 로그인 페이지로 이동
  } catch (error) {
    console.error('회원가입 2단계 실패:', error)
    alert('정보 저장에 실패했습니다. 다시 시도해 주세요.')
  }
}
</script>

<style scoped>
.form-section {
  position: relative;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 20px;
}

.form-section label {
  font-weight: bold;
  /* color: #444; */
  width: 120px;
  flex-shrink: 0;
  margin-bottom: 0;
}

.frequency-section {
  flex-direction: column;
  align-items: flex-start;
  padding-bottom: 30px;
  position: relative;
}

.frequency-row {
  display: flex;
  width: 100%;
  align-items: center;
  justify-content: space-between;
}

.frequency-group button {
  width: 35px;
  height: 35px;
  padding: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.frequency-buttons {
  flex-grow: 1;
  text-align: right;
}

.frequency-group {
  gap: 8px;
  display: inline-flex;
}

.frequency-labels {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  font-size: 0.9em;
  /* color: #888; */
  pointer-events: none;
}

.frequency-labels span {
  position: absolute;
  bottom: 0;
  text-align: center;
}

.frequency-labels .label-count {
  left: -10px;
  width: 10%;
}

.frequency-labels .label-under {
  left: 120px;
}
.frequency-labels .label-over {
  right: 60px;
}

.signup-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 50px 20px;
  min-height: 100vh;
  background-color: #f8f8f8;
}
h1 {
  font-size: 2em;
  margin-bottom: 40px;
}
.signup-form {
  max-width: 450px;
  width: 100%;
  text-align: left;
  display: flex;
  flex-direction: column;
  gap: 20px;
}
.form-section {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

.form-section label {
  font-weight: bold;
  /* color: #444; */
  width: 120px;
  flex-shrink: 0;
}

.input-group input {
  width: 100%;
  padding: 12px 10px;
  border: none;
  border-bottom: 1px solid #aaa;
  box-sizing: border-box;
  font-size: 1em;
}

.email-input-group input {
  flex-grow: 1;
}

.error-msg {
  color: red;
  font-size: 0.8em;
  margin-top: 5px;
}
.btn-next,
.btn-complete {
  background-color: #a99a8d;
  color: white;
  width: 100%;
  padding: 12px;
  border: none;
  border-radius: 6px;
  margin-top: 20px;
}

.radio-group {
  display: flex;
  gap: 15px;
  flex-grow: 1;
}

.radio-group button {
  background-color: #eaeaea;
  border: 1px solid #ccc;
  padding: 8px 15px;
  margin-right: 10px;
  border-radius: 20px;
  cursor: pointer;
  /* color: #333; */
  flex-grow: 0;
}

.radio-group button.selected {
  background-color: #79665a;
  color: white;
  border-color: #79665a;
}

.radio-group button.selected {
  background-color: #79665a;
  color: white;
  font-weight: bold;
}

.input-group-inline {
  display: flex;
  gap: 20px;
}
.input-group-inline input {
  flex-grow: 1;
  max-width: 135px;
}
</style>
