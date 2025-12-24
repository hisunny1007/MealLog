<template>
  <form @submit.prevent="handleFormSubmit" class="meal-form">
    <div class="row g-4">
      <!-- 왼쪽: 사진 / 검색 / 영양정보 -->
      <div class="col-md-6 position-relative">
        <!-- 이미지 등록 -->
        <div
          class="image-box rounded-4 mb-4 d-flex flex-column justify-content-center align-items-center"
          @click="triggerFile"
        >
          <input
            ref="fileInput"
            type="file"
            class="d-none"
            @change="onImageChange"
            accept="image/*"
          />

          <!-- 미리보기 -->
          <img v-if="previewUrl" :src="previewUrl" class="preview-img" />

          <!-- 기본 상태 -->
          <div v-else class="text-center text-muted">
            <div class="fs-1">+</div>
            <div>이미지 업로드</div>
          </div>
        </div>

        <div class="card-box mb-3 position-relative food-search-box">
          <div class="section-title">음식</div>

          <input
            type="text"
            class="form-control mb-2"
            v-model="keyword"
            placeholder="음식 검색"
            @input="onKeyWordChange"
            @keydown.enter.prevent="onEnterSelect"
          />

          <!-- 자동완성 -->
          <ul v-if="foods.length" class="list-group autocomplete">
            <li
              v-for="food in foods"
              :key="food.id"
              class="list-group-item list-group-item-action"
              @click="selectFood(food)"
            >
              {{ food.name }}
            </li>
          </ul>
          <!-- keyword-사용자 입력, foods-서버 검색 결과, foodId-선택 확정 여부 (직접 입력이면 foodId===null) -->
          <div
            v-if="!isSearching && keyword && foods.length === 0 && !form.foodId"
            class="text-muted small mb-3"
          >
            검색 결과가 없습니다. 음식을 직접 추가할 수 있습니다.
          </div>
        </div>

        <!-- 영양 정보 -->
        <div class="card-box">
          <div class="section-title">영양 정보</div>

          <div class="row g-2">
            <div class="col-6">
              <label class="form-label">음식 이름</label>
              <input v-model="form.foodName" class="form-control" placeholder="음식 이름 (필수)" />
            </div>
            <div class="col-6">
              <label class="form-label">칼로리 kcal</label>
              <input
                v-model="form.calories"
                type="number"
                step="any"
                class="form-control"
                placeholder="칼로리 kcal"
              />
            </div>
            <div class="col-4">
              <label class="form-label">탄수화물 g</label>
              <input
                v-model="form.carbs"
                type="number"
                step="any"
                class="form-control"
                placeholder="탄수 g"
              />
            </div>
            <div class="col-4">
              <label class="form-label">단백질 g</label>
              <input
                v-model="form.protein"
                type="number"
                step="any"
                class="form-control"
                placeholder="단백질 g"
              />
            </div>
            <div class="col-4">
              <label class="form-label">지방 g</label>
              <input
                v-model="form.fat"
                type="number"
                step="any"
                class="form-control"
                placeholder="지방 g"
              />
            </div>
          </div>
        </div>
      </div>

      <!-- 오른쪽 : 분류 / 점수 / 메모 -->
      <div class="col-md-6">
        <!-- 분류 -->
        <div class="card-box mb-3">
          <div class="section-title">분류</div>
          <div class="d-flex gap-2">
            <button
              type="button"
              class="meal-btn"
              :class="{ active: form.mealType === 'BREAKFAST' }"
              @click="form.mealType = 'BREAKFAST'"
            >
              아침
            </button>

            <button
              type="button"
              class="meal-btn"
              :class="{ active: form.mealType === 'LUNCH' }"
              @click="form.mealType = 'LUNCH'"
            >
              점심
            </button>

            <button
              type="button"
              class="meal-btn"
              :class="{ active: form.mealType === 'DINNER' }"
              @click="form.mealType = 'DINNER'"
            >
              저녁
            </button>
          </div>
        </div>

        <!-- 식단 점수 -->
        <div class="card-box mb-3">
          <div class="section-title">식단 점수</div>
          <div class="d-flex gap-2">
            <button
              v-for="n in 5"
              :key="n"
              type="button"
              class="score-btn"
              :class="{ active: form.score === n }"
              @click="form.score = n"
            >
              {{ n }}
            </button>
          </div>
        </div>

        <!-- 메모 -->
        <div class="card-box">
          <div class="section-title">메모</div>
          <textarea
            v-model="form.memo"
            class="form-control"
            rows="7"
            placeholder="메모를 입력하세요."
          />
        </div>
      </div>

      <!-- 등록 버튼 -->
      <div class="col-12 text-center mt-4">
        <button
          type="submit"
          class="submit-btn px-5"
          :class="{ disabled: !isLoggedIn }"
          @click="handleRegisterClick"
        >
          식단 등록
        </button>
        <Modal
          :isOpen="isModalOpen"
          :title="modalConfig.title"
          :message="modalConfig.message"
          :type="modalConfig.type"
          @confirm="handleModalConfirm"
          @close="isModalOpen = false"
        />
      </div>
    </div>
  </form>
</template>

<script setup>
import foodApi from '@/api/foodApi'
import { ref, reactive, computed } from 'vue'
import { toast } from 'vue3-toastify'
import Modal from '../common/Modal.vue'
import { useAuthStore } from '@/stores/authStore'
import { useRouter } from 'vue-router'

const props = defineProps({
  date: String,
})

const emit = defineEmits(['submit'])

// 비로그인 - 유저 구분
const authStore = useAuthStore()
const router = useRouter()

const isLoggedIn = computed(() => authStore.isAuthenticated)

// 비로그인 유저한테는 폼 미리보기까지 보여주는데 식단 등록 막고 회원가입 페이지로
const handleRegisterClick = (e) => {
  if (!isLoggedIn.value) {
    e.preventDefault()

    toast.info('회원가입 후 식단을 기록할 수 있어요 🍽️', {
      closeOnClick: true, // 클릭하면 닫힘
      pauseOnHover: true,
      onClose: () => {
        router.push('/signup')
      },
    })
    return
  }

  // 로그인 상태면 기존 submit 로직으로 흘려보냄
  handleFormSubmit()
}

// 음식 검색 관련
const keyword = ref('') // 검색어
const foods = ref([]) // 자동완성 결과 리스트
const isSearching = ref(false)

// 음식 검색
const searchFood = async () => {
  // 공백만 입력하거나 비어있으면 검색 x
  if (keyword.value.trim().length < 1) {
    foods.value = []
    isSearching.value = false
    return
  }

  isSearching.value = true // 검색 시작
  const result = await foodApi.searchAutoComplete(keyword.value) // 자동완성 api 호출
  foods.value = result
  isSearching.value = false // 검색 종료

  // 검색결과 없으면 검색했던 텍스트 그대로 메뉴 이름 자동 입력
  if (result.length === 0) {
    form.foodId = null
    form.foodName = keyword.value
  }
}

// 음식 검색 시 엔터 눌렀을 때 폼 버튼 클릭 방지 및 엔터로 선택
const onEnterSelect = async () => {
  // 우선 검색부터 실행하고 그 결과로 선택
  // 최신 keyword 기준으로 검색 먼저
  await searchFood()
}

// 음식 선택 시 자동으로 영양정보 채우기
const selectFood = async (food) => {
  // 자동완성 클릭 -> id, name만 있음
  form.foodId = food.id
  form.foodName = food.name

  // 검색창 닫기
  keyword.value = food.name
  foods.value = []
  isSearching.value = false

  // 상세조회 api 호출 후 나머지 영양정보 채우기
  const detail = await foodApi.getFoodDetail(food.id)
  form.calories = detail.calories
  form.carbs = detail.carbs
  form.protein = detail.protein
  form.fat = detail.fat
}

// 자동으로 입력된 음식 수정하기
const onKeyWordChange = () => {
  form.foodId = null
  foods.value = []
  searchFood()
}

// 이미지 업로드 관련
const imageFile = ref(null)
const previewUrl = ref(null) // 이미지 미리보기 (브라우저에서 생성)
const fileInput = ref(null)

const triggerFile = () => {
  fileInput.value.click()
}

const onImageChange = (e) => {
  const file = e.target.files[0]

  if (!file) return

  imageFile.value = file
  previewUrl.value = URL.createObjectURL(file) // 미리보기용 url 생성
}

const form = reactive({
  mealType: null, // 기본 선택 없음
  foodId: null,
  foodName: '',
  calories: '',
  carbs: '',
  protein: '',
  fat: '',
  score: null,
  memo: '',
})

const isModalOpen = ref(false)
const modalConfig = reactive({
  title: '',
  message: '',
  type: 'confirm',
})

const handleFormSubmit = () => {
  if (!isLoggedIn.value) return

  if (!form.foodName || form.foodName.trim() === '') {
    toast.warn('🥗 음식을 검색하거나 직접 추가해 보세요!')
    return
  }
  if (!form.mealType) {
    toast.warn('🍽️ 식단 분류를 선택해 주세요! 🍽️')
    return
  }

  if (!form.score) {
    toast.warn('⭐오늘의 식단 점수를 선택해 주세요! ')
    return
  }

  // 등록 버튼 클릭 시 (모달에는 확인, 취소버튼)
  modalConfig.title = '등록 확인'
  modalConfig.message = '입력하신 식단을 등록하시겠습니까?'
  modalConfig.type = 'confirm'
  isModalOpen.value = true
}

const handleModalConfirm = () => {
  if (modalConfig.type === 'confirm') {
    // 등록하시겠습니까? 모달의 확인 버튼 클릭 시
    emit('submit', {
      ...form,
      date: props.date,
      imageFile: imageFile.value,
    })

    modalConfig.title = '등록 완료'
    modalConfig.message = '식단이 기록되었습니다. 100 포인트가 적립되었어요!'
    modalConfig.type = 'alert'
  } else {
    isModalOpen.value = false
  }
}
</script>

<style scoped>
.meal-form {
  background: #faf7f5;
  padding: 2rem;
  border-radius: 1.75rem;
}

.card-box {
  background: #fff;
  padding: 1.25rem;
  border-radius: 1.25rem;
  border: 1px solid var();
  transition: all 0.2s ease;
}

/* transform 사용 -> tacking context 문제 생겨서 삭제 -> margin 방식 호버 방법 사용 */
.card-box:hover {
  margin-top: -2px;
  box-shadow: 0 6px 18px rgba(165, 124, 94, 0.12);
}

/* 자동완성 있는 카드 hover 예외 처리 */
.food-search-box:hover {
  margin-top: 0;
  box-shadow: none;
}

.section-title {
  font-weight: 600;
  margin-bottom: 0.75rem;
  color: var(--main-brown);
}

.image-box {
  height: 220px;
  background: #fff;
  border: 1px dashed #e2d1c3;
  border-radius: 1.25rem;
  cursor: pointer;
  transition: all 0.2s ease;
}

.image-box:hover {
  background: rgba(165, 124, 94, 0.04);
  margin-top: -2px;
}

.preview-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 1rem;
}

.meal-btn {
  padding: 0.45rem 1.1rem;
  border-radius: 999px;
  border: 1px solid #e2d1c3;
  background: #fff;
  color: var(--main-brown);
  transition: all 0.2s ease;
}

.meal-btn.active {
  background: var(--main-brown);
  color: #fff;
  box-shadow: 0 4px 12px rgba(75, 46, 30, 0.25);
}

.score-btn {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  border: 1px solid #e2d1c3;
  background: #fff;
  transition: all 0.15s ease;
}

.score-btn.active {
  background: var(--main-brown);
  color: #fff;
  box-shadow: 0 4px 10px rgba(75, 46, 30, 0.25);
}

.submit-btn {
  background: var(--main-brown);
  color: #fff;
  border: none;
  border-radius: 999px;
  padding: 0.65rem 2.8rem;
  font-weight: 600;
  transition: all 0.2s ease;
}

.submit-btn:hover {
  margin-top: -2px;
  box-shadow: 0 6px 16px rgba(75, 46, 30, 0.3);
}

.autocomplete {
  position: absolute;
  top: calc(100% - 8px);
  left: 0;
  width: 100%;
  z-index: 1000;
  max-height: 200px;
  overflow-y: auto;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.12);
}

.autocomplete .list-group-item {
  cursor: pointer;
}

.autocomplete .list-group-item:hover {
  background: rgba(165, 124, 94, 0.08);
}

.form-control:focus {
  border-color: #a57c5e;
  box-shadow: 0 0 0 0.15rem rgba(165, 124, 94, 0.15);
}
</style>
