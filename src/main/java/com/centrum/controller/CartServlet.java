

package com.centrum.controller;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.centrum.bean.Cart;
import com.centrum.bean.RecommendProduct;
import com.centrum.bean.User;
import com.centrum.dao.CartDao;
import com.centrum.dao.ProductDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;




public class CartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CartDao cartDao;
	private ProductDao productDao;
	private static final String LOGIN_PAGE = "/login-user.jsp";
	private static final String CART_SERVLET_URL = "/cart";
	private static final String CART_PAGE = "/buyer/cart.jsp";
	private static final String PRODUCT_DETAILS_PAGE = "/product-details";



	public void init() throws ServletException {

		try {


			cartDao = new CartDao();
			productDao = new ProductDao();

		} catch (Exception e) {


			e.printStackTrace();
			throw new ServletException("Failed to init CartDao", e);

		}

	}



	/**
	 * fetch data from database and display in the web page
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		globalRecommendation(request, response);

	}



	/**
	 * display all global recommendation base on there racking number
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */

	public void globalRecommendation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Cart> cartItems;

		try {


			HttpSession session = request.getSession();
			User loggedInUser = (User) session.getAttribute("loggedInUser");

			if (loggedInUser == null) {


				request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
				return;

			}

			cartItems = cartDao.getAllCart(loggedInUser.getUser_id());
			request.setAttribute("cartItems", cartItems);

			if (cartItems != null && !cartItems.isEmpty()) {


				cartItems.get(0).getProduct_id();

			}

			List<RecommendProduct> recommendations = null;
			recommendations = productDao.recommendationSystem();
			request.setAttribute("recommendations", recommendations);
			request.getRequestDispatcher(CART_PAGE).forward(request, response);

			return;

		} catch (SQLException e) {


			e.printStackTrace();
			request.setAttribute("errorMessage", "failed to load your cart.please try again later.");
			request.getRequestDispatcher(CART_PAGE).forward(request, response);
			return;

		}

	}



	/**
	 * do post to insert data into database hold all method
	 */

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");

		if ("updateCartQuantity".equals(action)) {


			updateCartQuantityFromCartPage(request, response);
			return;

		}

		String cartIdStr = request.getParameter("cart_id");

		if (cartIdStr != null && !cartIdStr.isEmpty()) {


			deleteItem(request, response);
			return;

		}

		if ("clearAll".equals(action)) {


			clearAllCart(request, response);
			return;

		}

		if ("checkoutAll".equals(action)) {


			checkoutAllItems(request, response);
			return;

		}

		handleAddOrUpdate(request, response);

	}



	/**
	 * Handle quantity updates from cart page (plus/minus buttons) Uses existing
	 * CartDao.updateCartQuantity method
	 */
	private void updateCartQuantityFromCartPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {


			response.getWriter().write("Error: Please login first");
			return;

		}

		String cartIdStr = request.getParameter("cart_id");
		String newQuantityStr = request.getParameter("new_quantity");

		if (cartIdStr == null || cartIdStr.isEmpty() || newQuantityStr == null || newQuantityStr.isEmpty()) {


			response.getWriter().write("Error: Missing cart ID or quantity");
			return;

		}

		int cartId;
		int newQuantity;

		try {


			cartId = Integer.parseInt(cartIdStr);
			newQuantity = Integer.parseInt(newQuantityStr);

		} catch (NumberFormatException e) {


			response.getWriter().write("Error: Invalid cart ID or quantity");
			return;

		}

		if (newQuantity < 1) {


			response.getWriter().write("Error: Quantity must be at least 1");
			return;

		}

		try {


			cartDao.updateCartQuantity(cartId, newQuantity);
			response.getWriter().write("Success: Quantity updated");

		} catch (SQLException e) {


			e.printStackTrace();
			response.getWriter().write("Error: Failed to update quantity");

		}

	}



	/**
	 * allow user to add cart and add more to there unit from product details and
	 * redirect to cart page and increase the database add cart and the quantity
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */

	private void handleAddOrUpdate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");

		String productIdStr = request.getParameter("product_id");
		String quantityStr = request.getParameter("quantity");

		if (productIdStr == null || productIdStr.isEmpty() || quantityStr == null || quantityStr.isEmpty()) {


			String contextPath = request.getContextPath();
			response.sendRedirect(
					contextPath + PRODUCT_DETAILS_PAGE + "?product_id=" + productIdStr + "&error=missing_fields");
			return;

		}

		int productId;
		int quantity;

		try {


			productId = Integer.parseInt(productIdStr);
			quantity = Integer.parseInt(quantityStr);

		} catch (NumberFormatException e) {


			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + PRODUCT_DETAILS_PAGE + "?error=invalid_numbers");
			return;

		}

		if (loggedInUser == null) {


			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + LOGIN_PAGE);
			return;

		}

		int user_id = loggedInUser.getUser_id();

		Cart existingCart = null;

		try {


			existingCart = cartDao.findCartItems(user_id, productId);

		} catch (SQLException e) {


			// TODO Auto-generated catch block
			e.printStackTrace();
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + PRODUCT_DETAILS_PAGE + "?error=try again");
			return;

		}

		if (existingCart != null) {


			int newTotalquanitity = existingCart.getQuantity() + quantity;

			try {


				cartDao.updateCartQuantity(existingCart.getCart_id(), newTotalquanitity);
				System.out.println("Existing Cart Found? "
						+ (existingCart != null ? "Yes (ID: " + existingCart.getCart_id() + ")" : "No"));

				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath + CART_SERVLET_URL + "?success=updated");
				return;

			} catch (SQLException e) {


				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath + PRODUCT_DETAILS_PAGE + "?error=update_failed");
				return;

			}

		} else {


			Cart cart_item = new Cart();
			cart_item.setUser_id(user_id);
			cart_item.setProduct_id(productId);
			cart_item.setQuantity(quantity);
			cart_item.setCart_status("active");
			cart_item.setAdded_at(new java.sql.Timestamp(System.currentTimeMillis()));

			String contextPath = request.getContextPath();

			try {


				cartDao.addCart(cart_item);

				String redirectUrl = contextPath + CART_SERVLET_URL + "?success=you add new carts";
				response.sendRedirect(response.encodeRedirectURL(redirectUrl));
				return;

			} catch (SQLException e) {


				e.printStackTrace();

			}

		}

	}



	/**
	 * this allow a single product to be deleted if user wish not to keep the items
	 * anymore
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");

		if (loggedInUser == null) {


			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + LOGIN_PAGE + "?error=please login first");
			return;

		}

		String cart_idstr = request.getParameter("cart_id");
		int cart_id;

		if (cart_idstr == null || cart_idstr.isEmpty()) {


			String contextPath = request.getContextPath();

			response.sendRedirect(contextPath + CART_SERVLET_URL + "?error=invalid delete request");
			return;

		}

		try {


			cart_id = Integer.parseInt(cart_idstr);

			cartDao.deleteCartById(cart_id);
			String contextPath = request.getContextPath();

			response.sendRedirect(contextPath + CART_SERVLET_URL + "?success=items deleted");
			return;

		} catch (NumberFormatException e) {


			e.printStackTrace();
			String contextPath = request.getContextPath();

			response.sendRedirect(contextPath + CART_SERVLET_URL + "?error=invalid cart ");
			return;

		} catch (SQLException e) {


			e.printStackTrace();
			String contextPath = request.getContextPath();

			response.sendRedirect(contextPath + CART_SERVLET_URL + "?error=invalid delete request.");
			return;

		}

	}



	/**
	 * this track login user and use the login session to known which cart will be
	 * clear out and only logged in user can clear cart it avoid current login user
	 * clearing other user cart
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void clearAllCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");

		if (loggedInUser == null) {


			String contextPath = request.getContextPath();

			response.sendRedirect(contextPath + LOGIN_PAGE);
			return;

		}

		String isChecked = request.getParameter("clear_all");

		if (isChecked == null || !isChecked.equals("on")) {


			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + CART_SERVLET_URL + "?error=select check box clear all");
			return;

		}

		try {


			int userId = loggedInUser.getUser_id();

			int rowDeleted = cartDao.deleteAllCartsByUser(userId);
			String feedback;

			if (rowDeleted > 0) {


				feedback = "success=cart cleared! " + rowDeleted + "  items removed";

			} else {


				feedback = "info= your cart was already empty";

			}

			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + CART_SERVLET_URL + "?" + feedback);
			return;

		} catch (SQLException e) {


			e.printStackTrace();
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + CART_SERVLET_URL + "?error= unable delete");
			return;

		}

	}



	private void checkoutAllItems(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {


			response.sendRedirect(request.getContextPath() + LOGIN_PAGE + "?error=please login first");
			return;

		}

		try {


			List<Cart> activeCart = cartDao.getActiveCartItemsByUser(loggedInUser.getUser_id());

			if (activeCart == null || activeCart.isEmpty()) {


				response.sendRedirect(request.getContextPath() + CART_SERVLET_URL + "?info=your cart is empty");
				return;

			}

			request.setAttribute("cartItems", activeCart);
			request.getRequestDispatcher("/buyer/confirmation.jsp").forward(request, response);

		} catch (SQLException e) {


			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + CART_SERVLET_URL + "?error=failed to load cart items");

		}

	}



	public void destroy() {

		cartDao = null;

	}



}
