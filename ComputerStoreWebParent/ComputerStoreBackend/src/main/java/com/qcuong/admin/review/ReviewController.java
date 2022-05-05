package com.qcuong.admin.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qcuong.common.entity.Review;

@Controller
public class ReviewController {

	@Autowired
	private ReviewService service;
	
	@GetMapping("/reviews")
	public String listFirstPage(Model model) {
		return listByPage(1, model);
	}

	@GetMapping("/reviews/page/{pageNum}")
	public String listByPage(@PathVariable(name="pageNum") int pageNum, Model model) {
		Page<Review> page = service.listByPage(pageNum);
		List<Review> listReviews = page.getContent();
		
		long startCount = (pageNum - 1) + ReviewService.REVIEWS_PER_PAGE + 1;
		long endCount = startCount + ReviewService.REVIEWS_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listReviews", listReviews);
		
		return "reviews";
	}
	
	@GetMapping("/reviews/delete/{id}")
	public String deleteReview(@PathVariable("id") Integer id, RedirectAttributes ra) {
		service.delete(id);
		ra.addFlashAttribute("message", "Review has been delete!");
		return "redirect:/reviews";
	}
}
