package com.groupd.bms.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.groupd.bms.model.User;
import com.groupd.bms.service.UserService;

import org.springframework.ui.Model;
import java.util.HashMap;

/**
 * LoginController
 * 로그인 페이지를 보여주기 위한 컨트롤러
 */
@Controller
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
        return "login"; 
    }

    /*
     * 로그인을 시도한다.
     * @param username
     * @param password
     * @return ModelAndView
     */
    @PostMapping("/login")
    public ModelAndView login(@RequestParam("username") String username, @RequestParam("password") String password) {

        User user = userService.login(username, password);
        
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
    
}