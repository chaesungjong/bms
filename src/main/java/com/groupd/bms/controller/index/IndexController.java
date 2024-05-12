package com.groupd.bms.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

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

    /**
	 * 사용자 IP 주소 반환
	 * @param request HttpServletRequest
	 * @return IP 주소 문자열
	 */
	protected String getUserIP(HttpServletRequest request) {
		String clientIP = request.getHeader("X-Forwarded-For");
		if (clientIP == null || clientIP.length() == 0 || "unknown".equalsIgnoreCase(clientIP)) {
			clientIP = request.getRemoteAddr();
		}
		return clientIP;
	}
    
}