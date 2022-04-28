package com.qcuong.cart;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qcuong.common.entity.Customer;
import com.qcuong.customer.CustomerService;

@RestController
public class CartRestController {

	@Autowired
	private CartService service;
	
	@Autowired 
	private CustomerService customerService;
	
	@PostMapping("/cart/add/{productId}/{quantity}")
	public String addProductToCart(@PathVariable(name = "productId") Integer productId, 
			@PathVariable("quantity") Integer quantity, HttpServletRequest request) {
		
		try {
			Customer customer = getCurrentCustomer(request);
			service.addProduct(productId, quantity, customer);
			
			return " Item has beed added to your cart!";
		} catch (Exception ex) {
			return "You must login to add product to cart!";
		}
	}
	
	private Customer getCurrentCustomer(HttpServletRequest request) {
		String currentEmail = null;
		currentEmail = request.getUserPrincipal().getName();
		
		return customerService.getByEmail(currentEmail);
	}
	
	@PostMapping("/cart/update/{productId}/{quantity}")
	public String updateQuantity(@PathVariable("productId") Integer productId, @PathVariable("quantity") Integer quantity,
			HttpServletRequest request) {
		try {
			Customer customer = getCurrentCustomer(request);
			float productTotal = service.updateQuantity(productId, quantity, customer);
			
			return String.valueOf(productTotal);
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}
	
	@DeleteMapping("cart/remove/{productId}")
	public String removeProduct(@PathVariable("productId") Integer productId, HttpServletRequest request) {
		try {
			Customer customer = getCurrentCustomer(request);
			service.removeProduct(productId, customer);
			
			return "Product removed";
		} catch (Exception ex) {
			return "Error!";
		}
	}
	
}
