

package com.centrum.servlet;



import com.centrum.dao.ReportDAO;
import com.centrum.dao.SellerDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;




@WebServlet("/reports")
public class ReportServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ReportDAO reportDAO = new ReportDAO();
	private SellerDAO sellerDAO = new SellerDAO();



	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("seller") == null) {


			response.sendRedirect(request.getContextPath() + "/logins");
			return;

		}

		int sellerId = (Integer) session.getAttribute("seller");

		try {


			double totalRevenue = reportDAO.getTotalRevenue(sellerId);
			int totalOrders = reportDAO.getTotalOrders(sellerId);
			double avgOrderValue = reportDAO.getAverageOrderValue(sellerId);
			Map<String, Double> revenueByCategory = reportDAO.getRevenueByCategory(sellerId);
			Map<String, Integer> orderStatus = reportDAO.getOrderStatusDistribution(sellerId);
			Map<String, Double> topProducts = reportDAO.getTopProducts(sellerId, 5);

			String shopName = sellerDAO.getShopNameBySellerId(sellerId);

			if (shopName == null || shopName.isBlank()) {


				shopName = "Your Shop";

			}

			String email = sellerDAO.loginSellerById(sellerId).getEmail();

			request.setAttribute("totalRevenue", totalRevenue);
			request.setAttribute("totalOrders", totalOrders);
			request.setAttribute("avgOrderValue", avgOrderValue);
			request.setAttribute("revenueByCategory", revenueByCategory);
			request.setAttribute("orderStatus", orderStatus);
			request.setAttribute("topProducts", topProducts);
			request.setAttribute("shopName", shopName);
			request.setAttribute("email", email);

			request.getRequestDispatcher("/WEB-INF/seller/view/report.jsp").forward(request, response);

		} catch (SQLException e) {


			e.printStackTrace();
			request.setAttribute("error", "Failed to load report: " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/seller/view/report.jsp").forward(request, response);

		}

	}



}
