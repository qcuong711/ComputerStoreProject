package com.qcuong.admin.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.qcuong.common.entity.Brand;
import com.qcuong.common.entity.Category;
import com.qcuong.common.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepoTest {
	
	@Autowired
	private ProductRepository repo;
	
	@Autowired
	private TestEntityManager tem;
	
	public void testCreateProduct() {
		Brand brand = tem.find(Brand.class, 13);
		Category cat = tem.find(Category.class, 7);
		
		Product product = new Product();
		product.setName("AMD Test Gaming Laptop 1");
		product.setDescription("Description ABC");
		
		product.setBrand(brand);
		product.setCategory(cat);
		product.setSellingPrice(199);
		
		Product pr = repo.save(product);
		assertThat(pr).isNotNull();
		assertThat(pr.getId()).isGreaterThan(0);
	}
	
	
	public void testSaveProductWithImages() {
		int pId = 2;
		Product product = repo.findById(pId).get();
		
		//product.setAvatar("avatar");
		product.addAdditionalImage("e_img_2");
		
		repo.save(product);
	}
	
	@Test
	public void testUpdateCountAndAverage() {
		Integer pid = 8;
		repo.updateCountAndAverageRating(pid);
	}
}
