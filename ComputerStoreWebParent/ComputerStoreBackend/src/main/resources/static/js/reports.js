var data;
var chartOptions;
var totalTotalSelling;
var totalOrders;

$(document).ready(function() {
	$(".button-sales-by-date").on("click", function(){
		period = $(this).attr("period");
		loadSalesReportByDate(period);
	});
});

function loadSalesReportByDate(period) {
	requestURL = contextPath + "reports/sales_by_date/" + period;
	
	$.get(requestURL, function(responseJSON) {
		prepareChartData(responseJSON);
		customizeChart(period);
		drawChart(period);	
	});
}

function prepareChartData(responseJSON) {
	data = new google.visualization.DataTable();
	data.addColumn('string', 'Date');
	data.addColumn('number', 'Total');
	data.addColumn('number', 'Orders');
	
	totalTotalSelling = 0.0;
	totalOrders = 0;
	
	$.each(responseJSON, function(index, reportItem) {
		data.addRows([[reportItem.identifier, reportItem.totalSelling, reportItem.orderCount]]);
		totalTotalSelling += parseFloat(reportItem.totalSelling);
		totalOrders += parseInt(reportItem.orderCount);
	});
}

function customizeChart(period) {
	chartOptions = {
		title: getChartTitle(period),
		'height': 360,
		legend: {position: 'top'},
		
		series: {
			0: {targetAxisIndex: 0},
			1: {targetAxisIndex: 1}
		},
		
		vAxes: {
			0: {title: 'Total', format: 'currency'},
			1: {title: 'Numbers of Orders'},
		}
	};
	
	var formatter = new google.visualization.NumberFormat({
		prefix: '$'
	});
	
	formatter.format(data, 1);
}

function drawChart(period) {
	var chart = new google.visualization.ColumnChart(document.getElementById('chart_sales_by_date'));
	chart.draw(data, chartOptions);
	
	days = getDays(period);
	
	$("#textTotal").text("$" + $.number(totalTotalSelling, 2));
	$("#textAvgTotal").text("$" + $.number(totalTotalSelling / days, 2));	
	$("#textOrders").text(totalOrders);
}

function getChartTitle(period) {
	if(period == "last_7_days") return "Last 7 days";
	if(period == "last_30_days") return "Last 30 days";
	if(period == "last_12_months") return "Last 12 months";
	
	return "Last_7_days";
}

function getDays(period) {
	if(period == "last_7_days") return 7;
	if(period == "last_30_days") return 30;
	if(period == "last_12_months") return 365;
	
	return 7;
}