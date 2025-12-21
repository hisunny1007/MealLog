<template>
  <form @submit.prevent="submit">
    <!-- 이미지 등록 -->
    <div class="col-md-4">
      <div
        class="image-box rounded-4 d-flex justify-content-center align-items-center"
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
          <div class="small">사진 추가</div>
        </div>
      </div>
    </div>

    <!-- 음식 검색 -->
    <input
      type="text"
      class="form-control mb-3"
      v-model="keyword"
      placeholder="메뉴 검색"
      @input="searchFood"
    />

    <!-- 자동완성 리스트 추가 -->
    <ul v-if="foods.length" class="list-group position-absolute w-100">
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
      class="text-muted mt-2"
    >
      검색 결과가 없습니다. 직접 음식을 추가해 보세요.
    </div>

    <!-- 영양정보 -->
    <div class="nutrition row g-2">
      <div class="col-6">
        <label class="form-label small">메뉴 이름</label>
        <input v-model="form.foodName" class="form-control form-control-sm" placeholder="음식명" />
      </div>
      <div class="col-6">
        <label class="form-label small">칼로리 kcal</label>
        <input
          v-model="form.calories"
          type="number"
          class="form-control form-control-sm"
          placeholder="kcal"
        />
      </div>
      <div class="col-4">
        <label class="form-label small">탄수화물 g</label>
        <input
          v-model="form.carbs"
          type="number"
          class="form-control form-control-sm"
          placeholder="g"
        />
      </div>
      <div class="col-4">
        <label class="form-label small">단백질 g</label>
        <input
          v-model="form.protein"
          type="number"
          class="form-control form-control-sm"
          placeholder="g"
        />
      </div>
      <div class="col-4">
        <label class="form-label small">지방 g</label>
        <input
          v-model="form.fat"
          type="number"
          class="form-control form-control-sm"
          placeholder="g"
        />
      </div>
    </div>

    <!-- 분류 -->
    <div class="mb-4">
      <div class="fw-semibold mb-2">분류</div>
      <div class="d-flex gap-2">
        <button
          type="button"
          class="btn btn-sm"
          :class="form.mealType === 'BREAKFAST' ? 'btn-secondary' : 'btn-outline-secondary'"
          @click="form.mealType = 'BREAKFAST'"
        >
          아침
        </button>

        <button
          type="button"
          class="btn btn-sm"
          :class="form.mealType === 'LUNCH' ? 'btn-secondary' : 'btn-outline-secondary'"
          @click="form.mealType = 'LUNCH'"
        >
          점심
        </button>

        <button
          type="button"
          class="btn btn-sm"
          :class="form.mealType === 'DINNER' ? 'btn-secondary' : 'btn-outline-secondary'"
          @click="form.mealType = 'DINNER'"
        >
          저녁
        </button>
      </div>
    </div>

    <!-- 식단 점수 -->
    <div class="fw-semibold mb-2">식단 점수</div>

    <div class="score d-flex gap-2">
      <button
        v-for="n in 5"
        :key="n"
        type="button"
        class="btn btn-sm btn-outline-secondary rounded-circle"
        :class="{ active: form.score === n }"
        @click="form.score = n"
      >
        {{ n }}
      </button>
    </div>

    <!-- 메모 -->
    <div class="col-md-6">
      <div class="fw-semibold mb-2">메모</div>
      <textarea
        v-model="form.memo"
        class="form-control"
        rows="7"
        placeholder="메모를 입력하세요"
      ></textarea>
    </div>

    <!-- 추가 버튼 -->
    <div class="col-12 text-center mt-4">
      <!-- submit은 form에서만 1번 처리 -->
      <button class="btn btn-secondary px-5 rounded-pill" type="submit">식단 등록</button>
    </div>
  </form>
</template>

<script setup>
import foodApi from '@/api/foodApi'
import { ref, reactive } from 'vue'

const props = defineProps({
  date: String,
})

const emit = defineEmits(['submit'])

// 음식 검색 관련
const keyword = ref('') // 검색어
const foods = ref([]) // 자동완성 결과 리스트
const isSearching = ref(false) // 검색 중인지 여부

// 이미지 업로드 관련
const imageFile = ref(null)
const previewUrl = ref(null) // 이미지 미리보기 (브라우저에서 생성)
const fileInput = ref(null)

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

// 음식 검색
const searchFood = async () => {
  // 공백만 입력하거나 비어있으면 검색 x
  if (keyword.value.trim().length < 1) {
    foods.value = []
    isSearching.value = false
    return
  }

  isSearching.value = true // 검색 시작
  foods.value = await foodApi.searchAutoComplete(keyword.value) // 자동완성 api 호출
  isSearching.value = false // 검색 종료
}

// 음식 선택 시 자동으로 영양정보 채우기
const selectFood = (food) => {
  form.foodId = food.id
  form.foodName = food.name
  form.calories = food.calories
  form.carbs = food.carbs
  form.protein = food.protein
  form.fat = food.fat

  // 검색창 닫기
  keyword.value = food.name
  foods.value = []
  isSearching.value = false
}

const triggerFile = () => {
  fileInput.value.click()
}

const onImageChange = (e) => {
  const file = e.target.files[0]

  if (!file) return

  imageFile.value = file
  previewUrl.value = URL.createObjectURL(file) // 미리보기용 url 생성
}

const submit = () => {
  emit('submit', {
    ...form,
    date: props.date,
    imageFile: imageFile.value,
  })
}
</script>

<style scoped>
.image-box {
  height: 220px;
  border: 2px dashed var(--brown-50);
  background-color: #faf7f5;
  cursor: pointer;
  transition: all 0.2s ease;
}

.image-box:hover {
  background-color: #f3ede9;
}

.preview-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 1rem;
}
</style>
