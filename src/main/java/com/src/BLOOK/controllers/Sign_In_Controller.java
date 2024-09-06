package com.src.BLOOK.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.src.BLOOK.models.Account;
import com.src.BLOOK.repository.AccountRepository;
import com.src.BLOOK.services.CookieService;
import com.src.BLOOK.services.SessionService;

import jakarta.servlet.http.HttpSession;

@Controller
public class Sign_In_Controller {
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CookieService cookieService;
	
	@Autowired
	SessionService sessionService;
	
	@GetMapping("/BLOOK/sign-in")
	public String doGet(Model model) {
		
		String username = cookieService.getValue("username");
		String password = cookieService.getValue("password");
		
		if(username != null && password != null) {
			Boolean remember = cookieService.get("remember") != null && cookieService.get("remember").getValue().equals("true");

		    if (remember) {
		        model.addAttribute("username", username);
		        model.addAttribute("password", password);
		    } else {
		        model.addAttribute("username", "");
		        model.addAttribute("password", "");
		    }
			
			model.addAttribute("username", remember ? username : "");
			model.addAttribute("password", remember ? password : "");
			System.out.println(username);
			System.out.println(password);
		}else {
			model.addAttribute("username", "");
			model.addAttribute("password", "");
			System.out.println("");
			System.out.println("");
		}
		
		return "account/sign-in";
	}
	
	@PostMapping("/BLOOK/homepage")
	public String signIn(
			@RequestParam("username") String username, 
			@RequestParam("password") String password,
			RedirectAttributes redirectAttributes, 
			HttpSession session,
			@ModelAttribute("account") Account accountAttribute,
			BindingResult result,
			Model model
	) {
		
		Optional<Account> optional = accountRepository.findById(username);
		if(username.isEmpty() || password.isEmpty()) {
			redirectAttributes.addFlashAttribute("error", "Không được bỏ trống tài khoản hoặc mật khẩu!");
			return "redirect:/BLOOK/sign-in?error=blankAccount";
		}
		
		if(optional.isPresent()) {
			Account account = optional.get();
			
			if(account != null && account.getPassword().equalsIgnoreCase(password)) {
				session.setAttribute("username", account);
				session.setAttribute("role", account.getRole().getRole());
				
				if (!result.hasErrors()) {
	                Account acc = accountRepository.findById(account.getUsername()).orElse(null);
	                if (acc != null) {
	                    sessionService.set("username", acc);
	                    String uri = (String) sessionService.get("security-uri");

	                    if (uri != null) {
	                        return "redirect:/" + uri;
	                    } else {
	                        if (acc.getRole().getRole()) {
	                            return "redirect:/BLOOK/homepage";
	                        } else {
	                            return "redirect:/BLOOK/homepage";
	                        }
	                    }
	                }
	            }
				
				return "home-page/homepage";
			}
			else {
				redirectAttributes.addFlashAttribute("error", "Sai tài khoản hoặc mật khẩu!");
				return "redirect:/BLOOK/sign-in?error=incorrectPassword";
			}
		}
		else {
			redirectAttributes.addFlashAttribute("error", "Tài khoản không tồn tại!");
	        return "redirect:/BLOOK/sign-in?error=notfoundAccount";
	    }
	}	
	
	@GetMapping("/BLOOK/sign-out")
	public String signOut(HttpSession session) {
	    session.invalidate();
	    cookieService.remove("username");
        cookieService.remove("password");
        
        sessionService.remove("username");
		sessionService.remove("security-uri");
		
	    return "redirect:/BLOOK/homepage";
	}
}
