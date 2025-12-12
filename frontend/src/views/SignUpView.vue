<template>
  <div class="signup-container">
    <h1>가입하기</h1>

    <form v-if="step === 1" @submit.prevent="handleSignupStep1" class="signup-form">
      <div class="input-group">
        <label for="email">이메일 입력</label>
        <input type="email" id="email" v-model="step1Data.email" required>
      </div>
      <div class="input-group">
        <label for="password">비밀번호 입력</label>
        <input type="password" id="password" v-model="step1Data.password" required>
        <small>8 ~ 16자리를 입력해 주세요.</small>
      </div>
      <div class="input-group">
        <label for="confirm-password">비밀번호 재입력</label>
        <input type="password" id="confirm-password" v-model="confirmPassword" required>
        <p v-if="step1Data.password && confirmPassword && step1Data.password !== confirmPassword" class="error-msg">비밀번호가 일치하지 않습니다.</p>
      </div>
      <div class="input-group">
        <label for="nickname">닉네임</label>
        <input type="text" id="nickname" v-model="step1Data.nickname" required>
        <small>2 ~ 12자를 입력해 주세요.</small>
      </div>

      <button type="submit" :disabled="step1Data.password !== confirmPassword" class="btn btn-next">다음</button>
    </form>

    <form v-else @submit.prevent="handleSignupStep2" class="signup-form">
      <div class="form-section">
        <label>성별</label>
        <div class="radio-group">
          <button type="button" :class="{ selected: step2Data.gender === 'M' }" @click="step2Data.gender = 'M'">남</button>
          <button type="button" :class="{ selected: step2Data.gender === 'F' }" @click="step2Data.gender = 'F'">여</button>
        </div>
      </div>

      <div class="form-section">
        <label>운동 목표</label>
        <div class="radio-group">
          <button type="button" :class="{ selected: step2Data.exerciseGoal === 'DIET' }" @click="step2Data.exerciseGoal = 'DIET'">다이어트</button>
          <button type="button" :class="{ selected: step2Data.exerciseGoal === 'MUSCLE' }" @click="step2Data.exerciseGoal = 'MUSCLE'">근육 증가</button>
        </div>
      </div>

      <div class="form-section">
        <label>주 운동 빈도</label>
        <div class="radio-group frequency-group">
          <button v-for="n in 5" :key="n" type="button" :class="{ selected: step2Data.exerciseFrequency === n.toString() }" @click="step2Data.exerciseFrequency = n.toString()">{{ n }}</button>
          <span>이하 | 이상</span>
        </div>
      </div>

      <div class="input-group-inline">
        <input type="number" placeholder="키" v-model.number="step2Data.height" required>
        <input type="number" placeholder="몸무게" v-model.number="step2Data.weight" required>
        <input type="number" placeholder="나이" v-model.number="step2Data.age" required>
      </div>

      <button type="submit" class="btn btn-complete">회원가입</button>
    </form>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { signup, signupComplete } from '@/api/auth';

const router = useRouter();

// Step 1: 상태 관리
const step = ref(1); // 1 또는 2
const userId = ref(null); // 1단계 성공 후 백엔드에서 받은 사용자 ID
const confirmPassword = ref('');

const step1Data = reactive({
  email: '',
  password: '',
  nickname: '',

});

// Step 2: 상태 관리
const step2Data = reactive({
  gender: '',
  age: '',
  height: '',
  weight: '',
  exerciseGoal: '',
  exerciseFrequency: ''
});

// **TODO 3: 회원가입 1단계 API 연동 로직 구현**
const handleSignupStep1 = async () => {
  if (step1Data.password !== confirmPassword.value) {
    alert('비밀번호가 일치하지 않습니다.');
    return;
  }

  try {
    const response = await signup(step1Data);

    // 1단계 성공 시, 백엔드에서 사용자 ID를 반환
    userId.value = response.data.userId;

    alert('회원가입 1단계 성공! 추가 정보를 입력해주세요.');
    step.value = 2; // 다음 단계로 이동

  } catch (error) {
    console.error('회원가입 1단계 실패:', error);
    alert('회원가입 실패: 이미 존재하는 이메일입니다.');
  }
};

// **TODO 3: 회원가입 2단계 API 연동 로직 구현**
const handleSignupStep2 = async () => {
  if (!userId.value) {
    alert('잘못된 접근입니다. 다시 시작해주세요.');
    router.push('/signup');
    return;
  }

  try {
    await signupComplete(userId.value, step2Data);

    alert('회원가입이 완료되었습니다. 로그인해 주세요.');
    router.push('/login'); // 로그인 페이지로 이동

  } catch (error) {
    console.error('회원가입 2단계 실패:', error);
    alert('정보 저장에 실패했습니다. 다시 시도해 주세요.');
  }
};
</script>

<style scoped>
/* UI 시안에 맞춘 스타일 적용 필요 */
.signup-container { max-width: 500px; margin: 50px auto; padding: 20px; text-align: center; }
.signup-form { text-align: left; }
.input-group, .form-section { margin-bottom: 20px; }
.input-group input { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; }
.error-msg { color: red; font-size: 0.8em; margin-top: 5px; }
.btn-next, .btn-complete { background-color: #6474F2; color: white; width: 100%; padding: 12px; border: none; border-radius: 6px; margin-top: 20px; }


.radio-group button {
  background-color: #f0f0f0;
  border: 1px solid #ccc;
  padding: 8px 15px;
  margin-right: 10px;
  border-radius: 20px;
  cursor: pointer;
}
.radio-group button.selected {
  background-color: #6474F2;
  color: white;
  border-color: #6474F2;
}
.frequency-group button { width: 30px; padding: 5px; }
.input-group-inline { display: flex; gap: 10px; }
.input-group-inline input { flex-grow: 1; }
</style>
