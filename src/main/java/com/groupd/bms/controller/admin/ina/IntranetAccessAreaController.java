package com.groupd.bms.controller.admin.ina;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.groupd.bms.controller.BaseController;

/**
 * IntranetAccessAreaController
 * 인트라넷 컨트롤러
 */
@Controller
@RequestMapping("admin/ina")
public class IntranetAccessAreaController extends BaseController{

    /*
     * 프로젝트 정보 화면 페이지 
     */
    @GetMapping("/{path}")
    public String handleSinglePath(@PathVariable("path") String path) {
        return "admin/ina/" + path;
    }

}
