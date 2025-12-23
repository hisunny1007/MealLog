<template>
  <div class="point-shop-container">
    <h2 class="text-center fw-bold mb-4">포인트샵</h2>

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
      <span>잔여 포인트 {{ userPoints.toLocaleString() }}p</span>
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
import { useRoute } from 'vue-router';

const authStore = useAuthStore();
const route = useRoute();

const categories = ref([
  { name: '단백질', value: 'protein' },
  { name: '다이어트 도시락', value: 'diet-box' },
  { name: '야채', value: 'vegetable' },
]);

// ref 변수 선언
const selectedCategory = ref('protein');
const products = ref([]);
const userPoints = computed(() => authStore.user?.rewardPoint || 0);

const isModalVisible = ref(false);
const selectedProduct = ref(null);

// 상품 목록 조회 함수
const fetchProducts = async (category) => {
  try {
    const response = await getProducts(category);
    if (Array.isArray(response.data)) {
      products.value = response.data;
    } else {
      products.value = [];
    }
  } catch (error) {
    console.error('Error fetching products:', error);
    products.value = [];
  }
};

// 탭 선택 함수
const selectCategory = (category) => {
  selectedCategory.value = category;
  fetchProducts(category);
};


onMounted(() => {
  if (route.query.category) {
    selectedCategory.value = route.query.category;
  }
  fetchProducts(selectedCategory.value);
});


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
  justify-content: center;
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
  background-color: var(--main-brown);
  color: #fff;

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
  gap: 2.2rem;
}
</style>
