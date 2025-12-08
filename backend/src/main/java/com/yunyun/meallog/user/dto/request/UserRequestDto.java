package com.yunyun.meallog.user.dto.request;

import com.yunyun.meallog.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {
    // 페이지 1: 로그인 정보
    private String email;
    private String password;
    private String nickname;
    private String name;

    // 페이지 2: 개인 정보
    private Integer age;
    private String gender;
    private Float height;
    private Float weight;
    private String exerciseFrequency;
    private String exerciseGoal;

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .age(age)
                .gender(gender)
                .height(height)
                .weight(weight)
                .exerciseFrequency(exerciseFrequency)
                .exerciseGoal(exerciseGoal)
                .build();
    }
}
