import instance from './axios';


const setAuthHeader = (token) => {
  instance.defaults.headers.common['Authorization'] = `Bearer ${token}`;
};

const signup = (step1Data) => {


  return instance.post('/users/signup', step1Data);
};

const signupComplete = (userId, step2Data) => {
  // 회원가입 1단계 성공 후, 사용자 ID를 Path Variable로 전달하여 2단계 정보 업데이트
  return instance.put(`/users/${userId}/profile`, step2Data);
};

const login = (credentials) => {
  return instance.post('/users/login', credentials);
};

const logout = () => {
  // JWT 기반에서는 클라이언트 토큰 삭제가 주를 이루지만, 서버 Refresh Token 삭제를 위해 호출 가능
  return instance.post('/users/logout');
};

const getMyProfile = () => {
  return instance.get('/users/me');
};

export { signup, signupComplete, login, logout, setAuthHeader, getMyProfile };
