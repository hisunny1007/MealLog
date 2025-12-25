<template>
  <div class="analysis-page container py-5">

    <!-- 비로그인 미리보기 -->
    <AnalysisPreview v-if="!isLoggedIn" :date="date" />

    <!-- 로그인 - 오늘의 식단 없음 -->
    <AnalysisEmpty
      v-else-if="meals.length === 0"
      :date="date"
    />

    <!-- 로그인 - 오늘의 식단 있음 -->
    <AnalysisContent
      v-else
      :date="date"
      :meals="meals"
    />
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'
import { storeToRefs } from 'pinia'
import mealApi from '@/api/mealApi'

import AnalysisEmpty from '@/components/analysis/AnalysisEmpty.vue'
import AnalysisContent from '@/components/analysis/AnalysisContent.vue'
import AnalysisPreview from '@/components/analysis/AnalysisPreview.vue'

const route = useRoute()
const date = route.params.date

const authStore = useAuthStore()
const { user } = storeToRefs(authStore)
const isLoggedIn = computed(() => !!user.value)

const meals = ref([])

const fetchMeals = async () => {
  if (!isLoggedIn.value) return
  meals.value = (await mealApi.getMealsByDate(date)) ?? []
}

onMounted(fetchMeals)
</script>
