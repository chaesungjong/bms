package com.groupd.bms.controller.Enterprise.csf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.groupd.bms.controller.BaseController;

/**
 * CustomerBoardController
 * 고객지원 게시판 컨트롤러
 */
@Controller
@RequestMapping("enterprise/csf")
public class CustomerBoardController extends BaseController{

    /*
     * 프로젝트 정보 화면 페이지 
     */
    @GetMapping("/{path}")
    public String handleSinglePath(@PathVariable("path") String path, Model model) {
        return "enterprise/csf/" + path;
    }
}