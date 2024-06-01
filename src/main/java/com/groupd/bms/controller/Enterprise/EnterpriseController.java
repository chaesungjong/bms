package com.groupd.bms.controller.Enterprise;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;

/*
 * EnterpriseController
 * 업체 관리 페이지를 보여주기 위한 컨트롤러
 */
@Controller
@RequestMapping("enterprise")
public class EnterpriseController {

    /*
     * 대시보드 페이지를 보여준다.
     */
    @GetMapping("/main")
    public String main(Model model) {
        return "enterprise/main";
    }

    /*
     * 업체 등록 페이지를 보여준다.
     */
    @RequestMapping(value = "/Registration.do", method = { RequestMethod.POST, RequestMethod.GET })
    public ResponseEntity<?> Registration(HttpServletRequest request) {

        // 로그인 시도
        HashMap<String, Object> RegistrationMap = new HashMap<>();

        RegistrationMap.put("retVal", "9999");
        RegistrationMap.put("errMsg", "기능 준비중입니다.");

        if (RegistrationMap != null)
            return ResponseEntity.ok(RegistrationMap);
        else
            return ResponseEntity.status(500).build();

    }

}
