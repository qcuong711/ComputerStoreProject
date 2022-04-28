package com.qcuong.checkout;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.qcuong.cart.CartService;
import com.qcuong.common.entity.CartItem;
import com.qcuong.common.entity.Customer;
import com.qcuong.customer.CustomerService;
import com.qcuong.order.OrderService;

@Controller
public class CheckoutController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired 
	private CartService cartService;
	
	@Autowired 
	private CheckoutService checkoutService;
	
	@Autowired 
	private OrderService orderService;
	
	@GetMapping("/checkout")
	public String showCheckoutPage(Model model, HttpServletRequest request) {
		Customer customer = getCurrentCustomer(request);
		
		List<CartItem> items = cartService.listCartItems(customer);
		
		
		String address = customer.getAddress();
		String country = customer.getCountry();
		String phone = customer.getPhoneNumber();
		String postalCode = customer.getPostalCode();
		float shippingCost = calculateShippingCost(request);
		String deliverDays = deliverDays(request);
		
		float pTotal = 0.0f;
		for (CartItem item : items) {
			pTotal += item.getProductTotal();
		}
		
		String pTotalStr = String.format("%.02f", pTotal);
		float productTotal = Float.parseFloat(pTotalStr);
		
		float aTotal = productTotal + shippingCost;
		String totalStr = String.format("%.02f", aTotal);
		float total = Float.parseFloat(totalStr);
		
		model.addAttribute("address", address);
		model.addAttribute("country", country);
		model.addAttribute("items", items);
		model.addAttribute("phone", phone);
		model.addAttribute("postalCode", postalCode);
		model.addAttribute("shippingCost", shippingCost);
		model.addAttribute("deliverDays", deliverDays);
		model.addAttribute("productTotal", productTotal);
		model.addAttribute("total", total);
		
		return "checkout";
	}
	
	@PostMapping("/place_order")
	public String placeOrder(HttpServletRequest request) {
		Customer customer = getCurrentCustomer(request);
		List<CartItem> items = cartService.listCartItems(customer);
		CheckoutInfo info = checkoutService.prepareCheckout(items, request);
		
		orderService.createOrder(customer, items, info);
		cartService.deleteByCustomer(customer);
		
		return "order_completed";
	}
	
	public Customer getCurrentCustomer(HttpServletRequest request) {
		String currentEmail = null;
		currentEmail = request.getUserPrincipal().getName();
		
		return customerService.getByEmail(currentEmail);
	}
	
	public float calculateShippingCost(HttpServletRequest request) {
		Customer customer = getCurrentCustomer(request);
		if(customer.getCountry().equals("Vietnam")) {
			return 2;
		} else if (customer.getCountry().equals("United States of America")) {
			return 1000;
		} else if (customer.getCountry().equals("China")) {
			return 200;
		} else if (customer.getCountry().equals("Singapore")) {
			return 200;
		} else if (customer.getCountry().equals("Thailand")) {
			return 250;
		} else if (customer.getCountry().equals("Cambodia")) {
			return 210;
		} else if (customer.getCountry().equals("England")) {
			return 750;
		} else if (customer.getCountry().equals("France")) {
			return 650;
		} else if (customer.getCountry().equals("Germany")) {
			return 700;
		} else if (customer.getCountry().equals("Korea South")) {
			return 650;
		} else if (customer.getCountry().equals("Japan")) {
			return 640;
		} else if (customer.getCountry().equals("Russia")) {
			return 720;
		} else if (customer.getCountry().equals("Canada")) {
			return 1010;
		} else if (customer.getCountry().equals("Laos")) {
			return 150;
		}
		
		return 500;
	}
	
	public String deliverDays(HttpServletRequest request) {
		Customer customer = getCurrentCustomer(request);
		
		if(customer.getCountry().equals("Vietnam")) {
			return "2 to 4 days";
		} else if (customer.getCountry().equals("United States of America")) {
			return "8 to 11 days";
		} else if (customer.getCountry().equals("China")) {
			return "7 to 9 days";
		} else if (customer.getCountry().equals("Singapore")) {
			return "6 to 8 days";
		} else if (customer.getCountry().equals("Thailand")) {
			return "5 to 8 days";
		} else if (customer.getCountry().equals("Cambodia")) {
			return "5 to 8 days";
		} else if (customer.getCountry().equals("England")) {
			return "7 to 10 days";
		} else if (customer.getCountry().equals("France")) {
			return "7 to 9 days";
		} else if (customer.getCountry().equals("Germany")) {
			return "7 to 10 days";
		} else if (customer.getCountry().equals("Korea South")) {
			return "8 to 10 days";
		} else if (customer.getCountry().equals("Japan")) {
			return "8 to 10 days";
		} else if (customer.getCountry().equals("Russia")) {
			return "7 to 10 days";
		} else if (customer.getCountry().equals("Canada")) {
			return "8 to 11 days";
		} else if (customer.getCountry().equals("Laos")) {
			return "5 to 8 days";
		}
		
		return "10 to 14 days";
	}
}
