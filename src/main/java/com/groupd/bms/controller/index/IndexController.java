package com.groupd.bms.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * IndexController
 * 시작 페이지를 보여주기 위한 컨트롤러
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/mmb/login"; 
    }
    
}