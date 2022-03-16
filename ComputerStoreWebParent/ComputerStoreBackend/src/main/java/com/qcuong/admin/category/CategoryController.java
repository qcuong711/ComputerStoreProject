package com.qcuong.admin.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qcuong.common.entity.Category;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService service;
	
	@GetMapping("/categories")
	public String listAll(Model model) {
		List<Category> listCategories = service.listAll();
		model.addAttribute("listCategories", listCategories);
		
		return "categories";
	}
	
	@GetMapping("/categories/new")
	public String newCategory(Model model) {
		List<Category> listCatInForm = service.listCategoriesInForm();
		
		model.addAttribute("listCategories", listCatInForm);
		model.addAttribute("category", new Category());
		model.addAttribute("pageTitle", "Create New Category");
		
		return "category_form";
	}
	
	@PostMapping("/categories/save")
	public String saveCategory(Category category, RedirectAttributes redirectAttributes) {
		service.save(category);
		redirectAttributes.addFlashAttribute("message", "The information has been saved!");
		
		return "redirect:/categories";
	}
	
	@GetMapping("/categories/edit/{id}")
	public String editCategory(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
			Category category = service.get(id);
			List<Category> listCategories = service.listCategoriesInForm();
			
			model.addAttribute("category", category);
			model.addAttribute("listCategories", listCategories);
			model.addAttribute("pageTitle", "Edit Category with ID " + id);

			return "category_form";
	}
	
	@GetMapping("/categories/delete/{id}")
	public String deleteCategory(@PathVariable(name="id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		service.delete(id);
		redirectAttributes.addFlashAttribute("message", "Category with ID " + id + " deleted");
		
		return "redirect:/categories";
	}
}
