package com.yunyun.meallog.user.service.impl;

import com.yunyun.meallog.global.exception.CustomException;
import com.yunyun.meallog.global.exception.ErrorCode;
import com.yunyun.meallog.global.jwt.JwtUtil;
import com.yunyun.meallog.user.dao.UserDao;
import com.yunyun.meallog.user.domain.ExerciseFrequency;
import com.yunyun.meallog.user.domain.ExerciseGoal;
import com.yunyun.meallog.user.domain.User;
import com.yunyun.meallog.user.dto.request.LoginRequestDto;
import com.yunyun.meallog.user.dto.request.SignupStep1RequestDto;
import com.yunyun.meallog.user.dto.request.SignupStep2RequestDto;
import com.yunyun.meallog.user.dto.response.LoginResponseDto;
import com.yunyun.meallog.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Integer signUp(SignupStep1RequestDto requestDto) {
        // 이메일 중복 체크
        userDao.findByEmail(requestDto.getEmail())
                .ifPresent(user -> { throw new CustomException(ErrorCode.DUPLICATE_EMAIL); });

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = User.builder()
                .email(requestDto.getEmail())
                .password(encodedPassword)
                .nickname(requestDto.getNickname())
                .build();

        userDao.signUp(user);
        return user.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        User user = userDao.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_FAILED));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }

        String accessToken = jwtUtil.generateToken(String.valueOf(user.getId()));

        return new LoginResponseDto(accessToken);
    }

    @Override
    @Transactional
    public void updateProfile(Long userId, SignupStep2RequestDto requestDto) {
        User userToUpdate = userDao.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // DTO의 String 값을 Enum으로 변환
        ExerciseFrequency frequency = null;
        if (requestDto.getExerciseFrequency() != null) {
            frequency = switch (requestDto.getExerciseFrequency()) {
                case "0" -> ExerciseFrequency.NONE;
                case "1" -> ExerciseFrequency.LOW;
                case "2" -> ExerciseFrequency.MEDIUM;
                case "3" -> ExerciseFrequency.HIGH;
                case "4" -> ExerciseFrequency.VERY_HIGH;
                default -> null; // 또는 예외 처리
            };
        }

        ExerciseGoal goal = null;
        if (requestDto.getExerciseGoal() != null && !requestDto.getExerciseGoal().isEmpty()) {
            try {
                goal = ExerciseGoal.valueOf(requestDto.getExerciseGoal().toUpperCase());
            } catch (IllegalArgumentException e) {

                goal = null;
            }
        }

        User updatedUser = userToUpdate.toBuilder()
                .age(requestDto.getAge())
                .gender(requestDto.getGender())
                .height(requestDto.getHeight())
                .weight(requestDto.getWeight())
                .exerciseFrequency(frequency)
                .exerciseGoal(goal)
                .build();

        userDao.updateProfile(updatedUser);
    }
}


