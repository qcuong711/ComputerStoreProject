package com.qcuong.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.qcuong.common.entity.Product;

@Service
public class ProductService {

	public static final int PRODUCTS_PER_PAGE = 20;
	
	@Autowired
	private ProductRepository repo;
	
	public Page<Product> listByCategory(int pageNum, Integer categoryId) {
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);
		
		return repo.listByCategory(categoryId, pageable);
	}
	
	public Product getProduct(String endURL) {
		Product product = repo.findByEndURL(endURL);
		return product;
	}
}
