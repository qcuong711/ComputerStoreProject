package com.qcuong.admin.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.qcuong.common.entity.Category;

@Service
public class CategoryService {
	public static final int ROOT_CATEGORIES_PER_PAGE = 6;
	
	@Autowired
	private CategoryRepository repo;
	
	public List<Category> listAll(){
		return (List<Category>) repo.findAll();
	}
	
	public Page<Category> listByPage(int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1, ROOT_CATEGORIES_PER_PAGE);
		
		return repo.findAll(pageable);
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
		repo.deleteById(id);
	}
}
