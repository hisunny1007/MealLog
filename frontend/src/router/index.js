import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '@/views/LoginView.vue'
import SignUpView from '@/views/SignUpView.vue'
import MyPageView from '@/views/MyPageView.vue'
import CalendarView from '@/views/CalendarView.vue'
import MealCreateView from '@/views/MealCreateView.vue'
import MealDailyView from '@/views/MealDailyView.vue'
import DailyAnalysisView from '@/views/DailyAnalysisView.vue'

const router = createRouter({
  // history: createWebHistory(import.meta.env.BASE_URL),
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'home',
      component: CalendarView,
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView,
    },
    {
      path: '/signup',
      name: 'signup',
      component: SignUpView,
    },
    {
      path: '/mypage',
      name: 'mypage',
      component: MyPageView,
    },
    {
      path: '/meals/create/:date',
      name: 'MealCreate',
      component: MealCreateView,
      props: true,
    },
    {
      path: '/meals/date/:date',
      name: 'MealDaily',
      component: MealDailyView,
      props: true,
    },
    {
      path: '/meals/date/:date/analysis',
      name: 'MealDailyAnalysis',
      component: DailyAnalysisView,
    },
  ],
})

export default router
