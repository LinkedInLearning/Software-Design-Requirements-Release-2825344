<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.hplussport.red30.beans.Meal,
 	java.text.DateFormat "%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Save Meal</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:include page="/jsp/header.jsp"></jsp:include>

	<% boolean mealSaved = (Boolean)session.getAttribute("mealSaved"); 
	Meal meal = (Meal)session.getAttribute("meal");
 	if(mealSaved) {
	 	DateFormat dateFormat = DateFormat.getDateInstance();
		DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
%>
	<h4 class="message">
		Your
		<%= meal.getMealType() %>
		on
		<%= dateFormat.format(meal.getMealDateTime()) %> at <%= timeFormat.format(meal.getMealDateTime()) %>
		saved
	</h4>
	<% } else  {%>
	<h4 class="message">
		Sorry! Failed to save your meal! Please try again!
	</h4>
	<% } %>
</body>
</html>