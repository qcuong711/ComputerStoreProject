package com.qcuong.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.qcuong.common.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

	@Query("select count(d) from OrderDetail d join OrderTrack t on d.order.id = t.order.id "
			+ "where d.product.id = ?1 and d.order.customer.id = ?2 and t.status = ?3")
	public Long countByProductAndCustomerAndOrderStatus(Integer productId, Integer customerId, String status);
}
