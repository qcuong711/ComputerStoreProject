package com.qcuong.admin.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.qcuong.common.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

	@Query("select r from Review r order by r.id desc")
	public Page<Review> findAll(Pageable pageable);
}
