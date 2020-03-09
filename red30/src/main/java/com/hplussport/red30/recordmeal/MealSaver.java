package com.hplussport.red30.recordmeal;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hplussport.red30.beans.Meal;
import com.hplussport.red30.datalayer.MealDao;

@SuppressWarnings("serial")
@WebServlet (urlPatterns = "/savemeal")
public class MealSaver extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		Meal meal = (Meal)session.getAttribute("meal");

		boolean mealSaved = MealDao.saveMeal(meal);
		session.setAttribute("mealSaved", mealSaved);
		request.getRequestDispatcher("/jsp/recordmeal/mealSaved.jsp").include(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}


}
