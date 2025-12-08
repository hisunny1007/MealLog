package com.yunyun.meallog.user.service.impl;

import com.yunyun.meallog.global.exception.CustomException;
import com.yunyun.meallog.global.exception.ErrorCode;
import com.yunyun.meallog.user.dao.UserDao;
import com.yunyun.meallog.user.domain.User;
import com.yunyun.meallog.user.dto.request.LoginRequestDto;
import com.yunyun.meallog.user.dto.request.UserRequestDto;
import com.yunyun.meallog.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    @Transactional
    public Long signUp(UserRequestDto userRequestDto) {
        // 이메일 중복 체크
        userDao.findByEmail(userRequestDto.getEmail()).ifPresent(user -> {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        });

        User user = userRequestDto.toEntity();

        userDao.signUp(user);
        return user.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Long login(LoginRequestDto loginRequestDto, HttpSession session) {
        User user = userDao.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_FAILED));

        if (!user.getPassword().equals(loginRequestDto.getPassword())) {
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }

        session.setAttribute("userId", user.getId());
        return user.getId();
    }

    @Override
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
