package com.yunyun.meallog.pointshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryDto {
    private String productName;      // 상품명 (Product 테이블에서 가져옴)
    private String imageUrl;         // 상품 이미지 URL (Product 테이블에서 가져옴)
    private int totalPoint;      // 총 사용 포인트 (Orders 테이블)
    private int amount;          // 구매 수량 (Orders 테이블)
    private LocalDateTime orderDate; // 구매일시 (Orders 테이블)

}
