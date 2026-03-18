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

@WebServlet("/profile/password")
public class ChangePasswordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private SellerDAO sellerDAO = new SellerDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("seller") == null) {
            response.sendRedirect(request.getContextPath() + "/logins");
            return;
        }

        int sellerId = (Integer) session.getAttribute("seller");

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        String message = null;

        if (currentPassword == null || currentPassword.isEmpty() ||
            newPassword == null || newPassword.isEmpty() ||
            confirmPassword == null || confirmPassword.isEmpty()) {
            message = "All password fields are required.";
        } else if (!newPassword.equals(confirmPassword)) {
            message = "New password and confirm password do not match.";
        }

        if (message != null) {
            request.setAttribute("error", message);
            request.getRequestDispatcher("/WEB-INF/seller/view/profile.jsp").forward(request, response);
            return;
        }

        boolean success = sellerDAO.changePassword(sellerId, currentPassword, newPassword);

        if (success) {
            request.setAttribute("success", "Password changed successfully.");
            Seller seller = sellerDAO.loginSellerById(sellerId);
            request.setAttribute("seller", seller);

        } else {
            request.setAttribute("error", "Current password is incorrect.");
        }

        request.getRequestDispatcher("/WEB-INF/seller/view/profile.jsp").forward(request, response);
    }
}
