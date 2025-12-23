<template>
  <div class="calendar-page py-5">
    <h2 class="text-center fw-bold mb-4">하루 식단을 기록하는 가장 쉬운 방법 🍽️</h2>
    <FullCalendar :options="calendarOptions" />
  </div>
</template>

<script setup>
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import { useRouter } from 'vue-router'
import mealApi from '@/api/mealApi'
import { onMounted, ref } from 'vue'

const router = useRouter()

// 캘린더에 식단 존재 시 표시줄 추가하기
const calendarEvents = ref([])

// 식단 점수별 색상
const scoreColors = {
  1: '#F28B82',
  2: '#F6AD55',
  3: '#F4D35E',
  4: '#8BCF9B',
  5: '#7EA6E0',
}

function getScoreColor(score) {
  return scoreColors[score]
}

// 식단 등록 표시
async function loadCalendarMonth(year, month) {
  try {
    const summaries = await mealApi.getCalendarSummary(year, month)

    // 기존 이벤트 초기화
    calendarEvents.value = []

    // 각 날짜별로 아침/점심/저녁 점수 이벤트 생성
    summaries.forEach((day) => {
      ;['breakfastScore', 'lunchScore', 'dinnerScore'].forEach((key) => {
        const score = day[key]

        if (score) {
          calendarEvents.value.push({
            date: day.date,
            allDay: true,
            backgroundColor: getScoreColor(score),
            borderColor: 'transparent',
          })
        }
      })
    })
  } catch (err) {
    console.error('캘린더 데이터 로드 실패', err)
  }
}

const meals = ref([])

// FullCalendar 옵션
const calendarOptions = ref({
  plugins: [dayGridPlugin, interactionPlugin],
  initialView: 'dayGridMonth',
  locale: 'ko', // 날짜 제목 한국어로 (버튼텍스트는 자동적용x)
  buttonText: {
    today: '오늘',
  },

  // 식단 분석으로 이동하는 버튼 추가
  customButtons: {
    todayAnalysis: {
      text: '오늘의 식단 분석',
      click: () => {
        const today = new Date().toISOString().slice(0, 10)

        router.push({
          name: 'MealDailyAnalysis',
          params: { date: today },
        })
      },
    },
  },

  // 버튼 위치 설정 // 무조건 다 명시해줘야 함
  headerToolbar: {
    left: 'todayAnalysis',
    center: 'title',
    right: 'today prev,next',
  },

  events: calendarEvents, // 이벤트 연결

  // 날짜 클릭했을 때
  // 식단 있음 -> MealDailyView (오늘의 식단 기록)
  // 식단 없음 -> MealCreateView (식단 등록 폼)

  async dateClick(info) {
    const date = info.dateStr // YYYY-MM-DD

    try {
      // 1. 클릭한 해당 날짜 식단 조회
      const result = await mealApi.getMealsByDate(date) // data그자체
      const meals = result ?? []

      // 2. 결과에 따라 분기
      if (meals.length === 0) {
        // 식단 없으면 CreateView 페이지로 이동
        router.push({
          name: 'MealCreate',
          params: { date },
        })
      } else {
        // 식단 있으면 DailyView 페이지로 이동
        router.push({
          name: 'MealDaily',
          params: { date },
        })
      }
    } catch (e) {
      console.error('식단 조회 실패:', e)
      router.push({
        name: 'MealCreate',
        params: { date },
      })
    }
  },
})

// 페이지 로드 시 현재 월 데이터 불러오기
onMounted(() => {
  const today = new Date()
  const year = today.getFullYear()
  const month = today.getMonth() + 1
  loadCalendarMonth(year, month)
})
</script>

<style>
/* FullCalendar 전용 CSS (scope 제외시킴) */
/* .calendar-page {
  background: #faf7f4;
} */

.fc {
  background: #ffffff;
  border: 2px solid #faf7f4;
  border-radius: 16px;
  padding: 32px;
  font-family: 'Pretendard', sans-serif;
}

/* 식단 등록 바 표시 */
.fc-daygrid-day-top {
  margin-bottom: 15px;
}

.fc-daygrid-event {
  height: 10px;
  margin-bottom: 5px;
  border-radius: 6px;
}

/* 월 제목 */
.fc-toolbar-title {
  font-size: 1.4rem;
  font-weight: 700;
  color: var(--main-brown);
}

/* 이전 / 다음 / 오늘 버튼 */
.fc-button {
  background: var(--bg-main) !important;
  color: var(--main-brown) !important;
  border: 1px solid rgba(75, 46, 30, 0.15) !important;
  border-radius: 12px !important;
  padding: 6px 14px !important;
  font-weight: 600;
  font-size: 0.85rem;
  transition: all 0.15s ease;
}

/* hover - 배경만 살짝 */
.fc-button:hover {
  background: rgba(75, 46, 30, 0.06) !important;
}

/* active - 눌림 */
.fc-button:active {
  background: rgba(75, 46, 30, 0.1) !important;
  transform: scale(0.96);
}

/* focus 제거 */
.fc-button:focus {
  box-shadow: none !important;
}

/* 요일 헤더 */
.fc-col-header-cell {
  background: var(--bg-main);
  border: none;
}

.fc-col-header-cell-cushion {
  color: #6b4b3a;
  font-weight: 600;
  font-size: 0.9rem;
}

/* 날짜 칸 */
.fc-daygrid-day {
  border: none;
}

.fc-daygrid-day-frame {
  border-radius: 5px;
  padding: 6px;
  transition: background 0.2s ease;
}

/* hover 효과 */
.fc-daygrid-day-frame:hover {
  background: #f7f1ec;
}

/* 오늘 날짜 today*/
.fc-day-today {
  background: transparent !important;
}

.fc-day-today .fc-daygrid-day-frame {
  border: 1px dashed;
  border-color: var(--border);
  border-radius: 14px;
}

/* 달력 날짜 */
.fc-daygrid-day-number {
  color: var(--main-brown);
  font-weight: 600;
  font-size: 0.85rem;
}

/* 버튼 간격 추가 */
.fc-toolbar-chunk button {
  margin-right: 6px;
}

/* 이벤트 바 기본 */
.fc-daygrid-event {
  border-radius: 8px;
  padding: 2px 6px;
  font-size: 0.6rem;
  letter-spacing: 1px;
  color: var(--main-brown);
}

/* 시간대별 미세 차이 */
.meal-breakfast {
  opacity: 0.9;
}

.meal-lunch {
  opacity: 0.85;
}

.meal-dinner {
  opacity: 0.8;
}
</style>
