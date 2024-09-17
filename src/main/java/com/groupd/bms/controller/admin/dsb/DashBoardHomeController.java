package com.groupd.bms.controller.admin.dsb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.groupd.bms.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

/**
 * SettingController
 * 그룹디 관리자 페이지의 대시보드 홈 컨트롤러
 */
@Controller
@RequestMapping("admin/dsb")
public class DashBoardHomeController extends BaseController {

    /**
     * 대시 보드 홈 메인 페이지
     */
    @GetMapping("/main")
    public String main(HttpServletRequest request, Model model) {
        return "admin/dsb/main";
    }

}
