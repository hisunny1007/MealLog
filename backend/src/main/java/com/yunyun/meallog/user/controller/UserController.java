package com.yunyun.meallog.user.controller;

import com.yunyun.meallog.user.dto.request.LoginRequestDto;
import com.yunyun.meallog.user.dto.request.UserRequestDto;
import com.yunyun.meallog.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserRequestDto userRequestDto) { // "signIn" -> "signUp"
        Long userId = userService.signUp(userRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody LoginRequestDto loginRequestDto, HttpSession session) {
        userService.logIn(loginRequestDto, session);
        return ResponseEntity.ok("로그인 되었습니다.");
    }

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logOut(HttpSession session) {
        userService.logOut(session);
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}
