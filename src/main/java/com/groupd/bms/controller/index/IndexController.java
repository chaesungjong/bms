package com.groupd.bms.controller.index;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.groupd.bms.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;

/**
 * IndexController
 * 시작 페이지를 보여주기 위한 컨트롤러
 */
@Controller
public class IndexController extends BaseController {

    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/dsb/main"; 
    }

	@GetMapping("/proxy/{fileName}")
    public ResponseEntity<byte[]> proxyFile(@PathVariable("fileName") String fileName) {
		return ResponseEntity.ok().body(null);
		//return ResponseEntity.ok().body(downloadFileFromGCS(fileName));
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