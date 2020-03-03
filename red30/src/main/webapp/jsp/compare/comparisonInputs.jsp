<%@page language="java" contentType="text/html"%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Red30 Compare</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:include page="/jsp/header.jsp"></jsp:include>
	<div style="text-align: center; vertical-align: middle;">
		<form name="search" action="comparename" method="post">
		<p align="center">
			<table class="center">
				<tr class="elements">
					<td colspan=2 align="center">
						<p align="center">
							<input type="search" name="searchString1"
								placeholder="Enter keywords to search first product" size="75"> 
							<input type="submit" name="compare"
								width="100px" value="Compare" /> 
							<input
								type="search" name="searchString2"
								placeholder="Enter keywords to search second product" size="75">
						</p>
					</td>

				</tr>
			</table>
		</form>
	</div>
</body>
</html>
