package com.qcuong.admin.category;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.qcuong.common.entity.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {
	
	public Category findByName(String name);
	
	public Long countById(Integer Id);
}
