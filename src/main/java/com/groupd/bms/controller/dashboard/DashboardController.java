package com.groupd.bms.controller.dashboard;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * DashboardController
 * 대시보드 페이지를 보여주기 위한 컨트롤러
 */
@Controller
@RequestMapping("dashboard")
public class DashboardController {
    
    /*
     * 대시보드 페이지를 보여준다.
     */
    @GetMapping("/main")
    public String main(Model model) {
        return "redirect:/setting/board?etcParam=NAV_PL"; 
    }

    /*
     * 고객지원 게시판을 만든다.
     */
    @GetMapping("/customerSupport")
    public String customerSupport(Model model) {
        return "/dashboard/customerSupport"; 
    }

    /*
     * 출퇴근 등록 페이지를 보여준다.
     */
    @GetMapping("/commute")
    public String commute(Model model) {
        return "/dashboard/commute"; 
    }
}
