package com.yunyun.meallog.user.service;

import com.yunyun.meallog.user.dto.request.LoginRequestDto;
import com.yunyun.meallog.user.dto.request.UserRequestDto;
import com.yunyun.meallog.user.dto.response.LoginResponseDto;
import jakarta.servlet.http.HttpSession;

public interface UserService {
    void signUp(UserRequestDto userRequestDto);

    LoginResponseDto login(LoginRequestDto loginRequestDto);
}
