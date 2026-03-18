package com.centrum.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.centrum.bean.Product;
import com.centrum.bean.User;
import com.centrum.dao.ConfirmDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// 🔥 Removed @WebServlet annotation (mapped in web.xml instead)
public class UpdateQuantityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ConfirmDao confirmDao;

	@Override
	public void init() throws ServletException {
		try {
			confirmDao = new ConfirmDao();
		} catch (Exception e) {
			throw new ServletException("Failed to initialize ConfirmDao", e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response type to JSON (for AJAX)
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		// Return error if user not logged in
		if (loggedInUser == null) {
			response.getWriter().write("{\"error\":\"Login required\"}");
			return;
		}

		try {
			// Get data from AJAX request
			int productId = Integer.parseInt(request.getParameter("productId"));
			int newQty = Integer.parseInt(request.getParameter("newQty"));

			// Fetch product to get price and max stock
			Product product = confirmDao.getProductbyId(productId);
			if (product == null) {
				response.getWriter().write("{\"error\":\"Product not found\"}");
				return;
			}

			// Enforce min 1 / max stock
			newQty = Math.max(1, Math.min(newQty, product.getStock_quantity()));
			double total = product.getPrice() * newQty;

			// Return updated data as JSON (format total to 2 decimals)
			String json = String.format("{\"newQty\":%d, \"total\":%.2f, \"maxStock\":%d}", newQty, total,
					product.getStock_quantity());
			response.getWriter().write(json);

		} catch (SQLException | NumberFormatException e) {
			e.printStackTrace();
			response.getWriter().write("{\"error\":\"Failed to update quantity\"}");
		}
	}
}