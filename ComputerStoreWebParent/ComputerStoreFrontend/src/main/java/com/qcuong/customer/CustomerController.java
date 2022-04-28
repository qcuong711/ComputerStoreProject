package com.qcuong.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qcuong.common.entity.Customer;
import com.qcuong.security.CustomerUserDetails;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomerService service;

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		
		model.addAttribute("pageTitle", "Customer Registration");
		model.addAttribute("customer", new Customer());
		
		return "customer_register";
	}
	
	@PostMapping("/create_customer")
	public String saveCustomer(Customer customer, RedirectAttributes redirectAttributes) {
		service.save(customer);
		
		return "save_customer_success";
	}
	
	@GetMapping("/update_account")
	public String viewDetails(@AuthenticationPrincipal CustomerUserDetails loggedUser, Model model) {
		String email = loggedUser.getUsername();
		Customer customer = service.getByEmail(email);
		model.addAttribute("customer", customer);
		
		return "customer_form";
	}
	
	@PostMapping("/customer/save")
	public String saveDetails(Customer customer, RedirectAttributes redirectAttributes) {
		service.updateAccount(customer);

		redirectAttributes.addFlashAttribute("message", "The information has been saved!");
		return "redirect:/update_account";
	}
}
