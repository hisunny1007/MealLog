package com.yunyun.meallog.user.dto.request;

import com.yunyun.meallog.user.domain.ExerciseFrequency;
import com.yunyun.meallog.user.domain.ExerciseGoal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupStep2RequestDto {
    private Integer age;
    private String gender;
    private Float height;
    private Float weight;
    private String exerciseFrequency;
    private String exerciseGoal;
}
