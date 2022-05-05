package com.qcuong.admin.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.qcuong.common.entity.Review;

@Service
public class ReviewService {

	public static int REVIEWS_PER_PAGE = 7;
	
	@Autowired 
	private ReviewRepository repo;
	
	public List<Review> listAll(){
		return (List<Review>) repo.findAll();
	}
	
	public Page<Review> listByPage(int pageNum){
		Pageable pageable = PageRequest.of(pageNum - 1, REVIEWS_PER_PAGE);
		
		return repo.findAll(pageable);
	}
	
	public void delete(int id) {
		repo.deleteById(id);
	}
}
