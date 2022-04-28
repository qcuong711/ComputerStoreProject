package com.qcuong.admin.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qcuong.common.entity.Order;

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
}
