package com.qcuong.admin.report;

import java.util.Objects;

public class ReportItem {

	private String identifier;
	private float totalSelling;
	private int orderCount;
	private float profit;
	
	public ReportItem() {
		
	}
	
	public ReportItem(String identifier) {
		this.identifier = identifier;
	}
	
	public ReportItem(String identifier, float totalSelling, float profit) {
		this.identifier = identifier;
		this.totalSelling = totalSelling;
		this.profit = profit;
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
	
	public float getProfit() {
		return profit;
	}

	public void setProfit(float profit) {
		this.profit = profit;
	}

	public void addTotalSelling(float total) {
		this.totalSelling += total;		
	}
	
	public void addProfit(float number) {
		this.profit += number;
	}

	public void increaseOrderCount() {
		this.orderCount++;		
	}

	@Override
	public int hashCode() {
		return Objects.hash(identifier);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportItem other = (ReportItem) obj;
		return Objects.equals(identifier, other.identifier);
	}
	
}
