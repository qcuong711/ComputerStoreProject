package com.qcuong.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qcuong.common.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	
}
