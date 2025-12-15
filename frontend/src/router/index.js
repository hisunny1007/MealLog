import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '@/views/LoginView.vue'
import SignUpView from '@/views/SignUpView.vue'
import MyPageView from '@/views/MyPageView.vue'
import CalendarView from '@/views/CalendarView.vue'
import MealRecordView from '@/views/MealRecordView.vue'

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
      path: '/meals/:date',
      name: 'mealRecord',
      component: MealRecordView,
    },
  ],
})


export default router
