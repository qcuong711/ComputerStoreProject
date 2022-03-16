package com.qcuong.admin.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcuong.common.entity.Category;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository repo;
	
	public List<Category> listAll(){
		return (List<Category>) repo.findAll();
	}
	
	public Category save(Category category) {
		return repo.save(category);
	}
	
	public List<Category> listCategoriesInForm() {
		return (List<Category>) repo.findAll();
	}
	
	public Category get(Integer id)  {
			return repo.findById(id).get();
	}
	
	public boolean checkUnique(Integer id, String name) {
		boolean isCreatingNew = (id == null);
		
		Category catByName = repo.findByName(name);
		
		if (catByName == null) return true;
		
		if (isCreatingNew) {
			if (catByName != null) return false; 
		} else {
			if (catByName.getId() != id) {
				return false;
			}
		}
		
		return true;
	}
	
	public void delete(Integer id) {
		Long countById = repo.countById(id);
		
		repo.deleteById(id);
	}
}
