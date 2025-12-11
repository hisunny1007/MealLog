package com.yunyun.meallog.food.service.impl;

import com.yunyun.meallog.food.dao.FoodDao;
import com.yunyun.meallog.food.domain.Food;
import com.yunyun.meallog.food.dto.request.FoodRequestDto;
import com.yunyun.meallog.food.dto.response.FoodResponseDto;
import com.yunyun.meallog.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodDao foodDao;

    @Override
    public List<FoodResponseDto> autoComplete(String query) {
        System.out.println(">>>> query: " + query);

//        return List.of();
        return foodDao.autoComplete(query)
                .stream()
                .map(FoodResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<FoodResponseDto> searchFood(String query) {
        return foodDao.searchFood(query)
                .stream()
                .map(FoodResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public FoodResponseDto getFoodDetail(Long id) {
        Food food = foodDao.getFoodDetail(id);

        if(food == null) {
            return null;
        }

        return FoodResponseDto.from(food);
    }

    @Override
    public FoodResponseDto createCustomFood(Long userId, FoodRequestDto requestDto) {
        Food food = requestDto.toEntity(userId);

        foodDao.insertCustomFood(food);
        return FoodResponseDto.from(food);
    }
}
