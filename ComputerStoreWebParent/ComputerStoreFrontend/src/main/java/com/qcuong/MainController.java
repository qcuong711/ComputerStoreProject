package com.qcuong;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.qcuong.category.CategoryService;
import com.qcuong.common.entity.Category;
import com.qcuong.common.entity.Product;
import com.qcuong.product.ProductService;

@Controller
public class MainController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("")
	public String viewHomePage(Model model) {
		List<Category> listCategories = categoryService.listNoChildCategory();
		
		//top Seller 1
		int id1 = 12;
		Product topSeller1 = productService.get(id1);
		
		//top Seller 2
		int id2 = 15;
		Product topSeller2 = productService.get(id2);
				
		//top Seller 3
		int id3 = 19;
		Product topSeller3 = productService.get(id3);
		
		//recc 1	
		int id4 = 18;
		Product recProduct1 = productService.get(id4);
		
		//recc 2
		int id5 = 25;
		Product recProduct2 = productService.get(id5);
		
		//recc 3
		int id6 = 9;
		Product recProduct3 = productService.get(id6);
				
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("topSeller1", topSeller1);
		model.addAttribute("topSeller2", topSeller2);
		model.addAttribute("topSeller3", topSeller3);
		model.addAttribute("recProduct1", recProduct1);
		model.addAttribute("recProduct2", recProduct2);
		model.addAttribute("recProduct3", recProduct3);
		
		return "index";
	}
	
	@GetMapping("/login")
	public String viewLoginPage() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || auth instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		
		return "redirect:/";
	}
}
