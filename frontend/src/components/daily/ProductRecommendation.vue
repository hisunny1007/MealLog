<template>
  <div class="recommendation-container card-soft">
    <h4 class="recommendation-title meal-type">이런 제품은 어떤가요?</h4>

    <div class="recommendation-list">
      <div
        v-for="product in recommendedProducts"
        :key="product.id"
        class="recommendation-item"
        @click="goToProduct(product)"
      >
        <div class="image-box">
          <img :src="product.imageUrl" :alt="product.name" class="product-img" />
        </div>
        <div class="product-info">
          <p class="product-name">{{ product.name }}</p>
          <p class="product-price">{{ product.pricePoint }}p</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getPersonalizedRecommendation } from '@/api/pointshop'

const router = useRouter()
const recommendedProducts = ref([])

// 1. 추천 제품 데이터 가져오기 함수
const fetchRecommendations = async () => {
  try {
    const response = await getPersonalizedRecommendation()
    // 백엔드 응답 구조 확인 (response.data.recommendedProducts)
    recommendedProducts.value = response.data.recommendedProducts
  } catch (error) {
    console.error('추천 데이터 로드 실패:', error)
  }
}

// 2. 제품 클릭 시 포인트샵 이동 함수
const goToProduct = (product) => {
  router.push({
    name: 'point-shop',
    query: {
      category: product.category,
      productId: product.id,
    },
  })
}

//  3. 생명주기 훅: goToProduct 함수 밖으로 이동
// 컴포넌트가 마운트될 때 추천 데이터를 즉시 불러옵니다.
onMounted(() => {
  fetchRecommendations()
})
</script>

<style scoped>
/* variable.css 및 common.css의 변수와 클래스 활용 */

.recommendation-container {
  /* 피그마 시안에 맞춘 너비 설정 */
  width: 100%;
  max-width: 220px;
  padding: 1.5rem 1rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  /* .card-soft 클래스가 배경색(--bg-main)과 테두리(--border)를 결정함 */
}

.recommendation-title {
  margin-bottom: 1.5rem;
  text-align: center;
  /* .meal-type 클래스가 폰트 굵기와 색상을 결정함 */
}

.recommendation-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  width: 100%;
}

.recommendation-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.6rem;
  cursor: pointer;
  transition: transform 0.2s ease;
}

.recommendation-item:hover {
  transform: translateY(-3px);
}

.image-box {
  width: 110px;
  height: 110px;
  background-color: #fff; /* 제품 이미지를 돋보이게 하기 위한 배경 */
  border-radius: 16px;
  overflow: hidden;
  border: 1px solid var(--border); /* variable.css 변수 활용 */
}

.product-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.product-info {
  text-align: center;
}

.product-name {
  font-size: 0.85rem;
  font-weight: 500;
  margin-bottom: 4px;
  /* 텍스트 색상은 main.css 전역 설정에 의해 var(--main-brown) 적용 */

  /* 긴 제품명 2줄 제한 및 말줄임표 처리 */
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  line-height: 1.3;
}

.product-price {
  font-size: 0.95rem;
  font-weight: 700;
  /* 포인트 샵의 정체성을 위해 포인트(p) 단위 사용 */
}
</style>
