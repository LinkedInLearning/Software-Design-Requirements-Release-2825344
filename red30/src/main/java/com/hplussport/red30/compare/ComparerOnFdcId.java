package com.hplussport.red30.compare;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
@WebServlet (urlPatterns = "/comparefdcid")
public class ComparerOnFdcId extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get session to save session attributes
		HttpSession session = request.getSession(true);
		String fdcId1 = (String)request.getParameter("fdcId1");
		String fdcId2 = (String)request.getParameter("fdcId2");

		List<Nutrient> nutrients1 = new ArrayList<>();  //nutrients of first product
		List<Nutrient> nutrients2 = new ArrayList<>();  //nutrients of second product

		if (fdcId1 != null) {
			//pull single product
			Product product1 = Dao.productMap.get(fdcId1);
			session.setAttribute("selectedProduct1", product1);
			//search its nutrients
			nutrients1 = Dao.searchNutrientsForProduct(fdcId1);
			//set attributes
			session.setAttribute("nutrientsList1", nutrients1);	
		}

		if (fdcId2 != null) {
			//pull single product
			Product product2 = Dao.productMap.get(fdcId2);
			session.setAttribute("selectedProduct2", product2);
			//search its nutrients
			nutrients2 = Dao.searchNutrientsForProduct(fdcId2);
			//set attributes
			session.setAttribute("nutrientsList2", nutrients2);	

		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/compare/comparisonResults.jsp");
		rd.forward(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
