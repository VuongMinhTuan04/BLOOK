package com.src.BLOOK.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.src.BLOOK.models.Account;
import com.src.BLOOK.models.Cart;
import com.src.BLOOK.repository.CartRepository;

@Service
public class CartService {
	@Autowired
	private CartRepository cartRepository;
	
	public Double getTotalAmount(Account account) {
		List<Cart> carts = cartRepository.findByAccount(account);
		return carts.stream().mapToDouble(cart -> cart.getPrice() * cart.getQuantity()).sum();
	}
	
	public Integer getTotalQuantity(Account account) {
		List<Cart> carts = cartRepository.findByAccount(account);
        return carts.stream().mapToInt(Cart::getQuantity).sum();
	}
}
