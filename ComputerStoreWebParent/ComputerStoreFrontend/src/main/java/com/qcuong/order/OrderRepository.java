package com.qcuong.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.qcuong.common.entity.Customer;
import com.qcuong.common.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	@Query("select o from Order o where o.customer.id = ?1 order by o.orderTime desc")
	public Page<Order> findAll(Integer customerId, Pageable pageable);
	
	public Order findByIdAndCustomer(Integer id, Customer customer);
}
