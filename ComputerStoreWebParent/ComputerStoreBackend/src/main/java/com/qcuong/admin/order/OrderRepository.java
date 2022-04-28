package com.qcuong.admin.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.qcuong.common.entity.Order;

public interface OrderRepository extends PagingAndSortingRepository<Order, Integer> {
		
	@Query("select o from Order o order by o.orderTime desc")
	public Page<Order> findAll(Pageable pageable);
			
	public Long countById(Integer Id);
	
}
