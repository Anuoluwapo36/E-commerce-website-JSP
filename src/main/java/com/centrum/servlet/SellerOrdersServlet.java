package com.centrum.servlet;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.centrum.bean.Order;
import com.centrum.model.Seller;
import com.centrum.dao.OrderDao;
import com.centrum.dao.SellerDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;




@WebServlet("/orders")
public class SellerOrdersServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private OrderDao orderDao;
	private SellerDAO sellerDAO;



	@Override
	public void init() throws ServletException {

		orderDao = new OrderDao();
		sellerDAO = new SellerDAO();

	}



	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("seller") == null) {


			response.sendRedirect(request.getContextPath() + "/logins");
			return;

		}

		int sellerId = (Integer) session.getAttribute("seller");

		Seller seller = sellerDAO.loginSellerById(sellerId);

		if (seller != null) {


			request.setAttribute("shopName", seller.getShopName() != null ? seller.getShopName() : "Seller");
			request.setAttribute("email", seller.getEmail() != null ? seller.getEmail() : "seller@example.com");

		} else {


			request.setAttribute("shopName", "Seller");
			request.setAttribute("email", "seller@example.com");

		}

		String statusFilter = request.getParameter("status");

		if (statusFilter == null || statusFilter.isEmpty()) {


			statusFilter = "all";

		} else {


			statusFilter = statusFilter.toLowerCase();

		}

		try {


			int[] stats = orderDao.getOrderStats(sellerId);
			request.setAttribute("pendingCount", stats[0]);
			request.setAttribute("processingCount", stats[1]);
			request.setAttribute("shippedCount", stats[2]);
			request.setAttribute("deliveredCount", stats[3]);

			List<Order> orders = orderDao.getOrdersBySeller(sellerId, statusFilter);
			request.setAttribute("orders", orders);

			request.getRequestDispatcher("/WEB-INF/seller/view/orders.jsp").forward(request, response);

		} catch (SQLException e) {


			e.printStackTrace();
			request.setAttribute("errorMessage", "Unable to fetch orders. Please try again later.");
			request.getRequestDispatcher("/WEB-INF/seller/view/orders.jsp").forward(request, response);

		}

	}



	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("seller") == null) {


			response.sendRedirect(request.getContextPath() + "/logins");
			return;

		}

		int sellerId = (Integer) session.getAttribute("seller");
		String action = request.getParameter("action");

		if ("updateStatus".equals(action)) {


			try {


				int orderId = Integer.parseInt(request.getParameter("orderId"));
				String newStatus = request.getParameter("status");

				boolean ownsOrder = orderDao.isOrderOwnedBySeller(orderId, sellerId);

				if (!ownsOrder) {


					request.setAttribute("errorMessage", "Unauthorized to update this order.");
					doGet(request, response);
					return;

				}

				orderDao.updateOrderStatus(orderId, newStatus);
				response.sendRedirect(request.getContextPath() + "/orders");

			} catch (NumberFormatException | SQLException e) {


				e.printStackTrace();
				request.setAttribute("errorMessage", "Failed to update order status.");
				doGet(request, response);

			}

		} else {


			doGet(request, response);

		}

	}



}
