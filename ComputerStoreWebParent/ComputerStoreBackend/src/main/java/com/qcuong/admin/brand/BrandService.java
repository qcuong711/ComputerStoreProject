package com.qcuong.admin.brand;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.qcuong.common.entity.Brand;

@Service
public class BrandService {
	static final int BRANDS_PER_PAGE = 6;
	
	@Autowired
	private BrandRepository repo;
	
	public List<Brand> listAll(){
		return (List<Brand>) repo.findAll();
	}
	
	public Page<Brand> listByPage(int pageNum){
		Pageable pageable = PageRequest.of(pageNum - 1, BRANDS_PER_PAGE);
		
		return repo.findAll(pageable);
	}
	
	public Brand save(Brand brand) {
		return repo.save(brand);
	}
	
	public Brand get(int id) {
		return repo.findById(id).get();
	}
	
	public void delete(int id) {
		repo.deleteById(id);
	}
}
