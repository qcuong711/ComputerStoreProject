package com.qcuong.category;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.qcuong.common.entity.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SiteCategoryTest {
	
	@Autowired
	private CategoryRepository repo;
	
	
	public void testListEnabled() {
		List<Category> cat = repo.findAllEnabled();
		for (Category cate : cat) {
			System.out.println(cate.getName() + "," + cate.isEnabled());
		}
	}
	
	@Test
	public void testFindCat() {
		String endURL = "laptops";
		Category category = repo.findByEndURL(endURL);
	}
	
}
