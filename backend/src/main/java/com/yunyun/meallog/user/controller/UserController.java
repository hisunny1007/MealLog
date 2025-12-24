package com.yunyun.meallog.user.controller;

import com.yunyun.meallog.user.dto.request.LoginRequestDto;
import com.yunyun.meallog.user.dto.request.SignupStep1RequestDto;
import com.yunyun.meallog.user.dto.request.SignupStep2RequestDto;
import com.yunyun.meallog.user.dto.response.LoginResponseDto;
import com.yunyun.meallog.user.dto.response.UserResponseDto;
import com.yunyun.meallog.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * "userId"라는 키를 통해 이 값이 무엇을 의미하는지 명확하게 함
     * 회원가입 시 추가적인 정보를 함께 반환해야 할 경우 Map에 새로운 키-값 쌍을 추가하여 확장 가능
     */
    @PostMapping("/signup")
    public ResponseEntity<Map<String, Long>> signUp(@Valid @RequestBody SignupStep1RequestDto requestDto) {
        Long userId = userService.signUp(requestDto);
        Map<String, Long> response = Collections.singletonMap("userId", userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{userId}/profile")
    public ResponseEntity<String> updateProfile(@PathVariable Long userId, @RequestBody SignupStep2RequestDto requestDto) {
        userService.updateProfile(userId, requestDto);
        return ResponseEntity.ok("프로필이 성공적으로 업데이트되었습니다.");
    }

   
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {

        LoginResponseDto response = userService.login(loginRequestDto);

        return ResponseEntity.ok(response);
    }

 
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {

        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

  
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyProfile(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        UserResponseDto response = userService.getMyProfile(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/emailcheck")
    public ResponseEntity<String> checkEmailDuplication(@RequestParam String email) {
        userService.checkEmailDuplication(email);
        return ResponseEntity.ok("사용 가능한 이메일입니다.");
    }
}
