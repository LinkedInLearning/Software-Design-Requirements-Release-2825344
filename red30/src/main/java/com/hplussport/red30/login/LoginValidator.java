package com.hplussport.red30.login;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hplussport.red30.Dao;
import com.hplussport.red30.beans.User;

@SuppressWarnings("serial")
@WebServlet (urlPatterns = "/validate")
public class LoginValidator extends HttpServlet {
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String username = (String) request.getParameter("username");
		String password = (String) request.getParameter("password");
		
		User user = Dao.getInstance().validateUser(username, password);
		session.setAttribute("user", user);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/login/loginResponse.jsp");
		rd.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
