package com.qcuong.admin.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.qcuong.common.entity.Customer;

@Service
public class CustomerService {
public static final int CUSTOMERS_PER_PAGE = 6;
	
	@Autowired
	private CustomerRepository repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<Customer> listAll(){
		return (List<Customer>) repo.findAll();
	}
	
	public Page<Customer> listByPage(int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1, CUSTOMERS_PER_PAGE);
		
		return repo.findAll(pageable);
	}
	
	public void save(Customer customer) {
		boolean isUpdatingCustomer = (customer.getId() != null);
		
		if (isUpdatingCustomer) {
			Customer existCustomer = repo.findById(customer.getId()).get();
			
			if (customer.getPassword().isEmpty()) {
				customer.setPassword(existCustomer.getPassword());
			} else {
				encodePassword(customer);
			}
			
		} else {
			encodePassword(customer);
		}
		
		repo.save(customer);
	}
	
	public List<Customer> listCustomersInForm() {
		return (List<Customer>) repo.findAll();
	}
	
	public Customer get(Integer id)  {
			return repo.findById(id).get();
	}
	
	private void encodePassword(Customer customer) {
		String encodedPassword = passwordEncoder.encode(customer.getPassword());
		customer.setPassword(encodedPassword);
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}
}
