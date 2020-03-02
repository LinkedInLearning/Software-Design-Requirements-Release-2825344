<%@page language="java" contentType="text/html"%>
<%@page
	import="java.sql.*, 
	java.util.List, 
	java.util.Map,
	com.hplussport.red30.beans.Product,
	com.hplussport.red30.beans.Nutrient"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search Results</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:include page="/jsp/header.jsp"></jsp:include>
	<%
		List<Product> productsList = (List<Product>) session.getAttribute("productsList"); //list of products in search result
		String searchString = (String) session.getAttribute("searchString"); //search string entered by user
		Product selectedProduct = (Product) session.getAttribute("selectedProduct"); //selected product to show nutrients
		List<Nutrient> nutrientsList = (List<Nutrient>) session.getAttribute("nutrientsList");//nutrients in selected product
		int count = 0; //used for numbering search results
	%>
	<form id="searchNutrients" action="searchnutrients" method="post"></form>
	<%
		if (productsList != null && productsList.size() > 0) {
	%>
	<div class="fullPage">
		<div class="halfPage">
			<table class="elements">
				<tr>
					<th class="elements" colspan=4><%="Found " + productsList.size() + " products with " + searchString%>
					</th>
				</tr>
				<tr>
					<th>Product</th>
					<th>Brand</th>
					<th>Code</th>
					<th align="center">Select</th>
				</tr>
				<%
					for (Product product : productsList) { //display each product in table
				%>
				<tr class="elements">
					<td width="60%" class="elements"><%=(++count) + ". " + product.getDescription()%>
					</td>
					<td width="20%" class="elements"><%=product.getBrand_owner()%>
					</td>
					<td class="elements"><%=product.getFdc_id()%></td>
					<td><input type="radio" value="<%=product.getFdc_id()%>"
						name="fdcId"
						<%=product.equals(selectedProduct) ? "checked" : ""%>
						form="searchNutrients" onChange="form.submit()" /></td>
				</tr>
				<%
					} // end for
				%>
			</table>

		</div>

		<!--  Following code fetches nutrient-data -->

		<div class="halfPage">
			<table class="elements">
				<tr>
					<th class="elements" colspan=3><%="Nutrients in " + selectedProduct.getDescription()%>
					</th>
				</tr>
				<tr>
					<th>Nutrient</th>
					<th>Nutrient qty <br>per 100 units of product
					</th>
					<th>Nutrient <br>unit of measure
					</th>
				</tr>
				<%
					if (nutrientsList != null && nutrientsList.size() > 0) {
							for (Nutrient nutrient : nutrientsList) {
				%>
				<tr class="elements">
					<td width="60%" class="elements"><%=nutrient.getName()%></td>
					<td class="elements"><%=selectedProduct.getProductNutrientMap().get(nutrient.getId())%></td>
					<td class="elements"><%=nutrient.getUnit_name()%></td>
				</tr>
				<%
					} //end for
						} //end if
				%>
			</table>
		</div>
	</div>
	<%
		} else {
	%>
	<h4 class="message">
		No product found with
		<%=searchString%></h4>
	<%
		}
	%>
</body>
</html>