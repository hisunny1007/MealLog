package com.yunyun.meallog.food.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Food {

    private Long id;
    private String name;

    private float calories;
    private float carbs;
    private float protein;
    private float fat;

    private boolean isCustom; // 기본 false (직접 등록하면 true)
    private Long createdBy; // 기본 null (userId - 직접 등록일 경우)
    private LocalDateTime createdAt;



}
