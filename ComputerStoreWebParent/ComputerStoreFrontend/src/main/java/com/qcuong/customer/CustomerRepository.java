package com.qcuong.customer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.qcuong.common.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
	
	@Query("select c from Customer c where c.email = ?1")
	public Customer findByEmail(String email);
	
	@Query("SELECT c FROM Customer c Where c.email = :email")
	public Customer getCustomerByEmail(@Param("email") String email);
}
