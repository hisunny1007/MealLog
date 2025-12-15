<template>
  <div class="calendar-page p-4">
    <FullCalendar :options="calendarOptions"/>
  </div>
</template>

<script setup>
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
// import timeGridPlugin from '@fullcalendar/timegrid'; // 시간 그리드 플러그인 추가
import interactionPlugin from '@fullcalendar/interaction'
import { useRouter } from 'vue-router'

const router = useRouter()

const calendarOptions = {
  plugins: [dayGridPlugin, interactionPlugin],
  initialView: 'dayGridMonth', 
  dateClick(info) {
    router.push({
      name: 'MealPage',
      params: { date: info.dateStr}
    })
  }
};

</script>

<style scoped>
</style>