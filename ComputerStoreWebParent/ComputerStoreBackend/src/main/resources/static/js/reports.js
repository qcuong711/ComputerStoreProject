var data;
var chartOptions;
var totalTotalSelling;
var totalOrders;

function loadSalesReportByDate() {
	requestURL = contextPath + "reports/sales_by_date/last_7_days";
	
	$.get(requestURL, function(responseJSON) {
		prepareChartData(responseJSON);
		customizeChart();
		drawChart();	
	});
}

function prepareChartData(responseJSON) {
	data = new google.visualization.DataTable();
	data.addColumn('string', 'Date');
	data.addColumn('number', 'Total');
	data.addColumn('number', 'Orders');
	
	$.each(responseJSON, function(index, reportItem) {
		data.addRows([[reportItem.identifier, reportItem.totalSelling, reportItem.orderCount]]);
		totalTotalSelling += parseFloat(reportItem.totalSelling);
		totalOrders += parseInt(reportItem.orderCount);
	});
}

function customizeChart() {
	chartOptions = {
		title: 'Last 7 days',
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

function drawChart() {
	var chart = new google.visualization.ColumnChart(document.getElementById('chart_sales_by_date'));
	chart.draw(data, chartOptions);
}