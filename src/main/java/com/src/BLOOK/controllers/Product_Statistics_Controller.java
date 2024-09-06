package com.src.BLOOK.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Product_Statistics_Controller {
	@GetMapping("/BLOOK/manager/product_statistics")
	public String doGet() {
		return "manager/product_statistics";
	}
}
