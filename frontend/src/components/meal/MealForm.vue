<template>
  <form @submit.prevent="submit" class="meal-form">
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

        <!-- 음식 검색 -->
        <div class="card-box mb-3">
          <div class="section-title">음식</div>

          <input
            type="text"
            class="form-control mb-2"
            v-model="keyword"
            placeholder="음식 검색"
            @input="onKeyWordChange"
          />

          <!-- 자동완성 리스트 -->
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
            검색 결과가 없습니다. 음식을 직접 입력할 수 있습니다.
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
                class="form-control"
                placeholder="칼로리 kcal"
              />
            </div>
            <div class="col-4">
              <label class="form-label">탄수화물 g</label>
              <input v-model="form.carbs" type="number" class="form-control" placeholder="탄수 g" />
            </div>
            <div class="col-4">
              <label class="form-label">단백질 g</label>
              <input
                v-model="form.protein"
                type="number"
                class="form-control"
                placeholder="단백질 g"
              />
            </div>
            <div class="col-4">
              <label class="form-label">지방 g</label>
              <input v-model="form.fat" type="number" class="form-control" placeholder="지방 g" />
            </div>
          </div>
        </div>
      </div>

      <!-- 왼쪽 : 분류 / 점수 / 메모 -->
      <div class="col-md-6">
        <!-- 분류 -->
        <div class="card-box mb-3">
          <div class="section-title">분류</div>
          <div class="d-flex gap-2">
            <button
              type="button"
              class="meal-btn"
              :class="form.mealType === 'BREAKFAST' ? 'btn-secondary' : 'btn-outline-secondary'"
              @click="form.mealType = 'BREAKFAST'"
            >
              아침
            </button>

            <button
              type="button"
              class="meal-btn"
              :class="form.mealType === 'LUNCH' ? 'btn-secondary' : 'btn-outline-secondary'"
              @click="form.mealType = 'LUNCH'"
            >
              점심
            </button>

            <button
              type="button"
              class="meal-btn"
              :class="form.mealType === 'DINNER' ? 'btn-secondary' : 'btn-outline-secondary'"
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
        <button type="submit" class="submit-btn px-5">식단 등록</button>
      </div>
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

const submit = () => {
  if (!form.mealType || !form.foodName || !form.score) {
    alert('필수 항목을 모두 선택해 주세요.')
    return
  }
  emit('submit', {
    ...form,
    date: props.date,
    imageFile: imageFile.value,
  })
}
</script>

<style scoped>
.meal-form {
  background: #faf7f5;
  padding: 2rem;
  border-radius: 1.5rem;
}

/* 카드 */
.card-box {
  background: #fff;
  padding: 1.25rem;
  border-radius: 1rem;
}

.section-title {
  font-weight: 600;
  margin-bottom: 0.75rem;
  color: var(--main-brown);
}

/* 이미지 */
.image-box {
  height: 220px;
  border: 1px solid var(--brown-50);
  background: #fff;
  cursor: pointer;
}

.preview-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 1rem;
}

/* 버튼 */
.meal-btn {
  padding: 0.4rem 1rem;
  border-radius: 999px;
  border: 1px solid var(--brown-50);
  background: #fff;
}

.meal-btn.active {
  background: var(--main-brown);
  color: #fff;
}

.score-btn {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  border: 1px solid var(--brown-50);
  background: #fff;
}

.score-btn.active {
  background: var(--main-brown);
  color: #fff;
}

/* submit */
.submit-btn {
  background: var(--main-brown);
  color: #fff;
  border: none;
  border-radius: 999px;
  padding: 0.6rem 2.5rem;
  font-weight: 600;
}

/* 자동완성 */
.autocomplete {
  position: absolute;
  width: 100%;
  z-index: 10;
}
</style>
