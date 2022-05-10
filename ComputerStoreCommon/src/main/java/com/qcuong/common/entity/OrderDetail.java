package com.qcuong.common.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="order_details")
public class OrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private int quantity;
	
	private float productCost;
		
	private float unitPrice;
		
	@ManyToOne
	@JoinColumn(name = "product")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	public OrderDetail(String categoryName, int quantity, float productCost, float unitPrice) {
		this.product = new Product();
		this.product.setCategory(new Category(categoryName));
		this.quantity = quantity;
		this.productCost = productCost;
		this.unitPrice = unitPrice;
	}
	
	public OrderDetail(int quantity, String productName, float productCost, float unitPrice) {
		this.product = new Product(productName);
		this.quantity = quantity;
		this.productCost = productCost;
		this.unitPrice = unitPrice;
	}
	
	public OrderDetail() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getProductCost() {
		return productCost;
	}

	public void setProductCost(float productCost) {
		this.productCost = productCost;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
	
}
