import api from './axios'

const signup = (step1Data) => {
  return api.post('/users/signup', step1Data)
}

const signupComplete = (userId, step2Data) => {
  
  return api.put(`/users/${userId}/profile`, step2Data)
}

const login = (credentials) => {
  return api.post('/users/login', credentials)
}

const logout = () => {
  
  return api.post('/users/logout')
}

const getMyProfile = () => {
  return api.get('/users/me');
};

const checkEmailDuplication = (email) => {
  return api.get('/users/emailcheck' , {
    params: {
      email: email
    }
  })
}

export { signup, signupComplete, login, logout, getMyProfile, checkEmailDuplication }
