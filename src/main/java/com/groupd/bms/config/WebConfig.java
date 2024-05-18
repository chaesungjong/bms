package com.groupd.bms.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.groupd.bms.util.ForbiddenWordFilter;
import com.groupd.bms.util.BmsInterceptor;

/**
 * WebConfig
 * 웹 설정을 위한 클래스
 * @version 1.0
 * @since 2024.04.26
 * @see com.groupd.bms.util.ForbiddenWordFilter
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	/**
	 * 사용자 정의 필터(금지된 단어 필터) 등록
	 * 
	 * @return FilterRegistrationBean<ForbiddenWordFilter>
	 */
	@Bean
	public FilterRegistrationBean<ForbiddenWordFilter> loggingFilter() {
		FilterRegistrationBean<ForbiddenWordFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new ForbiddenWordFilter());
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}

	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BmsInterceptor())
                .excludePathPatterns("/css/**", "/images/**", "/js/**");
    }
}