package com.hplussport.red30.recordmeal;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hplussport.red30.beans.Meal;
import com.hplussport.red30.beans.Serving;

@SuppressWarnings("serial")
@WebServlet (urlPatterns = "/removeServingFromMeal")
public class ServingRemover extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String fdcId = (String)request.getParameter("fdcIdRemoved");
		Meal meal = (Meal)session.getAttribute("meal");
		
		Iterator<Serving> iter = meal.getMealServingList().iterator();
		Serving serving;
		while (iter.hasNext()) {
			serving = iter.next();
			if (serving.getProduct().getFdc_id().equals(fdcId)) iter.remove();
		}
		session.setAttribute("meal", meal);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/recordmeal/mealProducts.jsp");
		rd.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
