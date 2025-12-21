<template>
  <div class="point-shop-container">
    <h1 class="title">카테고리</h1>
    <div class="category-tabs">
      <button
        v-for="cat in categories"
        :key="cat.value"
        @click="selectCategory(cat.value)"
        :class="{ active: selectedCategory === cat.value }"
      >
        {{ cat.name }}
      </button>
    </div>

    <div class="user-points">
      <span>잔여 포인트 {{ userPoints }}p</span>
    </div>

    <div class="product-grid">
      <ProductCard
        v-for="product in products"
        :key="product.id"
        :product="product"
        @purchase="openPurchaseModal"
      />
    </div>
  </div>
  <PurchaseModal
    v-if="isModalVisible"
    :product="selectedProduct"
    @close="closePurchaseModal"
    @confirm="handlePurchase"
  />
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useAuthStore } from '@/stores/authStore';
import { getProducts, purchaseProduct } from '@/api/pointshop';
import ProductCard from '@/components/pointshop/ProductCard.vue';
import PurchaseModal from '@/components/pointshop/PurchaseModal.vue';

const authStore = useAuthStore();

const categories = ref([
  { name: '단백질', value: 'protein' },
  { name: '다이어트 도시락', value: 'diet-box' },
  { name: '야채', value: 'vegetable' },
]);

const selectedCategory = ref('protein');
const products = ref([]);
const userPoints = computed(() => authStore.user?.rewardPoint || 0);

const isModalVisible = ref(false);
const selectedProduct = ref(null);

const fetchProducts = async (category) => {
  try {
    const response = await getProducts(category);
    if (Array.isArray(response.data)) {
      products.value = response.data;
    } else {
      console.warn('API response for products is not an array:', response.data);
      products.value = []; // 렌더링 오류를 방지하기 위해 빈 배열로 초기화
    }
  } catch (error) {
    console.error('Error fetching products:', error);
    products.value = []; // 에러 발생 시에도 빈 배열로 초기화
  }
};

const selectCategory = (category) => {
  selectedCategory.value = category;
  fetchProducts(category);
};

const openPurchaseModal = (product) => {
  selectedProduct.value = product;
  isModalVisible.value = true;
};

const closePurchaseModal = () => {
  isModalVisible.value = false;
  selectedProduct.value = null;
};

const handlePurchase = async ({ productId, amount }) => {
  try {
    const response = await purchaseProduct({ productId, amount });
  
    authStore.updateUserPoints(response.data.remainingPoint);
    alert('교환이 완료되었습니다.');
    closePurchaseModal();
  } catch (error) {
    console.error('Error purchasing product:', error);
    alert('포인트가 부족하거나 오류가 발생했습니다.');
  }
};

onMounted(() => {
  fetchProducts(selectedCategory.value);
});
</script>

<style scoped>
.point-shop-container {
  padding: 2rem;
  background-color: #fdfaf6; 
}

.title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 1rem;
}

.category-tabs {
  display: flex;
  gap: 1rem;
  margin-bottom: 1.5rem;
}

.category-tabs button {
  padding: 0.5rem 1rem;
  border: 1px solid #ddd;
  background-color: #fff;
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.2s;
}

.category-tabs button.active {
  background-color: #ff8c00;
  color: #fff;
  border-color: #ff8c00;
}

.user-points {
  text-align: right;
  margin-bottom: 1.5rem;
  font-weight: bold;
  font-size: 1.1rem;
  border-top: 1px solid #eee;
  padding-top: 1rem;
}

.product-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 1.5rem;
}
</style>
