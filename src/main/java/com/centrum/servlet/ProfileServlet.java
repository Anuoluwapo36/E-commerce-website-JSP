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

@WebServlet("/Account/profile")
public class ProfileServlet extends HttpServlet {

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

        request.getRequestDispatcher("/WEB-INF/seller/view/profile.jsp").forward(request, response);
    }

    
}
