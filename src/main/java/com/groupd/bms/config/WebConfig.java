package com.groupd.bms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.groupd.bms.util.ForbiddenWordFilter;
import io.micrometer.common.lang.NonNull;
import com.groupd.bms.util.BmsInterceptor;

import org.springframework.boot.web.servlet.FilterRegistrationBean;

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
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                .excludePathPatterns("/css/**", "/img/**", "/js/**");
    }

    @Bean
    public BmsInterceptor authInterceptor(){
        return new BmsInterceptor();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD");
    }
}
