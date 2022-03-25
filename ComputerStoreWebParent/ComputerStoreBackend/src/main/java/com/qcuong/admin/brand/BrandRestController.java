package com.qcuong.admin.brand;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qcuong.common.entity.Brand;
import com.qcuong.common.entity.Category;

@RestController
public class BrandRestController {

	@Autowired
	private BrandService service;
	
	@PostMapping("/brands/check_unique")
	public String checkUnique(@Param("id") Integer id, @Param("name") String name) {
		return service.checkUnique(id, name) ? "OK" : "Duplicated";
	}
	
	@GetMapping("brands/{id}/categories")
	public List<CategoryDTO> listCategoriesByBrand(@PathVariable(name="id") int brandId){
		List<CategoryDTO> listCat = new ArrayList<>();
		
		Brand brand = service.get(brandId);
		Set<Category> categories = brand.getCategories();
		for(Category category : categories) {
			CategoryDTO dto = new CategoryDTO(category.getId(), category.getName());
			listCat.add(dto);
		}
		
		return listCat;
	}
}
