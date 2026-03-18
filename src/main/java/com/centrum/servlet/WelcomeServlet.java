

package com.centrum.servlet;



import java.io.IOException;

import com.centrum.dao.SellerDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;




@WebServlet("/welcome/greeting")
public class WelcomeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SellerDAO sellerDAO;



	@Override
	public void init() throws ServletException {

		sellerDAO = new SellerDAO();

	}



	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("seller") == null) {


			response.sendRedirect(request.getContextPath() + "/register");
			return;

		}

		int sellerId = (int) session.getAttribute("seller");

		String shopName = sellerDAO.getShopNameBySellerId(sellerId);

		if (shopName == null || shopName.isBlank()) {


			shopName = "Your Shop";

		}

		request.setAttribute("shopName", shopName);
		response.setHeader("Refresh", "9; URL=" + request.getContextPath() + "/dashboard");

		request.getRequestDispatcher("/WEB-INF/seller/view/welcome.jsp").forward(request, response);

	}



}
