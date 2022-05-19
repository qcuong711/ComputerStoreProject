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
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.qcuong.common.entity.Customer;
import com.qcuong.common.entity.Order;
import com.qcuong.common.entity.OrderDetail;
import com.qcuong.common.entity.OrderTrack;
import com.qcuong.common.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class OrderRepoTest {

	@Autowired
	private OrderRepository repo;
	
	@Autowired
	private TestEntityManager manager;
	
	
	public void TestNewOrder() {
		Customer customer = manager.find(Customer.class, 7);
		Product product = manager.find(Product.class, 7);
		
		Order order = new Order();
		
		order.setCustomer(customer);
		order.setFirstName(customer.getFirstName());
		order.setLastName(customer.getLastName());
		order.setAddress(customer.getAddress());
		order.setCountry(customer.getCountry());
		order.setEmail(customer.getEmail());
		order.setPostalCode(customer.getPostalCode());
		order.setShippingCost(10);
		order.setDeliverDays("3 to 6");
		order.setCartTotal(300);
		order.setTotal(order.getShippingCost() + order.getCartTotal());
		order.setStatus("New");
		order.setOrderTime(new Date());
		
		OrderDetail details = new OrderDetail();
		
		details.setOrder(order);
		details.setProduct(product);
		details.setProductCost(product.getSellingPrice());
		details.setQuantity(2);
		details.setUnitPrice(details.getProductCost() * details.getQuantity());
		
		order.getOrderDetails().add(details);
		
		repo.save(order);
		
		
	}
	
	
	public void testListOrder() {
		Iterable<Order> orders = repo.findAll();
		
		orders.forEach(System.out::print);
	}
	
	public void TestNewOrder2() {
		Customer customer = manager.find(Customer.class, 8);
		Product product = manager.find(Product.class, 19);
		
		Order order = new Order();
		
		order.setCustomer(customer);
		order.setFirstName(customer.getFirstName());
		order.setLastName(customer.getLastName());
		order.setAddress(customer.getAddress());
		order.setCountry(customer.getCountry());
		order.setEmail(customer.getEmail());
		order.setPostalCode(customer.getPostalCode());
		order.setShippingCost(10);
		order.setDeliverDays("3 to 6");
		order.setCartTotal(400);
		order.setTotal(order.getShippingCost() + order.getCartTotal());
		order.setStatus("New");
		order.setOrderTime(new Date());
		
		OrderDetail details = new OrderDetail();
		
		details.setOrder(order);
		details.setProduct(product);
		details.setProductCost(product.getSellingPrice());
		details.setQuantity(1);
		details.setUnitPrice(details.getProductCost() * details.getQuantity());
		
		order.getOrderDetails().add(details);
		
		repo.save(order);	
		
	}
	
	
	public void testUpdateOrder() {
		Integer orderId = 5;
		Order order = repo.findById(orderId).get();
		
		OrderTrack newTrack = new OrderTrack();
		
		newTrack.setOrder(order);
		newTrack.setUpdateTime(new Date());
		newTrack.setStatus("Delivered");
		newTrack.setNotes("delivered des");
		
		List<OrderTrack> tracks = order.getOrderTracks();
		
		tracks.add(newTrack);
		repo.save(order);
	}
		
	@Test
	public void testFindByTime() throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date st = df.parse("2022-03-01");
		Date ed = df.parse("2022-05-08");
		
		List<Order> orders = repo.findByOrderTimeBetween(st, ed);
		
		for(Order order : orders) {
			System.out.printf("%s | %s | %.2f | %.2f \n", order.getId(), order.getOrderTime(), order.getCartTotal(),
					order.getTotal());
		}
	}
}
