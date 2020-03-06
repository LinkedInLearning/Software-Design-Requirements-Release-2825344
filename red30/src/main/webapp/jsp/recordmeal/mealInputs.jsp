<%@page language="java" contentType="text/html"%>
<%@page
	import="com.hplussport.red30.beans.Meal,
	java.time.Instant,
	java.sql.Timestamp "%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Record Meal</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:include page="/jsp/header.jsp"></jsp:include>
	<%
		Meal meal = (Meal) session.getAttribute("meal");
	%>
	<div style="text-align: center;">
		<form name="initializeMeal" action="initializeMeal" method="post">
			<table align="center">
				<tr>
					<th>Meal date</th>
					<th><input name="mealDateTime" type="datetime-local"
						value='<%=meal.getMealDateTime() != null ? meal.getMealDateTime() : Timestamp.from(Instant.now())%>'></th>
					<th>Meal type</th>
					<th><select name="mealType"
						value=<%=meal.getMealType() != null ? "mealType" : " "%>>
							<option value="Breakfast">Breakfast</option>
							<option value="Lunch">Lunch</option>
							<option value="Dinner">Dinner</option>
							<option value="Snack">Snack</option>
					</select></th>
				</tr>

			</table>
			<input type="submit" value="Create meal">
		</form>
	</div>
</body>
</html>
