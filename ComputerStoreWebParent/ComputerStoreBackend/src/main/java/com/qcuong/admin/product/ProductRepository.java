package com.qcuong.admin.product;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.qcuong.common.entity.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
	public Product findByName(String name);
	
	public Long countById(Integer Id);
}
