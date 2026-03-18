package com.centrum.servlet;

import java.io.IOException;

import com.centrum.dao.SellerDAO;
import com.centrum.model.Seller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Acoount/settings")
public class SettingsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
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

         Seller seller = sellerDAO.loginSellerById(sellerId);
        request.setAttribute("seller", seller);

        request.getRequestDispatcher("/WEB-INF/seller/view/settings.jsp").forward(request, response);
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

        String shopName = request.getParameter("shopName");
        String ownerName = request.getParameter("ownerName");
        String phone = request.getParameter("phone");

        String errorMessage = null;

        if (shopName == null || shopName.trim().isEmpty()) {
            errorMessage = "Shop name is required.";
        } else if (ownerName == null || ownerName.trim().isEmpty()) {
            errorMessage = "Owner name is required.";
        } 

        if (errorMessage != null) {
            request.setAttribute("error", errorMessage);
            doGet(request, response);
            return;
        }

        boolean success = sellerDAO.updateProfileDeatils(
                sellerId,
                shopName.trim(),
                ownerName.trim(),
                phone != null ? phone.trim() : null
        );

        if (success) {
            request.setAttribute("success", "Settings updated successfully.");
        } else {
            request.setAttribute("error", "Failed to update settings. Try again.");
        }

        Seller seller = sellerDAO.loginSellerById(sellerId);
        request.setAttribute("seller", seller);

        request.getRequestDispatcher("/WEB-INF/seller/view/settings.jsp").forward(request, response);
    }
}
