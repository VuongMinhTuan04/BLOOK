package com.src.BLOOK.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.src.BLOOK.models.Product;
import com.src.BLOOK.repository.ProductRepository;

@Controller
public class ProductController {
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping("/BLOOK/product")
	public String doGet(Model model, @RequestParam(name = "pageNO", defaultValue = "1") Integer pageNO) {
		Pageable pageable = PageRequest.of(pageNO - 1, 9);
		Page<Product> list = this.productRepository.findByStatusTrue(pageable);
		
		model.addAttribute("products", list);
		model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNO);
        
		return "product/items";
	}
}
