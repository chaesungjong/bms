package com.groupd.bms.controller.login;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.groupd.bms.model.MemberLogin;
import com.groupd.bms.service.UserService;
import com.groupd.bms.util.SHA256Util;
import com.groupd.bms.util.Util;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import org.springframework.ui.Model;

/**
 * LoginController
 * 로그인 페이지를 보여주기 위한 컨트롤러
 */
@Controller
@RequestMapping("mmb")
public class LoginController {

    @Autowired
    private UserService userService;

    /*
     * 로그인 페이지를 보여준다.
     * @param model
     * @return login.html
     */
    @GetMapping("/login")
    public String index(Model model) {
        return "mmb/login"; 
    }
    
    /*
     * 회원가입 페이지를 보여준다.
     * @return register.html
     */
    @GetMapping("/register")
    public String register() {
        return "mmb/register"; 
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
        HashMap<String, Object> loginResultMap = userService.login(new MemberLogin(userID, SHA256Util.hashWithSHA256(password), Util.getUserIP(request), "BMS"));

        if (loginResultMap != null) return ResponseEntity.ok(loginResultMap);
        else return ResponseEntity.status(500).build();

    }

    /*
     * 회원가입을 시도한다.
     * @param userId
     * @param password
     * @param username
     * @param firstName
     * @param lastName
     * @param email
     * @return ModelAndView
     */
    @RequestMapping(value="/registerProcess.do", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView register( @RequestParam("userId") String userID,  @RequestParam("password") String password, @RequestParam("username") String username, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,@RequestParam("email") String email ) {
       
        HashMap<String, Object> user = userService.register(userID, SHA256Util.hashWithSHA256(password), username, firstName, lastName, email);
        ModelAndView modelAndView = new ModelAndView();
        
        modelAndView.setViewName("mmb/login");
        if (user != null)  modelAndView.addObject("user", user);
        else modelAndView.addObject("error", "Invalid username or password");
        
        return modelAndView; // ModelAndView 객체를 반환
    }
    
}
