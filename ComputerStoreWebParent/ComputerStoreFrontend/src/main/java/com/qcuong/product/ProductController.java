package com.qcuong.product;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.qcuong.category.CategoryService;
import com.qcuong.common.entity.Category;
import com.qcuong.common.entity.Customer;
import com.qcuong.common.entity.Product;
import com.qcuong.common.entity.Review;
import com.qcuong.customer.CustomerService;
import com.qcuong.review.ReviewService;

@Controller
public class ProductController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private CustomerService customerService;
		
	@GetMapping("/category/{category_endURL}")
	public String viewCategoryFirstPage(@PathVariable("category_endURL") String endURL, Model model) {
		return viewCategoryByPage(endURL, model, 1);
	}
	
	@GetMapping("/category/{category_endURL}/page/{pageNum}")
	public String viewCategoryByPage(@PathVariable("category_endURL") String endURL, Model model, 
			@PathVariable("pageNum") int pageNum) {
		
		Category category = categoryService.getCategory(endURL);
		List<Category> listCategories = categoryService.listNoChildCategory();
		List<Category> categoryParents = categoryService.getCatParent(category);
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
		model.addAttribute("categoryParents", categoryParents);
		
		model.addAttribute("PageTitle", category.getName());
		model.addAttribute(endURL, category);
		
		return "product_by_categories";
	}
	
	@GetMapping("/product/{product_endURL}")
	public String viewProductDetail(@PathVariable("product_endURL") String endURL, Model model, HttpServletRequest request) {
		
		
		Product product = productService.getProduct(endURL);
		List<Category> listParents = categoryService.getCatParent(product.getCategory());
		model.addAttribute("categoryParents", listParents);
		
		model.addAttribute("product", product);
		model.addAttribute("PageTitle", product.getName());
		Page<Review> listReviews = reviewService.listRecentReview(product);
		
		
		
		try {
			Customer customer = getCurrentCustomer(request);
			boolean checkCustomerHasReviewed = reviewService.checkCustomerHasReviewed(customer, product.getId());
			
			if(checkCustomerHasReviewed) {
				model.addAttribute("customerReviewed", checkCustomerHasReviewed);
			} else {
				boolean checkCustomerCanReview = reviewService.checkCustomerCanReview(customer, product.getId());
				model.addAttribute("customerCanReview", checkCustomerCanReview);
			}
		} catch(Exception e) {
		}
		
		//recommend product
		int rd1 = (int)(Math.random()*5) + 7;
		Product rec1 = productService.get(rd1);
		
		//recommend product
		int rd2 = (int)(Math.random()*5) + 12;
		Product rec2 = productService.get(rd2);
		
		//recommend product
		int rd3 = (int)(Math.random()*4) + 17;
		Product rec3 = productService.get(rd3);
		
		//recommend product
		int rd4 = (int)(Math.random()*3) + 25;
		Product rec4 = productService.get(rd4);
		
		model.addAttribute("rec1", rec1);
		model.addAttribute("rec2", rec2);
		model.addAttribute("rec3", rec3);
		model.addAttribute("rec4", rec4);
		model.addAttribute("listReviews", listReviews);
		
		return "product_detail";
	}
	
	@GetMapping("/search")
	public String searchFirstPage(@Param("keyword") String keyword, @PathVariable("pageNum") int pageNum, Model model) {
		return searchByPage(keyword, 1, model);
	}
	
	@GetMapping("/search/page/{pageNum}")
	public String searchByPage(@Param("keyword") String keyword, @PathVariable(name="pageNum") int pageNum, Model model) {
		Page<Product> pageProducts = productService.search(keyword, pageNum);
		List<Product> listResults = pageProducts.getContent();
		List<Category> listCategories = categoryService.listNoChildCategory();
			
		
		long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
		long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
		if(endCount > pageProducts.getTotalElements()) {
			endCount = pageProducts.getTotalElements();
		}
		
		model.addAttribute("PageTitle", "Search Result");
		model.addAttribute("keyword", keyword);
		model.addAttribute("searchKeyword", keyword);
		model.addAttribute("listResults", listResults);
		model.addAttribute("listCategories", listCategories);
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPage", pageProducts.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", pageProducts.getTotalElements());
		
		return "search_result";
	}
	
	public Customer getCurrentCustomer(HttpServletRequest request) {
		String currentEmail = null;
		currentEmail = request.getUserPrincipal().getName();
		
		return customerService.getByEmail(currentEmail);
	}
	
}
