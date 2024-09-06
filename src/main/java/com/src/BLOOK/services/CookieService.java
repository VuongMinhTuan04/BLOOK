package com.src.BLOOK.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CookieService {
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpServletResponse response;
	
	public Cookie get(String name) {
		Cookie[] cookies = request.getCookies(); //Request để lấy danh sách trong client
		if(cookies != null) {
			for(Cookie cookie : cookies) { //1 Cookie được lấy ra và lưu vào biến cookie sau mỗi vòng lặp
				if(cookie.getName().equals(name)) { //Kiểm tra cookie có trùng khớp với name được chỉ định hay không
					return cookie;
				}
			}
		}
		return null;
	}
	
	public Cookie add(String name, String value, int hours) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(hours * 60 * 60); //Set thời gian
		cookie.setPath("/"); //Đặt Path của Cookie để có hiệu lực
		response.addCookie(cookie);
		
		return cookie;
	}
	
	public void remove(String name) {
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);
		cookie.setPath("/"); //Đặt Path của Cookie để có hiệu lực
		response.addCookie(cookie);
	}
	
	public String getValue(String name) {
		Cookie cookie = get(name); //Cookie đọc name
		return (cookie != null) ? cookie.getValue() : ""; //Nếu rỗng thì "" còn có giá trị thì Cookie sẽ lấy Value
	}
}
