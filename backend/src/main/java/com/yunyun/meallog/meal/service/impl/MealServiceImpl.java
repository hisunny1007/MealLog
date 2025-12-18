package com.yunyun.meallog.meal.service.impl;

import com.yunyun.meallog.food.dto.request.FoodRequestDto;
import com.yunyun.meallog.food.dto.response.FoodResponseDto;
import com.yunyun.meallog.food.service.FoodService;
import com.yunyun.meallog.global.common.FileService;
import com.yunyun.meallog.meal.dao.MealDao;
import com.yunyun.meallog.meal.domain.Meal;
import com.yunyun.meallog.meal.dto.request.MealRequestDto;
import com.yunyun.meallog.meal.dto.response.MealCalendarSummaryResponseDto;
import com.yunyun.meallog.meal.dto.response.MealResponseDto;
import com.yunyun.meallog.meal.service.MealService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {

    private final MealDao mealDao;
    private final FileService fileService;
    private final FoodService foodService;

    @Override
    public MealResponseDto createMeal(Long userId, MealRequestDto requestDto, MultipartFile image) {
        // 같은 날짜, 같은 식단 타입 존재하는지 확인 필요
        boolean exist = mealDao.existsByDateAndMealType(userId, requestDto.getDate(), requestDto.getMealType());

        if (exist) {
            throw new IllegalArgumentException(requestDto.getMealType() + " 식단은 이미 등록되어 있습니다.");
        }

        // DB에서 음식 조회
        List<FoodResponseDto> foods = foodService.searchFood(requestDto.getFoodName());
        FoodResponseDto selectedFood;

        //  DB에 있으면 음식 선택
        if(!foods.isEmpty() && foods.get(0).getName().equals(requestDto.getFoodName())) {
            selectedFood = foods.get(0);
        } else {
            // 없으면 직접 등록함
            selectedFood = foodService.createCustomFood(userId, FoodRequestDto.builder()
                    .name(requestDto.getFoodName())
                    .calories(requestDto.getCalories())
                    .carbs(requestDto.getCarbs())
                    .protein(requestDto.getProtein())
                    .fat(requestDto.getFat())
                    .build());
        }

        requestDto.setSelectedFood(selectedFood);

        // 이미지 처리
        String imageUrl;

        try {
            if(image != null && !image.isEmpty()) {
                imageUrl = fileService.saveFile(image);
            } else {
                imageUrl = fileService.getRandomDefaultMealImage();
            }
        } catch (FileUploadException e) {
            throw new RuntimeException("이미지 처리 실패");
        }

        // dto -> entity
        Meal meal = requestDto.toEntity(userId);
        meal.setImageUrl(imageUrl);

        mealDao.insertMeal(meal);

        return MealResponseDto.from(meal);
    }


    @Override
    public MealResponseDto getMeal(Long userId, Long mealId) {
        Meal meal = mealDao.findById(mealId);

        if (meal == null || !meal.getUserId().equals(userId)) {
            throw new IllegalArgumentException("해당 식단을 찾을 수 없습니다.");
        }
        return MealResponseDto.from(meal);
    }

    // 특정 날짜 식단 조회
    @Override
    public List<MealResponseDto> getMealsByDate(Long userId, LocalDate date) {
       List<Meal> meals = mealDao.findByDate(userId, date);

       // list 형식 stream으로
       return meals.stream()
                .map(MealResponseDto::from)
                .sorted((m1, m2) -> m1.getMealType().compareTo(m2.getMealType())) // 아침-점심-저녁 순서 정렬
                .collect(Collectors.toList());
    }



    @Override
    public MealResponseDto updateMeal(Long userId, Long mealId, MealRequestDto requestDto) {
        Meal meal = mealDao.findById(mealId);
        if (meal == null || !meal.getUserId().equals(userId)) {
            throw new IllegalArgumentException("해당 식단을 찾을 수 없습니다.");
        }

        // 같은 날짜, 같은 mealType 체크  필요
        boolean exist = mealDao.existsByDateAndMealType(userId, requestDto.getDate(), requestDto.getMealType());
        if (exist && !meal.getMealType().equals(requestDto.getMealType())) {
            throw new IllegalArgumentException("해당 날짜에는 이미 같은 식단 종류가 존재합니다.");
        }

        Meal updated = requestDto.toEntity(userId);
        updated.setId(mealId);
        mealDao.insertMeal(updated); // update 쿼리 필요
        return MealResponseDto.from(updated);
    }

    @Override
    public void deleteMeal(Long userId, Long mealId) {
        Meal meal = mealDao.findById(mealId);
        if (meal == null || !meal.getUserId().equals(userId)) {
            throw new IllegalArgumentException("해당 식단을 삭제할 수 없습니다.");
        }
        mealDao.deleteMeal(mealId);
    }

    // 캘린더 표시용
    @Override
    public List<MealCalendarSummaryResponseDto> getCalendarSummary(Long userId, int year, int month) {

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        return mealDao.findCalendarSummary(userId, start, end);
    }
}
