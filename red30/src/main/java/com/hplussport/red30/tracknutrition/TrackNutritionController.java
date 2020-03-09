package com.hplussport.red30.tracknutrition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hplussport.red30.beans.Meal;
import com.hplussport.red30.beans.Nutrient;
import com.hplussport.red30.beans.User;
import com.hplussport.red30.datalayer.MealDao;
import com.hplussport.red30.datalayer.USDADao;

@SuppressWarnings("serial")
@WebServlet (urlPatterns = "/tracknutrition")
public class TrackNutritionController extends HttpServlet {


	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		User user = (User)session.getAttribute("user");
		String fromDate = (String)request.getParameter("fromDate");
		String toDate = (String)request.getParameter("toDate");
		
		//get all nutrients for dropdown
		List<Nutrient> nutrientList = new ArrayList<>();
		nutrientList.addAll(USDADao.nutrientMap.values());
		Collections.sort(nutrientList);
		session.setAttribute("nutrientList", nutrientList);
		
		//reinitialize session level dates
		session.setAttribute("fromDate", fromDate);
		session.setAttribute("toDate", toDate);
		
		String nutrientId = request.getParameter("nutrientId"); //nutrientId to be tracked
		if (nutrientId == null || nutrientId.isEmpty()) nutrientId = "1003"; //default protein
		request.setAttribute("selectedNutrient", USDADao.nutrientMap.get(nutrientId));
		request.setAttribute("nutrientId", nutrientId);
		
		if (user != null) {
			List<Meal> meals = MealDao.findMeals(user.getUsername(), fromDate, toDate);
			Map<Calendar, Map<String, Float>> dailyNutrientsMap = MealDao.findDailyNutrients(meals, nutrientId, user.getUsername(), fromDate, toDate);
			session.setAttribute("dailyNutrientsMap", dailyNutrientsMap);
			
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/track/trackDailyNutrients.jsp");
		rd.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

