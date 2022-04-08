package com.qcuong.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.qcuong.common.entity.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>{
	
	@Query("select p from Product p where (p.category.id = ?1)")
	public Page<Product> listByCategory(Integer categoryId, Pageable pageable);
	
	public Product findByEndURL(String endURL);
}
