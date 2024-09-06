package com.src.BLOOK.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.src.BLOOK.models.Account;
import com.src.BLOOK.services.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthInterceptorImplements implements HandlerInterceptor{
	@Autowired 
	SessionService session;
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        Account account = (Account) session.get("username"); // Lấy từ session

        String error = "";
        if (account == null) {
            error = "Please Login!";
        } else if (!account.getRole().getRole() && uri.startsWith("/BLOOK/manager/")) {
            error = "Access Denied!";
        }

        if (!error.isEmpty()) {
            session.set("security-uri", uri);
            response.sendRedirect("/BLOOK/sign-in?error=" + error);
            return false;
        }

        return true;
    }	
}
