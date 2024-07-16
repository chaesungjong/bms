package com.groupd.bms.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.groupd.bms.model.MemberLogin;
import com.groupd.bms.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Slf4j
@Component
public class BmsInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    /*
     * preHandle은 컨트롤러가 실행되기 전에 실행되는 메소드
     * 로그인이 되어있는지 확인하고, 로그인이 되어있지 않다면 로그인 페이지로 리다이렉트
     * 로그인 페이지, 회원가입 페이지, 로그아웃 페이지는 세션 체크를 하지 않음
     * @param request  HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @param handler  처리할 핸들러 객체
     * @return boolean 핸들러를 실행할지 여부를 반환
     * @throws Exception 예외가 발생할 수 있음
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("===============================================");
        log.debug("==================== BEGIN ====================");
        log.debug("Request URI ===> " + request.getRequestURI());

        // 디자이너 화면은 예외 처리
        if (request.getRequestURI().contains("Designer")) {
            return true;
        }

        // 세션 체크 로직
        if (request.getSession().getAttribute("member") == null) {
            log.debug("No session found, checking cookies for auto-login.");

            // 자동 로그인 쿠키 체크 로직
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                String userId = null;
                String password = null;
                for (Cookie cookie : cookies) {
                    if ("autoLogin".equals(cookie.getName()) && "true".equals(cookie.getValue())) {
                        for (Cookie c : cookies) {
                            if ("userId".equals(c.getName())) {
                                userId = c.getValue();
                            } else if ("password".equals(c.getName())) {
                                password = c.getValue();
                            }
                        }

                        if (userId != null && password != null) {
                            // 로그인 처리 로직
                            HashMap<String, Object> loginResultMap = userService.login(new MemberLogin(userId, SHA256Util.hashWithSHA256(password), Util.getUserIP(request), "BMS"));

                            if (loginResultMap != null) {
                                if (loginResultMap.get("member") != null) {
                                    request.getSession().setAttribute("member", loginResultMap.get("member"));
                                    response.sendRedirect("/dsb/main");
                                    return false;
                                } else {
                                    response.sendRedirect("/acm/login");
                                    return false; // 현재 요청을 중지하고 로그인 페이지로 리다이렉트
                                }
                            }
                        }
                    }
                }
            }
            
            response.sendRedirect("/acm/login");
            return false; // 현재 요청을 중지하고 로그인 페이지로 리다이렉트
        }

        return true; // 로그인, 등록, 로그아웃 페이지는 세션 체크를 하지 않음
    }

    /*
     * postHandle은 컨트롤러가 실행된 후에 실행되는 메소드
     * 현재 URI를 ModelAndView에 추가
     * @param request  HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @param handler  처리할 핸들러 객체
     * @param modelAndView 컨트롤러가 반환하는 ModelAndView 객체
     * @throws Exception 예외가 발생할 수 있음
     */
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

    /*
     * afterCompletion은 컨트롤러가 실행된 후에 실행되는 메소드
     * 예외가 발생하면 로그에 기록
     * @param request  HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @param handler  처리할 핸들러 객체
     * @param ex       발생한 예외 객체
     * @throws Exception 예외가 발생할 수 있음
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex != null) {
            log.error("Request processing error", ex);
        }
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
