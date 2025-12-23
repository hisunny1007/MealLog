<template>
  <div class="history-container">
    <h2 class="text-center fw-bold mb-4">포인트 교환내역</h2>
    <div v-if="isLoading" class="loading-message">
      불러오는 중...
    </div>
    <div v-else-if="history.length === 0" class="empty-message">
      교환 내역이 없습니다.
    </div>
    <div v-else class="history-list">
      <div v-for="item in history" :key="item.orderDate" class="history-item">
        <div class="item-date">{{ formatDate(item.orderDate) }}</div>
        <div class="item-details">
          <img :src="item.imageUrl"
               :alt="item.productName"
               class="product-image"
               @error="(e) => handleImageError(e, item)"/>
          <div class="product-info">
            <div class="product-name">{{ item.productName }} x {{ item.amount }}</div>
          </div>
          <div class="points-info">
            <span class="points-spent">{{ item.totalPoint }} p 소진</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getOrderHistory } from '@/api/pointshop';

const history = ref([]);
const isLoading = ref(true);

const historyDefault = new URL('@/assets/history-default.png', import.meta.url).href;

const handleImageError = (e) => {
  e.target.onerror = null; // 무한 루프 방지
  e.target.src = historyDefault;
};

const fetchHistory = async () => {
  try {
    isLoading.value = true;
    const response = await getOrderHistory();

    history.value = response.data;
  } catch (error) {
    console.error("Error fetching order history:", error);
  } finally {
    isLoading.value = false;
  }
};

const formatDate = (dateString) => {
  const options = { year: 'numeric', month: 'long', day: 'numeric' };
  return new Date(dateString).toLocaleDateString('ko-KR', options);
};

onMounted(fetchHistory);
</script>

<style scoped>
.history-container {
  padding: 2rem;
  background-color: #fdfaf6;
}

.title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 2rem;
}

.loading-message, .empty-message {
  text-align: center;
  /* color: #888; */
  font-size: 1.1rem;
  padding: 3rem 0;
}

.history-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.history-item {
  background-color: #fff;
  border-radius: 12px;
  padding: 1.5rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.item-date {
  text-align: right;
  font-size: 0.9rem;
  /* color: #888; */
  margin-bottom: 1rem;
}

.item-details {
  display: flex;
  align-items: center;
  gap: 1.5rem;
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
}

.points-info {
  text-align: right;
}

.points-spent {
  font-size: 1.1rem;
  font-weight: bold;
  color: #4b2e1e;
}
</style>
