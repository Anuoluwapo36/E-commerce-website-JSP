package com.centrum.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.centrum.bean.User;
import com.centrum.dao.UserDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeactivateServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	private static final String LOGIN_PAGE = "/login-user.jsp";
	private static final String SETTING_PAGE = "/buyer/settings.jsp";

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();

		try {
			userDao = new UserDao();
		} catch (Exception e) {
			throw new ServletException("failed to initilize userDao for deacative:", e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doPost(req, resp);

		userDeactivateAccount(request, response);
	}

	private void userDeactivateAccount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");

		if (loggedInUser == null) {
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + LOGIN_PAGE + "?error=Account not found");
			return;
		}

		try {
			String actions = request.getParameter("action");
			if ("deactivate".equals(actions)) {

				userDao.deactivateUser(loggedInUser.getUser_id());

				request.getSession().invalidate();
				response.sendRedirect(
						request.getContextPath() + LOGIN_PAGE + "?message=Your account has been deactivated ");

			} else {
				response.sendRedirect(request.getContextPath() + SETTING_PAGE + "?error=Invalid action ");

			}

		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + SETTING_PAGE + "?error=failed to deactivate  ");

		}

	}

}
