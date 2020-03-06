package com.hplussport.red30.recordmeal;

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
import com.hplussport.red30.beans.Product;

@SuppressWarnings("serial")
@WebServlet (urlPatterns = "/searchMealProduct")
public class MealProductSearcher extends HttpServlet {
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		List<Product> productsList = new ArrayList<>();
		String searchString = (String)request.getParameter("mealSearchString");

		//search for products with the input searchString
		productsList = Dao.searchProductOnName(searchString);

		request.setAttribute("searchString", searchString);
		session.setAttribute("productsList", productsList);	
		if (productsList != null && productsList.size() > 0)
			session.setAttribute("selectedProduct", productsList.get(0));  //this to select the radio button
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/recordmeal/mealProducts.jsp");
		rd.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
