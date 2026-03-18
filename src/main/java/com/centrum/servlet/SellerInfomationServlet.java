

package com.centrum.servlet;



import java.io.IOException;

import com.centrum.dao.SellerDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;




@WebServlet("/verify/information")
public class SellerInfomationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SellerDAO sellerDAO = new SellerDAO();



	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("seller") == null) {


			response.sendRedirect(request.getContextPath() + "/register");
			return;

		}

		request.getRequestDispatcher("/WEB-INF/seller/view/seller-details.jsp").forward(request, response);

	}



	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("seller") == null) {


			response.sendRedirect(request.getContextPath() + "/register");
			return;

		}

		request.setCharacterEncoding("UTF-8");

		try {

            int sellerId = (int) session.getAttribute("seller");

			String shopName = request.getParameter("shopName");
			String shopType = request.getParameter("shopType");
			String ownerName = request.getParameter("name");
			String phone = request.getParameter("phone");

			String sellerIdStr = request.getParameter("sellerId");

			if (sellerIdStr == null || sellerIdStr.isEmpty()) {


				request.setAttribute("error", "can't complete , Please start registration again.");
				request.getRequestDispatcher("/WEB-INF/seller/view/seller_step2.jsp").forward(request, response);
				return;

			}


			if (!sellerDAO.isSellerIdExists(sellerId)) {


				request.setAttribute("error", "Invalid seller ID. Please start registration again.");
				request.getRequestDispatcher("/WEB-INF/seller/view/seller_step2.jsp").forward(request, response);
				return;

			}

			boolean updated = sellerDAO.updateShopDetails(sellerId, shopName, shopType, ownerName, phone);

			if (updated) {


				response.sendRedirect(request.getContextPath() + "/verify/identity?sellerId=" + sellerId +"?verification=upload path verifivation protected ");

			} else {


				request.setAttribute("error", "Failed to update shop details. Please try again.");
				request.getRequestDispatcher("/WEB-INF/seller/view/seller-details.jsp").forward(request, response);

			}

		} catch (Exception e) {


			e.printStackTrace();
			request.setAttribute("error", "An unexpected error occurred. Please try again.");
			request.getRequestDispatcher("/WEB-INF/seller/view/seller-details.jsp").forward(request, response);

		}

	}



}
