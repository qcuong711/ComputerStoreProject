package com.qcuong.admin.order;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qcuong.common.entity.Order;
import com.qcuong.common.entity.OrderTrack;

@Controller
public class OrderController {

	@Autowired
	private OrderService service;
	
	@GetMapping("/orders")
	public String listFirstPage(Model model) {
		return listByPage(1, model);
	}
	
	@GetMapping("/orders/page/{pageNum}")
	public String listByPage(@PathVariable(name="pageNum") int pageNum, Model model) {
		Page<Order> page = service.listByPage(pageNum);
		List<Order> listOrders = page.getContent();
		
		long startCount = (pageNum - 1) + OrderService.ORDERS_PER_PAGE + 1;
		long endCount = startCount + OrderService.ORDERS_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listOrders", listOrders);
		
		return "orders";
	}
	
	@GetMapping("/orders/info/{id}")
	public String orderInfo(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		Order order = service.get(id);					
		
		model.addAttribute("pageTitle", "Order Information");
		model.addAttribute("order", order);

		return "order_info";
	}
	
	@PostMapping("/orders/save")
	public String saveBrand(Order order, RedirectAttributes redirectAttributes) {
		service.save(order);
		redirectAttributes.addFlashAttribute("message", "The information has been saved!");
		
		return "redirect:/orders";
	}
	
	@GetMapping("/orders/track/{id}")
	public String trackInfo(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		Order order = service.get(id);
		List<OrderTrack> orderTracks = order.getOrderTracks();
				
		model.addAttribute("pageTitle", "Order Tracking Information");
		model.addAttribute("order", order);

		model.addAttribute("orderTracks", orderTracks);
		
		return "order_track";
	}
	
	
	@PostMapping("/ordersTrack/save")
	private String OrderTracks(Order order, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		String status = request.getParameter("selectStatus");
		String notes = request.getParameter("selectNotes");
		
		service.addTrack(order, status, notes);
		redirectAttributes.addFlashAttribute("message", "The information has been saved!");

		return "redirect:/orders";
	}

	
}
