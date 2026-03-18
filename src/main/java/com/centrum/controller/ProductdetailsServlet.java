package com.centrum.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.centrum.bean.Product;
import com.centrum.bean.ProductImages;
import com.centrum.dao.ProductDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProductdetailsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String BROWSING = "product-browsing.jsp";

	private ProductDao productDao;

	@Override
	public void init() throws ServletException {
		super.init();

		try {
			productDao = new ProductDao();
		} catch (Exception e) {

			throw new ServletException("failed to initilized productDao: ", e);

		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		productView(request, response);

		showViewDetails(request, response);

	}

	/**
	 * this add count to product view and this how we track which product users view
	 * the most and use it as global recommendation to existing user and new user
	 * 
	 * 
	 * in relation with recommendation system in product dao and view increase as
	 * well
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */

	public void productView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String productID = request.getParameter("product_id");

			if (productID == null) {
				response.sendRedirect(request.getContextPath() + "product-browsing.jsp");
				return;
			}
			int productId = Integer.parseInt(productID);

			productDao.incrementViewCount(productId);

		} catch (SQLException | NumberFormatException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Failed to connect to database. Please try again.");
			response.sendRedirect(request.getContextPath() + BROWSING + "?error=product_not_found");
			return;

		}

	}

	/**
	 * this show product image price description from product home page when the
	 * product is click he direct to product details page and the view are display
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */

	public void showViewDetails(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String productIdstring = request.getParameter("product_id");

			if (productIdstring == null || productIdstring.isEmpty()) {
				response.sendRedirect(request.getContextPath() + "product-browsing.jsp");
				return;
			}
			
			

			int productId = Integer.parseInt(productIdstring);
			Product product = productDao.getProductbyId(productId);

			if (product == null) {
				response.sendRedirect(request.getContextPath() + BROWSING + "?error=product_not_found");
				return;
			}
			request.setAttribute("productshow", product);

			List<ProductImages> productImageList = productDao.getAllProductImages(productId);
			
			request.setAttribute("productImageList", productImageList);
			
			List<Product> mayalsolike =productDao.mayalsoLike(productId);
			request.setAttribute("mayAlsoLikeProducts", mayalsolike);

			request.getRequestDispatcher("/buyer/productdetails.jsp").forward(request, response);
			return;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/product-browsing.jsp?error=invalid_product_id");
			return;

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Failed to connect to database. Please try again.");
			request.getRequestDispatcher("/product-browsing.jsp").forward(request, response);
			return;

		}

	}

	/**
	 * this increment view count but i stop using it cause it have issue with my
	 * cart system and i had to create new way to display recommendation pending
	 * method ( not been used )keep off ha ha ha it will serve me different way
	 * maybe for most purchase items buy
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
//	public void recommendationViewCount(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		String productId = request.getParameter("product_id");
//		System.out.println("Received product_id: " + productId);
//		if (productId == null || productId.isEmpty()) {
//			String contextPath = request.getContextPath();
//			response.sendRedirect(contextPath + BROWSING + "?error=invalid product id");
//			return;
//		}
//		try {
//			int product_id = Integer.parseInt(productId);
//			if (product_id <= 0) {
//				String contextPath = request.getContextPath();
//				response.sendRedirect(contextPath + BROWSING + "?error=invalid product id");
//				return;
//			}
//
//			ProductDao productDao = new ProductDao();
//			productDao.viewCount(product_id);
//
//		} catch (NumberFormatException e) {
//			String contextPath = request.getContextPath();
//			response.sendRedirect(contextPath + BROWSING + "?error=invalid product id");
//			e.printStackTrace();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//	}

	@Override
	public void destroy() {
		productDao = null;
	}

}
