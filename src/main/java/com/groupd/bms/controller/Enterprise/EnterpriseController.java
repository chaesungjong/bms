package com.groupd.bms.controller.enterprise;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * EnterpriseController
 * 업체 관리 페이지를 보여주기 위한 컨트롤러
 */
@Controller
@RequestMapping("ent")
public class EnterpriseController {

    @GetMapping("/registration")
    public String Registration(Model model) {
        return "enterprise/registration";
    }
}
