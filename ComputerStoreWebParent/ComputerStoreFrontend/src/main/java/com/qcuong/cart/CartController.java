package com.qcuong.cart;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.qcuong.common.entity.CartItem;
import com.qcuong.common.entity.Customer;
import com.qcuong.customer.CustomerService;

@Controller
public class CartController {

	@Autowired
	private CartService service;
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/cart")
	public String viewCart(Model model, HttpServletRequest request) {
		Customer customer = getCurrentCustomer(request);
		List<CartItem> items = service.listCartItems(customer);
		
		float cartTotalf = 0;
		
		for(CartItem item : items) {
			cartTotalf += item.getProductTotal();
		}
		
		String cartTotalstr = String.format("%.02f", cartTotalf);
		float cartTotal = Float.parseFloat(cartTotalstr);
		
		
		model.addAttribute("items", items);
		model.addAttribute("cartTotal", cartTotal);
		
		
		
		return "cart";
	}
	
	private Customer getCurrentCustomer(HttpServletRequest request) {
		String currentEmail = null;
		currentEmail = request.getUserPrincipal().getName();
		
		return customerService.getByEmail(currentEmail);
	}
}
