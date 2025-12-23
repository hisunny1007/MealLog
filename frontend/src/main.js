import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'

import '@/assets/style/main.css'
import Vue3Toastify from 'vue3-toastify'
import 'vue3-toastify/dist/index.css'

const app = createApp(App)

app.use(Vue3Toastify, {
  autoClose: 2000, // 2초
  position: 'top-center',
  style: {
    width: '400px',
  },
})
app.use(createPinia())
app.use(router)

app.mount('#app')
