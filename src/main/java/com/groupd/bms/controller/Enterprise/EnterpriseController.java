package com.groupd.bms.controller.enterprise;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.groupd.bms.controller.BaseController;

/*
 * EnterpriseController
 * 업체 관리 페이지를 보여주기 위한 컨트롤러
 */
@Controller
@RequestMapping("enterprise")
public class EnterpriseController extends BaseController{

    /*
     * 프로젝트 정보 화면 페이지 
     */
    @GetMapping("/{path}")
    public String handleSinglePath(@PathVariable("path") String path, Model model) {
        return "enterprise/" + path;
    }
}
