

package com.centrum.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.centrum.bean.Order;
import com.centrum.bean.Order_items;
import com.centrum.bean.Product;
import com.centrum.bean.ProductImages;
import com.centrum.bean.User;
import com.centrum.connections.MySQLcnn;




public class OrderDao {

	/**
	 * gateway to order database to create order table and order item table this
	 * save all the relevant order coming from the confirmation page base on user
	 * desire product to buy the user id of logged in user, total amount status of
	 * the order if it complete or not shipping address payment method base on how
	 * user choose to settle payment , the payment method if it failed or successful
	 * and the date the order was created
	 * 
	 * @param order order table in database
	 * @return
	 * @throws SQLException throw SQL exception is something goes wrong and let user
	 *                      known the error this talk to database and make the and
	 *                      point out what cause the error
	 */

	public int createOrder(Order order) throws SQLException {

		String sql = "INSERT INTO orders (user_id,total_amount, status,address_id, payment_method, payment_status, create_at) "
				+ " VALUES(?,?,?,?,?,?,?)";
		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


			statement.setInt(1, order.getUser_id());
			statement.setDouble(2, order.getTotal_amount());
			statement.setString(3, order.getStatus());
			statement.setInt(4, order.getAddress_id());
			statement.setString(5, order.getPayment_method());
			statement.setString(6, order.getPayment_status());
			statement.setTimestamp(7, order.getCreate_at());

			statement.executeUpdate();

			try (ResultSet generatedkeys = statement.getGeneratedKeys()) {


				if (generatedkeys.next()) {


					return generatedkeys.getInt(1);

				} else {


					throw new SQLException("creating order failed: no ID obtained");

				}

			}

		}

	}



	/**
	 * Gets all the details of a specific order from the database using its ID.
	 * 
	 * @param orderID the unique ID of the order you want to fetch
	 * @return the order with all its info (like total amount, status, payment
	 *         details), or null if there's no order with that ID
	 * @throws SQLException if something goes wrong when talking to the database
	 */
	public Order getOrderById(int orderID) throws SQLException {

		String sql = "SELECT order_id, user_id,total_amount,status,address_id, payment_method,payment_status,create_at FROM orders WHERE order_id =?";

		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {


			statement.setInt(1, orderID);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {


				Order order = new Order();
				order.setOrder_id(resultSet.getInt("order_id"));
				order.setUser_id(resultSet.getInt("user_id"));
				order.setTotal_amount(resultSet.getDouble("total_amount"));
				order.setStatus(resultSet.getString("status"));
				order.setAddress_id(resultSet.getInt("address_id"));
				order.setPayment_method(resultSet.getString("payment_method"));
				order.setPayment_status(resultSet.getString("payment_status"));
				order.setCreate_at(resultSet.getTimestamp("create_at"));

				return order;

			} else {


				return null;

			}

		}

	}



	/**
	 * Updates an order to "paid" status and sets the payment method
	 * (Visa/Mastercard).
	 * 
	 * @param orderId       The ID of the order to update
	 * @param paymentMethod The card type (1=Visa, 2=Mastercard) – we'll convert to
	 *                      string here
	 * @throws SQLException If there's a database error
	 */

	public void markedOrderCompleted(int orderId, int paymentMethod) throws SQLException {

		String paymentMethodStr = (paymentMethod == 1) ? "Visa" : "Mastercard";
		String sql = "UPDATE  orders SET status = 'completed', payment_method = ? , payment_status = 'paid' 	WHERE order_id = ? ";
		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {


			statement.setString(1, paymentMethodStr);
			statement.setInt(2, orderId);
			statement.executeUpdate();

		}

	}



	/**
	 * Fetches the FULL order history for a logged-in user, including: - Order
	 * details (ID, tracking number, shipping status, total, etc.) - All products in
	 * each order (name, description, price, image URL) - Order item data (quantity
	 * of each product)
	 * 
	 * Uses a JOIN query to combine data from orders, order_items, products, and
	 * product_images. Groups products under their parent order (no duplicate orders
	 * in the result).
	 * 
	 * @param userId Unique ID of the logged-in user (filters orders to only this
	 *               user)
	 * @return List<Order> where each Order contains its associated
	 *         products/images/order items
	 * @throws SQLException If database connection/query fails (e.g., invalid
	 *                      column, connection error)
	 */
	public List<Order> getAllOrder(User userId) throws SQLException {

		String sql = "SELECT DISTINCT  " + "o.order_id, o.status, o.tracking_number, o.courier, o.shipping_status, "
				+ "o.create_at, o.total_amount, o.payment_method, " + "oi.product_id, oi.quantity, "
				+ "p.product_name, p.description, p.price, " + "pi.image_url " + "FROM orders o "
				+ "JOIN order_item oi ON o.order_id = oi.order_id " + "JOIN product p ON oi.product_id = p.product_id "
				+ "LEFT JOIN product_images pi ON p.product_id = pi.product_id " + "AND pi.is_Primary = true "
				+ "WHERE o.user_id = ? " + "ORDER BY o.create_at DESC";

		List<Order> orderHistory = new ArrayList<>();
		Order currentOrder = null;

		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {


			statement.setInt(1, userId.getUser_id());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {


				int orderId = resultSet.getInt("order_id");
				String status = resultSet.getString("status");

				String trackingNumber = resultSet.getString("tracking_number");
				String courier = resultSet.getString("courier");
				String shippingStatus = resultSet.getString("shipping_status");
				Timestamp createAt = resultSet.getTimestamp("create_at");
				double total = resultSet.getDouble("total_amount");
				String paymentMethod = resultSet.getString("payment_method");

				if (currentOrder == null || currentOrder.getOrder_id() != orderId) {


					currentOrder = new Order();
					currentOrder.setOrder_id(orderId);
					currentOrder.setStatus(status);
					currentOrder.setTrackingNumber(trackingNumber);
					currentOrder.setCourier(courier);
					currentOrder.setShippingStatus(shippingStatus);
					currentOrder.setCreate_at(createAt);
					currentOrder.setTotal_amount(total);
					currentOrder.setPayment_method(paymentMethod);

					orderHistory.add(currentOrder);

				}

				int productId = resultSet.getInt("product_id");
				String productName = resultSet.getString("product_name");
				String productDesc = resultSet.getString("description");
				Double productPrice = resultSet.getDouble("price");
				int quantity = resultSet.getInt("quantity");
				String imageUrl = resultSet.getString("image_url");

				Product product = new Product();
				product.setProduct_id(productId);
				product.setProduct_name(productName);
				product.setDescription(productDesc);
				product.setPrice(productPrice);

				ProductImages productImages = new ProductImages();
				productImages.setImage_url(imageUrl);
				product.setProductImages(productImages);

				Order_items orderItem = new Order_items();
				orderItem.setProduct(product);
				orderItem.setQuantity(quantity);
				orderItem.setOrder_id(orderId);

				product.setOrderItem(orderItem);

				currentOrder.getProducts().add(product);

			}

		} catch (SQLException e) {


			e.printStackTrace();

		}

		return orderHistory;

	}



	public Order getMostRecentPendingOrder(int user_id) throws SQLException {

		String sql = "SELECT * FROM orders WHERE user_id = ? AND status = 'pending' ORDER BY create_at DESC LIMIT 1";
		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection conn = dbConnection.getCnn(); PreparedStatement pstmt = conn.prepareStatement(sql)) {


			pstmt.setInt(1, user_id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {


				Order order = new Order();
				order.setOrder_id(rs.getInt("order_id"));
				order.setUser_id(rs.getInt("user_id"));
				order.setTotal_amount(rs.getDouble("total_amount"));
				order.setStatus(rs.getString("status"));
				order.setAddress_id(rs.getInt("address_id"));
				order.setPayment_method(rs.getString("payment_method"));
				order.setPayment_status(rs.getString("payment_status"));
				order.setCreate_at(rs.getTimestamp("create_at"));
				return order;

			}

		}

		return null;

	}



	/**
	 * Fetch all orders for a seller, optionally filtered by status.
	 *
	 * @param sellerId the seller's ID
	 * @param status   "pending", "processing", "shipped", "delivered", "cancelled",
	 *                 or "all"
	 * @return List<Order> with products, order items, and primary images
	 * @throws SQLException
	 */
	public List<Order> getOrdersBySeller(int sellerId, String status) throws SQLException {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT o.order_id, o.user_id, o.total_amount, o.status, o.payment_status, ").append(
				"o.payment_method, o.create_at, o.address_id, o.tracking_number, o.courier, o.shipping_status, ")
				.append("oi.order_item_id, oi.product_id, oi.quantity, oi.price AS item_price, ")
				.append("p.product_name, p.description, p.price AS product_price, p.stock_quantity, ")
				.append("pi.image_url ").append("FROM orders o ")
				.append("JOIN order_item oi ON o.order_id = oi.order_id ")
				.append("JOIN product p ON oi.product_id = p.product_id ")
				.append("LEFT JOIN product_images pi ON p.product_id = pi.product_id AND pi.is_Primary = 1 ")
				.append("WHERE p.seller_id = ? ");

		if (status != null && !status.equalsIgnoreCase("all")) {


			sql.append("AND o.status = ? ");

		}

		sql.append("ORDER BY o.create_at DESC");

		List<Order> orders = new ArrayList<>();
		Order currentOrder = null;

		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection conn = dbConnection.getCnn(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {


			ps.setInt(1, sellerId);

			if (status != null && !status.equalsIgnoreCase("all")) {


				ps.setString(2, status);

			}

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {


				int orderId = rs.getInt("order_id");

				// create new Order if needed
				if (currentOrder == null || currentOrder.getOrder_id() != orderId) {


					currentOrder = new Order();
					currentOrder.setOrder_id(orderId);
					currentOrder.setUser_id(rs.getInt("user_id"));
					currentOrder.setTotal_amount(rs.getDouble("total_amount"));
					currentOrder.setStatus(rs.getString("status"));
					currentOrder.setPayment_status(rs.getString("payment_status"));
					currentOrder.setPayment_method(rs.getString("payment_method"));
					currentOrder.setCreate_at(rs.getTimestamp("create_at"));
					currentOrder.setAddress_id(rs.getInt("address_id"));
					currentOrder.setTrackingNumber(rs.getString("tracking_number"));
					currentOrder.setCourier(rs.getString("courier"));
					currentOrder.setShippingStatus(rs.getString("shipping_status"));

					orders.add(currentOrder);

				}

				// Map product
				Product product = new Product();
				product.setProduct_id(rs.getInt("product_id"));
				product.setProduct_name(rs.getString("product_name"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getDouble("product_price"));
				product.setStock_quantity(rs.getInt("stock_quantity"));

				ProductImages img = new ProductImages();
				img.setImage_url(rs.getString("image_url"));
				product.setProductImages(img);

				// Map order item
				Order_items orderItem = new Order_items();
				orderItem.setOrder_item_id(rs.getInt("order_item_id"));
				orderItem.setOrder_id(orderId);
				orderItem.setProduct_id(rs.getInt("product_id"));
				orderItem.setQuantity(rs.getInt("quantity"));
				orderItem.setPrice(rs.getDouble("item_price"));
				orderItem.setProduct(product);

				product.setOrderItem(orderItem);

				currentOrder.getProducts().add(product);

			}

		}

		return orders;

	}



	/**
	 * Get counts of orders by status for seller (Pending, Processing, Shipped,
	 * Delivered)
	 *
	 * @param sellerId seller ID
	 * @return int[] {pending, processing, shipped, delivered}
	 * @throws SQLException
	 */
	public int[] getOrderStats(int sellerId) throws SQLException {

		String sql = "SELECT o.status, COUNT(*) AS total " + "FROM orders o "
				+ "JOIN order_item oi ON o.order_id = oi.order_id " + "JOIN product p ON oi.product_id = p.product_id "
				+ "WHERE p.seller_id = ? " + "GROUP BY o.status";

		int pending = 0, processing = 0, shipped = 0, delivered = 0;

		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection conn = dbConnection.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setInt(1, sellerId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {


				String status = rs.getString("status");
				int count = rs.getInt("total");

				switch (status.toLowerCase()) {


				case "pending":
					pending = count;
					break;

				case "processing":
					processing = count;
					break;

				case "shipped":
					shipped = count;
					break;

				case "delivered":
					delivered = count;
					break;

				}

			}

		}

		return new int[] { pending, processing, shipped, delivered };

	}



	/**
	 * Update status of a specific order (used for the dropdown in table)
	 *
	 * @param orderId order ID
	 * @param status  new status (pending, processing, shipped, delivered,
	 *                cancelled)
	 * @throws SQLException
	 */
	public void updateOrderStatus(int orderId, String status) throws SQLException {

		String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection conn = dbConnection.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setString(1, status);
			ps.setInt(2, orderId);
			ps.executeUpdate();

		}

	}



	public boolean isOrderOwnedBySeller(int orderId, int sellerId) throws SQLException {

		String sql = "SELECT COUNT(*) FROM orders o JOIN order_item oi ON o.order_id = oi.order_id "
				+ "JOIN product p ON oi.product_id = p.product_id " + "WHERE o.order_id = ? AND p.seller_id = ?";
		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection conn = dbConnection.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setInt(1, orderId);
			ps.setInt(2, sellerId);

			try (ResultSet rs = ps.executeQuery()) {


				if (rs.next()) {


					return rs.getInt(1) > 0;

				}

			}

		}

		return false;

	}



}
