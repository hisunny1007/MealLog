<template>
  <div class="product-card">
    <img :src="product.imageUrl" :alt="product.name" class="product-image" />
    <div class="product-info">
      <h3 class="product-name">{{ product.name }}</h3>
      <p class="product-description">{{ product.description }}</p>
      <div class="product-details">
 
        <span>Weight: 100g</span>
        <span>Protein: 25g</span>
        <span>Calories: 130kcal</span>
      </div>
    </div>
    <div class="product-actions">
      <div class="product-price">{{ product.pricePoint }}p</div>
      <button @click="onPurchase" class="purchase-button">구매</button>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  product: {
    type: Object,
    required: true,
  },
});

const emit = defineEmits(['purchase']);

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
  gap: 1rem;
  align-items: center;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  transition: transform 0.2s;
}

.product-card:hover {
  transform: translateY(-5px);
}

.product-image {
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 8px;
}

.product-info {
  flex-grow: 1;
}

.product-name {
  font-size: 1.1rem;
  font-weight: bold;
  margin: 0 0 0.5rem 0;
}

.product-description {
  font-size: 0.9rem;
  color: #666;
  margin: 0 0 0.5rem 0;
}

.product-details {
  font-size: 0.8rem;
  color: #888;
  display: flex;
  gap: 1rem;
}

.product-actions {
  text-align: right;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-end;
  height: 100%;
}

.product-price {
  font-size: 1.2rem;
  font-weight: bold;
  color: #ff8c00;
  margin-bottom: 1rem;
}

.purchase-button {
  background-color: #ff8c00;
  color: #fff;
  border: none;
  padding: 0.5rem 1.5rem;
  border-radius: 8px;
  cursor: pointer;
}
</style>
