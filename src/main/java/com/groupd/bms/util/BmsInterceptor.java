package com.groupd.bms.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Slf4j
public class BmsInterceptor implements HandlerInterceptor {

    private static final String[] LOGIN_CHECK_EXCLUDE_PATHS = {"/mmb/login","/","/error","/mmb/loginProcess.do" };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("===============================================");
        log.debug("==================== BEGIN ====================");
        log.debug("Request URI ===> " + request.getRequestURI());

        // 세션 체크 로직
        // if (!Arrays.asList(LOGIN_CHECK_EXCLUDE_PATHS).contains(request.getRequestURI())) {
        //     if (request.getSession().getAttribute("member") == null) {
        //         log.debug("No session found, redirecting to login page.");
        //         response.sendRedirect("/mmb/login");
        //         return false; // 현재 요청을 중지하고 로그인 페이지로 리다이렉트
        //     }
        // }
        return true; // 로그인, 등록, 로그아웃 페이지는 세션 체크를 하지 않음
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String currentUri = request.getRequestURI();
        log.debug("Handling postHandle for URI: " + currentUri);
        if (modelAndView != null) {
            log.debug("ModelAndView is not null, adding currentUri");
            modelAndView.addObject("currentUri", currentUri);
        } else {
            log.debug("ModelAndView is null");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        if (ex != null) {
            log.error("Request processing error", ex);
        }
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}