package com.groupd.bms.util;

import java.io.IOException;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * ForbiddenWordFilter
 * 사용자의 입력값에 대해 금지어를 필터링하는 필터
 * @version 1.0
 * @since 2024.04.26
 * @see com.groupd.bms.util.ForbiddenWordFilter
 */
@Component
public class ForbiddenWordFilter implements Filter  {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        CustomHttpServletRequestWrapper wrappedRequest = new CustomHttpServletRequestWrapper((HttpServletRequest) request);
        chain.doFilter(wrappedRequest, response);

    }

    @Override
    public void destroy() {}
    
}