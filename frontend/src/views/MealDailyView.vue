<template>
  <div class="meal-daily-page container py-5">
    <div class="text-center mb-5">
      <h2 class="fw-bold mb-3">{{ formattedDate }} 식단</h2>
      <button class="btn btn-outline-brown rounded-pill px-4 shadow-sm mt-4" @click="goToAnalysis">
        📊 데일리 분석 리포트 보기
      </button>
    </div>

    <div class="row g-4">
      <section class="col-lg-8">
        <div class="timeline-container ps-4">
          <MealTimeSection
            v-for="(section, index) in mealSections"
            :key="section.type"
            :meal="section.meal"
            :label="section.label"
            :mealType="section.type"
            :isLast="index === mealSections.length - 1"
            @add="goToCreate"
            @edit="goToEdit"
            @delete="openDeleteModal"
          />
        </div>
      </section>

      <aside class="col-lg-4">
        <ProductRecommendation />
      </aside>
    </div>
    <Modal
      :isOpen="isModalOpen"
      :title="'식단 삭제'"
      :message="'정말 이 식단을 삭제하시겠습니까?'"
      :type="'confirm'"
      @confirm="handleDeleteConfirm"
      @close="isModalOpen = false"
    />
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import mealApi from '@/api/mealApi'

import MealTimeSection from '@/components/daily/MealTimeSection.vue'
import ProductRecommendation from '@/components/daily/ProductRecommendation.vue'
import Modal from '@/components/common/Modal.vue'
import { toast } from 'vue3-toastify'

const route = useRoute()
const router = useRouter()
const date = route.params.date

const isModalOpen = ref(false)
const mealIdToDelete = ref(null)

const formattedDate = computed(() => {
  const rawDate = new Date(date)
  const year = rawDate.getFullYear()
  const month = rawDate.getMonth() + 1
  const day = rawDate.getDate()
  return `${year}년 ${month}월 ${day}일`
})

const meals = ref([])

onMounted(async () => {
  fetchMeals()
})

const fetchMeals = async () => {
  try {
    const result = await mealApi.getMealsByDate(date)
    meals.value = result ?? []
  } catch (error) {
    console.error('식단 조회 실패:', error)
    meals.value = []
  }
}

const mealSections = computed(() => [
  {
    type: 'BREAKFAST',
    label: '아침',
    meal: meals.value.find((m) => m.mealType === 'BREAKFAST'),
  },
  {
    type: 'LUNCH',
    label: '점심',
    meal: meals.value.find((m) => m.mealType === 'LUNCH'),
  },
  {
    type: 'DINNER',
    label: '저녁',
    meal: meals.value.find((m) => m.mealType === 'DINNER'),
  },
])

const goToCreate = (mealType) => {
  router.push({
    name: 'MealCreate',
    params: { date: date },
    query: { mealType: mealType }, // mealType만 쿼리로 보냄
  })
}

const goToEdit = (mealId) => {
  router.push({
    name: 'MealEdit',
    params: { mealId: mealId, date: date },
  })
}

const goToAnalysis = () => {
  router.push({
    name: 'MealDailyAnalysis',
    params: { date: date },
  })
}

const openDeleteModal = (mealId) => {
  mealIdToDelete.value = mealId
  isModalOpen.value = true
}

const handleDeleteConfirm = async () => {
  isModalOpen.value = false
  const id = mealIdToDelete.value

  if (!id) return

  try {
    await mealApi.deleteMeal(id)

    // 상태 업데이트
    meals.value = meals.value.filter((m) => m.id !== id)
    toast.success('🗑️ 식단이 삭제되었습니다.')
  } catch (error) {
    toast.error('식단 삭제 중 오류가 발생했습니다.')
  } finally {
    mealIdToDelete.value = null
  }
}
</script>

<style scoped>
.meal-daily-page {
  max-width: 1100px;
  margin: 0 auto;
}

.timeline-container {
  position: relative;
  border-left: 2px dashed #e2d1c3;
}

.btn-outline-brown {
  border: 1px solid #a57c5e;
  color: #a57c5e;
  background: white;
  transition: all 0.2s;
}
.btn-outline-brown:hover {
  background: #a57c5e;
  color: #fff;
}
</style>
