package com.qcuong.admin.product;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qcuong.admin.FileUploadUtil;
import com.qcuong.admin.brand.BrandService;
import com.qcuong.admin.category.CategoryService;
import com.qcuong.common.entity.Brand;
import com.qcuong.common.entity.Product;
import com.qcuong.common.entity.ProductImage;


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
	public String saveproduct(Product product, RedirectAttributes redirectAttributes,
			@RequestParam("fileImage") MultipartFile avatarImg) throws IOException {
		
		//@RequestParam("addImage1") MultipartFile addImg1,
		//@RequestParam("addImage2") MultipartFile addImg2,
		//@RequestParam("addImage3") MultipartFile addImg3
		
		//setAddImg1Name(addImg1, product);
		//setAddImg2Name(addImg2, product);
		//setAddImg3Name(addImg3, product);
		
		setAvatarName(avatarImg, product);
		
		Product savedProduct = service.save(product);
		
		//saveUploadedImages(avatarImg, addImg1, addImg2, addImg3, savedProduct);
		saveUploadedImages(avatarImg, savedProduct);
		
		redirectAttributes.addFlashAttribute("message", "The information has been saved!");
		
		return "redirect:/products";
	}
	
	private void setAvatarName(MultipartFile avatarImg, Product product) {
		if(!avatarImg.isEmpty()) {
			String fileName = StringUtils.cleanPath(avatarImg.getOriginalFilename());
			product.setAvatar(fileName);
		}
	}
	
	private void setAddImg1Name(MultipartFile addImg1, Product product) {
		if(!addImg1.isEmpty()) {
			String fileName = StringUtils.cleanPath(addImg1.getOriginalFilename());
			product.addAdditionalImage(fileName);
		}
	}
	
	
	private void setAddImg2Name(MultipartFile addImg2, Product product) {
		if(!addImg2.isEmpty()) {
			String fileName = StringUtils.cleanPath(addImg2.getOriginalFilename());
			product.addAdditionalImage(fileName);
		}
	}
	
	private void setAddImg3Name(MultipartFile addImg3, Product product) {
		if(!addImg3.isEmpty()) {
			String fileName = StringUtils.cleanPath(addImg3.getOriginalFilename());
			product.addAdditionalImage(fileName);
		}
	}
	
	private void saveUploadedImages(MultipartFile avatarImg, Product savedProduct) throws IOException {
		//MultipartFile addImg1, MultipartFile addImg2, MultipartFile addImg3,
		if(!avatarImg.isEmpty()) {
			String fileName = StringUtils.cleanPath(avatarImg.getOriginalFilename());
			String uploadDir = "../product-images/" + savedProduct.getId();
			
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, avatarImg);
		}
		
		String uploadDir = "../product-images/" + savedProduct.getId() + "/additional";
		
		/*
		if(!addImg1.isEmpty()) {
			String fileName = StringUtils.cleanPath(addImg1.getOriginalFilename());
			FileUploadUtil.saveFile(uploadDir, fileName, addImg1);
		}
		if(!addImg2.isEmpty()) {
			String fileName = StringUtils.cleanPath(addImg2.getOriginalFilename());
			FileUploadUtil.saveFile(uploadDir, fileName, addImg2);
		}
		if(!addImg3.isEmpty()) {
			String fileName = StringUtils.cleanPath(addImg3.getOriginalFilename());
			FileUploadUtil.saveFile(uploadDir, fileName, addImg3);
		}
		*/
		
	}
	
	@GetMapping("/products/edit/{id}")
	public String editproduct(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
			Product product = service.get(id);
			List<Brand> listBrands = brandService.listAll();
			
			model.addAttribute("product", product);
			model.addAttribute("pageTitle", "Edit product with ID " + id);
			model.addAttribute("listBrands", listBrands);
			
			
			ProductImage additionalImage1 = null;
			ProductImage additionalImage2 = null;
			ProductImage additionalImage3 = null;
			if (product.getImages().size() > 0) {
			    additionalImage1 = product.getImages().get(0);
			    additionalImage2 = product.getImages().get(1);
			    additionalImage3 = product.getImages().get(2);
			}
			model.addAttribute("additionalImage1", additionalImage1);
			model.addAttribute("additionalImage2", additionalImage2);
			model.addAttribute("additionalImage3", additionalImage3);
			
			
			return "product_form";
	}
	
	@GetMapping("/products/delete/{id}")
	public String deleteproduct(@PathVariable(name="id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		service.delete(id);
		String uploadDir = "../product-images/" + id + "/additional";
		String uploadDirRoot = "../product-images/" + id;
		
		FileUploadUtil.removeDir(uploadDir);
		FileUploadUtil.removeDir(uploadDirRoot);
		
		
		redirectAttributes.addFlashAttribute("message", "product with ID " + id + " deleted");
		
		return "redirect:/products";
	}
}
