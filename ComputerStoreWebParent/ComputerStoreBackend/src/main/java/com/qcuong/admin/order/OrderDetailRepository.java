package com.qcuong.admin.order;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.qcuong.common.entity.OrderDetail;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, Integer> {

	@Query("select new com.qcuong.common.entity.OrderDetail(d.product.category.name, d.quantity, d.productCost, d.unitPrice) "
			+ "from OrderDetail d where d.order.orderTime between ?1 and ?2")
	public List<OrderDetail> findWithCategoryAndTimeBetween(Date startTime, Date endTime);
	
	@Query("select new com.qcuong.common.entity.OrderDetail(d.quantity, d.product.name, d.productCost, d.unitPrice) "
			+ "from OrderDetail d where d.order.orderTime between ?1 and ?2")
	public List<OrderDetail> findWithProductAndTimeBetween(Date startTime, Date endTime);
}
