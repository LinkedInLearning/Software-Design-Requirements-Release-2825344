<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Red30 Login</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:include page="/jsp/header.jsp"></jsp:include>
	<div align="center">
		<form name="login" action="validate" method="post">
			<table>
				<tr>
					<td>User name</td>
					<td><input type="text" name="username" size="20"></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="password" size="20"></td>
				</tr>
				<tr>
				<td colspan=2>
				<input type="submit" value="Login">
				</td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>