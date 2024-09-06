//package com.src.BLOOK.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//	    http
//	        .securityMatcher("/BLOOK/**", "/BLOOK/**")
//	        .authorizeHttpRequests((authz) -> authz
//	            .requestMatchers("/BLOOK/manager/**").hasRole("MANAGER")
//	            .requestMatchers("/BLOOK/user/**").hasRole("USER")
//	            .anyRequest().authenticated()
//	        );
//	    
//	    return http.build();
//	}
//}
