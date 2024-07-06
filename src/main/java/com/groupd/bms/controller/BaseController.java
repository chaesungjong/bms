package com.groupd.bms.controller;

import org.springframework.stereotype.Controller;
import java.util.HashMap;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BaseController {

    public HashMap<String, Object> setRequest(HttpServletRequest request) {
        HashMap<String, Object> requestMap = new HashMap<>();

        request.getParameterMap().forEach((key, value) -> {
            requestMap.put(key, value[0]);
        });

        return requestMap;
    }

}