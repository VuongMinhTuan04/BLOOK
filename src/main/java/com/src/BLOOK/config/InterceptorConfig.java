package com.src.BLOOK.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.src.BLOOK.implement.AuthInterceptorImplements;
import com.src.BLOOK.implement.GlobalInterceptorImplements;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	@Autowired
	GlobalInterceptorImplements globalInterceptorImplements;
	
    @Autowired
    AuthInterceptorImplements authInterceptorImplements;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalInterceptorImplements)
                .addPathPatterns("/**")
                .excludePathPatterns("/assets/**", "/BLOOK/sign-in", "/BLOOK/sign-out");

        registry.addInterceptor(authInterceptorImplements)
                .addPathPatterns("/BLOOK/manager/**") // Bảo vệ tất cả các đường dẫn quản lý
                .excludePathPatterns("/assets/**", "/BLOOK/sign-in", "/BLOOK/sign-out");
    }
}
