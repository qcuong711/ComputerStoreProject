package com.qcuong.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.qcuong.common.entity.Customer;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private void encodePassword(Customer customer) {
		String encodedPassword = passwordEncoder.encode(customer.getPassword());
		customer.setPassword(encodedPassword);
	}
	
	public Customer updateAccount(Customer customerInForm) {
		Customer customerInDB = customerRepo.findById(customerInForm.getId()).get();
		
		if(!customerInForm.getPassword().isEmpty()) {
			customerInDB.setPassword(customerInForm.getPassword());
			encodePassword(customerInDB);
		}
		
		customerInDB.setFirstName(customerInForm.getFirstName());
		customerInDB.setLastName(customerInForm.getLastName());
		
		return customerRepo.save(customerInDB);
	}
	
	public void save(Customer customer) {
		boolean isUpdatingCustomer = (customer.getId() != null);
		
		if (isUpdatingCustomer) {
			Customer existCustomer = customerRepo.findById(customer.getId()).get();
			
			if (customer.getPassword().isEmpty()) {
				customer.setPassword(existCustomer.getPassword());
			} else {
				encodePassword(customer);
			}
			
		} else {
			encodePassword(customer);
		}
		customerRepo.save(customer);
	}
	
	public Customer getByEmail(String email)  {
		return customerRepo.getCustomerByEmail(email);
	}
	
	public List<Customer> listCustomersInForm() {
		return (List<Customer>) customerRepo.findAll();
	}
	
	public boolean isEmailUnique(String email) {
		Customer customerByEmail = customerRepo.getCustomerByEmail(email);
		
		if (customerByEmail == null) {
			return true;
		} else {
			return false; 
		}
	}
}
