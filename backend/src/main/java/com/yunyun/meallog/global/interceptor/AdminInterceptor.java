package com.yunyun.meallog.global.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AdminInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // 로그인한 사용자 관리자인지 아닌지 판별
        HttpSession session = request.getSession();
        if(!"admin".equals(session.getAttribute("loginUser"))) {
            response.sendRedirect("list");
            return false;
        }

        return true;
    }
}