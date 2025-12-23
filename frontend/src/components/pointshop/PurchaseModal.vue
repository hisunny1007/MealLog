<template>
  <div class="modal-overlay" @click.self="$emit('close')">
    <div class="modal-content">
      <h2 class="modal-title">상품 교환</h2>
      <div class="product-info">
        <strong>{{ product.name }}</strong>
      </div>

      <div class="quantity-control">
        <label for="quantity">수량:</label>
        <input type="number" id="quantity" v-model.number="amount" min="1" />
      </div>

      <div class="total-points">
        총 필요 포인트: <strong>{{ totalPoints.toLocaleString() }}p</strong>
      </div>

      <div class="modal-actions">
        <button class="cancel-button" @click="$emit('close')">취소</button>
        <button class="confirm-button" @click="confirmPurchase">교환하기</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';

const props = defineProps({
  product: {
    type: Object,
    required: true,
  },
});

const emit = defineEmits(['close', 'confirm']);

const amount = ref(1);

const totalPoints = computed(() => {
  return props.product.pricePoint * amount.value;
});

const confirmPurchase = () => {
  emit('confirm', { productId: props.product.id, amount: amount.value });
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background-color: #fff;
  padding: 2rem;
  border-radius: 12px;
  width: 90%;
  max-width: 400px;
  text-align: center;
}

.modal-title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 1.5rem;
}

.product-info {
  font-size: 1.1rem;
  margin-bottom: 1rem;
}

.quantity-control {
  margin-bottom: 1.5rem;
}

.quantity-control input {
  width: 60px;
  text-align: center;
  padding: 0.3rem;
  margin-left: 0.5rem;
}

.total-points {
  font-size: 1.2rem;
  margin-bottom: 2rem;
}

.total-points strong {
  color:  #4b2e1e;
}

.modal-actions {
  display: flex;
  justify-content: center;
  gap: 1rem;
}

.modal-actions button {
  padding: 0.7rem 1.5rem;
  border-radius: 8px;
  border: none;
  cursor: pointer;
  font-weight: bold;
}

.cancel-button {
  background-color: #eee;
  color: #333;
}

.confirm-button {
  background-color:  #4b2e1e;
  color: #fff;
}
</style>
