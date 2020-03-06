package com.hplussport.red30.dietlog;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hplussport.red30.Dao;
import com.hplussport.red30.beans.Meal;
import com.hplussport.red30.beans.User;


@SuppressWarnings("serial")
@WebServlet (urlPatterns = "/dietlog")
public class DietLogController extends HttpServlet {
	       

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		User user = (User)session.getAttribute("user");
		String fromDate = (String)request.getParameter("fromDate");
		String toDate = (String)request.getParameter("toDate");
		if (user != null) {
			List<Meal> meals = Dao.getInstance().findMeals(user.getUsername(), fromDate, toDate);
			session.setAttribute("meals", meals);
			session.setAttribute("fromDate", fromDate);
			session.setAttribute("toDate", toDate);
		}
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/dietlog/dietLogResults.jsp");
		rd.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
