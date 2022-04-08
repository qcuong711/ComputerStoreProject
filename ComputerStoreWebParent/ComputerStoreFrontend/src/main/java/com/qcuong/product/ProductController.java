package com.qcuong.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.qcuong.category.CategoryService;
import com.qcuong.common.entity.Category;
import com.qcuong.common.entity.Product;

@Controller
public class ProductController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/category/{category_endURL}")
	public String viewCategoryFirstPage(@PathVariable("category_endURL") String endURL, Model model) {
		return viewCategoryByPage(endURL, model, 1);
	}
	
	@GetMapping("/category/{category_endURL}/page/{pageNum}")
	public String viewCategoryByPage(@PathVariable("category_endURL") String endURL, Model model, 
			@PathVariable("pageNum") int pageNum) {
		
		Category category = categoryService.getCategory(endURL);
		List<Category> listCategories = categoryService.listNoChildCategory();
		Page<Product> pageProducts = productService.listByCategory(pageNum, category.getId());
		List<Product> listProducts = pageProducts.getContent();
		
		long startCount = (pageNum - 1) + ProductService.PRODUCTS_PER_PAGE + 1;
		long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
		if(endCount > pageProducts.getTotalElements()) {
			endCount = pageProducts.getTotalElements();
		}
		
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPage", pageProducts.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", pageProducts.getTotalElements());
		model.addAttribute("listCategories", listCategories);
		
		model.addAttribute("PageTitle", category.getName());
		model.addAttribute(endURL, category);
		
		return "product_by_categories";
	}
	
	@GetMapping("/product/{product_endURL}")
	public String viewProductDetail(@PathVariable("product_endURL") String endURL, Model model) {
		Product product = productService.getProduct(endURL);
		
		return "product_detail";
	}
}