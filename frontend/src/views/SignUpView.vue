<template>
  <div class="signup-container">
    <h1 class="title">가입하기</h1>

    <form v-if="step === 1" @submit.prevent="handleSignupStep1" class="signup-form">

      <div class="input-group email-group">
        <input
          type="email"
          placeholder="이메일 입력"
          id="email"
          v-model="step1Data.email"
          @input="isEmailChecked = false"
          required
        />
        <button type="button" class="check-btn" @click="checkEmailDup">중복 확인</button>
      </div>

      <div class="input-group">
        <input
          type="password"
          placeholder="비밀번호 입력"
          id="password"
          v-model="step1Data.password"
          required
        />
        <small v-if="step1Data.password && step1Data.password.length < 8" class="msg error">
          8 ~ 16자리를 입력해 주세요.
        </small>
        <small v-else-if="step1Data.password.length >= 8" class="msg success">
          8자리 이상입니다.
        </small>
      </div>

      <div class="input-group">
        <input
          type="password"
          placeholder="비밀번호 재입력"
          id="confirm-password"
          v-model="confirmPassword"
          required
        />
        <small v-if="confirmPassword && step1Data.password !== confirmPassword" class="msg error">
          비밀번호가 일치하지 않습니다.
        </small>
      </div>

      <div class="input-group">
        <input
          type="text"
          placeholder="닉네임"
          id="nickname"
          v-model="step1Data.nickname"
          required
        />
        <small v-if="step1Data.nickname && step1Data.nickname.length < 2" class="msg error">
          2 ~ 12자를 입력해 주세요.
        </small>
      </div>

      <button
        type="submit"
        :disabled="!isStep1Valid"
        class="btn btn-next"
      >
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
import { ref, reactive, computed } from 'vue'
import { useRouter } from 'vue-router'
import { signup, signupComplete, checkEmailDuplication } from '@/api/authApi'

const router = useRouter()

// Step 1: 상태 관리
const step = ref(1)
const userId = ref(null)
const confirmPassword = ref('')
const isEmailChecked = ref(false)

const step1Data = reactive({
  email: '',
  password: '',
  nickname: '',
})

// 유효성 검사 (Computed)
const isStep1Valid = computed(() => {
  return (
    isEmailChecked.value &&
    step1Data.password.length >= 8 &&
    step1Data.password.length <= 16 &&
    step1Data.password === confirmPassword.value &&
    step1Data.nickname.length >= 2 &&
    step1Data.nickname.length <= 12
  )
})

// 이메일 중복 확인
const checkEmailDup = async () => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!emailRegex.test(step1Data.email)) {
    alert('유효한 이메일 형식을 입력해주세요.')
    return
  }
  try {
    // 실제 API 연동 시 주석 해제
    // await checkEmailDuplication(step1Data.email)
    isEmailChecked.value = true
    alert('사용 가능한 이메일입니다.')
  } catch (error) {
    alert('이미 사용 중인 이메일입니다.')
  }
}

// Step 1 제출
const handleSignupStep1 = async () => {
  if (!isStep1Valid.value) {
    alert('입력 정보를 다시 확인해주세요.')
    return
  }
  try {
    // const response = await signup(step1Data)
    // userId.value = response.data.userId
    step.value = 2
  } catch (error) {
    alert('가입 진행 중 오류가 발생했습니다.')
  }
}

// Step 2 상태 및 제출 로직 (기존 유지)
const step2Data = reactive({
  gender: '',
  age: '',
  height: '',
  weight: '',
  exerciseGoal: '',
  exerciseFrequency: '',
})

const handleSignupStep2 = async () => {
  if (!userId.value) {
    alert('잘못된 접근입니다. 다시 시작해주세요.')
    router.push('/signup')
    return
  }
  try {
    await signupComplete(userId.value, step2Data)
    alert('회원가입이 완료되었습니다. 로그인해 주세요.')
    router.push('/login')
  } catch (error) {
    console.error('회원가입 2단계 실패:', error)
    alert('정보 저장에 실패했습니다. 다시 시도해 주세요.')
  }
}
</script>

<style scoped>
/* 전체 컨테이너 및 폰트 설정 */
.signup-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 80px 20px;
  min-height: 100vh;
  background-color: #faf7f4; /* 요청하신 배경색 */
  color: #333;
}

.title {
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 50px;
  color: #000;
}

.signup-form {
  max-width: 400px;
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 25px; /* 입력 필드 간 간격 */
}

/* 입력 필드 공통 스타일 (피그마 시안 반영) */
.input-group {
  position: relative;
  display: flex;
  flex-direction: column;
  width: 100%;
}

.input-group input {
  width: 100%;
  padding: 10px 5px;
  border: none;
  border-bottom: 1px solid #ccc; /* 하단 라인만 표시 */
  background: transparent;
  font-size: 1rem;
  outline: none;
  border-radius: 0; /* 기본 둥글기 제거 */
  transition: border-color 0.3s;
}

.input-group input:focus {
  border-bottom-color: #5d4037; /* 포커스 시 갈색 라인 */
}

/* 이메일 그룹 (Flexbox 사용) */
.email-group {
  flex-direction: row;
  align-items: center; /* 수직 중앙 정렬 */
  border-bottom: 1px solid #ccc; /* 그룹 전체에 라인 적용 */
}

.email-group input {
  border-bottom: none; /* 내부 인풋 라인 제거 */
  flex-grow: 1; /* 인풋이 남은 공간 차지 */
}

.email-group:focus-within {
  border-bottom-color: #5d4037;
}

/* 중복 확인 버튼 (시안 스타일) */
.check-btn {
  background-color: #fff;
  border: 1px solid #999;
  border-radius: 4px;
  padding: 5px 10px;
  font-size: 0.85rem;
  color: #333;
  cursor: pointer;
  white-space: nowrap;
  margin-left: 10px;
  margin-bottom: 5px; /* 라인 위로 살짝 띄우기 */
}

.check-btn:hover {
  background-color: #f0f0f0;
}

/* 에러 및 성공 메시지 스타일 */
.msg {
  font-size: 0.75rem;
  margin-top: 6px;
  padding-left: 5px;
  text-align: left;
}

.error {
  color: #ff5252; /* 빨간색 */
}

.success {
  color: #2196f3; /* 파란색 */
}

/* 다음 버튼 */
.btn-next,
.btn-complete {
  background-color: #7a6658; /* 메인 갈색 */
  color: white;
  width: 100%;
  padding: 15px;
  border: none;
  border-radius: 8px;
  margin-top: 30px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
}

.btn-next:disabled {
  background-color: #d7ccc8;
  cursor: not-allowed;
}

/* Step 2 관련 스타일 (기존 유지) */
.form-section {
  position: relative;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 20px;
}
.form-section label {
  font-weight: bold;
  width: 100px;
  flex-shrink: 0;
  margin-bottom: 0;
}
.radio-group {
  display: flex;
  gap: 10px;
  flex-grow: 1;
}
.radio-group button {
  background-color: white;
  border: 1px solid #ccc;
  padding: 8px 15px;
  border-radius: 20px;
  cursor: pointer;
  flex-grow: 1;
}
.radio-group button.selected {
  background-color: #7a6658;
  color: white;
  border-color: #7a6658;
  font-weight: bold;
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
.frequency-buttons {
  flex-grow: 1;
  text-align: right;
}
.frequency-group {
  gap: 5px;
  display: inline-flex;
}
.frequency-group button {
  width: 30px;
  height: 30px;
  padding: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-grow: 0;
}
.frequency-labels {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  font-size: 0.8em;
  pointer-events: none;
}
.frequency-labels span {
  position: absolute;
  bottom: 0;
  color: #888;
}
.frequency-labels .label-count { left: 0; }
.frequency-labels .label-under { right: 80px; }
.frequency-labels .label-over { right: 10px; }

.input-group-inline {
  display: flex;
  gap: 15px;
}
.input-wrapper {
  flex: 1;
}
.input-wrapper input {
  border-bottom: 1px solid #ccc;
  text-align: center;
}
</style>
