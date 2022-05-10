package com.qcuong.review;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.qcuong.common.entity.Customer;
import com.qcuong.common.entity.Product;
import com.qcuong.common.entity.Review;
import com.qcuong.order.OrderDetailRepository;
import com.qcuong.product.ProductRepository;

@Service
@Transactional
public class ReviewService {

	public static int REVIEWS_PER_PAGE = 5;
	
	@Autowired
	private ReviewRepository repo;
	
	@Autowired
	private OrderDetailRepository odRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	public Page<Review> listByCustomerByPage(Customer customer, int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1, REVIEWS_PER_PAGE);
		
		return repo.findByCustomer(customer.getId(), pageable);
	}
	
	public Page<Review> listRecentReview(Product product) {
		Pageable pageable = PageRequest.of(0, 5);
		
		return repo.findByProduct(product, pageable);
	}
	
	public Page<Review> listReview(Product product, int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1, REVIEWS_PER_PAGE);
		
		return repo.findByProduct(product, pageable);
	}
	
	public boolean checkCustomerHasReviewed(Customer customer, Integer productId) {
		Long count  = repo.countByProductAndCustomer(customer.getId(), productId);
		return count > 0;
	}
	
	public boolean checkCustomerCanReview(Customer customer, Integer productId) {
		Long count = odRepo.countByProductAndCustomerAndOrderStatus(productId, customer.getId(), "DELIVERED");
		return count > 0;
	}
	
	public Review save(Review review) {
		review.setReviewTime(new Date());
		Review saveReview = repo.save(review);
		Integer productId = saveReview.getProduct().getId();
		
		productRepo.updateCountAndAverageRating(productId);
		
		return saveReview;
	}
}
