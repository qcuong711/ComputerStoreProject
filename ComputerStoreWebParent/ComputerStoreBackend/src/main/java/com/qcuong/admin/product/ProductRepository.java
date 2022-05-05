package com.qcuong.admin.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.qcuong.common.entity.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
	
	public Product findByName(String name);
	
	public Long countById(Integer Id);
	
	@Query("select o from Product o order by o.id desc")
	public Page<Product> findAll(Pageable pageable);
	
	@Query("update Product p set p.averageRating = (select avg(r.rating) from Review r where r.product.id = ?1), "
			+ " p.reviewCount = (select count(r.id) from Review r where r.product.id = ?1)"
			+ " where p.id = ?1")
	@Modifying
	public void updateCountAndAverageRating(Integer productId);
}
