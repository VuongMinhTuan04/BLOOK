package com.src.BLOOK.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.src.BLOOK.models.Categories;
import com.src.BLOOK.models.Product;
import com.src.BLOOK.repository.CategoryRepository;
import com.src.BLOOK.repository.ProductRepository;

import jakarta.validation.Valid;

@Controller
public class Products_Management_Controller {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping("/BLOOK/manager/product_management")
	public String doGet(Model model) {
		List<Categories> categories = categoryRepository.findAll();
	    model.addAttribute("categories", categories);
	    model.addAttribute("product", new Product());
	    model.addAttribute("products", productRepository.findAll());
		return "manager/product_management";
	}
	
	@PostMapping("/BLOOK/manager/product_management/add")
	public String add(
			@ModelAttribute("product") @Valid Product product, 
			BindingResult bindingResult, 
			RedirectAttributes redirectAttributes,
			@RequestParam("file") MultipartFile multipartFile
	) {
		if (bindingResult.hasErrors()) {
	        redirectAttributes.addFlashAttribute("fail", "Thêm Thất Bại: Dữ liệu không hợp lệ.");
	        return "redirect:/BLOOK/manager/product_management";
	    }
		
	    try {
	    	List<Product> products = productRepository.findByName(product.getName());
	            
	        if(products != null && !products.isEmpty()) {
	        	redirectAttributes.addFlashAttribute("fail", "Sản Phẩm Đang Tồn Tại");
	        }
	    	else {
	    		if(!multipartFile.isEmpty()) {
		    		String fileName = multipartFile.getOriginalFilename();
		    		String uploadDir = "src/main/resources/static/img-product";
		    		Path path = Paths.get(uploadDir + "/" + fileName);
		    		Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		    		product.setImage(fileName);
		    	}
	    		
	    		if(product.getQuantity() > 0) {
	    			product.setStatus(true);
	    		}else {
	    			product.setStatus(false);
	    		}
		    	
		        productRepository.save(product);
		        redirectAttributes.addFlashAttribute("success", "Thêm Thành Công");
	    	}
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("fail", "Thêm Thất Bại");
	        e.printStackTrace();
	    }
	    return "redirect:/BLOOK/manager/product_management";
	}
	
	@PostMapping("/BLOOK/manager/product_management/remove/{id}")
	public String remove(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		try {
			Optional<Product> optional = productRepository.findById(id);
			
			if(optional.isPresent()) {
				Product product = optional.get();
				productRepository.delete(product);
				redirectAttributes.addFlashAttribute("success", "Xóa thành công");
			}else {
				redirectAttributes.addFlashAttribute("fail", "Xóa thất bại");
			}
			
			return "redirect:/BLOOK/manager/product_management";
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("fail","Sản phẩm đang còn trong giỏ hàng của khách hàng, bạn không được phép xóa!");
		}
		return "redirect:/BLOOK/manager/product_management";
	}
	
	@PostMapping("/BLOOK/manager/product_management/update/{id}")
	public String update(
			@PathVariable("id") Long id, 
            @ModelAttribute("product") @Valid Product product, 
            BindingResult bindingResult, 
            RedirectAttributes redirectAttributes
	) {
		if (bindingResult.hasErrors()) {
	        redirectAttributes.addFlashAttribute("fail", "Cập nhật thất bại: Dữ liệu không hợp lệ.");
	        return "redirect:/BLOOK/manager/product_management";
	    }
		
		try {
	        Optional<Product> optional = productRepository.findById(id);
	        
	        if (optional.isPresent()) {
	            Product products = optional.get();
	            
	            if(product.getQuantity() > 0) {
	    			product.setStatus(true);
	    		}else {
	    			product.setStatus(false);
	    		}

	            // Update product
	            products.setPrice(product.getPrice());
	            products.setQuantity(product.getQuantity());
	            products.setStatus(product.getStatus());

	            productRepository.save(products);
	            redirectAttributes.addFlashAttribute("success", "Cập nhật thành công");
	        } else {
	            redirectAttributes.addFlashAttribute("fail", "Cập nhật thất bại: Không tìm thấy sản phẩm");
	        }
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("fail", "Cập nhật thất bại");
	        e.printStackTrace();
	    }
		
		return "redirect:/BLOOK/manager/product_management";
	}
	
	@GetMapping("/BLOOK/manager/product_management/edit/{id}")
	public String edit(
			@PathVariable("id") Long id, 
			Model model
	) {
		 Optional<Product> optionalProduct = productRepository.findById(id);
		    if (optionalProduct.isPresent()) {
		        Product product = optionalProduct.get();
		        model.addAttribute("product", product);

		        List<Product> products = productRepository.findAll();
		        model.addAttribute("products", products);
		    } else {
		        return "redirect:/BLOOK/manager/product_management";
		    }
		    return "manager/product_management";
	}
	
	@PostMapping("/BLOOK/manager/product_management/clear")
	public String clear() {
		return "redirect:/BLOOK/manager/product_management";
	}
}
