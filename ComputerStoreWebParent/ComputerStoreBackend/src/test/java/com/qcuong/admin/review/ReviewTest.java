package com.qcuong.admin.review;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.qcuong.common.entity.Customer;
import com.qcuong.common.entity.Product;
import com.qcuong.common.entity.Review;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ReviewTest {

	@Autowired
	private ReviewRepository repo;
	
	@Test
	public void testAddReview() {
		Product product = new Product(28); 
		
		Customer customer = new Customer(8);

		Review review = new Review();
		
		review.setProduct(product);
		review.setCustomer(customer);
		review.setComment("Lorem Ipsum is simply dummy text of the printing and typesetting industry. "
				+ "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, "
				+ "when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
		review.setHeading("Heading of a comment");
		review.setRating(3);
		review.setReviewTime(new Date());
		
		repo.save(review);
	}
}
