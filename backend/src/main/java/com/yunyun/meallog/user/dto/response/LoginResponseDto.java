package com.yunyun.meallog.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

//Token을 반환하기 위한 응답 DTO
@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String accessToken;
}

