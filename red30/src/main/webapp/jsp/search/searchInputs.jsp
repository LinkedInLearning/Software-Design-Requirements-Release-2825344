<%@page language="java" contentType="text/html"%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Red30 Product Search</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:include page="/jsp/header.jsp"></jsp:include>
	<div style="text-align: center; vertical-align: middle;">
		<form name="search" action="searchproducts" method="post">
			<input type="search" name="searchString"
				placeholder="Enter keywords to search food product" size="100" /><br />
		</form>
	</div>
</body>
</html>

