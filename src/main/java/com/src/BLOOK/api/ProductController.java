package com.src.BLOOK.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.src.BLOOK.repository.ProductRepository;

@Controller
public class ProductController {
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping("/BLOOK/product")
	public String doGet(Model model) {
		model.addAttribute("products", productRepository.findAll());	
		return "product/items";
	}
}
