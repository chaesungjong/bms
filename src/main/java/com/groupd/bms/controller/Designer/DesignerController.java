package com.groupd.bms.controller.Designer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * DesignerController
 * 업체 관리 페이지를 보여주기 위한 컨트롤러
 */
@Controller
@RequestMapping("Designer")
public class DesignerController {

    @GetMapping("/{path1}")
    public String handleSinglePath(@PathVariable String path1) {
        return "/ " + path1;
    }

    @GetMapping("/{path1}/{path2}")
    public String handleTwoPaths(@PathVariable String path1, @PathVariable String path2) {
        return "/" + path1 + "/" + path2;
    }

    @GetMapping("/{path1}/{path2}/{path3}")
    public String handleThreePaths(@PathVariable String path1, @PathVariable String path2, @PathVariable String path3) {
        return "/" + path1 + "/" + path2 + "/" + path3;
    }

    @GetMapping("/{path1}/{path2}/{path3}/{path4}")
    public String handleForPaths(@PathVariable String path1, @PathVariable String path2, @PathVariable String path3, @PathVariable String path4) {
        return "/" + path1 + "/" + path2 + "/" + path3 + "/" + path4;
    }
}
