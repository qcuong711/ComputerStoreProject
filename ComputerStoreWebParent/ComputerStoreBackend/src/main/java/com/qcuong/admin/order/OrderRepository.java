package com.qcuong.admin.order;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.qcuong.common.entity.Order;

public interface OrderRepository extends PagingAndSortingRepository<Order, Integer> {
		
	@Query("select o from Order o order by o.orderTime desc")
	public Page<Order> findAll(Pageable pageable);
			
	public Long countById(Integer Id);
	
	@Query("select new com.qcuong.common.entity.Order(o.id, o.orderTime, o.cartTotal, o.total) from Order o "
		+ "where o.orderTime between ?1 and ?2 order by orderTime asc")
	public List<Order> findByOrderTimeBetween(Date startTime, Date endTime);
}
