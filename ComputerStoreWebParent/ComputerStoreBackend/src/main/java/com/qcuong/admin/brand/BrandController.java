package com.qcuong.admin.brand;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qcuong.admin.category.CategoryService;
import com.qcuong.common.entity.Brand;
import com.qcuong.common.entity.Category;

@Controller
public class BrandController {
	
	@Autowired
	private BrandService service;
	
	@Autowired
	private CategoryService catService;
	
	@GetMapping("/brands")
	public String listFirstPage(Model model) {
		return listByPage(1, model);
	}
	
	@GetMapping("/brands/page/{pageNum}")
	public String listByPage(@PathVariable(name="pageNum") int pageNum, Model model) {
		Page<Brand> page = service.listByPage(pageNum);
		List<Brand> listBrands = page.getContent();
		
		long startCount = (pageNum - 1) + BrandService.BRANDS_PER_PAGE + 1;
		long endCount = startCount + BrandService.BRANDS_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listBrands", listBrands);
		
		
		return "brands";
	}
	
	@GetMapping("/brands/new")
	public String newBrand(Model model) {
		List<Category> listCategories = catService.listCategoriesInForm();
		
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("brand", new Brand());
		model.addAttribute("pageTitle", "Create New Brand");
		
		return "brand_form";
	}
	
	@PostMapping("/brands/save")
	public String saveBrand(Brand brand, RedirectAttributes redirectAttributes) {
		service.save(brand);
		redirectAttributes.addFlashAttribute("message", "The information has been saved!");
		
		return "redirect:/brands";
	}
	
	@GetMapping("/brands/edit/{id}")
	public String editBrand(@PathVariable(name = "id") int id, Model model, RedirectAttributes redirectAttributes) {
			Brand brand = service.get(id);
			List<Category> listCat = catService.listCategoriesInForm();
			
			model.addAttribute("brand", brand);
			model.addAttribute("listCategories", listCat);
			model.addAttribute("pageTitle", "Edit Brand with ID " + id);

			return "brand_form";
	}
	
	@GetMapping("/brands/delete/{id}")
	public String deleteBrand(@PathVariable(name="id") int id, Model model, RedirectAttributes redirectAttributes) {
		service.delete(id);
		redirectAttributes.addFlashAttribute("message", "Brand with ID " + id + " deleted");
		
		return "redirect:/brands";
	}
}
