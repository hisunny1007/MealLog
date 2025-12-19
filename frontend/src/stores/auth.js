import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
import { getMyProfile } from '@/api/auth';

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

  function updateUserPoints(newPoint) {
    if (user.value) {
      user.value.rewardPoint = newPoint;
      localStorage.setItem('user', JSON.stringify(user.value));
    }
  }

  async function fetchUser() {
    console.log("Attempting to fetch user profile...");
    if (token.value) {
      try {
        const response = await getMyProfile();
        console.log("Successfully fetched user profile:", response.data);
        user.value = response.data;
        localStorage.setItem('user', JSON.stringify(response.data));
      } catch (error) {
        console.error("Failed to fetch user profile:", error);
 
      }
    } else {
      console.warn("No token found, cannot fetch user profile.");
    }
  }

  return { token, user, isAuthenticated, setLogin, setLogout, updateUserPoints, fetchUser };
});
