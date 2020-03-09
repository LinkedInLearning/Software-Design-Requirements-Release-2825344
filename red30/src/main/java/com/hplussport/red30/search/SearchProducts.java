package com.hplussport.red30.search;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hplussport.red30.beans.Nutrient;
import com.hplussport.red30.beans.Product;
import com.hplussport.red30.datalayer.USDADao;


@SuppressWarnings("serial")
@WebServlet (urlPatterns = "/searchproducts")
public class SearchProducts extends HttpServlet {
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get session to save session attributes
		HttpSession session = request.getSession(true);
		String searchString = (String)request.getParameter("searchString");
		session.setAttribute("searchString", searchString);
		
		//ask Dao to search for products
		List<Product> productsList = USDADao.searchProductOnName(searchString);

		//get nutrients for first product in the productsList
		List<Nutrient> nutrientsList = null;
		if (productsList != null && productsList.size() > 0) {
			nutrientsList = USDADao.searchNutrientsForProduct(productsList.get(0).getFdc_id());
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
