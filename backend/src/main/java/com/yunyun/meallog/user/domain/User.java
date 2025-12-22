package com.yunyun.meallog.user.domain;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    private Long id;

    private String email;

    private String password;

    private String nickname;

    private Integer age;

    private String gender;
    private Float height;

    private Float weight;

    private ExerciseFrequency exerciseFrequency;

    private ExerciseGoal exerciseGoal;

    private Integer rewardPoint = 0;

    private LocalDateTime createdAt = LocalDateTime.now();

    public void updatePointBalance(int points) {
        this.rewardPoint += points;
    }
}
