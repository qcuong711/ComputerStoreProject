package com.qcuong.admin.brand;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.qcuong.common.entity.Brand;

public interface BrandRepository extends PagingAndSortingRepository<Brand, Integer> {
	public Brand findByName(String name);
	
	public Long countById(Integer Id);
	
	public List<Brand> findAll();
}
