package com.yunyun.meallog.pointshop.service;


import com.yunyun.meallog.pointshop.dto.OrderHistoryDto;
import com.yunyun.meallog.pointshop.dto.OrderRequestDto;
import com.yunyun.meallog.pointshop.dto.OrderResponseDto;
import com.yunyun.meallog.pointshop.dto.ProductViewDto;

import java.util.List;

public interface PointShopService {
    // 카테고리별 상품 목록 조회
    List<ProductViewDto> getProducts(String category);

    // 포인트 교환 트랜잭션 처리
    OrderResponseDto processPointExchange(Long userId, OrderRequestDto dto);

    List<OrderHistoryDto> getOrderHistory(Long userId);

}
