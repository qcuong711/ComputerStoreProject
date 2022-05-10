package com.qcuong.admin.report;

public class ReportItem {

	private String identifier;
	private float totalSelling;
	private float profit;
	private int orderCount;
	
	public ReportItem() {
		
	}
	
	public ReportItem(String identifier) {
		this.identifier = identifier;
	}
	
	public ReportItem(String identifier, float totalSelling) {
		this.identifier = identifier;
		this.totalSelling = totalSelling;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public float getTotalSelling() {
		return totalSelling;
	}

	public void setTotalSelling(float totalSelling) {
		this.totalSelling = totalSelling;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public void addTotalSelling(float total) {
		this.totalSelling += total;		
	}

	public void increaseOrderCount() {
		this.orderCount++;		
	}
	
}
