package com.centrum.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.centrum.bean.Product;
import com.centrum.bean.ProductImages;
import com.centrum.bean.User;
import com.centrum.bean.User_address;
import com.centrum.dao.ConfirmDao;
import com.centrum.dao.ProductDao;
import com.centrum.util.AddressService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ConfirmServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConfirmDao confirmDao;
	private ProductDao productDao;
	private static final String PRODUCT_DETAILS_PAGE = "/product-details.jsp";
	private static final String CONFRIM_PAGE = "/buyer/confirmation.jsp";
	private static final String LOGIN_PAGE = "/login-user.jsp";

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try {
			confirmDao = new ConfirmDao();
			productDao = new ProductDao();

		} catch (Exception e) {
			throw new ServletException("failed to initialize ConfirmDao: ", e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {

			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + LOGIN_PAGE + "?error=error logged in user");
			return;
		}

		try {
			String productID = request.getParameter("product_id");

			if (productID == null) {
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath + PRODUCT_DETAILS_PAGE + "?error=Please log in to continue");
				return;
			}

			int product_id = Integer.parseInt(productID);
			Product product = confirmDao.getProductbyId(product_id);

			if (product == null) {
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath + PRODUCT_DETAILS_PAGE + "?error=Product ID is required");
				return;
			}
			List<ProductImages> productImage = productDao.getAllProductImages(product_id);

			request.setAttribute("productImage", productImage);
			request.setAttribute("product", product);

			String quantityStr = request.getParameter("quantity");
			int quantity = 1;

			if (quantityStr != null && !quantityStr.isEmpty()) {
				try {
					quantity = Integer.parseInt(quantityStr);
					quantity = Math.max(1, Math.min(quantity, product.getStock_quantity()));
				} catch (NumberFormatException e) {
				}
			}
			
			User_address address = AddressService.getDefaultAddress(loggedInUser);
			
			request.setAttribute("address", address);

			request.setAttribute("quantity", quantity);

			request.getRequestDispatcher(CONFRIM_PAGE).forward(request, response);

			return;

		} catch (SQLException | NumberFormatException e) {
			e.printStackTrace();
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + PRODUCT_DETAILS_PAGE + "?error=Invalid product ID");
			return;
		}

	}
	


}
