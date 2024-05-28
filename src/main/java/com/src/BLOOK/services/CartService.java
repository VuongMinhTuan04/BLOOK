package com.src.BLOOK.services;

import java.util.Collection;

import com.src.BLOOK.models.Product;

public interface CartService {
	public Product add(Long id);
	public Product update(Long id, Integer quantity);
	public void remove(Long id);
	public void clear();
	public Collection<Product> getItems();
	public int getCount();
	public double getAmount();
}
