package com.yunyun.meallog.pointshop.dao;

import com.yunyun.meallog.pointshop.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductDao {
    // 특정 카테고리에 속하는 상품 목록 조회
    List<Product> selectProductsByCategory(@Param("category") String category);
    
    Product selectProductById(Long productId);

    // 추천 시스템용 - 특정 카테고리에서 랜덤으로 1개 상품 조회
    Product selectOneRandomByCategory(@Param("category") String category);

    List<Product> selectRandomProducts(@Param("limit") int limit);
}
