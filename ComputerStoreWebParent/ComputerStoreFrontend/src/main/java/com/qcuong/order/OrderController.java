package com.qcuong.order;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.qcuong.common.entity.Customer;
import com.qcuong.common.entity.Order;
import com.qcuong.common.entity.OrderDetail;
import com.qcuong.common.entity.Product;
import com.qcuong.customer.CustomerService;
import com.qcuong.review.ReviewService;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ReviewService reviewService;
	
	@GetMapping("/cusOrder") 
	public String listFirstPage(Model model, HttpServletRequest request) {
		return listOrderByPage(model, request, 1);
	}
	
	@GetMapping("/cusOrder/page/{pageNum}")
	public String listOrderByPage(Model model, HttpServletRequest request, @PathVariable(name="pageNum") int pageNum) {
		Customer customer = getCurrentCustomer(request);
		
		Page<Order> page = orderService.listOrderForCustomer(customer, pageNum);
		List<Order> orders = page.getContent();
		
		long startCount = (pageNum - 1) + OrderService.ORDERS_PER_PAGE + 1;
		long endCount = startCount + OrderService.ORDERS_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
				
		model.addAttribute("orders", orders);
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		
		
		return "cusOrder";
	}
	
	@GetMapping("/orders/detail/{id}")
	public String orderDetail(Model model,  @PathVariable(name="id") Integer id, HttpServletRequest request) {
		Customer customer = getCurrentCustomer(request);
		Order order = orderService.getOrder(id, customer);		
		
		setProductReviewableStatus(customer, order);
		
		model.addAttribute("order", order);
		
		return "order_detail";
	}
	
	private void setProductReviewableStatus(Customer customer, Order order) {
		Iterator<OrderDetail> iterator = order.getOrderDetails().iterator();
		
		while(iterator.hasNext()) {
			OrderDetail detail = iterator.next();
			Product product = detail.getProduct();
			Integer productId = product.getId();
			
			boolean checkReviewed = reviewService.checkCustomerHasReviewed(customer, productId);
			product.setReviewedByCustomer(checkReviewed);
			
			if(!checkReviewed) {
				boolean checkReviewAvailable = reviewService.checkCustomerCanReview(customer, productId);
				product.setCustomerCanReview(checkReviewAvailable);
			}
		}
	}

	public Customer getCurrentCustomer(HttpServletRequest request) {
		String currentEmail = null;
		currentEmail = request.getUserPrincipal().getName();
		
		return customerService.getByEmail(currentEmail);
	}
}
