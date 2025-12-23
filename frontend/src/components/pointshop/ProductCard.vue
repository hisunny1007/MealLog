<template>
  <div class="product-card">
    <img
      :src="product.imageUrl"
      :alt="product.name"
      class="product-image"
      @error="handleImageError"
    />

    <div class="product-info">
      <h3 class="product-name">{{ product.name }}</h3>
      <p class="product-description">{{ parsedData.desc }}</p>

      <div class="product-details">
        <span>탄수화물: {{ parsedData.carbs }}</span>
        <span>단백질: {{ parsedData.protein }}</span>
        <span>지방: {{ parsedData.fat }}</span>
      </div>
    </div>

    <div class="product-actions">
      <div class="product-price">{{ product.pricePoint.toLocaleString() }}p</div>
      <button @click="onPurchase" class="purchase-button">구매</button>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  product: { type: Object, required: true },
})

const emit = defineEmits(['purchase'])

const defaultImages = {
  'protein': new URL('@/assets/protein-default.png', import.meta.url).href,
  'diet-box': new URL('@/assets/dietbox-default.png', import.meta.url).href,
  'vegetable': new URL('@/assets/vegetable-default.png', import.meta.url).href,
  default: 'https://via.placeholder.com/110?text=MelalLog'
}

const handleImageError = (e) => {
  const img = e.target;
  const categoryKey = props.product.category?.toLowerCase().trim();
  const categoryDefault = defaultImages[categoryKey];

  if(categoryDefault && img.src !== categoryDefault) {
    img.src = categoryDefault;
  }


}

const parsedData = computed(() => {
  const fullDesc = props.product.description || ''

  const [nutrientPart, ...descParts] = fullDesc.split('|')
  const pureDesc = descParts.join('|').trim()

  return {
    carbs: nutrientPart.match(/탄:(\d+g)/)?.[1] || '0g',
    protein: nutrientPart.match(/단:(\d+g)/)?.[1] || '0g',
    fat: nutrientPart.match(/지:(\d+g)/)?.[1] || '0g',
    desc: pureDesc || fullDesc,
  }
})

const onPurchase = () => {
  emit('purchase', props.product)
}
</script>

<style scoped>
.product-card {
  display: flex;
  background-color: #fff;
  border-radius: 12px;
  padding: 1rem;
  gap: 1.5rem;
  align-items: center;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  transition: transform 0.2s;
  min-height: 140px;
}

.product-image {
  width: 110px;
  height: 110px;
  object-fit: cover;
  border-radius: 10px;
  flex-shrink: 0;
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
  color: #4b2e1e;
  display: flex;
  gap: 0.8rem;
}

.product-actions {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-end;
  height: 100px;
  flex-shrink: 0;
}

.product-price {
  font-size: 1.2rem;
  font-weight: 800;
  color: var(--main-brown);
}

.purchase-button {
  background-color: #7a6658;
  color: #fff;
  border: none;
  padding: 0.8rem 1.2rem;
  border-radius: 8px;
  cursor: pointer;
  font-weight: bold;
  white-space: nowrap;
  min-width: 65px;
  transition: background-color 0.2s;
}

.purchase-button:hover {
  background-color: var(--main-brown);
}
</style>
