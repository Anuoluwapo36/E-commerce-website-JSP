package com.centrum.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.centrum.bean.Order;
import com.centrum.bean.Order_items;
import com.centrum.bean.Product;
import com.centrum.bean.User;
import com.centrum.bean.User_address;
import com.centrum.dao.AddressDao;
import com.centrum.dao.ConfirmDao;
import com.centrum.dao.OrderDao;
import com.centrum.dao.OrderItemsDao;
import com.centrum.dao.ProductImageDao;
import com.centrum.util.AddressService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class PaymentMethodServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONFIRMATION_PAGE = "/buyer/confirmation.jsp";

	private static final String LOGIN_PAGE = "/login-user.jsp";
	private static final String PAYMENT_METHOD = "/buyer/payment-method.jsp";
	private static final String PAYMENT_METHOD_SERVLET = "/payment-method";

	private OrderDao orderDao;
	private OrderItemsDao orderitemsDao;
	private ConfirmDao confirmDao;
	private AddressDao addressDao;
	private ProductImageDao productImageDao;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try {
			orderDao = new OrderDao();
			orderitemsDao = new OrderItemsDao();
			confirmDao = new ConfirmDao();
			addressDao = new AddressDao();
			productImageDao = new ProductImageDao();

		} catch (Exception e) {
			throw new ServletException("failed to initialize: ", e);

		}

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		getSummaryOrder(request, response);
	}

	/**
	 * Loads all required data for the payment method page and forwards the request
	 * to the payment JSP.
	 * 
	 * This method verifies the user is logged in, fetches the order (via order_id
	 * from the URL), retrieves the order's line items, and gets the user's default
	 * address. All data is stored in the request object so the payment JSP can
	 * display order details, items, and address.
	 * 
	 * @param request  HttpServletRequest object to: - Access session (logged-in
	 *                 user), URL parameters (order_id), and set attributes (order,
	 *                 order_items, address). - Forward to the payment JSP with
	 *                 populated data.
	 * @param response HttpServletResponse object to: - Redirect unauthenticated
	 *                 users to login, or users with invalid order_id to the payment
	 *                 page with errors. - Handle redirects for error cases (e.g.,
	 *                 database failures).
	 * @throws ServletException If there's an error in servlet processing (e.g.,
	 *                          failed to forward to JSP).
	 * @throws IOException      If there's an input/output error (e.g., failed to
	 *                          redirect the user).
	 */
	public void getSummaryOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		
		

		if (loggedInUser == null) {
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + LOGIN_PAGE + "?error=login to continue");
			return;
		}
		
		
		

		String orderIDStr = request.getParameter("order_id");
		if (orderIDStr == null) {
			request.setAttribute("error", "Order ID is missing. Please select an order to pay for.");
			request.getRequestDispatcher(PAYMENT_METHOD).forward(request, response);
			return;
		}
		
		
		
		if (orderIDStr == null || orderIDStr.trim().isEmpty()) {
		    request.setAttribute("error", "Order ID is missing. Please select an order to pay for.");
		    request.getRequestDispatcher(PAYMENT_METHOD).forward(request, response);
		    return;
		}

		int orderId;
		try {
		    orderId = Integer.parseInt(orderIDStr.trim());
		} catch (NumberFormatException e) {
		    request.setAttribute("error", "Invalid order ID.");
		    request.getRequestDispatcher(PAYMENT_METHOD).forward(request, response);
		    return;
		}
		try {
			
			
			

			Order order = orderDao.getOrderById(orderId);
			List<Order_items> orderItems = orderitemsDao.getOrderItemsByOrderId(orderId);
			
			

			if (order == null) {
				request.setAttribute("error", "Invalid order.");
				request.getRequestDispatcher(PAYMENT_METHOD).forward(request, response);
				return;
			}
			
			
			
			

			if (orderItems == null) {
				request.setAttribute("error", "Order items not found.");
				request.getRequestDispatcher(PAYMENT_METHOD).forward(request, response);
				return;
			}
			
			
			
			
			

			if (order.getUser_id() != loggedInUser.getUser_id()) {
				request.setAttribute("error", "Invalid order.");
				request.getRequestDispatcher(PAYMENT_METHOD).forward(request, response);
				return;
			}
			
			
			
			

			User_address address = AddressService.getDefaultAddress(loggedInUser);

			request.setAttribute("order", order);
			request.setAttribute("address", address);
			request.setAttribute("order_items", orderItems);
			request.setAttribute("redirectUrl", "payment-method");
			request.setAttribute("fallbackUrl", "payment-method");
			
			
			

			
			
			
			

			if (address == null && orderItems != null && !orderItems.isEmpty()) {
			    Order_items firstItem = orderItems.get(0);
			    request.setAttribute("product_id", firstItem.getProduct_id());
			    request.setAttribute("quantity", firstItem.getQuantity());
			    request.setAttribute("total", order.getTotal_amount());
			    request.getRequestDispatcher(CONFIRMATION_PAGE).forward(request, response);
			    return;
			}


			
			
			request.setAttribute("success", "Address saved successfully!");
			request.setAttribute("order_id", request.getParameter("order_id"));
			request.getRequestDispatcher(PAYMENT_METHOD).forward(request, response);
			
			
			
			

		} catch (SQLException | NumberFormatException e) {
			e.printStackTrace();
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + PAYMENT_METHOD + "?error=invalid try again");
		}
	}

	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		createOrder(request, response);
	}

	/**
	 * 
	 * this is for creating order from confirmation page once user confirm the
	 * product they want to buy and press payment this save the order information
	 * into the database the order go to order table and order item table
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */

	private void createOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User LoggedInUser = (User) session.getAttribute("loggedInUser");
		if (LoggedInUser == null) {
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + LOGIN_PAGE + "?error= please login to continue");
			return;
		}

		try {

			String productIdStr = request.getParameter("product_id");
			String quantityStr = request.getParameter("quantity");

			String totalStr = request.getParameter("total");

			if (productIdStr == null || productIdStr.trim().isEmpty() || quantityStr == null
					|| quantityStr.trim().isEmpty() || totalStr == null || totalStr.trim().isEmpty()) {
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath + CONFIRMATION_PAGE + "?error=Missing order details. Try again.");
				return;
			}

			int product_id = Integer.parseInt(productIdStr);
			int quantity = Integer.parseInt(quantityStr);
			double total = Double.parseDouble(totalStr);

			if (product_id < 0 || quantity < 1 || total < 0) {
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath + CONFIRMATION_PAGE + "?error= invalid confirmation of order");
				return;

			}
			Product product = confirmDao.getProductbyId(product_id);
			if (product == null) {
				String contextPath = request.getContextPath();

				response.sendRedirect(contextPath + CONFIRMATION_PAGE + "?error=product not found");
				return;
			}

			if (product.getStock_quantity() < quantity) {
				String contextPath = request.getContextPath();

				response.sendRedirect(contextPath + CONFIRMATION_PAGE + "?error=Not enough stock");
				return;
			}
			String orderStatus = "pending";
			String unpaid = "pending";
			String payment = "not selected";
			Order orders = new Order();
			User_address address = addressDao.getAddress(LoggedInUser.getUser_id());

			if (address == null) {
				request.setAttribute("product", product);
				request.setAttribute("productImage", productImageDao.getImagesProductId(product_id));
				request.setAttribute("quantity", quantity);
				request.setAttribute("total", total);
				request.setAttribute("error", "Please add a default address before proceeding to payment!");

				request.getRequestDispatcher(CONFIRMATION_PAGE).forward(request, response);
				return;
			}

			orders.setUser_id(LoggedInUser.getUser_id());
			orders.setTotal_amount(total);
			orders.setStatus(orderStatus);
			orders.setAddress_id(address.getAddress_id());
			orders.setPayment_method(payment);
			orders.setPayment_status(unpaid);
			orders.setCreate_at(new Timestamp(System.currentTimeMillis()));
			int order_id = orderDao.createOrder(orders);
			/**
			 * save to order items
			 */
			Order_items orderItems = new Order_items();
			orderItems.setOrder_id(order_id);
			orderItems.setProduct_id(product_id);
			orderItems.setQuantity(quantity);
			orderItems.setPrice(product.getPrice());
			orderitemsDao.createOrderItems(orderItems);
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + PAYMENT_METHOD_SERVLET + "?order_id=" + order_id);
			return;

		} catch (SQLException | NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendRedirect(CONFIRMATION_PAGE + "?error= can't proccess now try again later");
			return;
		}

	}

}
