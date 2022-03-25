package com.qcuong.admin.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qcuong.admin.brand.BrandService;
import com.qcuong.admin.category.CategoryService;
import com.qcuong.common.entity.Brand;
import com.qcuong.common.entity.Product;

@Controller
public class ProductController {
	@Autowired
	private ProductService service;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/products")
	public String listFirstPage(Model model) {
		return listByPage(1, model);
	}
	
	@GetMapping("/products/page/{pageNum}")
	public String listByPage(@PathVariable(name="pageNum") int pageNum, Model model) {
		Page<Product> page = service.listByPage(pageNum);
		List<Product> listproducts = page.getContent();
		
		long startCount = (pageNum - 1) + ProductService.PRODUCTS_PER_PAGE + 1;
		long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listproducts", listproducts);
		
		
		return "products";
	}
	
	@GetMapping("/products/new")
	public String newproduct(Model model) {
		List<Brand> listBrands = brandService.listAll();
		
		Product product = new Product();
		
		product.setInstock(true);
		
		model.addAttribute("product", product);
		model.addAttribute("listBrands", listBrands);
		
		model.addAttribute("pageTitle", "Create New product");
		
		return "product_form";
	}
	
	@PostMapping("/products/save")
	public String saveproduct(Product product, RedirectAttributes redirectAttributes) {
		service.save(product);
		redirectAttributes.addFlashAttribute("message", "The information has been saved!");
		
		return "redirect:/products";
	}
	
	@GetMapping("/products/edit/{id}")
	public String editproduct(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
			Product product = service.get(id);
			
			model.addAttribute("product", product);
			model.addAttribute("pageTitle", "Edit product with ID " + id);

			return "product_form";
	}
	
	@GetMapping("/products/delete/{id}")
	public String deleteproduct(@PathVariable(name="id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		service.delete(id);
		redirectAttributes.addFlashAttribute("message", "product with ID " + id + " deleted");
		
		return "redirect:/products";
	}
}
