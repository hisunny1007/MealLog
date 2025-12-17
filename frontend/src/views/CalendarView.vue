<template>
  <div class="calendar-page p-4">
    <FullCalendar :options="calendarOptions" />
  </div>
</template>

<script setup>
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
// import timeGridPlugin from '@fullcalendar/timegrid'; // 시간 그리드 플러그인 추가
import interactionPlugin from '@fullcalendar/interaction'
import { useRouter } from 'vue-router'
import mealApi from '@/api/meal'

const router = useRouter()

const calendarOptions = {
  plugins: [dayGridPlugin, interactionPlugin],
  initialView: 'dayGridMonth',

  // dateClick(info) {
  //   router.push({
  //     name: 'MealDaily',
  //     params: { date: info.dateStr}
  //   })
  // }

  // 날짜 클릭했을 때
  // 식단 있음 -> MealDailyView (오늘의 식단 기록)
  // 식단 없음 -> MealCreateView (식단 등록 폼)

  async dateClick(info) {
    const date = info.dateStr
    console.log('date clicked:', info.dateStr)
    try {
      // 1. 해당 날짜 식단 조회
      const meals = await mealApi.getMealsByDate(date)

      // 2. 결과에 따라 분기
      if (meals.length === 0) {
        router.push({
          name: 'MealCreate',
          query: { date },
        })
      } else {
        router.push({
          name: 'MealDaily',
          params: { date },
        })
      }
    } catch (e) {
      console.error('식단 조회 실패:', e)
      router.push({
        name: 'MealCreate',
        query: { date },
      })
    }
  },
}
</script>

<style scoped></style>
