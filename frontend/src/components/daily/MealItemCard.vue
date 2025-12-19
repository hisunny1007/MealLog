<template>
  <div class="card-soft meal-card-layout">
    <!-- 왼쪽: 이미지 -->
    <section class="meal-image-wrapper">
      <img
        v-if="meal.imageUrl"
        :src="getImageUrl(meal.imageUrl)"
        class="meal-image"
        alt="식단 이미지"
      />
    </section>

    <!-- 오른쪽: 정보 -->
    <section class="meal-info">
      <!-- 음식 이름 -->
      <div class="title-row">
        <h5 class="meal-title">{{ meal.foodName }}</h5>

        <!-- 점수 -->
        <div class="score-badge">{{ meal.score }}점</div>
      </div>

      <!-- 영양 정보 -->
      <div class="nutrient">
        <span>칼로리 {{ meal.calories }} kcal</span>
        <span>탄수화물 {{ meal.carbs }}g</span>
        <span>단백질 {{ meal.protein }}g</span>
        <span>지방 {{ meal.fat }}g</span>
      </div>

      <!-- 메모 -->
      <p v-if="meal.memo" class="memo">
        메모 | {{ meal.memo }}
      </p>
    </section>
  </div>
</template>

<script setup>
defineProps({
  meal: Object,
})

const getImageUrl = (filename) => {
  console.log('사진이름', filename)

  if (!filename) return '/default-meal.png' // 기본 이미지
  return `http://localhost:8080/api/v1/uploads/${filename}`
}
</script>

<style scoped>
/* ✅ 카드 전체 */
.meal-card-layout {
  display: grid;
  grid-template-columns: 120px 1fr;
  gap: 20px;
  padding: 16px;
}

/* 이미지 */
.meal-image {
  width: 120px;
  height: 150px;
  padding-left: 20px;
  object-fit: cover;
  border-radius: 16px;
}

/* 오른쪽 정보 */
.meal-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

/* 제목 + 점수 */
.title-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.meal-title {
  font-weight: 700;
  color: var(--main-brown);
}

/*  점수 뱃지 */
.score-badge {
  font-size: 0.8rem;
  padding: 4px 10px;
  border-radius: 999px;
  border: 1px solid var(--border);
  color: var(--main-brown);
}

/* 영양 정보 */
.nutrient {
  display: flex;
  gap: 12px;
  font-size: 0.85rem;
  color: #555;
  margin-top: 6px;
}

/* 메모 */
.memo {
  font-size: 0.85rem;
  color: #777;
  margin-top: 8px;
}
</style>
