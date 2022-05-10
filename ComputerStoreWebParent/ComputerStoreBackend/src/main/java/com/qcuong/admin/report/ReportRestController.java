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
	public List<ReportItem> getReportByDatePeriod(@PathVariable("period") String period) {
		System.out.println("Report period: " + period);
		return morService.getReportLast7Days();
	}
}