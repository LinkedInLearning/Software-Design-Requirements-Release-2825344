package com.hplussport.red30.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@SuppressWarnings("serial")
@WebServlet (urlPatterns = "/logout")
public class Logout extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session != null)
		    session.invalidate();
		
		String logoutMessage = "<html><h3>You have been logged out</h3></html>";
		response.getWriter().write(logoutMessage);
		request.getRequestDispatcher("/index.jsp").include(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
