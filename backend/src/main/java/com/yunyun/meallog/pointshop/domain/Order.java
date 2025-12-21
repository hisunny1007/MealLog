package com.yunyun.meallog.pointshop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private int id;
    private int userId;
    private int productId;
    private int totalPoint;
    private int amount;
    private LocalDateTime createdAt;
}
