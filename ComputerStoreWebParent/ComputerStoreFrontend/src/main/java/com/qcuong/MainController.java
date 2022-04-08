package com.qcuong;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.qcuong.category.CategoryService;
import com.qcuong.common.entity.Category;

@Controller
public class MainController {

	@Autowired
	private CategoryService categoryService;
	
	
	
	@GetMapping("/")
	public String viewHomePage(Model model) {
		List<Category> listCategories = categoryService.listNoChildCategory();
		model.addAttribute("listCategories", listCategories);
		
		return "index";
	}
}
