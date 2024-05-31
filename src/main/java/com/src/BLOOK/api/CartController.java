package com.src.BLOOK.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.src.BLOOK.models.Cart;
import com.src.BLOOK.models.Product;
import com.src.BLOOK.repository.CartRepository;
import com.src.BLOOK.repository.ProductRepository;

@Controller
public class CartController {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@GetMapping("/BLOOK/cart")
	public String doGet(Model model) {
		List<Cart> cart = cartRepository.findAll();
		model.addAttribute("carts", cart);
		return "product/cart";
	}
	
	@PostMapping("/BLOOK/product/add/{id}")
	public String add(@PathVariable("id") Long id) {
		Optional<Product> optional = productRepository.findById(id);
		
		if(optional.isPresent()) {
			Product product = optional.get();
			Cart cart = cartRepository.findByName(product.getName()).orElse(new Cart());
			
			if(cart.getId_cart() == null) {
				cart.setId_cart(product.getId_product().intValue());
				cart.setImage(product.getImage());
				cart.setName(product.getName());
				cart.setPrice(product.getPrice());
				cart.setQuantity(1);
				System.out.println(cart);
			}
			else {
				cart.setQuantity(product.getQuantity() + 1);
			}
			
			productRepository.save(product);
			
			return "redirect:/BLOOK/cart";
		}
		
		return "redirect:/BLOOK/product";
	}
	
	@GetMapping("/BLOOK/product/remove/{id}")
	public String remove(@PathVariable("id") Integer id) {
		Optional<Cart> optional = cartRepository.findById(id);
		
		if(optional.isPresent()) {
//			Cart cart = optional.get();
			cartRepository.deleteById(id);
		}

		return "redirect:/BLOOK/cart";
	}
	
	@PostMapping("/BLOOK/product/clear")
	public String clear() {
		productRepository.deleteAll();
		return "redirect:/BLOOK/cart";
	}
}