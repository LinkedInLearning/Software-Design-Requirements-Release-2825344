<%@page language="java" contentType="text/html"%>
<%@page import="com.hplussport.red30.Dao"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search Results</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:include page="/jsp/header.jsp"></jsp:include>

	<h2>
		Wow! I found
		<%=Dao.productMap.size()%>
		products in database
	</h2>
</body>
</html>