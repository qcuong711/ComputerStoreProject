package com.qcuong.admin.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportRestController {
	
	@Autowired
	private MasterOrderReportService morService;
	
	@GetMapping("/reports/sales_by_date/{period}")
	public List<ReportItem> getReportDataByDatePeriod(@PathVariable("period") String period) {
		System.out.println("Report period: " + period);
		
		switch (period) {
		case "last_7_days":
			return morService.getReportDataLast7Days();
			
		case "last_30_days":
			return morService.getReportDataLast30Days();
			
		case "last_12_months":
			return morService.getReportDataLast12Months();
			
		default:
			return morService.getReportDataLast7Days();
		
		}
	}
}
