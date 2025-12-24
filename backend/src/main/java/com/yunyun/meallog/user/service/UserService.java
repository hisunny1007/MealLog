package com.yunyun.meallog.user.service;

import com.yunyun.meallog.user.dto.request.LoginRequestDto;
import com.yunyun.meallog.user.dto.request.SignupStep1RequestDto;
import com.yunyun.meallog.user.dto.request.SignupStep2RequestDto;
import com.yunyun.meallog.user.dto.response.LoginResponseDto;
import com.yunyun.meallog.user.dto.response.UserResponseDto;

public interface UserService {
    Long signUp(SignupStep1RequestDto requestDto);

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    void updateProfile(Long userId, SignupStep2RequestDto requestDto);

    UserResponseDto getMyProfile(Long userId);

    void checkEmailDuplication(String email);
}
