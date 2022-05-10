package com.qcuong.common.entity;

import java.util.ArrayList;
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
@Table(name="products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true, length = 256, nullable = false)
	private String name;
	
	@Column(length = 8192, nullable = false)
	private String description;
	
	@Column(length = 512, nullable = false)
	private String itemSpecifics;
	
	@Column(name = "inStock")
	private boolean instock;
	
	private int quantity;
	
	private float purchasePrice;
	private float sellingPrice; 
	
	private float discount;
	
	@Column(unique = true, length = 64)
	private String endURL;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ProductImage> images = new ArrayList<>();
	
	private String avatar; 
	
	private int reviewCount;
	
	private float averageRating;
	
	@Transient
	private boolean customerCanReview;
	
	@Transient
	private boolean reviewedByCustomer;
	
	public Product(Integer id) {
		this.id = id;
	}

	public Product() {
	}
	
	public Product(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getItemSpecifics() {
		return itemSpecifics;
	}

	public void setItemSpecifics(String itemSpecifics) {
		this.itemSpecifics = itemSpecifics;
	}

	public boolean isInstock() {
		return instock;
	}

	public void setInstock(boolean instock) {
		this.instock = instock;
	}

	public float getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(float purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public float getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(float sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}
	
	public String getEndURL() {
		return endURL;
	}

	public void setEndURL(String endURL) {
		this.endURL = endURL;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public List<ProductImage> getImages() {
		return images;
	}

	public void setImages(List<ProductImage> images) {
		this.images = images;
	}
	
	public void addAdditionalImage(String imageName) {
		this.images.add(new ProductImage(imageName, this));
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public float getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(float averageRating) {
		this.averageRating = averageRating;
	}
	
	public boolean isCustomerCanReview() {
		return customerCanReview;
	}

	public void setCustomerCanReview(boolean customerCanReview) {
		this.customerCanReview = customerCanReview;
	}

	public boolean isReviewedByCustomer() {
		return reviewedByCustomer;
	}

	public void setReviewedByCustomer(boolean reviewedByCustomer) {
		this.reviewedByCustomer = reviewedByCustomer;
	}

	@Transient
	public String getAvatarPath() {
		if (avatar == null) {
			return "/images/img-product-default.png";
		}
		
		return "/product-images/" + this.id + "/" + this.avatar;
	}
	
	@Transient
	public float getDiscountSellingPrice() {
		float discountSellingPricef = 0;
		
		if(discount > 0) {
			discountSellingPricef = sellingPrice * ((100 - discount) / 100);
		} else {
			discountSellingPricef = this.sellingPrice;
		}
		
		String discountPriceString = String.format("%.02f", discountSellingPricef);
		float discountSellingPrice = Float.parseFloat(discountPriceString);
		
		return discountSellingPrice;
	}
	
	
	
}
