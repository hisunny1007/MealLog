package com.yunyun.meallog.user.dto.request;

import com.yunyun.meallog.user.domain.ExerciseFrequency;
import com.yunyun.meallog.user.domain.ExerciseGoal;
import com.yunyun.meallog.user.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {
    // 페이지 1: 로그인 정보
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 16, message = "비밀번호는 8자 이상 16자 이하이어야 합니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력해주세요.")
    private String nickname;
    
    private String name;

    // 페이지 2: 개인 정보
    @NotNull(message = "나이는 필수 입력 값입니다.")
    private Integer age;

    private String gender;

    @NotNull(message = "키는 필수 입력 값입니다.")
    private Float height;

    @NotNull(message = "몸무게는 필수 입력 값입니다.")
    private Float weight;
    
    private ExerciseFrequency exerciseFrequency;
    
    private ExerciseGoal exerciseGoal;

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
