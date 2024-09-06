package com.src.BLOOK.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.src.BLOOK.models.Account;
import com.src.BLOOK.models.Cart;
import com.src.BLOOK.models.Product;
import com.src.BLOOK.repository.CartRepository;
import com.src.BLOOK.repository.ProductRepository;
import com.src.BLOOK.services.CartService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired 
	private CartService cartService;
	
	@GetMapping("/BLOOK/cart")
	public String doGet(Model model, HttpSession session) {
		Account account = (Account) session.getAttribute("username");
		
		if(account != null) {
			List<Cart> carts = cartRepository.findByAccount(account);
			model.addAttribute("carts", carts);
			
			Double totalAmount = cartService.getTotalAmount(account);
			Integer totalQuantity = cartService.getTotalQuantity(account);
			
			model.addAttribute("sum", totalAmount);
			model.addAttribute("count", totalQuantity);
		}
		else {
			model.addAttribute("sum", 0);
			model.addAttribute("count", 0);
		}
		
		model.addAttribute("sum");
		model.addAttribute("count");
		
		return "product/cart";
	}
	
	@PostMapping("/BLOOK/product/add/{id}")
	public String add(@PathVariable("id") Long id, HttpSession session) {
	    Optional<Product> optionalProduct = productRepository.findById(id);
	    Account account = (Account) session.getAttribute("username");

	    if (optionalProduct.isPresent() && account != null) {
	        Product product = optionalProduct.get();

	        // Kiểm tra sản phẩm còn hàng hay không
	        if (product.getQuantity() > 0) {
	            Optional<Cart> optionalCart = cartRepository.findByProductAndAccount(product, account);
	            Cart cart = optionalCart.orElse(new Cart());

	            if (cart.getId_cart() == null) {
	                cart.setProduct(product); // Thiết lập liên kết Product
	                cart.setImage(product.getImage());
	                cart.setName(product.getName());
	                cart.setPrice(product.getPrice());
	                cart.setQuantity(1);
	                cart.setAccount(account);
	                System.out.println(cart);
	            } else {
	                cart.setQuantity(cart.getQuantity() + 1);
	            }

	            // Giảm số lượng sản phẩm trong bảng Product
	            product.setQuantity(product.getQuantity() - 1);

	            // Kiểm tra nếu số lượng sản phẩm sau khi giảm xuống 0
	            if (product.getQuantity() == 0) {
	                product.setStatus(false); // Đánh dấu sản phẩm hết hàng
	            }

	            productRepository.save(product);

	            cartRepository.save(cart);

	            return "redirect:/BLOOK/cart";
	        } else {
	            // Trường hợp sản phẩm đã hết hàng
	            return "redirect:/BLOOK/product";
	        }
	    }

	    return "redirect:/BLOOK/product";
	}
	
	@GetMapping("/BLOOK/product/remove/{id}")
	public String remove(@PathVariable("id") Integer id) {
	    Optional<Cart> optionalCart = cartRepository.findById(id);

	    if (optionalCart.isPresent()) {
	        Cart cart = optionalCart.get();
	        cartRepository.deleteById(id);

	        // Tăng số lượng sản phẩm trong bảng Product
	        Product product = cart.getProduct();
	        product.setQuantity(product.getQuantity() + cart.getQuantity());

	        // Đặt lại trạng thái của sản phẩm dựa trên số lượng mới
	        if (product.getQuantity() > 0) {
	            product.setStatus(true); // Sản phẩm vẫn còn hàng
	        } else {
	            product.setStatus(false); // Sản phẩm đã hết hàng
	        }

	        productRepository.save(product);

	        System.out.println("New product quantity: " + product.getQuantity());
	    } else {
	        System.out.println("No cart item found with id: " + id);
	    }

	    return "redirect:/BLOOK/cart";
	}
	

	@PostMapping("/BLOOK/product/update/{id}")
	public String update(@PathVariable("id") Integer id, HttpServletRequest request, RedirectAttributes redirectAttributes) {
	    String quantityString = "quantity" + id;
	    String quantityRequest = request.getParameter(quantityString);

	    if (quantityRequest != null) {
	        Integer newQuantity = Integer.parseInt(quantityRequest);
	        Optional<Cart> optionalCart = cartRepository.findById(id);

	        if (optionalCart.isPresent()) {
	            Cart cart = optionalCart.get();

	            if (cart != null) {
	                Integer oldQuantity = cart.getQuantity();
	                Integer availableQuantity = cart.getProduct().getQuantity(); // Số lượng sản phẩm có sẵn

	                if (newQuantity > availableQuantity + oldQuantity) {
	                    redirectAttributes.addFlashAttribute("error", "Số lượng sản phẩm vượt quá số lượng có sẵn!");
	                    return "redirect:/BLOOK/cart";
	                }

	                // Điều chỉnh số lượng trong giỏ hàng
	                cart.setQuantity(newQuantity);
	                cartRepository.save(cart);

	                // Điều chỉnh số lượng sản phẩm trong bảng Product
	                Product product = cart.getProduct();
	                Integer updatedProductQuantity = availableQuantity + oldQuantity - newQuantity;

	                // Đảm bảo số lượng không âm và cập nhật trạng thái sản phẩm
	                if (updatedProductQuantity > 0) {
	                    product.setQuantity(updatedProductQuantity);
	                    product.setStatus(true); // Sản phẩm còn hàng
	                } else if (updatedProductQuantity == 0) {
	                    product.setQuantity(0);
	                    product.setStatus(false); // Đánh dấu sản phẩm hết hàng
	                } else {
	                    // Trường hợp số lượng mới âm
	                    product.setQuantity(0);
	                    product.setStatus(false); // Đánh dấu sản phẩm hết hàng
	                }

	                productRepository.save(product);
	            }
	        }
	    }

	    return "redirect:/BLOOK/cart";
	}
	
	@PostMapping("/BLOOK/product/clear")
	public String clear(HttpSession session) {
	    Account account = (Account) session.getAttribute("username");

	    if (account != null) {
	        // Lấy tất cả các giỏ hàng của tài khoản từ cơ sở dữ liệu
	        List<Cart> carts = cartRepository.findByAccount(account);

	        if (!carts.isEmpty()) {
	            // Duyệt qua từng mục trong giỏ hàng và cập nhật lại số lượng sản phẩm và trạng thái
	            for (Cart cart : carts) {
	                Product product = cart.getProduct();
	                product.setQuantity(product.getQuantity() + cart.getQuantity());
	                product.setStatus(true); // Đánh dấu sản phẩm là còn hàng
	            }

	            // Lưu các thay đổi vào cơ sở dữ liệu
	            productRepository.saveAll(carts.stream().map(Cart::getProduct).collect(Collectors.toList()));
	            
	            // Xóa tất cả các mục trong giỏ hàng
	            cartRepository.deleteAll(carts);
	        }
	    }

	    return "redirect:/BLOOK/cart";
	}
}