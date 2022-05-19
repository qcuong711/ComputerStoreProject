package com.qcuong.admin.report;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcuong.admin.order.OrderRepository;
import com.qcuong.common.entity.Order;

@Service
public class MasterOrderReportService {

	@Autowired
	private OrderRepository orderRepo;
	
	private DateFormat dateFormatter;
	 
	public List<ReportItem> getReportDataLast7Days(){
		System.out.println("Report 7 days data:");
		return getReportDataLastXDays(7);
	}

	public List<ReportItem> getReportDataLastXDays(int days) {
		Date endTime = new Date();
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DAY_OF_MONTH, -(days - 1));
		Date startTime = ca.getTime();
		
		System.out.println("Start time: " + startTime);
		System.out.println("End time: " + endTime);
		
		dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		
		return getReportDataByDateRange(startTime, endTime);
	}
	
	private List<ReportItem> getReportDataByDateRange(Date startTime, Date endTime) {
		List<Order> listOrders = orderRepo.findByOrderTimeBetween(startTime, endTime);
		printRawData(listOrders);
		
		List<ReportItem> listReportItems = createReportData(startTime, endTime);
		
		calculateSalesForReportData(listOrders, listReportItems);
		
		System.out.println();
		
		printReportData(listReportItems);
		
		return listReportItems;
	}
	
	private List<ReportItem> createReportData(Date startTime, Date endTime) {
		List<ReportItem> listReportItems = new ArrayList<>();
		
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(startTime);
		
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(endTime);
		
		Date currentDate = startDate.getTime();
		String dateString = dateFormatter.format(currentDate);
		
		listReportItems.add(new ReportItem(dateString));
		
		do {
			startDate.add(Calendar.DAY_OF_MONTH, 1);
			currentDate = startDate.getTime();
			dateString = dateFormatter.format(currentDate);
			
			listReportItems.add(new ReportItem(dateString));
		} while (startDate.before(endDate));
		
		return listReportItems;
		
	}

	private void calculateSalesForReportData(List<Order> listOrders, List<ReportItem> listReportItems) {
		for(Order order : listOrders) {
			String orderDateString = dateFormatter.format(order.getOrderTime());
			
			ReportItem reportItem = new ReportItem(orderDateString);
			
			int itemIndex = listReportItems.indexOf(reportItem);
			
			if(itemIndex >= 0) {
				reportItem = listReportItems.get(itemIndex);
				
				reportItem.addTotalSelling(order.getTotal());
				//rItem.addProfit();
				reportItem.increaseOrderCount();
			}
		}
	}

	private void printReportData(List<ReportItem> items) {
		items.forEach(item -> {
			System.out.printf("%s, %10.2f, %d \n", item.getIdentifier(), item.getTotalSelling(), item.getOrderCount());
		});
		
	}

	private void printRawData(List<Order> listOrders) {
		listOrders.forEach(order -> {
			System.out.printf("%-3d | %s | %10.2f | %10.2f \n", order.getId(), order.getOrderTime(), 
					order.getCartTotal(), order.getTotal());
		}) ;
	}
	
}
