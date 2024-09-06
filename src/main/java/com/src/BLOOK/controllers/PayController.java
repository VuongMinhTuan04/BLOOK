package com.src.BLOOK.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PayController {
	@GetMapping("/BLOOK/pay")
	public String doGet() {
		return "product/pay";
	}
}
