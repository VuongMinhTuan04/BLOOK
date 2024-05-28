//package com.src.BLOOK.api;
//
//import java.math.RoundingMode;
//import java.text.NumberFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.src.BLOOK.implement.CartImplement;
//import com.src.BLOOK.models.Order;
//import com.src.BLOOK.models.Product;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.Valid;
//
//@Controller
//public class CartController {
////	@Autowired
////	private HttpServletRequest request;
////	
////	@Autowired
////	private CartImplement cartImplement;
//	
//	@GetMapping("/BLOOK/cart")
//	public String doGet(Model model) {
////		List<Product> products = new ArrayList<>(Data.map.values());
////		model.addAttribute("products", cartImplement.getItems());
////		model.addAttribute("count", cartImplement.getCount());
////		model.addAttribute("sum", formatCurrency(cartImplement.getAmount()));
////		model.addAttribute("order", new Order());
////		
////		formatPrice(products);
//		return "product/cart";
//	}
//	
//	
////	@PostMapping("/BLOOK/product/add/{id}")
////	public String add(@PathVariable("id") Long id) {
////		cartImplement.add(id);
////		return "redirect:/BLOOK/cart";
////	}
////	
////	@PostMapping("/BLOOK/product/update/{id}")
////	public String update(@PathVariable("id") Long id) {
////		//Khai báo hàm này để số lượng bắt được ID trong th:name="'quantity-' + ${product.id}"
////	    String quantityParam = "quantity-" + id; 
////	    
////	    //Sử dụng để trích xuất giá trị của quantityParam từ yêu cầu gửi lên server. Nó trả về giá trị của trường input có tên tương ứng.
////	    String quantityRequest = request.getParameter(quantityParam); 
////	    
////	    if (quantityRequest != null) {
////	    	Integer quantity = Integer.parseInt(quantityRequest); //Ép kiểu quantityRequest về dạng Integer như trong Product
////	    	cartImplement.update(id, quantity);
////	    }
////	    
////	    return "redirect:/BLOOK/cart";
////	}
////	
////	@GetMapping("/BLOOK/product/remove/{id}")
////	public String remove(@PathVariable("id") Long id) {
////		cartImplement.remove(id);
////		return "redirect:/BLOOK/cart";
////	}
////	
////	@PostMapping("/BLOOK/product/clear")
////	public String clear() {
////		cartImplement.clear();
////		return "redirect:/BLOOK/cart";
////	}
////	
////	@PostMapping("/BLOOK/product/buy")
////	public String buy(@Valid @ModelAttribute("order") Order order, BindingResult errors, Model model){
////		if(errors.hasErrors()) {
////			model.addAttribute("message", "Vui lòng nhập đầy đủ thông tin!");
////		}else {
////			model.addAttribute("message", "");
////		}
////		
////		return "redirect:/BLOOK/cart";
////	}
//	
////	private void formatPrice(List<Product> products) {
////		Locale locale = new Locale("vi","VN");
////		NumberFormat format = NumberFormat.getCurrencyInstance(locale);
////		format.setRoundingMode(RoundingMode.HALF_UP);
////		
////		for(Product product : products) {
////			Double price = product.getPrice();
////			product.setFormatPrice(format.format(price));
////		}
////	}
//	
//	private String formatCurrency(Double amount) {
//	    Locale locale = new Locale("vi", "VN");
//	    NumberFormat format = NumberFormat.getCurrencyInstance(locale);
//	    format.setRoundingMode(RoundingMode.HALF_UP);
//	    return format.format(amount);
//	}
//}
//
