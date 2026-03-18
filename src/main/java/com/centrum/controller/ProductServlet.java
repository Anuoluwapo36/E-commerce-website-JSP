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

public class ProductServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProductDao productDao;

	@Override
	public void init() throws ServletException {
		super.init();
		productDao = new ProductDao();

	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ProductServlet handling request (Method: " + request.getMethod() + ")");
		super.service(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		populateHomePage(request, response);

	}

	/**
	 * this allow home page which is product browsing to display product from
	 * database
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void populateHomePage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Product> productList;
		try {
			productList = productDao.getAllProduct();

			for (Product p : productList) {
				int productId = p.getProduct_id();
				String primaryImageUrl = productDao.getPrimaryImageUrl(productId);

				if (primaryImageUrl != null) {
					p.setImage_url(primaryImageUrl);
				} else {
					p.setImage_url("/product/Image/centrum_default.svg");
				}
			}
		    List<Product> banner = productDao.getMostPurchasedWeekly();
		    request.setAttribute("banner", banner);

			request.getSession().setAttribute("productList", productList);
			request.getRequestDispatcher("/buyer/productbrowsing.jsp").forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("error", "Failed to load products");
			request.getRequestDispatcher("/buyer/productbrowsing.jsp").forward(request, response);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public void destroy() {
		super.destroy();
		System.out.println("productServlet destroyed");
	}

}
