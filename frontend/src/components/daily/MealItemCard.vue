<template>
  <div class="card rounded-4 shadow-sm border-0 meal-card-hover">
    <div class="p-3">
      <div class="row g-0 align-items-center">
        <div class="col-3 col-md-2">
          <div class="ratio ratio-1x1 overflow-hidden rounded-3 shadow-sm">
            <img
              :src="meal.imageUrl ? getImageUrl(meal.imageUrl) : '/default-meal.png'"
              class="object-fit-cover"
              alt="식단 이미지"
            />
          </div>
        </div>

        <div class="col-9 col-md-10 ps-4">
          <div class="d-flex justify-content-between align-items-start">
            <div>
              <h5 class="fw-bold text-dark mb-1">{{ meal.foodName }}</h5>
              <p class="text-brown fw-bold mb-2">{{ meal.calories }} kcal</p>
            </div>

            <!-- 점수랑 버튼 -->
            <div class="d-flex flex-column align-items-end gap-2">
              <!-- 점수 -->
              <div
                class="score-pill"
                :style="{
                  color: scoreColor,
                  borderColor: scoreColor,
                  backgroundColor: scoreColor + '15',
                }"
              >
                <span class="fs-4 fw-black">{{ meal.score }}</span
                ><small>점</small>
              </div>

              <!-- 수정 / 삭제 -->
              <div class="meal-action-group">
                <button class="meal-action-btn edit" @click="$emit('edit', meal.id)">수정</button>
                <button class="meal-action-btn delete" @click="$emit('delete', meal.id)">
                  삭제
                </button>
              </div>
            </div>
          </div>

          <div class="d-flex flex-wrap gap-2 mb-3">
            <span class="nutrient-tag">탄수화물 {{ meal.carbs }}g</span>
            <span class="nutrient-tag">단백질 {{ meal.protein }}g</span>
            <span class="nutrient-tag">지방 {{ meal.fat }}g</span>
          </div>

          <div v-if="meal.memo" class="memo-box">
            {{ meal.memo }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  meal: Object,
})

// 식단 점수별 색상
const scoreColors = {
  1: '#F28B82',
  2: '#F6AD55',
  3: '#F4D35E',
  4: '#8BCF9B',
  5: '#7EA6E0',
}

// 식단 점수에 맞는 색상 계산
const scoreColor = computed(() => {
  return scoreColors[props.meal.score] || '#ccc'
})

const getImageUrl = (filename) => {
  if (!filename) return '/default-meal.png' // 기본 이미지
  return `http://localhost:8080/api/v1/uploads/${filename}`
}
</script>

<style scoped>
.meal-card-hover {
  background-color: #ffffff;
  border: 1px solid rgba(165, 124, 94, 0.05) !important;
}
.text-brown {
  color: #a57c5e;
}

.nutrient-tag {
  background-color: #f8f5f2;
  color: #7d6e63;
  font-size: 0.75rem;
  padding: 3px 10px;
  border-radius: 6px;
  font-weight: 600;
}

.score-pill {
  padding: 4px 16px;
  border-radius: 15px;
  font-weight: 600;
  border: 1px solid;
}

.memo-box {
  font-size: 0.85rem;
  color: #8a7b6e;
  padding: 10px;
  background: #fcfaf9;
  border-radius: 10px;
}
.meal-action-group {
  display: flex;
  gap: 6px;
}

/* 공통 버튼 */
.meal-action-btn {
  font-size: 0.7rem;
  padding: 3px 10px;
  border-radius: 999px;
  border: 1px solid rgba(165, 124, 94, 0.35);
  background: transparent;
  color: #8a6a55;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.15s ease;
}

/* 수정 */
.meal-action-btn.edit:hover {
  background: rgba(165, 124, 94, 0.12);
  border-color: #a57c5e;
}

/* 삭제 - danger 느낌 최소화 */
.meal-action-btn.delete {
  color: #a57c5e;
}

.meal-action-btn.delete:hover {
  background: rgba(165, 124, 94, 0.18);
  border-color: #a57c5e;
}
</style>
