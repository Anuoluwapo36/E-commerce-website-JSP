

package com.centrum.servlet;



import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;




@WebServlet("/verification/success")
public class VerificationSuccessServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;



	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("seller") == null) {


			response.sendRedirect(request.getContextPath() + "/register" + "?error=not a member");
			return;

		}

		response.setHeader("Refresh", "9; URL=" + request.getContextPath() + "/welcome/greeting");

		request.getRequestDispatcher("/WEB-INF/seller/view/verification-success.jsp").forward(request, response);

	}



}
