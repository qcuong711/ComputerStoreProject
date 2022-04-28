package com.qcuong.checkout;

public class CheckoutInfo {

	private float productTotal;
	private float paymentTotal;
	private float shippingCost;
	private String deliverDays;
	
	public float getProductTotal() {
		return productTotal;
	}
	public void setProductTotal(float productTotal) {
		this.productTotal = productTotal;
	}
	public float getPaymentTotal() {
		return paymentTotal;
	}
	public void setPaymentTotal(float paymentTotal) {
		this.paymentTotal = paymentTotal;
	}
	public float getShippingCost() {
		return shippingCost;
	}
	public void setShippingCost(float shippingCost) {
		this.shippingCost = shippingCost;
	}
	public String getDeliverDays() {
		return deliverDays;
	}
	public void setDeliverDays(String deliverDays) {
		this.deliverDays = deliverDays;
	}


	
	
}
