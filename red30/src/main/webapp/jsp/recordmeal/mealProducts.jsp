<%@page language="java" contentType="text/html"%>
<%@page import="java.sql.*, 
	java.util.List, 
	java.util.Map, 
	java.text.DateFormat, 
	com.hplussport.red30.beans.Product,
	com.hplussport.red30.beans.Nutrient,
	com.hplussport.red30.beans.Meal,
	com.hplussport.red30.beans.Serving"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Record meal</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:include page="/jsp/header.jsp"></jsp:include>
	<%
		Meal meal = (Meal) session.getAttribute("meal");
		String searchString = (String) session.getAttribute("searchString"); //search string entered by user
		DateFormat dateFormat = DateFormat.getDateInstance();
		DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
		String date = dateFormat.format(meal.getMealDateTime());
		String time = timeFormat.format(meal.getMealDateTime());
	%>
	<h3 align="center"><%=meal.getMealType() + " on " + date + " at " + time%></h3>
	<form id="searchMealProduct" action="searchMealProduct" method="post">
		<table align="center">
			<tr>
				<th>Search food product</th>
				<th colspan=5><input id="searchString" type="search"
					name="mealSearchString" size="100"
					placeholder='<%=(searchString != null && !searchString.isEmpty()) ? searchString : "Enter keywords to search"%>'></th>
			</tr>
		</table>
	</form>
	<%
		List<Product> productsList = (List<Product>) session.getAttribute("productsList"); //list of products in search result
		Product selectedProduct = (Product) session.getAttribute("selectedProduct"); //selected product to show nutrients
		meal = (Meal) session.getAttribute("meal");
		searchString = (String) session.getAttribute("searchString"); //search string entered by user
		int count = 0; //used for numbering search results
	%>
	<form id="addServingToMeal" action="addServingToMeal" method="post">
		<%
			if (productsList != null && productsList.size() > 0) {
		%>
		<div class="halfPage" style="height: 30%;">

			<table class="elements">
				<tr>
					<th class="elements" colspan=4><%="Found " + ((productsList == null || productsList.isEmpty()) ? "0" : productsList.size())
						+ " products with " + searchString%></th>
				</tr>
				<tr>
					<th>Product</th>
					<th>Brand</th>
					<th>Unit</th>
					<th><p align="center">Select</th>
				</tr>
				<%
					for (Product product : productsList) { //display each product in table
				%>
				<tr class="elements">
					<td width="40%" class="elements"><%=(++count) + ". " + product.getDescription()%>
					</td>
					<td width="20%" class="elements"><%=product.getBrand_owner()%></td>
					<td width="20%" class="elements"><%=product.getServing_size_unit()%></td>
					<td width="20%"><p align="center">
							<input type="radio" name="fdcId" value=<%=product.getFdc_id()%>
								<%=product.getFdc_id().equals(selectedProduct.getFdc_id()) ? "checked" : ""%>
								form="addServingToMeal" onClick="form.submit()"></td>
				</tr>
				<%
					} // end for
				%>
				<tr class="lastrow"> </tr>
			</table>

		</div>
		<%
			} // end if
		%>
		<%
			if (selectedProduct != null) {
		%>
		<div align="center">
			<table>
				<tr>
					<th align="center">Quantity <input type="number"
						name="productQuantity"> <%=(selectedProduct != null) ? selectedProduct.getServing_size_unit() : ""%>
						<input type="submit" value="Add to meal" form="addServingToMeal"
						onSubmit="form.submit()">
					</th>
				</tr>
			</table>
		</div>
		<%
			}
		%>
	</form>
	<form id="savemeal" action="savemeal" method="post"></form>
	<form id="removeServing" action="removeServingFromMeal" method="post">
		<%
			if (meal != null && meal.getMealServingList() != null && meal.getMealServingList().size() > 0) {
		%>
		<div class="halfPage" style="height: 30%;">

			<table class="small">
				<tr>
					<th class="elements" colspan=4>Servings in my meal</th>
				</tr>
				<tr>
					<th>Product</th>
					<th>Quantity</th>
					<th>Unit</th>
					<th><p align="center">
							<input type="submit" value="Remove from meal"
								form="removeServing" onSubmit="form.submit()"></th>
				</tr>
				<%
					for (Serving serving : meal.getMealServingList()) {
				%>

				<tr class="elements">
					<td width="40%"><%=serving.getProduct().getDescription()%></td>
					<td width="20%"><%=serving.getQuantity()%></td>
					<td width="20%"><%=serving.getProduct().getServing_size_unit()%></td>
					<td width="20%"><p align="center">
							<input type="radio" name="fdcIdRemoved"
								value=<%=serving.getProduct().getFdc_id()%>>
				</tr>
				<%
					} //end for
				%>

			</table>
		</div>

		<div align="center">
			<table>
				<tr>
					<td colSpan=4><input type="submit" value="Save meal"
						form="savemeal" onSubmit="form.submit()"></td>
				</tr>
			</table>
		</div>
		<%
			} //end if
		%>
	</form>

</body>
</html>