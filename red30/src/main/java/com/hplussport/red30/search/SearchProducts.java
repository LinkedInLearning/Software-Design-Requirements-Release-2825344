package com.hplussport.red30.search;

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
@WebServlet (urlPatterns = "/searchproducts")
public class SearchProducts extends HttpServlet {
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		List<Product> productsList = new ArrayList<>();
		String searchString = (String)request.getParameter("searchString");
		session.setAttribute("searchString", searchString);
		
		//get dao and search for products
		Dao dao = Dao.getInstance();
		productsList = dao.searchProductOnName(searchString);

		//get nutrients for first product in the productsList
		List<Nutrient> nutrientsList = null;
		if (productsList != null && productsList.size() > 0) {
			nutrientsList = dao.searchNutrientsForProduct(productsList.get(0).getFdc_id());
			session.setAttribute("productsList", productsList);	
			session.setAttribute("nutrientsList", nutrientsList);
			session.setAttribute("selectedProduct", productsList.get(0));  //this to select the radio button
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/search/searchResults.jsp");
		rd.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
