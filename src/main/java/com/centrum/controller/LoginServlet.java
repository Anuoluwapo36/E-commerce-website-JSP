package com.centrum.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

import com.centrum.bean.User;
import com.centrum.dao.LoginDao;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String DEACTIVATE_PAGE = "/static/account-deactivated.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatch = request.getRequestDispatcher("/login-user.jsp");
		dispatch.forward(request, response);
		return;

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		User loginUser = new User();
		loginUser.setEmail(email);
		loginUser.setPassword(password);

		LoginDao loginDao = new LoginDao();

		try {
			User loggedInUser = loginDao.validateCredential(loginUser);

			if (loggedInUser != null) {
				
				HttpSession session = request.getSession();
				session.setAttribute("loggedInUser", loggedInUser);

				if ("inactive".equals(loggedInUser.getStatus())) {

					String contextPath = request.getContextPath();
					response.sendRedirect(contextPath + DEACTIVATE_PAGE + "?error= Account deactivated");
					return;

				} else {

					response.sendRedirect(request.getContextPath() + "/product-browsing");
					return;
				}

			} else {
				String encodedError = URLEncoder.encode("invalid email or password.", "UTF-8");

				response.sendRedirect(request.getContextPath() + "/login-user.jsp?error=" + encodedError);
				return;

			}

		} catch (SQLException e) {
			e.printStackTrace();
			String encodedError = URLEncoder.encode("Database error. Please try again later.", "UTF-8");
			response.sendRedirect(request.getContextPath() + "/login-user.jsp?error=" + encodedError);
			return;

		}
	}

}
