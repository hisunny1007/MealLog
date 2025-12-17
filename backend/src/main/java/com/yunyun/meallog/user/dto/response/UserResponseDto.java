package com.yunyun.meallog.user.dto.response;

import com.yunyun.meallog.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Integer id;
    private String email;
    private String nickname;
    private Integer age;
    private String gender;
    private Float height;
    private Float weight;
    private String exerciseFrequency;
    private String exerciseGoal;
    private Integer rewardPoint;
    private LocalDateTime createdAt;

    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .age(user.getAge())
                .gender(user.getGender())
                .height(user.getHeight())
                .weight(user.getWeight())
                .exerciseFrequency(String.valueOf(user.getExerciseFrequency()))
                .exerciseGoal(String.valueOf(user.getExerciseGoal()))
                .rewardPoint(user.getRewardPoint())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
