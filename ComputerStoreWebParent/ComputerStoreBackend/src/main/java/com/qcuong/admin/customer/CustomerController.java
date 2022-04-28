package com.qcuong.admin.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qcuong.common.entity.Customer;

@Controller
public class CustomerController {

	@Autowired
	public CustomerService service;
	
	@GetMapping("/customers")
	public String listFirstPage(Model model) {
		return listByPage(1, model);
	}
	
	@GetMapping("/customers/page/{pageNum}")
	public String listByPage(@PathVariable(name="pageNum") int pageNum, Model model) {
		Page<Customer> page = service.listByPage(pageNum);
		List<Customer> listCustomers = page.getContent();
		
		long startCount = (pageNum - 1) + CustomerService.CUSTOMERS_PER_PAGE + 1;
		long endCount = startCount + CustomerService.CUSTOMERS_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listCustomers", listCustomers);
		
		
		return "customers";
	}
	
	@GetMapping("/customers/new")
	public String newcustomer(Model model) {
		List<Customer> listCatInForm = service.listCustomersInForm();
		
		model.addAttribute("listcustomers", listCatInForm);
		model.addAttribute("customer", new Customer());
		model.addAttribute("pageTitle", "Create New customer");
		
		return "customer_form";
	}
	
	@PostMapping("/customers/save")
	public String savecustomer(Customer customer, RedirectAttributes redirectAttributes) {
		service.save(customer);
		redirectAttributes.addFlashAttribute("message", "The information has been saved!");
		
		return "redirect:/customers";
	}
	
	@GetMapping("/customers/edit/{id}")
	public String editcustomer(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
			Customer customer = service.get(id);
			List<Customer> listcustomers = service.listCustomersInForm();
			
			model.addAttribute("customer", customer);
			model.addAttribute("listcustomers", listcustomers);
			model.addAttribute("pageTitle", "Edit customer with ID " + id);

			return "customer_form";
	}
	
	@GetMapping("/customers/delete/{id}")
	public String deletecustomer(@PathVariable(name="id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		service.delete(id);
		redirectAttributes.addFlashAttribute("message", "Customer deleted");
		
		return "redirect:/customers";
	}
}
