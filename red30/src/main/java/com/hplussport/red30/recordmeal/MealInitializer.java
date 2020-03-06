package com.hplussport.red30.recordmeal;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hplussport.red30.beans.Meal;

@SuppressWarnings("serial")
@WebServlet (urlPatterns = "/initializeMeal")
public class MealInitializer extends HttpServlet{
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String dateTimeString = (String)request.getParameter("mealDateTime").replace("T", " "); //adjust time formatting issue
		Meal meal = (Meal)session.getAttribute("meal");

		//store meal inputs in session
		if (dateTimeString != null) {  
			dateTimeString += ":00"; //this is because we are not taking input in seconds 
			Timestamp mealDateTime = Timestamp.valueOf(dateTimeString);
			meal.setMealDateTime(mealDateTime);
			meal.setMealType((String)request.getParameter("mealType"));
			session.setAttribute("meal", meal);
		}

		RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/recordmeal/mealProducts.jsp");
		rd.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
