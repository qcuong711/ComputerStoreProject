package com.qcuong.admin.order;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.qcuong.common.entity.OrderDetail;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class OrderDetailTest {

	@Autowired
	private OrderDetailRepository odRepo;

	
	public void find() throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date st = df.parse("2022-03-01");
		Date ed = df.parse("2022-05-08");
		
		List<OrderDetail> list = odRepo.findWithCategoryAndTimeBetween(st, ed);
		
		for(OrderDetail od : list) {
			System.out.printf("%s | %s | %.2f | %.2f \n", od.getProduct().getCategory().getName(), 
					od.getQuantity(), od.getProductCost(), od.getUnitPrice());
		}
	}
	
	@Test
	public void findPr() throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date st = df.parse("2022-03-01");
		Date ed = df.parse("2022-05-08");
		
		List<OrderDetail> list = odRepo.findWithProductAndTimeBetween(st, ed);
		
		for(OrderDetail od : list) {
			System.out.printf("%s | %s | %.2f | %.2f \n", od.getProduct().getName(), 
					od.getQuantity(), od.getProductCost(), od.getUnitPrice());
		}
	}
}
