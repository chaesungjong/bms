package com.groupd.bms.util;

import jakarta.servlet.http.HttpServletRequest;

public class Util {
    

    /**
	 * 사용자 IP 주소 반환
	 * @param request HttpServletRequest
	 * @return IP 주소 문자열
	 */
	public static String getUserIP(HttpServletRequest request) {
		String clientIP = request.getHeader("X-Forwarded-For");
		if (clientIP == null || clientIP.length() == 0 || "unknown".equalsIgnoreCase(clientIP)) {
			clientIP = request.getRemoteAddr();
		}
		return clientIP;
	}
}
