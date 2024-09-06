package com.src.BLOOK.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Invoice_Statistics_Controller {
	@GetMapping("/BLOOK/manager/invoice_statistics")
	public String doGet() {
		return "manager/invoice_statistics";
	}
	
}
