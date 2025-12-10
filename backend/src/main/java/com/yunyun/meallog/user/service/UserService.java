package com.yunyun.meallog.user.service;

import com.yunyun.meallog.user.dto.request.LoginRequestDto;
import com.yunyun.meallog.user.dto.request.UserRequestDto;
import jakarta.servlet.http.HttpSession;

public interface UserService {
    Long signUp(UserRequestDto userRequestDto);

    String login(LoginRequestDto loginRequestDto);
}
