package com.qcuong.admin.customer;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.qcuong.common.entity.Customer;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer> {
	
	public Customer findByEmail(String email);
	
	public List<Customer> findAll();
	
	public Long countById(Integer Id);
}
