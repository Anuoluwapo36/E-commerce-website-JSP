package com.centrum.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.centrum.bean.Product;
import com.centrum.bean.User;
import com.centrum.dao.ProductDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class BannerServlet extends HttpServlet{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ProductDao productDao;
	private static final String LOGIN_PAGE = "/login-user.jsp";
	private static final String BROWSING = "/buyer/productbrowsing.jsp";
	
	
@Override
public void init() throws ServletException {
	// TODO Auto-generated method stub
	super.init();
	try {
		productDao = new ProductDao();
	}catch(Exception e) {
		throw new ServletException ("failed to initilize productDao for banner:" , e);
	}
	
	
}

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
	adsBannerTopSales(request, response);
}


private void adsBannerTopSales(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	HttpSession session = request.getSession();
	User loggedInUser = (User) session.getAttribute("loggedInUser");
	if(loggedInUser == null) {
		String contextPath = request.getContextPath();
		response.sendRedirect(contextPath  + LOGIN_PAGE + "?error=Account not found");
		return;
	}
	
	try {
		List<Product> banner = productDao.getMostPurchasedWeekly();
		
		request.setAttribute("banner", banner);
		System.out.println("banner product" + banner);
		
		request.getRequestDispatcher(BROWSING).forward(request, response);
		
		
	} catch (SQLException e) {
		e.printStackTrace();
		request.setAttribute("error", "Failed to load products");
		request.getRequestDispatcher("/error.jsp").forward(request, response);
	}
}

}
