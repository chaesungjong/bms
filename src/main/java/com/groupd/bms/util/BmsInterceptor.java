package com.groupd.bms.util;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.groupd.bms.model.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Slf4j
public class BmsInterceptor implements HandlerInterceptor {


    private static final String[] LOGIN_CHECK_EXCLUDE_PATHS = {"/acm/login","/","/error","/acm/loginProcess.do" };

    /*
     * preHandle은 컨트롤러가 실행되기 전에 실행되는 메소드
     * 로그인이 되어있는지 확인하고, 로그인이 되어있지 않다면 로그인 페이지로 리다이렉트
     * 로그인 페이지, 회원가입 페이지, 로그아웃 페이지는 세션 체크를 하지 않음
     * @param request
     * @param response
     * @param handler
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("===============================================");
        log.debug("==================== BEGIN ====================");
        log.debug("Request URI ===> " + request.getRequestURI());

        //디자이너 화면은 예외 처리 
        if(request.getRequestURI().contains("Designer")){
            return true;
        }

        // 세션 체크 로직
        if (!Arrays.asList(LOGIN_CHECK_EXCLUDE_PATHS).contains(request.getRequestURI())) {

             if (request.getSession().getAttribute("member") == null) {
                 log.debug("No session found, redirecting to login page.");
                 response.sendRedirect("/acm/login");
                 return false; // 현재 요청을 중지하고 로그인 페이지로 리다이렉트
             }else{
                Member member = (Member) request.getSession().getAttribute("member");
                log.debug("Session found, member: " + member.toString());
                request.setAttribute("member", member);
                
             }

         }

        return true; // 로그인, 등록, 로그아웃 페이지는 세션 체크를 하지 않음
    }


    /*
     * postHandle은 컨트롤러가 실행된 후에 실행되는 메소드
     * 현재 URI를 ModelAndView에 추가
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
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
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex != null) {
            log.error("Request processing error", ex);
        }
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}