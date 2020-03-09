<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.util.Map, 
	java.util.List,
	java.util.Calendar, 
	java.util.Locale, 
	java.util.Collections,
	java.util.Iterator,
	com.hplussport.red30.beans.Meal, 
	com.hplussport.red30.beans.Serving,
	com.hplussport.red30.beans.Nutrient,
	com.hplussport.red30.datalayer.Dao"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Track Nutrition</title>

<link rel="stylesheet" href="css/style.css">

</head>
<body>
	<jsp:include page="/jsp/header.jsp"></jsp:include>
	<%
		List<Nutrient> nutrientList = (List<Nutrient>) session.getAttribute("nutrientList");
		Nutrient selectedNutrient = (Nutrient) request.getAttribute("selectedNutrient");
		String nutrientId = (String) request.getAttribute("nutrientId");
	%>

	<form action="tracknutrition" method="post">
		<table>
			<tr>
				<th>Select a nutrient <select name="nutrientId">
						<%
							Iterator<Nutrient> iter = nutrientList.iterator();
							while (iter.hasNext()) {
								Nutrient nutrient = iter.next();
						%>
						<option value=<%=nutrient.getId()%>>
							<%=nutrient.getId() + ": " + nutrient.getName()%>
						</option>
						<%
							}
						%>

				</select>
				<th>From</th>
				<th><input id="theDate" name="fromDate" type="date"></th>
				<th>To</th>
				<th><input name="toDate" type="date"></th>
				<th><input type="submit" value="Submit" /></th>
			</tr>
		</table>
		
	</form>
	<%
		//get session data and store it in the form of an array in a string
		//to be used in JavaScript for drawing charts. 
		Map<Calendar, Map<Integer, Float>> dailyNutrientsMap = (Map<Calendar, Map<Integer, Float>>) session
				.getAttribute("dailyNutrientsMap");
		StringBuffer chartData = new StringBuffer();

		int i = 0;
		if (dailyNutrientsMap != null && !dailyNutrientsMap.isEmpty()) {
			for (Map.Entry<Calendar, Map<Integer, Float>> entry : dailyNutrientsMap.entrySet()) {
				//extract day, month, year from calendar key
				String date = entry.getKey().get(Calendar.DAY_OF_MONTH) + "-"
						+ entry.getKey().getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) + "-"
						+ entry.getKey().get(Calendar.YEAR);
				//append a new row in chart data with date and nutrient value
				chartData.append("['" + date + "',"
						+ entry.getValue().get((String) request.getAttribute("nutrientId")) + "],");
			}
	%>
	<h2>
		Nutrient:
		<%=selectedNutrient.getId() + ": " + selectedNutrient.getName() + " "
						+ selectedNutrient.getUnit_name()%></h2>
	<%
		} else {
	%>
	<h2>
		Nutrient
		<%=selectedNutrient.getId() + ": " + selectedNutrient.getName()%>
		not found in your meals!
	</h2>
	<%
		}
		//remove the last comma
		if (chartData.length() > 0)
			chartData.replace(chartData.length() - 1, chartData.length() - 1, "");
	%>


	<!-- following code adapted from https://developers.google.com/chart/interactive/docs/gallery/linechart -->
	<script type="text/javascript"
		src="https://www.gstatic.com/charts/loader.js"></script>
	<script type="text/javascript">
		google.charts.load('current', {
			'packages' : [ 'corechart' ]
		});
		google.charts.setOnLoadCallback(drawChart);
		var jsMealData = [
	<%=chartData.toString()%>
		];

		function drawChart() {

			var data = new google.visualization.DataTable();

			data.addColumn('string', 'Date');
			data.addColumn('number', 'Quantity');

			data.addRows(jsMealData.length);
			for (i = 0; i < jsMealData.length; i++) {
				data.setCell(i, 0, jsMealData[i][0]);
				data.setCell(i, 1, parseFloat(jsMealData[i][1]));
			}

			var options = {
				curveType : 'function',
				legend : {
					position : 'bottom'
				}
			};

			var chart = new google.visualization.LineChart(document
					.getElementById('curve_chart'));

			chart.draw(data, options);

		}
	</script>
	<div id="curve_chart" style="width: 900px; height: 500px"></div>

</body>
</html>