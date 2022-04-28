package com.qcuong.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.qcuong.common.entity.Customer;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)

public class CustomerRepoTest {
	
	@Autowired
	private CustomerRepository repo;
	
	@Test
	public void testCreateCustomer() {
		Customer customer = new Customer();
		customer.setEmail("customer1@gmail.com");
		customer.setLastName("Yamaha");
		customer.setFirstName("Exciter 155");
		customer.setPassword("pass");
		customer.setPhoneNumber("01111144444");
		customer.setPostalCode("101010101");
		customer.setCountry("VN");
		customer.setAddress("212 ABC XYz");
		
		repo.save(customer);
	}
}
