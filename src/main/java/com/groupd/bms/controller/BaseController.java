package com.groupd.bms.controller;

import org.springframework.stereotype.Controller;

import com.groupd.bms.model.Member;

import java.util.HashMap;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BaseController {

    /**
     * setRequest 요청 받은 파라미터를 HashMap으로 변환
     * @param request
     * @return
     */
    public HashMap<String, Object> setRequest(HttpServletRequest request) {
        HashMap<String, Object> requestMap = new HashMap<>();

        request.getParameterMap().forEach((key, value) -> {
            requestMap.put(key, value[0]);
        });

        Member member = (Member) request.getSession().getAttribute("member");

        if(member != null){
            requestMap.put("member", member);
        }

        return requestMap;
    }

}