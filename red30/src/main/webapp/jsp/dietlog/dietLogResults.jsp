<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.util.List, 
	java.text.DateFormat, 
	com.hplussport.red30.beans.Meal, 
	com.hplussport.red30.beans.Serving"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Diet log</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:include page="/jsp/header.jsp"></jsp:include>
	<%
		List<Meal> meals = (List<Meal>) session.getAttribute("meals");
	%>
	
		<div style="text-align: center; vertical-align: middle;">
		<form name="dietlog" action="dietlog" method="post">

			<table align="center">
				<tr>
					<th>From</th>
					<th><input id="theDate" name="fromDate" type="date"></th>
					<th>To</th>
					<th><input name="toDate" type="date"></th>
				</tr>
			</table>
			<input type ="submit" value = "View meals" >
		</form>
	</div>
	<div style="text-align: center; vertical-align: middle;">
		<form name="viewmeallog" action="dietlog" method="post">
		<% if (meals != null && meals.size() > 0) { %>
			<table align="center">
				<tr class="elements">
					<th class="elements">Date</th>
					<th class="elements">Time</th>
					<th class="elements">Meal type</th>
					<th class="elements">Servings</th>
				</tr>
					<%
						
							for (Meal meal : meals) {
								DateFormat dateFormat = DateFormat.getDateInstance();
								DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
								
								String date = dateFormat.format(meal.getMealDateTime());
								String time = timeFormat.format(meal.getMealDateTime());
					%>
					<tr class="elements">
					<td class="elements"><%=date%></td>
					<td class="elements"><%=time%></td>
					<td class="elements"><%=meal.getMealType()%></td>
					<td class="elements">
						<%
							if (meal.getMealServingList() != null && meal.getMealServingList().size() > 0) {
										for (Serving serving : meal.getMealServingList()) {
						%> <%=serving.getProduct().getDescription() + " (" + serving.getQuantity()
									+ serving.getProduct().getServing_size_unit() + ");"%> 
									<br>
									<%
 	} //end for
 			} //end if
 		} //end for
 	} else { %>
 		<h3 class="message"> No meals logged during this period! </h3>
 <%	}
 %>

					</td>
				</tr>
			</table> 
			
		</form>
	</div>
</body>
</html>