package com.centrum.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.centrum.bean.Order;
import com.centrum.bean.User;
import com.centrum.dao.OrderDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class TrackingOrderServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrderDao orderDao;
	private static final String LOGIN_PAGE = "/login-user.jsp";


	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try {
			orderDao = new OrderDao();
		} catch (Exception e) {
			throw new ServletException("failed to initialize Orderdao : ", e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		fetchOrderHsitory(request, response);
	}

	private void fetchOrderHsitory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		if (loggedInUser == null) {

			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + LOGIN_PAGE + "?error= logged in user");
			return;
		}
		try {
			List<Order> orderHistory = orderDao.getAllOrder(loggedInUser);
			request.setAttribute("orderHistory", orderHistory);

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("error", "Failed to load order history");

		}

		request.getRequestDispatcher("/buyer/trackingorder.jsp").forward(request, response);
		return;

	}

}
