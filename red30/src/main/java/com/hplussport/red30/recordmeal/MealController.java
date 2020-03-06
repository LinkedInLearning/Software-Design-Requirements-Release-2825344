package com.hplussport.red30.recordmeal;

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

import com.hplussport.red30.beans.Meal;
import com.hplussport.red30.beans.Serving;
import com.hplussport.red30.beans.User;

@SuppressWarnings("serial")
@WebServlet (urlPatterns = "/recordmeal")
public class MealController extends HttpServlet {
	       
 	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String searchString = request.getParameter("searchString");
		session.setAttribute("searchString", searchString);
		Meal meal = new Meal();
		List<Serving> mealServingList = new ArrayList<>();
		meal.setMealServingList(mealServingList);
		User user = (User)session.getAttribute("user");
		meal.setUsername(user.getUsername());
		session.setAttribute("meal", meal);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/recordmeal/mealInputs.jsp");
		rd.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
