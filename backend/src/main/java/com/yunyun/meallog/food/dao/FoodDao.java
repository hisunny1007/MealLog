package com.yunyun.meallog.food.dao;

import com.yunyun.meallog.food.domain.Food;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FoodDao {

    // 자동완성
    List<Food> autoComplete(@Param("query") String query);

    // 검색
    List<Food> searchFood(@Param("query") String query);

    Food getFoodDetail(@Param("id") Long id);

    void insertCustomFood(Food food);

}
