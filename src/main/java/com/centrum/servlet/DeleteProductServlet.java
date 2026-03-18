

package com.centrum.servlet;



import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.centrum.dao.ProductDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;




@WebServlet("/products/delete")
public class DeleteProductServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ProductDao productDao;



	@Override
	public void init() throws ServletException {

		productDao = new ProductDao();

	}



	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("seller") == null) {


			response.sendRedirect(request.getContextPath() + "/logins");
			return;

		}

		int sellerId = (Integer) session.getAttribute("seller");
		String productIdStr = request.getParameter("productId");

		if (productIdStr == null || productIdStr.isEmpty()) {


			response.sendRedirect(request.getContextPath() + "/products/dashboard");
			return;

		}

		int productId = Integer.parseInt(productIdStr);

		try {


			List<String> imagePaths = productDao.getAllImagePaths(productId);

			boolean deleted = productDao.deleteProductWithImages(productId, sellerId);

			if (deleted) {


				for (String path : imagePaths) {


					File file = new File(getServletContext().getRealPath("/") + path);

					if (file.exists()) {


						file.delete();

					}

				}

				response.sendRedirect(request.getContextPath() + "/products/dashboard?success=deleted");

			} else {


				response.sendRedirect(request.getContextPath() + "/products/dashboard?error=notfound");

			}

		} catch (SQLException e) {


			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/products/dashboard?error=db");

		}

	}



}
