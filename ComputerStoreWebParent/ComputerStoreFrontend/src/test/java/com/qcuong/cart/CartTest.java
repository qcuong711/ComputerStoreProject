package com.qcuong.cart;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.qcuong.common.entity.CartItem;
import com.qcuong.common.entity.Customer;
import com.qcuong.common.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CartTest {

	@Autowired
	private CartItemRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	
	public void testSaveItem() {
		Integer customerId = 6;
		Integer productId = 8;
		
		Customer customer = entityManager.find(Customer.class, customerId);
		Product product = entityManager.find(Product.class, productId);
		
		CartItem cartItem = new CartItem();
		cartItem.setCustomer(customer);
		cartItem.setProduct(product);
		cartItem.setQuantity(2);
		
		repo.save(cartItem);
	}
	
	
	public void testFindCustomer() {
		
		Integer customerId = 7;
		Customer customer = new Customer(customerId);
		List<CartItem> listItems = repo.findByCustomer(customer);
		
		listItems.forEach(System.out::println);
	}
	
	
	public void testFindCustomerAndProduct() {
		
		Integer customerId = 7;
		Customer customer = new Customer(customerId);
		Integer productId = 7;
		Product product = new Product(productId);
		
		CartItem item = repo.findByCustomerAndProduct(customer, product);
		
		System.out.println(item);
		
	}
	
	public void testUpdateQuantity() {
		Integer customerId = 7;
		Integer productId = 7;
		Integer quantity = 4;
		
		repo.updateQuantity(quantity, customerId, productId);
	}
	
	@Test
	public void testDeleteCartItem() {
		Integer customerId = 6;
		Integer productId = 8;
		
		repo.deleteByCustomerAndProduct(customerId, productId);
	}
}
