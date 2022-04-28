package com.qcuong.cart;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcuong.common.entity.CartItem;
import com.qcuong.common.entity.Customer;
import com.qcuong.common.entity.Product;
import com.qcuong.product.ProductRepository;

@Service
@Transactional
public class CartService {
	
	@Autowired
	private CartItemRepository repo;
	
	@Autowired
	private ProductRepository productRepo;
	
	public Integer addProduct(Integer productId, Integer quantity, Customer customer) {
		Integer updatedQuantity = quantity;
		Product product = new Product(productId);
		CartItem item = repo.findByCustomerAndProduct(customer, product);
		
		if (item != null) {
			updatedQuantity = item.getQuantity() + quantity;
			item.setQuantity(updatedQuantity);
		} else {
			item = new CartItem();
			item.setCustomer(customer);
			item.setProduct(product);
			item.setQuantity(updatedQuantity);
		}
		
		repo.save(item);
		
		return updatedQuantity;
	}
	
	public List<CartItem> listCartItems(Customer customer) {
		return repo.findByCustomer(customer);
	}
	
	public float updateQuantity(Integer productId, Integer quantity, Customer customer) {
		repo.updateQuantity(quantity, customer.getId(), productId);
		Product product = productRepo.findById(productId).get();
		
		float productTotal = product.getDiscountSellingPrice() * quantity;
		return productTotal;
	}
	
	public void removeProduct(Integer productId, Customer customer) {
		repo.deleteByCustomerAndProduct(customer.getId(), productId);
	}
	
	public void deleteByCustomer(Customer customer) {
		repo.deleteByCustomer(customer.getId());
	}
	
}
