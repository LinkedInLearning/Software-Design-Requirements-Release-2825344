package com.hplussport.red30.compare;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hplussport.red30.Dao;
import com.hplussport.red30.beans.Nutrient;
import com.hplussport.red30.beans.Product;

@SuppressWarnings("serial")
@WebServlet (urlPatterns = "/comparename")
public class ComparerOnProductName extends HttpServlet {
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get session to save session attributes
		HttpSession session = request.getSession(true);
		String searchString1 = (String)request.getParameter("searchString1");
		String searchString2 = (String)request.getParameter("searchString2");

		//get product list for searchString1
		List<Product> productsList1 = new ArrayList<>();
		if (searchString1 != null && !searchString1.isEmpty())  {
			session.setAttribute("searchString1", searchString1);
			productsList1 = Dao.searchProductOnName(searchString1);
		}
		
		//get product list for searchString2
		List<Product> productsList2 = new ArrayList<>();
		if (searchString2 != null && !searchString2.isEmpty()) {
			session.setAttribute("searchString2", searchString2);
			productsList2 = Dao.searchProductOnName(searchString2);
		}

		//get nutrients for first product in productList1 as selectedProduct1
		List<Nutrient> nutrientsList1 = null;
		if (productsList1 != null && productsList1.size() > 0) {
			nutrientsList1 = Dao.searchNutrientsForProduct(productsList1.get(0).getFdc_id());
			session.setAttribute("productsList1", productsList1);	
			session.setAttribute("nutrientsList1", nutrientsList1);
			session.setAttribute("selectedProduct1", productsList1.get(0));  //to select the radio button
		}

		//get nutrients for first product in productList2 as selectedProduct2
		List<Nutrient> nutrientsList2 = null;
		if (productsList2 != null && productsList2.size() > 0) {
			nutrientsList2 = Dao.searchNutrientsForProduct(productsList2.get(0).getFdc_id());
			session.setAttribute("productsList2", productsList2);	
			session.setAttribute("nutrientsList2", nutrientsList2);
			session.setAttribute("selectedProduct2", productsList2.get(0));  //to select the radio button
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/compare/comparisonResults.jsp");
		rd.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
