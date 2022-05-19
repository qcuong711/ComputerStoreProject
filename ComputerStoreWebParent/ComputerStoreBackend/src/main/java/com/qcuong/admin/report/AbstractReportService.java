package com.qcuong.admin.report;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class AbstractReportService {
	protected DateFormat dateFormatter;
	 
	public List<ReportItem> getReportDataLast7Days(ReportType reportType){
		System.out.println("Report 7 days data:");
		return getReportDataLastXDays(7, reportType);
	}

	protected List<ReportItem> getReportDataLastXDays(int days, ReportType reportType) {
		Date endTime = new Date();
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DAY_OF_MONTH, -(days - 1));
		Date startTime = ca.getTime();
		
		System.out.println("Start time: " + startTime);
		System.out.println("End time: " + endTime);
		
		dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		
		return getReportDataByDateRangeInternal(startTime, endTime, reportType);
	}
	
	protected abstract List<ReportItem> getReportDataByDateRangeInternal(Date startDate, 
			Date endDate, ReportType reportType);
}
