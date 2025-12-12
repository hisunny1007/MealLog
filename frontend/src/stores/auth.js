import { defineStore } from 'pinia';
import { ref, computed } from 'vue';

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('token') || null);

  // 안전한 JSON 파싱
  const initialUser = localStorage.getItem('user');
  let parsedUser = null;

  try {
    parsedUser = initialUser ? JSON.parse(initialUser) : null;
  } catch (e) {
    console.error("Failed to parse user from localStorage:", e);
    parsedUser = null;
    localStorage.removeItem('user'); // 잘못된 값 제거
  }

  const user = ref(parsedUser);

  const isAuthenticated = computed(() => token.value !== null);

  function setLogin(newToken, newUser) {
    token.value = newToken;
    user.value = newUser;

    localStorage.setItem('token', newToken);
    localStorage.setItem('user', JSON.stringify(newUser));
  }

  function setLogout() {
    token.value = null;
    user.value = null;

    localStorage.removeItem('token');
    localStorage.removeItem('user');
  }

  return { token, user, isAuthenticated, setLogin, setLogout };
});
