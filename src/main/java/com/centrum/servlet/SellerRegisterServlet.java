package com.centrum.servlet;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import com.centrum.dao.SellerDAO;
import com.centrum.model.Seller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/register")
public class SellerRegisterServlet extends HttpServlet {

 
	private static final long serialVersionUID = 1L;
	private SellerDAO sellerDAO = new SellerDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String terms = request.getParameter("terms");

        if (email == null || password == null || confirmPassword == null || terms == null) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/WEB-INF/seller/view/register.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("/WEB-INF/seller/view/register.jsp").forward(request, response);
            return;
        }

        if (sellerDAO.isEmailExists(email)) {
            request.setAttribute("error", "This email is already registered. Please use another email.");
            request.getRequestDispatcher("/WEB-INF/seller/view/register.jsp").forward(request, response);
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        Seller seller = new Seller();
        seller.setEmail(email);
        seller.setPassword(hashedPassword);
        seller.setVerificationStatus("pending");
        seller.setIsActive(true);

        boolean created = sellerDAO.registerSeller(seller);

        if (created) {
            int sellerId = sellerDAO.getSellerIdByEmail(email);

            if (sellerId > 0) {
                HttpSession session = request.getSession();
                session.setAttribute("seller", sellerId);

                response.sendRedirect(request.getContextPath() + "/verify/information" + "?next=setup business information");
            } else {
                request.setAttribute("error", "Failed to retrieve your account ID. Please try again.");
                request.getRequestDispatcher("/WEB-INF/seller/view/register.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Failed to create account. Please try again.");
            request.getRequestDispatcher("/WEB-INF/seller/view/register.jsp").forward(request, response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/seller/view/register.jsp").forward(request, response);
    }
}
