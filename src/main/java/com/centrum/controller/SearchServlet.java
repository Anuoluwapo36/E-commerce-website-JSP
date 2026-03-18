package com.centrum.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.centrum.bean.Product;
import com.centrum.dao.ProductDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ProductDao productDao;

	@Override
	public void init() throws ServletException {

		try {
			productDao = new ProductDao();

		} catch (Exception e) {
			throw new ServletException("failed to initialize ProductDao", e);
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String query = request.getParameter("query");

		if (query == null || query.isEmpty() || query.isBlank() || query.length() < 4) {
			request.setAttribute("message", "please enter a search name");
			request.getRequestDispatcher("/buyer/search-product.jsp").forward(request, response);
			return;

		}
		List<Product> productFetch;
		try {
			productFetch = productDao.searchProduct(query);
			request.setAttribute("productFetch", productFetch);
			if (productFetch.isEmpty()) {
				request.setAttribute("message", "No product found for: " + query);
			}
			
			for (Product p : productFetch) {
				int productId = p.getProduct_id();
				String primaryImageUrl = productDao.getPrimaryImageUrl(productId);

				if (primaryImageUrl != null) {
					p.setImage_url(primaryImageUrl);
				} else {
					p.setImage_url("/product/Image/centrum_default.svg");
				}
			}

			request.getRequestDispatcher("/buyer/search-product.jsp").forward(request, response);
			return;

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("error", "search failed. try again.");
			request.getRequestDispatcher("/buyer/search-proudct.jsp").forward(request, response);
			return;
		}

	}

	@Override
	public void destroy() {
		productDao = null;
	}
}