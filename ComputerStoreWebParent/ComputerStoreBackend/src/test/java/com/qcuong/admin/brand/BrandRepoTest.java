package com.qcuong.admin.brand;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.qcuong.common.entity.Brand;
import com.qcuong.common.entity.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BrandRepoTest {
	
	@Autowired
	private BrandRepository repo;
	
	/*
	@Test
	public void testCreateBrands() {
		Category cpu = new Category(10);
		
		Brand intel = new Brand("Intel");
		Brand amd = new Brand("AMD");
		//Brand dell = new Brand("Dell");
		//Brand hp = new Brand("HP");
		//Brand mac = new Brand("Macbook");
		//Brand lenovo = new Brand("Lenovo");
		//Brand surface = new Brand("Surface");
		//Brand msi = new Brand("MSI");
		//Brand ibm = new Brand("IBM");
		
		intel.getCategories().add(cpu);
		amd.getCategories().add(cpu);

		
		//repo.save(ibm);
		repo.saveAll(List.of(intel, amd));
	}
	*/
	
	
	@Test
	public void testCreateBrands() {
		Category cat = new Category(23);
		
		//Brand kingston = repo.findById(15).get();
		//Brand giga = repo.findById(14).get();
		//Brand sandisk = new Brand("Sandisk");
		//Brand acer = repo.findById(1).get();
		//Brand asus = repo.findById(2).get();
		//Brand dell = repo.findById(3).get();
		//Brand hp = repo.findById(4).get();
		//Brand mac = repo.findById(5).get();
		//Brand lenovo = repo.findById(6).get();
		//Brand surface = repo.findById(7).get();
		//Brand msi = repo.findById(8).get();
		//Brand ibm = repo.findById(11).get();
		//Brand intel = repo.findById(12).get();
		//Brand amd = repo.findById(13).get();
		Brand razer = repo.findById(18).get();
		Brand newmen = repo.findById(19).get();
		Brand logi = repo.findById(20).get();
		
		logi.getCategories().add(cat);
		newmen.getCategories().add(cat);
		razer.getCategories().add(cat);
		//sandisk.getCategories().add(cat);
		//kingston.getCategories().add(cat);
		//acer.getCategories().add(cat);
		//asus.getCategories().add(cat);
		//dell.getCategories().add(cat);
		//hp.getCategories().add(cat);
		//mac.getCategories().add(cat);
		//lenovo.getCategories().add(cat);
		//surface.getCategories().add(cat);
		//msi.getCategories().add(cat);
		//intel.getCategories().add(cat);
		//amd.getCategories().add(cat);
		//giga.getCategories().add(cat);
		//ibm.getCategories().add(cat);
		//samsung.getCategories().add(cat);
		
		//repo.save(logi);
		//repo.saveAll(List.of(razer, newmen));
	}
	
}
