package com.src.BLOOK.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
	@GetMapping("/BLOOK/homepage")
	public String doGet() {
		return "home-page/homepage";
	}
}
