package com.src.BLOOK.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Sign_Up_Controller {
	@GetMapping("/BLOOK/sign-up")
	public String doGet() {
		return "account/sign-up";
	}
}
