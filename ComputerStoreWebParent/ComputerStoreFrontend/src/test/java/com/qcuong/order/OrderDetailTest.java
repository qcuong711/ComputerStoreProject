package com.qcuong.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OrderDetailTest {

	@Autowired
	private OrderDetailRepository repo;
	
	@Test
	public void testDetail() {
		Integer productId = 12;
		Integer customerId = 7;
		
		Long count = repo.countByProductAndCustomerAndOrderStatus(productId, customerId, "DELIVERED");
		System.out.println(count);
	}
}
