<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.hplussport.red30.beans.User"%>
<header>
	<% User user = (User)session.getAttribute("user"); 
		if (user == null) { %>
	<table>
		<tbody>
			<tr>
				<td>
					<div>
						<a><img src="images/Red30AppLogo.png"
							alt="Logo - Red30" width="200" /></a>
					</div>
				</td>
				<td align="right" valign="bottom">
					<div id="menu">
						<ul>
							<li><a href="index.jsp">home</a></li>
							<li><a href=search>search products</a></li>
							<li><a href=compare>compare products</a></li>
							<li><a href=login>login</a></li>
						</ul>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
		<% } else  { %>
	<table>
		<tbody>
			<tr>
				<td>
					<div>
						<a><img src="images/Red30AppLogo.png" alt="Logo - Red30"
							width="200" /></a>
					</div>
				</td>
				<td align="right" valign="bottom">
					<div id="menu">
						<ul>				
						<li ><a href="index.jsp">home</a></li>
								<li><a href=search>search products</a></li>
								<li><a href=compare>compare products</a></li>
								<li><a href=recordmeal>record meal</a></li>
								<li><a href=dietlog>view diet log</a></li>
								<li><a href=tracknutrition>track nutrition</a></li>
								<li><a href=logout>logout <%=user.getUsername()%></a>
						</ul>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	<% } %>
</header>