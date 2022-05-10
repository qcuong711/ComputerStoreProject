package com.qcuong.review;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.qcuong.common.entity.Customer;
import com.qcuong.common.entity.Product;
import com.qcuong.common.entity.Review;
import com.qcuong.customer.CustomerService;
import com.qcuong.product.ProductService;

@Controller
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/reviews/{endURL}")
	public String listFirstPage(Model model, @PathVariable(name = "endURL") String endURL) {
		return listProductByPage(model, endURL, 1);
	}
	
	@GetMapping("/reviews/{endURL}/page/{pageNum}")
	public String listProductByPage(Model model, @PathVariable(name = "endURL") String endURL, 
			@PathVariable(name = "pageNum") int pageNum) {
		Product product = productService.getProduct(endURL);
		Page<Review> page = reviewService.listReview(product, pageNum);
		List<Review> listReviews = page.getContent();
		
		long startCount = (pageNum - 1) + ReviewService.REVIEWS_PER_PAGE + 1;
		long endCount = startCount + ReviewService.REVIEWS_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
				
		model.addAttribute("product", product);
		model.addAttribute("listReviews", listReviews);
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		
		return "reviews";
	}
	
	@GetMapping("/write_review/product/{productId}")
	public String showViewForm(@PathVariable("productId") Integer productId, Model model, HttpServletRequest request) {
		
		Review review = new Review();
		
		Product product = productService.getProduct(productId);
		
		Customer customer = getCurrentCustomer(request);
		boolean customerReviewed = reviewService.checkCustomerHasReviewed(customer, product.getId());
		
		if(customerReviewed) {
			model.addAttribute("customerReviewed", customerReviewed);
		} else {
			boolean customerCanReview = reviewService.checkCustomerCanReview(customer, product.getId());
			model.addAttribute("customerCanReview", customerCanReview);
		}
		
		model.addAttribute("product", product);
		model.addAttribute("review", review);
		
		return "review_form";
	}
	
	@PostMapping("/post_review")
	public String saveReview(Model model, Review review, Integer productId, HttpServletRequest request) {
		Customer customer = getCurrentCustomer(request);
		Product product = productService.getProduct(productId);
		
		review.setProduct(product);
		review.setCustomer(customer);
		
		Review savedReview = reviewService.save(review);
		model.addAttribute("review", savedReview);
		
		return "review_success";
	}
	
	public Customer getCurrentCustomer(HttpServletRequest request) {
		String currentEmail = null;
		currentEmail = request.getUserPrincipal().getName();
		
		return customerService.getByEmail(currentEmail);
	}
}
