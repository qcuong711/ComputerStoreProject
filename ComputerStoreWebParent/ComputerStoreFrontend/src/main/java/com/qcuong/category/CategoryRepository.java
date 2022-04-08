package com.qcuong.category;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.qcuong.common.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

	@Query("select c from Category c where c.enabled = true")
	public List<Category> findAllEnabled();
	
	@Query("select c from Category c where c.enabled = true and c.endURL = ?1")
	public Category findByEndURL(String endURL);
}
