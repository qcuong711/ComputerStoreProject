package com.qcuong.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.qcuong.common.entity.Product;
import com.qcuong.common.entity.Review;

public interface ReviewRepository extends PagingAndSortingRepository<Review, Integer> {
	
	@Query("select r from Review r where r.customer.id = ?1 order by r.reviewTime desc")
	public Page<Review> findByCustomer(Integer customerId, Pageable pageable);
	
	@Query("select r from Review r where r.customer.id = ?1 and r.id = ?2")
	public Review findByCustomerAndId(Integer customerId, Integer reviewId);
	
	@Query("select r from Review r where r.product = ?1 order by r.reviewTime desc")
	public Page<Review> findByProduct(Product product, Pageable pagealbe);
	
	@Query("select count(r.id) from Review r where r.customer.id = ?1 and r.product.id = ?2")
	public Long countByProductAndCustomer(Integer customerId, Integer productId);
}
