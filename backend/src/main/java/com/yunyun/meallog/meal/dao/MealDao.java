package com.yunyun.meallog.meal.dao;

import com.yunyun.meallog.meal.domain.Meal;
import com.yunyun.meallog.meal.domain.MealType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
//MyBatis Mapper
public interface MealDao {

    void insertMeal(Meal meal);

    Meal findById(Long id);

    // mybatis에서는 메서드 파라미터가 2개 이상일 경우 @Param 쓰는게 좋음
    // List<Meal> findByDate(Long userId, LocalDate date);
    List<Meal> findByDate(@Param("userId") Long userId,
                          @Param("date") LocalDate date);

    void updateMeal(Meal meal);

    void deleteMeal(Long id);

    // 식단 존재 여부 확인
    boolean existsByDateAndMealType(@Param("userId") Long userId,
                                    @Param("date") LocalDate date,
                                    @Param("mealType") MealType mealType);

    // 식단 개수 확인
    int countByDate(@Param("userId") Long userId,
                    @Param("date") LocalDate date);
}
