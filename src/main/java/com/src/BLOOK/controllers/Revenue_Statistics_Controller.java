package com.src.BLOOK.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Revenue_Statistics_Controller {
	@GetMapping("/BLOOK/manager/revenue_statistics")
	public String doGet() {
		return "manager/revenue_statistics";
	}
}
