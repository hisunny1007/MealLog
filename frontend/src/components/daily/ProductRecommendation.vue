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
          <img
            :src="product.imageUrl"
            :alt="product.name"
            class="product-img"
            @error="(e) => handleImageError(e, product.category)"/>
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


const defaultImages = {
  'protein': new URL('@/assets/protein-default.png', import.meta.url).href,
  'diet-box': new URL('@/assets/dietbox-default.png', import.meta.url).href,
  'vegetable': new URL('@/assets/vegetable-default.png', import.meta.url).href,
  'default': "https://via.placeholder.com/110?text=MealLog"
};


// 추천 제품 데이터 가져오기 함수
const fetchRecommendations = async () => {
  try {
    const response = await getPersonalizedRecommendation()
    recommendedProducts.value = response.data.recommendedProducts;
  } catch (error) {
    console.error('추천 데이터 로드 실패:', error);
  }
}
const handleImageError = (e, category) => {
  const categoryKey = category?.toLowerCase().trim();
  e.target.src = defaultImages[categoryKey] || defaultImages['default'];
};

// 제품 클릭 시 포인트샵 이동 함수
const goToProduct = (product) => {
  router.push({
    name: 'point-shop',
    query: {
      category: product.category,
      productId: product.id,
    },
  })
}


onMounted(() => {
  fetchRecommendations()
})
</script>

<style scoped>


.recommendation-container {

  width: 100%;
  max-width: 220px;
  padding: 1.5rem 1rem;
  display: flex;
  flex-direction: column;
  align-items: center;

}

.recommendation-title {
  margin-bottom: 1.5rem;
  text-align: center;

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
  background-color: #fff;
  border-radius: 16px;
  overflow: hidden;
  border: 1px solid var(--border);
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



  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  line-height: 1.3;
}

.product-price {
  font-size: 0.95rem;
  font-weight: 700;

}
</style>
