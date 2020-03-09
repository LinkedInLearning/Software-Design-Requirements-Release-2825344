package com.hplussport.red30.recordmeal;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hplussport.red30.beans.Meal;
import com.hplussport.red30.beans.Product;
import com.hplussport.red30.beans.Serving;
import com.hplussport.red30.datalayer.USDADao;

@SuppressWarnings("serial")
@WebServlet (urlPatterns = "/addServingToMeal")
public class ServingAdder extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String fdcId = (String)request.getParameter("fdcId");
		String qty = (String)request.getParameter("productQuantity");
		float productQuantity = Float.parseFloat((qty==null || qty.isEmpty() ? "0": qty));

		Product product = USDADao.productMap.get(fdcId);
		session.setAttribute("selectedProduct", product);
		if (productQuantity > 0) {  //user has entered some quantity
			Serving serving = new Serving(product, productQuantity);
			Meal meal =  (Meal)session.getAttribute("meal");
			//if same product was added in a serving earlier, find and increment its quantity
			boolean found = false;
			for (Serving s : meal.getMealServingList()) {  
				if (s.getProduct().getFdc_id().equals(serving.getProduct().getFdc_id())) { 
					s.setQuantity(s.getQuantity() + serving.getQuantity());
					found = true;
					break;
				}
			}
			if (!found) meal.getMealServingList().add(serving);
			session.setAttribute("meal", meal);
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/recordmeal/mealProducts.jsp");
		rd.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
