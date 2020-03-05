<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.hplussport.red30.beans.User"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Red30 Login</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<%
		User user = (User) session.getAttribute("user");
		if (user == null) {
	%>
	<jsp:include page="loginInput.jsp"></jsp:include>
	<h4 class="message" align = "center">Invalid username or password. Please try again</h4>
	<%
		} else {
	%>
	<jsp:include page="/jsp/header.jsp"></jsp:include>
	<h4 class="message" align="center">
		Welcome
		<%=user.getUsername()%></h4>
	<%
		}
	%>

</body>
</html>