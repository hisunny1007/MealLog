package com.yunyun.meallog.global.interceptor;

import com.yunyun.meallog.global.exception.CustomException;
import com.yunyun.meallog.global.exception.ErrorCode;
import com.yunyun.meallog.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.logging.LogException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String header = request.getHeader("Authorization");
        System.out.println("👉 Authorization header = " + header);

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            if (jwtUtil.isTokenValid(token)) {
                // String -> Long 변환
//                Long userId = Long.parseLong(jwtUtil.getUserId(token)); [수정]
                Long userId = jwtUtil.getUserId(token);
                System.out.println("👉 parsed userId = " + userId); // ⭐ 이 줄 추가

                request.setAttribute("userId", userId);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }
}