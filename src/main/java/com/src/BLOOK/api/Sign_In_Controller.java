package com.src.BLOOK.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.src.BLOOK.models.Account;
import com.src.BLOOK.repository.AccountRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class Sign_In_Controller {
	@Autowired
	private AccountRepository accountRepository;
	
	@GetMapping("/BLOOK/sign-in")
	public String doGet() {
		return "account/sign-in";
	}
	
	@PostMapping("/BLOOK/homepage")
	public String signIn(
			@RequestParam("username") String username, 
			@RequestParam("password") String password, 
			RedirectAttributes redirectAttributes, 
			HttpSession session) {
		Optional<Account> optional = accountRepository.findById(username);
		
		if(optional.isPresent()) {
			Account account = optional.get();
			if(account != null && account.getPassword().equalsIgnoreCase(password)) {
				session.setAttribute("username", account);
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
	    return "redirect:/BLOOK/homepage";
	}
}
