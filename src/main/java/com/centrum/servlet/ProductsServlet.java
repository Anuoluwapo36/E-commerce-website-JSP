

package com.centrum.servlet;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.centrum.bean.Product;
import com.centrum.dao.ProductDao;
import com.centrum.dao.SellerDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;




@WebServlet("/products/dashboard")
public class ProductsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private SellerDAO sellerDAO;
	private ProductDao productDao;



	@Override
	public void init() throws ServletException {

		sellerDAO = new SellerDAO();
		productDao = new ProductDao();

	}



	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("seller") == null) {


			response.sendRedirect(request.getContextPath() + "/logins");
			return;

		}

		int sellerId = (Integer) session.getAttribute("seller");

		String shopName = sellerDAO.getShopNameBySellerId(sellerId);

		if (shopName == null || shopName.isBlank()) {


			shopName = "Your Shop";

		}

		String email = sellerDAO.loginSellerById(sellerId).getEmail();

		request.setAttribute("shopName", shopName);
		request.setAttribute("email", email);

		try {


			List<Product> products = productDao.getProductsBySeller(sellerId);
			request.setAttribute("products", products);

			request.getRequestDispatcher("/WEB-INF/seller/view/products.jsp").forward(request, response);

		} catch (SQLException e) {


			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");

		}

	}



}
