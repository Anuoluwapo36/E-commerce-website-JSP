package com.centrum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.centrum.bean.Order_items;
import com.centrum.bean.Product;
import com.centrum.connections.MySQLcnn;

public class OrderItemsDao {

	/**
	 * this insert details coming from confirmation page once the user is move to
	 * next page
	 * 
	 * @param orderItems
	 * @throws SQLException
	 */
	public void createOrderItems(Order_items orderItems) throws SQLException {

		String sql = "INSERT INTO order_item (order_id, product_id,quantity,price)" + " VALUES(?,?,?,?)";
		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, orderItems.getOrder_id());
			statement.setInt(2, orderItems.getProduct_id());
			statement.setInt(3, orderItems.getQuantity());
			statement.setDouble(4, orderItems.getPrice());

			statement.executeUpdate();

		}

	}

	/**
	 * Fetches an order item from the 'order_item' table using the parent order's
	 * ID.
	 * 
	 * @param orderID the ID of the parent order to fetch the linked order item for
	 * @return an Order_items object containing details (product ID, quantity,
	 *         price) of the order item, or null if no order item is found for the
	 *         given order ID
	 * @throws SQLException if there's an error accessing the database
	 */
	
	
	public List<Order_items> getOrderItemsByOrderId(int orderId) throws SQLException {
	    String sql = "SELECT Ri.order_item_id, Ri.order_id, Ri.quantity, Ri.price AS item_price, " +
	                 "P.product_id, P.product_name, P.price AS product_price, P.category " +
	                 "FROM order_item Ri " +
	                 "JOIN product P ON Ri.product_id = P.product_id " +
	                 "WHERE Ri.order_id = ?";

	    List<Order_items> items = new ArrayList<>();
	    MySQLcnn dbConnection = new MySQLcnn();

	    try (Connection connection = dbConnection.getCnn();
	         PreparedStatement statement = connection.prepareStatement(sql)) {

	        statement.setInt(1, orderId);
	        ResultSet rs = statement.executeQuery();

	        while (rs.next()) {
	            Order_items orderItem = new Order_items();
	            orderItem.setOrder_item_id(rs.getInt("order_item_id"));
	            orderItem.setOrder_id(rs.getInt("order_id"));
	            orderItem.setProduct_id(rs.getInt("product_id"));
	            orderItem.setQuantity(rs.getInt("quantity"));
	            orderItem.setPrice(rs.getDouble("item_price"));

	            Product product = new Product();
	            product.setProduct_name(rs.getString("product_name"));
	            product.setPrice(rs.getDouble("product_price"));
	            product.setCategory(rs.getString("category"));
	            orderItem.setProduct(product);

	            items.add(orderItem);
	        }
	    }

	    return items;
	}

//	public Order_items getOrderITemById(int orderID) throws SQLException {
//		String sql = "SELECT Ri.order_item_id, Ri.order_id , Ri.quantity, Ri.price AS item_price ,P.product_id, P.product_name,P.price AS product_price,P.category FROM order_item Ri "
//				+ "JOIN  product P ON  Ri.product_id = P.product_id WHERE Ri.order_id = ?";
//
//		MySQLcnn dbConnection = new MySQLcnn();
//		try (Connection connection = dbConnection.getCnn();
//				PreparedStatement statement = connection.prepareStatement(sql)) {
//			statement.setInt(1, orderID);
//			ResultSet resultSet = statement.executeQuery();
//			if (resultSet.next()) {
//				Order_items order_items = new Order_items();
//				order_items.setOrder_item_id(resultSet.getInt("order_item_id"));
//				order_items.setOrder_id(resultSet.getInt("order_id"));
//				order_items.setProduct_id(resultSet.getInt("product_id"));
//				order_items.setQuantity(resultSet.getInt("quantity"));
//				order_items.setPrice(resultSet.getDouble("item_price"));
//
//				Product product = new Product();
//
//				product.setProduct_name(resultSet.getString("product_name"));
//				product.setPrice(resultSet.getDouble("product_price"));
//				product.setCategory(resultSet.getString("category"));
//				order_items.setProduct(product);
//
//				return order_items;
//			} else {
//				return null;
//			}
//		}
//	}

}
