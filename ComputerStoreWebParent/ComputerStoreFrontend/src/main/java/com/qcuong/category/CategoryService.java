package com.qcuong.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcuong.common.entity.Category;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repo;
	
	public List<Category> listNoChildCategory() {
		List<Category> listNoChild = new ArrayList<>();
		
		List<Category> listEnabled = repo.findAllEnabled();
		
		listEnabled.forEach(category -> {
			Set<Category> child = category.getChildren();
			if(child == null || child.size() == 0) {
				listNoChild.add(category);
			}
		});
		return listNoChild;
	}
	
	public Category getCategory(String endURL) {
		return repo.findByEndURL(endURL);
	}
	
	public List<Category> getCatParent(Category child) {
		List<Category> listParents = new ArrayList<>();
		
		Category parent = child.getParent();
		
		while(parent != null) {
			listParents.add(0, parent);
			parent = parent.getParent();
		}
		
		listParents.add(child);
		
		return listParents;
	}
}
