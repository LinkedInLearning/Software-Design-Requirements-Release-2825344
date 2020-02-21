<%@page language="java" contentType="text/html"%>
<%@page import="java.sql.*, 
			java.util.List,
			com.hplussport.red30.beans.Product,
			com.hplussport.red30.beans.Nutrient"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Red30 Compare</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>

	<jsp:include page="/jsp/header.jsp"></jsp:include>
	<%
		String searchString1 = (String)session.getAttribute("searchString1");
		List<Product> productsList1 = (List<Product>) session.getAttribute("productsList1"); //list of products in search result
		Product selectedProduct1 = (Product) session.getAttribute("selectedProduct1"); //selected product to show nutrients
		List<Nutrient> nutrientsList1 = (List<Nutrient>) session.getAttribute("nutrientsList1");//nutrients in selected product
		int count1 = 0; //used for numbering search results

		List<Product> productsList2 = (List<Product>) session.getAttribute("productsList2"); //list of products in search result
		String searchString2 = (String) session.getAttribute("searchString2"); //search string entered by user
		Product selectedProduct2 = (Product) session.getAttribute("selectedProduct2"); //selected product to show nutrients
		List<Nutrient> nutrientsList2 = (List<Nutrient>) session.getAttribute("nutrientsList2");//nutrients in selected product
		int count2 = 0; //used for numbering search results
	%>

	<form name="search" action="comparename" method="Get">
		<p align="center">
			<input type="search" name="searchString1"
				placeholder="Enter keywords to search first product" size="75">
			<input type="submit" name="compare" width="100px" value="Compare">
			<input type="search" name="searchString2"
				placeholder="Enter keywords to search second product" size="75">
		</p>
	</form>

	<table class="fullPage">

		<tr>
			<td class="fullPage">
				<% if (productsList1 != null && productsList1.size() > 0) { %>
				<div class="quarterPage">

					<form id="compareNutrients1" action="comparefdcid" method="post"></form>
					<table class="elements">
						<!--  first products-search table -->
						<tr>
							<th class="elements" colspan=4>
								<!-- This cell has table of products from searching product1 search box input -->
								<p align="center">
									<%= "Found " + productsList1.size() + " products with " + searchString1 %>
								</p>
							</th>
						</tr>

						<tr>
							<td>Product</td>
							<td>Brand</td>
							<td>Code</td>
							<td>Select</td>
						</tr>

						<%
							for (Product product : productsList1) { //display each product in table
						%>
						<tr class="elements">
							<td width="60%" class="elements">
								<%= (++count1) + ". " + product.getDescription() %>
							</td>
							<td width="20%" class="elements">
								<%=product.getBrand_owner()%>
							</td>
							<td class="elements">
								<%=product.getFdc_id()%>
							</td>

							<td><p align="center">
									<input type="radio" value="<%=product.getFdc_id()%>"
										name="fdcId1"
										<%=product.getFdc_id().equals(selectedProduct1.getFdc_id()) ? "checked" : ""%>
										form="compareNutrients1" onChange="form.submit()" />
								</p></td>
						</tr>
						<%
							} // end for
						%>
					</table>
				</div> <%
 	} else {
 %>
				<h4 class="message">
					No product found with
					<%=searchString1%></h4> <%
 	}
 %>
			</td>
			<!--  end of first products list -->
			<td>
				<%
					if (productsList2 != null && productsList2.size() > 0) {
				%>
				<div class="quarterPage">

					<form id="compareNutrients2" action="comparefdcid" method="post"></form>
					<table class="elements">
						<!--  second products-search table -->
						<tr>
							<th class="elements" colspan=4>
								<!-- This cell has products from searching product2 search box input -->
								<p align="center">
									<%="Found " + productsList2.size() + " products with " + searchString2%>
								</p>
							</th>
						</tr>
						<tr>
							<td>Product</td>
							<td>Brand</td>
							<td>Code</td>
							<td>Select</td>
						</tr>
						<%
							for (Product product : productsList2) { //display each product in table
						%>
						<tr class="elements">
							<td width="60%" class="elements">
								<%=(++count2) + ". " + product.getDescription()%>
							</td>
							<td width="20%" class="elements">
								<%=product.getBrand_owner()%>
							</td>
							<td class="elements">
								<%= product.getFdc_id()	%>
							</td>

							<td class="elements"><p align="center">
									<input type="radio" value="<%=product.getFdc_id()%>"
										name="fdcId2"
										<%=product.getFdc_id().equals(selectedProduct2.getFdc_id()) ? "checked" : ""%>
										form="compareNutrients2" onChange="form.submit()" />
								</p></td>
						</tr>
						<%
							} // end for
						%>
					</table>

				</div> <% } else {  %>
				<h4 class="message">
					No product found with
					<%=searchString2 %></h4> <% } %>
			</td>
			<!-- end of second products-search results cell -->
		</tr>
		<tr>
			<td>
				<% if (selectedProduct1 != null) { %>
				<div class="quarterPage">
					<table class="elements">
						<tr>
							<th class="elements" colspan=3>
								<!--  Following code fetches nutrient-data for first product -->
								<p />
								<p align="center">
									<%= "Nutrients in " + selectedProduct1.getDescription() %>
								</p>
							</th>

							<!--  for first product's nutrients table -->
							<%
								if (nutrientsList1 != null && nutrientsList1.size() > 0) {
							%>
						
						<tr>
							<td>Nutrient</td>
							<td>Nutrient qty <br>per 100 gm of product
							</td>
							<td>Nutrient <br>unit of measure
							</td>
						</tr>
						<%
							for (Nutrient nutrient : nutrientsList1) {
						%>
						<tr class="elements">
							<td width="40%" class="elements">
								<%= nutrient.getName()	%>
							</td>
							<td class="elements">
								<%= selectedProduct1.getProductNutrientMap().get(nutrient.getId())%>
							</td>
							<td class="elements">
								<%= nutrient.getUnit_name()	%>
							</td>
						</tr>
						<%
							} //end for
								} // end if
						%>
					</table>
					<!--  end of first product's nutrients table -->

				</div> <!--  end of div for first product's nutrient table --> <%
						} //end if
					%>
			</td>

			<td>
				<% if (selectedProduct2 != null) { %>
				<div class="quarterPage">
					<table class="elements">
						<tr>
							<th class="elements" colspan=3>
								<!--  Following code fetches nutrient-data for second product -->
								<p />
								<p align="center">
									<%= "Nutrients in " + selectedProduct2.getDescription()	%>
								</p>
							</th>
							<!--  for second product's nutrients table -->
							<%
								if (nutrientsList2 != null && nutrientsList2.size() > 0) {
							%>
						
						<tr>
							<td>Nutrient</td>
							<td>Nutrient qty <br>per 100 gm of product
							</td>
							<td>Nutrient <br>unit of measure
							</td>
						</tr>
						<%
							for (Nutrient nutrient : nutrientsList2) {
						%>

						<tr class="elements">
							<td width="40%" class="elements">
								<%= nutrient.getName()	%>
							</td>
							<td class="elements">
								<%= selectedProduct2.getProductNutrientMap().get(nutrient.getId()) %>
							</td>
							<td class="elements">
								<%= nutrient.getUnit_name()	%>
							</td>
						</tr>
						<%
							} //end for
								} // end if
						%>
					</table>
				</div> <%
						} //end if
					%>
			</td>
			<!--  end of cell for second product's nutrients -->
		</tr>
	</table>
</body>
</html>