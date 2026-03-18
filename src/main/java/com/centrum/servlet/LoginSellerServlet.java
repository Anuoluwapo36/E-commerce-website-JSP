package com.centrum.servlet;

import java.io.IOException;
import java.util.regex.Pattern;

import com.centrum.dao.SellerDAO;
import com.centrum.model.Seller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logins")
public class LoginSellerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private SellerDAO sellerDAO;

    @Override
    public void init() throws ServletException {
        sellerDAO = new SellerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("seller_email".equals(cookie.getName())) {
                    request.setAttribute("rememberedEmail", cookie.getValue());
                }
            }
        }

        request.getRequestDispatcher("/WEB-INF/seller/view/seller-login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String error = null;
        if (email == null || email.trim().isEmpty()) {
            error = "Email is required";
        } else if (!isValidEmail(email)) {
            error = "Invalid email format";
        } else if (password == null || password.trim().isEmpty()) {
            error = "Password is required";
        } else if (password.length() < 6) {
            error = "Password must be at least 6 characters long";
        }

        if (error != null) {
            request.setAttribute("error", error);
            request.getRequestDispatcher("/WEB-INF/seller/view/seller-login.jsp").forward(request, response);
            return;
        }

        Seller seller = sellerDAO.loginSeller(email, password);

        if (seller != null) {
            HttpSession session = request.getSession();
            session.setAttribute("seller", seller.getSellerId());

            if ("on".equals(request.getParameter("remember"))) {
                Cookie emailCookie = new Cookie("seller_email", email);
                emailCookie.setMaxAge(7 * 24 * 60 * 60); 
                emailCookie.setPath(request.getContextPath());
                response.addCookie(emailCookie);
            } else {
                Cookie emailCookie = new Cookie("seller_email", "");
                emailCookie.setMaxAge(0);
                emailCookie.setPath(request.getContextPath());
                response.addCookie(emailCookie);
            }

            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            boolean exists = sellerDAO.isEmailExists(email);
            if (exists) {
                request.setAttribute("error", "Your account is not active yet. Please contact support.");
            } else {
                request.setAttribute("error", "Invalid email or password");
            }
            request.getRequestDispatcher("/WEB-INF/seller/view/seller-login.jsp").forward(request, response);
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        return Pattern.matches(emailRegex, email);
    }
}
