package com.groupd.bms.controller;

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
        model.addAttribute("title", "테스트"); // `index.html`에서 사용될 타이틀
        return "index"; // `index.html` 템플릿 반환
    }
    
}