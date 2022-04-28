package com.qcuong.cart;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.qcuong.common.entity.CartItem;
import com.qcuong.common.entity.Customer;
import com.qcuong.common.entity.Product;

public interface CartItemRepository extends CrudRepository<CartItem, Integer>{

	public List<CartItem> findByCustomer(Customer customer);
	
	public CartItem findByCustomerAndProduct(Customer customer, Product product);
	
	@Modifying
	@Query("update CartItem c set c.quantity =?1 where c.customer.id = ?2 and c.product.id = ?3")
	public void updateQuantity(Integer quantity, Integer customerId, Integer productId);
	
	@Modifying
	@Query("delete from CartItem c where c.customer.id = ?1 and c.product.id = ?2")
	public void deleteByCustomerAndProduct(Integer customerId, Integer productId);
	
	@Modifying
	@Query("delete CartItem c where c.customer.id = ?1")
	public void deleteByCustomer(Integer customerId);
}
