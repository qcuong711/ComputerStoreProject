package com.qcuong.order;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcuong.checkout.CheckoutInfo;
import com.qcuong.common.entity.CartItem;
import com.qcuong.common.entity.Customer;
import com.qcuong.common.entity.Order;
import com.qcuong.common.entity.OrderDetail;
import com.qcuong.common.entity.OrderStatus;
import com.qcuong.common.entity.Product;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repo;
	
	public Order createOrder(Customer customer, List<CartItem> cartItems, CheckoutInfo checkoutInfo) {
		Order newOrder = new Order();
		newOrder.setOrderTime(new Date());
		newOrder.setStatus(OrderStatus.NEW);
		newOrder.setCustomer(customer);
		newOrder.setCountry(customer.getCountry());
		newOrder.setAddress(customer.getAddress());
		newOrder.setPhoneNumber(customer.getPhoneNumber());
		newOrder.setFirstName(customer.getFirstName());
		newOrder.setLastName(customer.getLastName());
		newOrder.setPostalCode(customer.getPostalCode());
		newOrder.setDeliverDays(checkoutInfo.getDeliverDays());
		newOrder.setCartTotal(checkoutInfo.getProductTotal());
		newOrder.setShippingCost(checkoutInfo.getShippingCost());
		newOrder.setTotal(checkoutInfo.getPaymentTotal());
		newOrder.setEmail(customer.getEmail());
		
		List<OrderDetail> orderDetails = newOrder.getOrderDetails();
		
		for (CartItem item : cartItems) {
			Product product = item.getProduct();
			
			OrderDetail orderDetail = new OrderDetail();
			
			orderDetail.setOrder(newOrder);
			orderDetail.setProduct(product);
			orderDetail.setQuantity(item.getQuantity());
			orderDetail.setUnitPrice(product.getDiscountSellingPrice());
			orderDetail.setProductCost(product.getDiscountSellingPrice() * item.getQuantity());
			
			orderDetails.add(orderDetail);
		}
		
		return repo.save(newOrder);
	}
}
