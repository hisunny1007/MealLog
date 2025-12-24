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
        v-for="product in paginatedProducts"
        :key="product.id"
        :product="product"
        @purchase="openPurchaseModal"
      />
    </div>
    <div v-if="products.length === 0" class="empty-message">
      상품이 없습니다.
    </div>

    <div v-if="products.length > 0" class="pagination-controls">
      <button
        class="page-btn"
        @click="prevPage"
        :disabled="currentPage === 1"
      >
        &lt; </button>

      <span class="page-info">{{ currentPage }} / {{ totalPages }}</span>

      <button
        class="page-btn"
        @click="nextPage"
        :disabled="currentPage === totalPages"
      >
        &gt; </button>
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
import { toast } from 'vue3-toastify'

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

//페이지 상태 관리
const currentPage = ref(1);
const itemsPerPage = 20;

//현재 페이지에 보여줄 제품 계산
const paginatedProducts = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  return products.value.slice(start, end);
});

const totalPages = computed(() => {
  return Math.ceil(products.value.length / itemsPerPage) || 1;
});

// 상품 목록 조회 함수
const fetchProducts = async (category) => {
  try {
    const response = await getProducts(category);
    if (Array.isArray(response.data)) {
      products.value = response.data;
    } else {
      products.value = [];
    }
    currentPage.value = 1;
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

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--;
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
};

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
    window.scrollTo({ top: 0, behavior: 'smooth' }); // 페이지 이동 시 맨 위로
  }
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
    toast.info('교환이 완료되었습니다.');
    closePurchaseModal();
  } catch (error) {
    console.error('Error purchasing product:', error);
    toast.warn('포인트가 부족합니다.');
  }
};
</script>

<style scoped>
.point-shop-container {
  padding: 60px 20px;
  background-color: var(--bg-main);
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

.pagination-controls {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px;
  margin-top: 20px;
}

.page-btn {
  width: 40px;
  height: 40px;
  border-radius: 12px;
  border: 1px solid #ddd;
  background-color: #fff;
  color: var(--main-brown);
  font-weight: bold;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all 0.2s;
}

.product-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 2.2rem;
}
</style>
