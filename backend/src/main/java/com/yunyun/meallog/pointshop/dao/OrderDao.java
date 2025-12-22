package com.yunyun.meallog.pointshop.dao;

import com.yunyun.meallog.pointshop.domain.Order;
import com.yunyun.meallog.pointshop.dto.OrderHistoryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderDao {
    int insertOrder(Order order);
    //사용자의 포인트 교환내역 조회
    List<OrderHistoryDto> selectOrderHistoryByUserId(@Param("userId") Long userId);
}
