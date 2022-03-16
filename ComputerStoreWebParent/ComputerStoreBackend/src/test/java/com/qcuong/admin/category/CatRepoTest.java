package com.qcuong.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.qcuong.common.entity.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CatRepoTest {
	
	@Autowired
	private CategoryRepository repo;
	
	/*
	@Test
	public void testCreateRootCategory() {
		Category computer = new Category("Components");
		
		Category savedCat = repo.save(computer);
		
		assertThat(savedCat.getId()).isGreaterThan(0);
	}
	
	
	@Test
	public void testCreateSubCategory() {
		Category parent = new Category(3);
		
		Category main = new Category("Motherboards", parent);
		Category card = new Category("Video Graphic Devides", parent);
		Category cases = new Category("Cases", parent);
		Category power = new Category("Power Supplies", parent);
		Category memory = new Category("Memories", parent);
		Category storage = new Category("Storages", parent);
		Category cool = new Category("PC Coolings", parent);
		Category cable = new Category("Cables", parent);
		Category hub = new Category("Hubs", parent);
		
		
		repo.saveAll(List.of(main, card, cases, power, memory, storage, cool, cable, hub));
		
		
	}
	*/
	
	@Test
	public void testGetCat() {
		Category category = repo.findById(2).get();
		System.out.println(category.getName());
		
		Set<Category> child = category.getChildren();
		
		for(Category subCat : child) {
			System.out.println(subCat.getName());
		}
		
		assertThat(child.size()).isGreaterThan(0);
	}
	
}
