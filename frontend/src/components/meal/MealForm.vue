<template>
  <form @submit.prevent="submit">
    <!-- 이미지 -->
    <div class="col-md-4">
      <div
        class="image-box border rounded-4 d-flex flex-column justify-content-center align-items-center"
        style="height: 220px; cursor: pointer"
      >
        <input type="file" @change="onImageChange" />

        <div class="fs-1">+</div>
        <div class="text-muted">사진</div>
      </div>
    </div>

    <!-- 음식 검색 -->
    <input
      type="text"
      class="form-control mb-3"
      v-model="form.foodName"
      placeholder="메뉴 검색"
      @input="searchFood"
    />

    <!-- 영양성분 -->
    <!-- <div class="fw-semibold mb-2">영양성분</div>

          <div class="d-flex gap-2 mb-3">
            <button class="btn btn-secondary btn-sm">직접 추가</button>
            <button class="btn btn-outline-secondary btn-sm">
              음식 검색으로 추가
            </button>
          </div> -->

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
      <button class="btn btn-secondary px-5 rounded-pill" type="submit" @click="submit">
        식단 등록
      </button>
    </div>
  </form>
</template>

<script setup>
import { ref, reactive } from 'vue'

const props = defineProps({
  date: String,
})

const emit = defineEmits(['submit'])

const keyword = ref('')
const foods = ref([])

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

const searchFood = async () => {
  if (keyword.value.length < 1) {
    foods.value = []
    return
  }
  foods.value = await foodApi.autoComplete(keyword.value)
}

const selectFood = (food) => {
  form.foodId = food.id
  form.foodName = food.name
  form.calories = food.calories
  form.carbs = food.carbs
  form.protein = food.protein
  form.fat = food.fat
  foods.value = []
}

const submit = () => {
  console.log('submit 직전 form:', form)

  emit('submit', {
    ...form,
    date: props.date,
  })
}
</script>

<style scoped></style>
