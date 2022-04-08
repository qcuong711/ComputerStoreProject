package com.qcuong.admin.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.qcuong.common.entity.Product;

@Service
public class ProductService {
	public static final int PRODUCTS_PER_PAGE = 6;
	
	@Autowired
	private ProductRepository repo;
	
	public List<Product> listAll(){
		return (List<Product>) repo.findAll();
	}
	
	public Page<Product> listByPage(int pageNum){
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);
		
		return repo.findAll(pageable);
	}
	
	public Product save(Product Product) {
		return repo.save(Product);
	}
	
	public Product get(int id) {
		return repo.findById(id).get();
	}
	
	public void delete(int id) {
		repo.deleteById(id);
	}
	
	public boolean checkUnique(Integer id, String name) {
		boolean isCreatingNew = (id == null);
		
		Product productByName = repo.findByName(name);
		
		if (productByName == null) return true;
		
		if (isCreatingNew) {
			if (productByName != null) return false; 
		} else {
			if (productByName != null && productByName.getId() != id) {
				return false;
			}
		}
		
		return true;
	}
	
}
