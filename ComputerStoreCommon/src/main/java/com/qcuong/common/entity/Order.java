package com.qcuong.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "first_name", nullable = false, length = 45)
	private String firstName;
	
	@Column(name = "last_name", nullable = false, length = 45)
	private String lastName;
	
	@Column(length = 45)
	private String email;
	
	@Column(length = 18)
	private String phoneNumber;
	
	@Column(length = 200)
	private String address;
	
	@Column(length = 10)
	private String postalCode;
	
	private String country;
	
	private Date orderTime;
	
	private float shippingCost;
	
	private float cartTotal;
	
	private float total;
	
	private String deliverDays;
	
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderDetail> orderDetails = new ArrayList<>();
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderTrack> orderTracks = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public float getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(float shippingCost) {
		this.shippingCost = shippingCost;
	}

	public float getCartTotal() {
		return cartTotal;
	}

	public void setCartTotal(float cartTotal) {
		this.cartTotal = cartTotal;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getDeliverDays() {
		return deliverDays;
	}

	public void setDeliverDays(String deliverDays) {
		this.deliverDays = deliverDays;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	@Transient
	public String getCustomerName() {
		return customer.getFirstName() + " " + customer.getLastName();
	}
	
	public List<OrderTrack> getOrderTracks() {
		return orderTracks;
	}

	public void setOrderTracks(List<OrderTrack> orderTracks) {
		this.orderTracks = orderTracks;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", cartTotal=" + cartTotal + ", customer=" + customer.getEmail();
	}
	
	@Transient
	public List<String> getProductList() {
		List<String> lists = new ArrayList<>();
		
		for (OrderDetail detail : orderDetails) {
			String productName = "- " + detail.getProduct().getName() 
					+ " X " + detail.getQuantity() + " = " + detail.getUnitPrice() + "$";
			lists.add(productName);
		}
		
		return lists;
	}
}
