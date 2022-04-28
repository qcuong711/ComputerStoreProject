package com.qcuong.admin.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.qcuong.common.entity.Order;

@Service
public class OrderService {

	public static final int ORDERS_PER_PAGE = 10;
	
	@Autowired
	private OrderRepository repo;
	
	public List<Order> listAll(){
		return (List<Order>) repo.findAll();
	}
	
	public Page<Order> listByPage(int pageNum){
		Pageable pageable = PageRequest.of(pageNum - 1, ORDERS_PER_PAGE);
		
		return repo.findAll(pageable);
	}
	
	public Order save(Order order) {
		return repo.save(order);
	}
	
	public Order get(int id) {
		return repo.findById(id).get();
	}
	
	public void delete(int id) {
		repo.deleteById(id);
	}
}
