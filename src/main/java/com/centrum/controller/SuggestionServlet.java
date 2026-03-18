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
import com.google.gson.Gson;

public class SuggestionServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProductDao productDao;

	@Override
	public void init() throws ServletException {
		super.init();

		try {
			productDao = new ProductDao();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("failed to initialized produtDao for suggestion", e);
		}

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String query = request.getParameter("query");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		List<Product> suggestion;
		try {
			suggestion = productDao.getProductNameSuggestion(query);
			if (suggestion == null || suggestion.isEmpty()) {
				response.getWriter().write("[]");
				return;
			}

			Gson gson = new Gson();
			String jsSuggestion = gson.toJson(suggestion);
			response.getWriter().write(jsSuggestion);
			return;

		} catch (SQLException e) {
			e.printStackTrace();
			response.getWriter().write("[]");

		}

	}

	@Override
	public void destroy() {
		productDao = null;
	}

}
