package com.groupd.bms.controller.enterprise.acm;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.groupd.bms.controller.BaseController;
import com.groupd.bms.model.MemberLogin;
import com.groupd.bms.service.UserService;
import com.groupd.bms.util.SHA256Util;
import com.groupd.bms.util.Util;

import jakarta.servlet.http.HttpServletRequest;

/**
 * AccountEntManageController
 * 업체 계정 관리 페이지를 보여주기 위한 컨트롤러
 */
@Controller
@RequestMapping("enterprise/acm")
public class AccountEntManageController extends BaseController{
    
    @Autowired
    private UserService userService;

    /*
     * 프로젝트 정보 화면 페이지 
     */
    @GetMapping("/{path}")
    public String handleSinglePath(@PathVariable("path") String path, Model model) {
        return "enterprise/acm/" + path;
    }

    /*
     * 로그인을 시도한다.
     * @param username
     * @param password
     * @return ModelAndView
     */
    @RequestMapping(value="/loginProcess.do", method = { RequestMethod.POST, RequestMethod.GET })
    public ResponseEntity<?> login(@RequestParam("userId") String userID, @RequestParam("password") String password, HttpServletRequest request) {

        // 로그인 시도
        HashMap<String, Object> loginResultMap = userService.login(new MemberLogin(userID, SHA256Util.hashWithSHA256(password), Util.getUserIP(request), "DT"));

        // 로그인 성공 시 세션에 회원 정보를 저장
        if (loginResultMap != null){

            if(loginResultMap.get("member") != null)
                request.getSession().setAttribute("member", loginResultMap.get("member"));
            
            // 로그인 결과 반환
            return ResponseEntity.ok(loginResultMap);
        }

        // 로그인 실패 시 500 에러 반환
        else return ResponseEntity.status(500).build();

    }

    /*
     * 로그아웃을 시도한다.
     * @param request
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value="/logout.do", method = { RequestMethod.POST, RequestMethod.GET })
    public ResponseEntity<?> logout(HttpServletRequest request) throws Exception {
        // 세션에서 회원 정보 삭제
        request.getSession().removeAttribute("member");
        HashMap<String, Object> loginoutMap = new HashMap<String, Object>();
        loginoutMap.put("result", "success");
        return ResponseEntity.ok(loginoutMap);
    }
        
}