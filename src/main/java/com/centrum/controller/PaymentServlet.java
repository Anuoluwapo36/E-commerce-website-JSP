package com.centrum.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.centrum.bean.Order;
import com.centrum.bean.Payment;
import com.centrum.bean.User;
import com.centrum.dao.CartDao;
import com.centrum.dao.OrderDao;
import com.centrum.dao.PaymentDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class PaymentServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PaymentDao paymentDao;
	private OrderDao orderDao;
	private CartDao cartDao;

	private static final String LOGIN_PAGE = "/login-user.jsp";
	private static final String PAYMENT_METHOD_SERVLET = "/payment-method";

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try {
			paymentDao = new PaymentDao();
			orderDao = new OrderDao();
			cartDao = new CartDao();
		} catch (Exception e) {
			throw new ServletException("failed to initilize paymentDao: ", e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processPayment(request, response);
	}

	/**
	 * Processes a credit card payment for an order, including validation of user,
	 * order, and card details, then saves the payment and updates the order status.
	 * 
	 * <p>
	 * Steps include:
	 * <ul>
	 * <li>Checking if the user is logged in</li>
	 * <li>Validating required payment details (order ID, address, card info)</li>
	 * <li>Verifying card details (format, expiry, type match)</li>
	 * <li>Ensuring the order exists, belongs to the user, and isn't already
	 * paid</li>
	 * <li>Saving the payment record to the database</li>
	 * <li>Updating the order to "completed" and "paid" status</li>
	 * <li>Redirecting to a success page or error page if validation fails</li>
	 * </ul>
	 * 
	 * @param request  The HttpServletRequest containing form data (card details,
	 *                 order ID, etc.) and session data (logged-in user).
	 * @param response The HttpServletResponse used to redirect the user to
	 *                 success/error pages.
	 * @throws ServletException If there's an error in servlet processing (e.g., DAO
	 *                          initialization failed).
	 * @throws IOException      If there's an error redirecting the user (e.g.,
	 *                          invalid URL).
	 */

	public void processPayment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		String contextPath = request.getContextPath();
		String order_id = request.getParameter("order_id");
		int orderId = 0; 

		if (loggedInUser == null) {
			response.sendRedirect(contextPath + LOGIN_PAGE + "?error=Please login to continue");
			return;
		}

		if (order_id == null || !order_id.matches("\\d+")) {
			response.sendRedirect(contextPath + PAYMENT_METHOD_SERVLET + "?error=Invalid order ID");
			return;
		}
		orderId = Integer.parseInt(order_id);

		try {
			String cardNumberStr = request.getParameter("cardNumber");
			String expiryDateStr = request.getParameter("expiryDate");
			String cvvStr = request.getParameter("cvv");
			String cardHolderNameStr = request.getParameter("cardholderName");
			String cardTypes = request.getParameter("cardType");
			String cardMethod = request.getParameter("card");
			String addressStr = request.getParameter("address_id");

			if (cardNumberStr == null || cardNumberStr.trim().isEmpty() || expiryDateStr == null
					|| expiryDateStr.trim().isEmpty() || cvvStr == null || cvvStr.trim().isEmpty()
					|| cardHolderNameStr == null || cardHolderNameStr.trim().isEmpty() || cardTypes == null
					|| cardTypes.trim().isEmpty() || addressStr == null || addressStr.trim().isEmpty()
					|| cardMethod == null || cardMethod.trim().isEmpty()) {

				response.sendRedirect(contextPath + PAYMENT_METHOD_SERVLET + "?order_id=" + orderId
						+ "&error=Please fill all required fields");
				return;
			}

			if (!addressStr.matches("\\d+")) {
				response.sendRedirect(
						contextPath + PAYMENT_METHOD_SERVLET + "?order_id=" + orderId + "&error=Invalid address ID");
				return;
			}
			int addressId = Integer.parseInt(addressStr);

			if (!cardTypes.equals("1") && !cardTypes.equals("2")) {
				response.sendRedirect(contextPath + PAYMENT_METHOD_SERVLET + "?order_id=" + orderId
						+ "&error=Only Visa/Mastercard accepted");
				return;
			}

			if (!"creditCard".equals(cardMethod.trim())) {
				response.sendRedirect(contextPath + PAYMENT_METHOD_SERVLET + "?order_id=" + orderId
						+ "&error=Invalid payment method");
				return;
			}

			String cleanCardNumberStr = cardNumberStr.replaceAll("[^0-9]", "");
			if (cleanCardNumberStr.length() < 16 || cleanCardNumberStr.length() > 19) {
				response.sendRedirect(contextPath + PAYMENT_METHOD_SERVLET + "?order_id=" + orderId
						+ "&error=Card number must be 16-19 digits");
				return;
			}

			if (cardTypes.equals("1") && !cleanCardNumberStr.startsWith("4")) {
				response.sendRedirect(contextPath + PAYMENT_METHOD_SERVLET + "?order_id=" + orderId
						+ "&error=Invalid Visa card number");
				return;
			}
			if (cardTypes.equals("2") && !cleanCardNumberStr.startsWith("5")) {
				response.sendRedirect(contextPath + PAYMENT_METHOD_SERVLET + "?order_id=" + orderId
						+ "&error=Invalid Mastercard number");
				return;
			}

			if (!expiryDateStr.matches("^(0[1-9]|1[0-2])/[0-9]{2}$")) {
				response.sendRedirect(contextPath + PAYMENT_METHOD_SERVLET + "?order_id=" + orderId
						+ "&error=Expiry date must be MM/YY");
				return;
			}
			String[] expiryParts = expiryDateStr.split("/");
			int expiryMonth = Integer.parseInt(expiryParts[0]);
			int expiryYear = 2000 + Integer.parseInt(expiryParts[1]);
			java.time.LocalDate now = java.time.LocalDate.now();
			if (expiryYear < now.getYear() || (expiryYear == now.getYear() && expiryMonth < now.getMonthValue())) {
				response.sendRedirect(
						contextPath + PAYMENT_METHOD_SERVLET + "?order_id=" + orderId + "&error=Card is expired");
				return;
			}

			if (cvvStr.length() < 3 || cvvStr.length() > 4 || !cvvStr.matches("\\d+")) {
				response.sendRedirect(contextPath + PAYMENT_METHOD_SERVLET + "?order_id=" + orderId
						+ "&error=CVV must be 3-4 digits");
				return;
			}

			if (!cardHolderNameStr.trim().equalsIgnoreCase(loggedInUser.getFull_name())) {
				response.sendRedirect(contextPath + PAYMENT_METHOD_SERVLET + "?order_id=" + orderId
						+ "&error=Cardholder name does not match your name");
				return;
			}

			Order order = orderDao.getOrderById(orderId);
			if (order == null) {
				response.sendRedirect(
						contextPath + PAYMENT_METHOD_SERVLET + "?order_id=" + orderId + "&error=Order not found");
				return;
			}
			if (order.getUser_id() != loggedInUser.getUser_id()) {
				response.sendRedirect(contextPath + PAYMENT_METHOD_SERVLET + "?order_id=" + orderId
						+ "&error=You are not authorized to pay for this order");
				return;
			}
			if ("paid".equals(order.getPayment_status())) {
				response.sendRedirect(contextPath + PAYMENT_METHOD_SERVLET + "?order_id=" + orderId
						+ "&error=Order is already paid");
				return;
			}

			Payment payment = new Payment();
			payment.setOrder_id(orderId);
			payment.setUser_id(loggedInUser.getUser_id());
			payment.setCard_last4(cleanCardNumberStr.substring(cleanCardNumberStr.length() - 4));
			payment.setCard_expiry(expiryDateStr);
			payment.setCardholder_name(cardHolderNameStr);
			payment.setPayment_method(Integer.parseInt(cardTypes));
			payment.setPayment_date(new java.sql.Timestamp(System.currentTimeMillis()));
			payment.setAmount(order.getTotal_amount());
			payment.setPayment_status("paid");
			payment.setAddress_id(addressId);

			paymentDao.createPayment(payment);
			orderDao.markedOrderCompleted(orderId, Integer.parseInt(cardTypes));
			cartDao.deleteAllCartsByUser(loggedInUser.getUser_id());

			response.sendRedirect(contextPath + PAYMENT_METHOD_SERVLET + "?order_id=" + orderId + "&success=true");

		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.sendRedirect(contextPath + PAYMENT_METHOD_SERVLET + "?order_id=" + orderId
					+ "&error=Invalid numeric value. Try again.");
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect(contextPath + PAYMENT_METHOD_SERVLET + "?order_id=" + orderId
					+ "&error=Payment failed. Try again.");
		}

	}

}
