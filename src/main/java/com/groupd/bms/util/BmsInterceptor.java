package com.groupd.bms.util;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.groupd.bms.model.Member;
import com.groupd.bms.model.MemberLogin;
import com.groupd.bms.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;

@Slf4j
public class BmsInterceptor implements HandlerInterceptor {


    private static final String[] LOGIN_CHECK_EXCLUDE_PATHS = {"/acm/login","/","/error","/acm/loginProcess.do" };

    @Autowired
    private UserService userService;

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

        // 로컬호스트 여부 체크
        String host = request.getServerName();
        request.setAttribute("isLocalhost", "localhost".equals(host));


        //디자이너 화면은 예외 처리 
        if(request.getRequestURI().contains("Designer")){
            return true;
        }

        // 세션 체크 로직
        if (!Arrays.asList(LOGIN_CHECK_EXCLUDE_PATHS).contains(request.getRequestURI())) {
            
            // 로그인이 되어있지 않은 상태
             if (request.getSession().getAttribute("member") == null) {

                 // 쿠키에 아이디 비밀번호가 있으면 로그인 시도
                if(request.getCookies() != null){

                    String autoLogin = "";              // 자동 로그인 여부
                    String userID    = "";              // 아이디
                    String password    = "";            // 비밀번호


                    // 쿠키에서 아이디, 비밀번호, 자동 로그인 여부를 가져옴
                    for (int i = 0; i < request.getCookies().length; i++) {
                        
                        // 자동 로그인 쿠키가 있는지 확인
                        if(request.getCookies()[i].getName().equals("autoLogin")){
                            autoLogin = request.getCookies()[i].getValue();
                        }

                        // 아이디 쿠키가 있는지 확인
                        if(request.getCookies()[i].getName().equals("userId")){
                            userID = request.getCookies()[i].getValue();
                        }

                        // 비밀번호 쿠키가 있는지 확인
                        if(request.getCookies()[i].getName().equals("password")){
                            password = request.getCookies()[i].getValue();
                        }
                        
                    }

                    // 자동 로그인 쿠키가 있으면 로그인 시도
                    if(autoLogin.equals("true") && !userID.equals("") && !password.equals("")){

                        // 로그인 시도
                        HashMap<String, Object> loginResultMap = userService.login(new MemberLogin(userID, SHA256Util.hashWithSHA256(password), Util.getUserIP(request), "BMS"));

                        // 로그인 성공 시 세션에 회원 정보를 저장
                        if (loginResultMap != null && loginResultMap.get("retVal").equals("0") && loginResultMap.get("member") != null){

                            Member member = (Member) loginResultMap.get("member");
                            request.getSession().setAttribute("member", member);
                            response.sendRedirect("/admin/dsb/main");
                            setMember(request);
                            return false; 

                        }
                    }
                }

                 response.sendRedirect("/admin/acm/login");
                 return false; // 현재 요청을 중지하고 로그인 페이지로 리다이렉트
             }

             setMember(request);

        }else if("/error".equals(request.getRequestURI())){
            // 에러 페이지는 세션 체크를 하지 않음
            return true;
        }else{
            // 로그인이 되어있지 않은 상태
            if (request.getSession().getAttribute("member") != null) {
                setMember(request);
                response.sendRedirect("/admin/dsb/main");
                return false; // 현재 요청을 중지하고 메인 페이지로 리다이렉트
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


    /*
     * setMember는 세션에 저장된 회원 정보를 request에 저장
     * @param request
     * @return void
     */
    private void setMember(HttpServletRequest request) {
        Member member = (Member) request.getSession().getAttribute("member");
        request.setAttribute("member", member);
    }
}