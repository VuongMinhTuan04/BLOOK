//package com.src.BLOOK.implement;
//
//import java.util.Collection;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import org.springframework.stereotype.Service;
//
//import com.src.BLOOK.models.Data;
//import com.src.BLOOK.models.Product;
//import com.src.BLOOK.services.CartService;
//
//@Service
//public class CartImplement implements CartService{
//	public Map<Long, Product> map = new LinkedHashMap<>();
//
//	@Override
//	public Product add(Long id) {
//		Product product = map.get(id);
//		if(product == null) {
//			product = Data.map.get(id);
//			product.setQuantity(1);
//			map.put(id, product);
//			System.out.println(id);
//		}
//		else {
//			product.setQuantity(product.getQuantity() + 1);
//		}
//		
//		return product;
//	}
//
//	@Override
//	public Product update(Long id, Integer quantity) {
//		Product product = Data.map.get(id);
//		if (product != null) {
//			
//	        product.setQuantity(quantity);
//	        System.out.println(product + " " + quantity);
//	    }
//		return product;
//	}
//
//	@Override
//	public void remove(Long id) {
//		if(map.containsKey(id)) {
//			map.remove(id);
//		}
//	}
//
//	@Override
//	public void clear() {
//		map.clear();
//	}
//
//	@Override
//	public Collection<Product> getItems() {
//		return map.values();
//	}
//
//	@Override
//	public int getCount() {
//		return map.values().stream().mapToInt(item -> item.getQuantity()).sum();
//	}
//
//	@Override
//	public double getAmount() {
//		return map.values().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
//	}
//
//}
