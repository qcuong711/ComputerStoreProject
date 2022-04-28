package com.qcuong.checkout;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcuong.common.entity.CartItem;
import com.qcuong.common.entity.Customer;
import com.qcuong.customer.CustomerService;

@Service
public class CheckoutService {
	
	@Autowired
	private CustomerService customerService;
	
	public CheckoutInfo prepareCheckout(List<CartItem> cartItems, HttpServletRequest request) {
		
		CheckoutInfo info = new CheckoutInfo();

		float productTotal = calculateProductTotal(cartItems);
		float shippingCost = calculateShippingCost(request); 
		float paymentTotal = productTotal + shippingCost;
		String deliverDays = calculateDeliverDays(request);
		
		info.setProductTotal(productTotal);
		info.setShippingCost(shippingCost);
		info.setPaymentTotal(paymentTotal);
		info.setDeliverDays(deliverDays);
		
		return info;
	}
	
	
	
	public float calculateProductTotal(List<CartItem> cartItems) {	
		float total = 0.0f;
		
		for (CartItem item : cartItems) {
			total += item.getProductTotal();
		}
		
		return total;
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
	
	public String calculateDeliverDays(HttpServletRequest request) {
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
