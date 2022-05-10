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
	
	public List<ReportItem> getReportLast7Days(){
		System.out.println("Report 7 days data:");
		return getReportData(7);
	}

	public List<ReportItem> getReportData(int days) {
		Date endTime = new Date();
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DAY_OF_MONTH, -(days - 1));
		Date startTime = ca.getTime();
		
		System.out.println("Start time: " + startTime);
		System.out.println("End time: " + endTime);
		
		dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		
		return getReportByDateRange(startTime, endTime);
	}
	
	private List<ReportItem> getReportByDateRange(Date startTime, Date endTime) {
		List<Order> listOrders = orderRepo.findByOrderTimeBetween(startTime, endTime);
		printRawData(listOrders);
		
		List<ReportItem> items = createReportData(startTime, endTime);
		
		calculateSalesForReportData(listOrders, items);
		printReportData(items);
		
		return null;
	}

	private void calculateSalesForReportData(List<Order> listOrders, List<ReportItem> items) {
		for(Order order : listOrders) {
			String OrderDateString = dateFormatter.format(order.getOrderTime());
			
			ReportItem rItem = new ReportItem(OrderDateString);
			
			int itemIndex = items.indexOf(rItem);
			
			if(itemIndex >= 0) {
				rItem = items.get(itemIndex);
				
				rItem.addTotalSelling(order.getTotal());
				rItem.increaseOrderCount();
			}
		}
	}

	private void printReportData(List<ReportItem> items) {
		items.forEach(item -> {
			System.out.printf("%s \n", item.getIdentifier());
		});
		
	}

	private List<ReportItem> createReportData(Date startTime, Date endTime) {
		List<ReportItem> items = new ArrayList<>();
		
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(startTime);
		
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(endTime);
		
		Date currentDate = startDate.getTime();
		String dateString = dateFormatter.format(currentDate);
		
		items.add(new ReportItem(dateString));
		
		do {
			startDate.add(Calendar.DAY_OF_MONTH, 1);
			currentDate = startDate.getTime();
			dateString = dateFormatter.format(currentDate);
			
			items.add(new ReportItem(dateString));
		} while (startDate.before(endDate));
		
		return items;
		
	}

	private void printRawData(List<Order> listOrders) {
		listOrders.forEach(order -> {
			System.out.printf("%-3d | %s | %10.2f | %10.2 \n", order.getId(), order.getOrderTime(), 
					order.getTotal(), order.getCartTotal());
		}) ;
	}
}
