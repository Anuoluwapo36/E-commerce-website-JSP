package com.centrum.servlet;

import com.centrum.bean.Order;
import com.centrum.bean.Product;
import com.centrum.dao.SellerDashboardDAO;
import com.centrum.dao.SellerDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SellerDashboardDAO dashboardDAO = new SellerDashboardDAO();
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

        String shopName = sellerDAO.getShopNameBySellerId(sellerId);
        if (shopName == null || shopName.isBlank()) {
            shopName = "Your Shop";
        }
        String email = sellerDAO.loginSellerById(sellerId).getEmail();

        request.setAttribute("shopName", shopName);
        request.setAttribute("email", email);

        try {
            int totalOrders = dashboardDAO.getTotalOrders(sellerId);
            double totalRevenue = dashboardDAO.getTotalRevenue(sellerId);
            int totalProducts = dashboardDAO.getTotalProducts(sellerId);

            List<Order> recentOrders = dashboardDAO.getRecentOrders(sellerId, 5);
            List<Product> lowStockProducts = dashboardDAO.getLowStockProducts(sellerId, 5);

            request.setAttribute("totalOrders", totalOrders);
            request.setAttribute("totalRevenue", totalRevenue);
            request.setAttribute("totalProducts", totalProducts);
            request.setAttribute("recentOrders", recentOrders);
            request.setAttribute("lowStockProducts", lowStockProducts);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to load dashboard: " + e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/seller/view/dashboard.jsp").forward(request, response);
    }
}
