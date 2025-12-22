<template>
  <div class="product-card">
    <img :src="product.imageUrl" :alt="product.name" class="product-image" />
    
    <div class="product-info">
      <h3 class="product-name">{{ product.name }}</h3>
      <p class="product-description">{{ parsedData.desc }}</p>
      
      <div class="product-details">
        <span>Carbs: {{ parsedData.carbs }}</span>
        <span>Protein: {{ parsedData.protein }}</span>
        <span>Fat: {{ parsedData.fat }}</span>
      </div>
    </div>

    <div class="product-actions">
      <div class="product-price">{{ product.pricePoint }}p</div>
      <button @click="onPurchase" class="purchase-button">구매</button>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
  product: { type: Object, required: true },
});

const emit = defineEmits(['purchase']);

// DB의 description 필드를 파싱하는 핵심 로직
const parsedData = computed(() => {
  const fullDesc = props.product.description || "";
  // '|' 기호를 기준으로 영양정보와 설명을 분리
  const [nutrientPart, ...descParts] = fullDesc.split('|');
  const pureDesc = descParts.join('|').trim(); // 설명 부분

  return {
    carbs: nutrientPart.match(/탄:(\d+g)/)?.[1] || "0g",
    protein: nutrientPart.match(/단:(\d+g)/)?.[1] || "0g",
    fat: nutrientPart.match(/지:(\d+g)/)?.[1] || "0g",
    desc: pureDesc || fullDesc // 구분이 안 될 경우 전체 출력
  };
});

const onPurchase = () => {
  emit('purchase', props.product);
};
</script>

<style scoped>
.product-card {
  display: flex;
  background-color: #fff;
  border-radius: 12px;
  padding: 1rem;
  gap: 1.5rem; /* 간격을 조금 더 넓힘 */
  align-items: center;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  transition: transform 0.2s;
  min-height: 140px; /* 카드의 최소 높이 설정 */
}

.product-image {
  width: 110px; /* 피그마에 맞춰 소폭 확대 */
  height: 110px;
  object-fit: cover;
  border-radius: 10px;
  flex-shrink: 0; /* 이미지 크기 보존 */
}

.product-info {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.product-name {
  font-size: 1.1rem;
  font-weight: bold;
  margin: 0 0 0.3rem 0;
}

.product-description {
  font-size: 0.85rem;
  color: #777;
  margin: 0 0 0.8rem 0;
  line-height: 1.4;
 
}

.product-details {
  font-size: 0.75rem;
  color: #999;
  display: flex;
  gap: 0.8rem;
}

.product-actions {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-end;
  height: 100px; /* 버튼과 가격 간격 유지 */
  flex-shrink: 0;
}

.product-price {
  font-size: 1.2rem;
  font-weight: 800;
  color: #ff8c00;
}

.purchase-button {
  background-color: #ff8c00;
  color: #fff;
  border: none;
  padding: 0.8rem 1.2rem; /* 세로/가로 패딩 확보 */
  border-radius: 8px;
  cursor: pointer;
  font-weight: bold;
  white-space: nowrap; /* ⭐ 글자 꺾임 방지 핵심 */
  min-width: 65px;     /* 버튼 최소 너비 보장 */
  transition: background-color 0.2s;
}

.purchase-button:hover {
  background-color: #e67e00;
}
</style>