package com.groupd.bms.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.groupd.bms.model.User;
import com.groupd.bms.service.UserService;
import com.groupd.bms.util.SHA256Util;

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
    public ModelAndView login(@RequestParam("userId") String userID, @RequestParam("password") String password) {
        HashMap<String, Object> user = userService.login(userID, password);
        ModelAndView modelAndView = new ModelAndView();
        
        if (user != null) {
            modelAndView.setViewName("dashboard");
            modelAndView.addObject("user", user);
        } else {
            modelAndView.setViewName("login");
            modelAndView.addObject("error", "Invalid username or password");
        }
        return modelAndView; 
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
        
        if (user != null) {
            modelAndView.setViewName("dashboard");
            modelAndView.addObject("user", user);
        } else {
            modelAndView.setViewName("login");
            modelAndView.addObject("error", "Invalid username or password");
        }
        return modelAndView; // ModelAndView 객체를 반환
    }
    
}
