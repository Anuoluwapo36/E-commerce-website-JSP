

package com.centrum.dao;



import java.sql.*;
import java.util.*;
import com.centrum.connections.MySQLcnn;




public class ReportDAO {

	public double getTotalRevenue(int sellerId) throws SQLException {

		String sql = "SELECT SUM(oi.price * oi.quantity) AS totalRevenue FROM order_item oi "
				+ "JOIN product p ON oi.product_id = p.product_id WHERE p.seller_id = ?";
		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection conn = dbConnection.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setInt(1, sellerId);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return rs.getDouble("totalRevenue");

		}

		return 0;

	}



	public int getTotalOrders(int sellerId) throws SQLException {

		String sql = "SELECT COUNT(DISTINCT o.order_id) AS totalOrders FROM orders o "
				+ "JOIN order_item oi ON o.order_id = oi.order_id "
				+ "JOIN product p ON oi.product_id = p.product_id WHERE p.seller_id = ?";
		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection conn = dbConnection.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setInt(1, sellerId);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt("totalOrders");

		}

		return 0;

	}



	public double getAverageOrderValue(int sellerId) throws SQLException {

		double totalRevenue = getTotalRevenue(sellerId);
		int totalOrders = getTotalOrders(sellerId);
		return totalOrders > 0 ? totalRevenue / totalOrders : 0;

	}



	public Map<String, Double> getRevenueByCategory(int sellerId) throws SQLException {

		String sql = "SELECT p.category, SUM(oi.price * oi.quantity) AS revenue FROM order_item oi "
				+ "JOIN product p ON oi.product_id = p.product_id WHERE p.seller_id = ? GROUP BY p.category";
		Map<String, Double> revenueMap = new LinkedHashMap<>();
		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection conn = dbConnection.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setInt(1, sellerId);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				revenueMap.put(rs.getString("category"), rs.getDouble("revenue"));

		}

		return revenueMap;

	}



	public Map<String, Integer> getOrderStatusDistribution(int sellerId) throws SQLException {

		String sql = "SELECT o.status, COUNT(DISTINCT o.order_id) AS count FROM orders o "
				+ "JOIN order_item oi ON o.order_id = oi.order_id "
				+ "JOIN product p ON oi.product_id = p.product_id WHERE p.seller_id = ? GROUP BY o.status";
		Map<String, Integer> statusMap = new LinkedHashMap<>();
		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection conn = dbConnection.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setInt(1, sellerId);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				statusMap.put(rs.getString("status"), rs.getInt("count"));

		}

		return statusMap;

	}



	public Map<String, Double> getTopProducts(int sellerId, int limit) throws SQLException {

		String sql = "SELECT p.product_name, SUM(oi.price * oi.quantity) AS revenue FROM order_item oi "
				+ "JOIN product p ON oi.product_id = p.product_id WHERE p.seller_id = ? "
				+ "GROUP BY p.product_name ORDER BY revenue DESC LIMIT ?";
		Map<String, Double> topProducts = new LinkedHashMap<>();
		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection conn = dbConnection.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setInt(1, sellerId);
			ps.setInt(2, limit);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				topProducts.put(rs.getString("product_name"), rs.getDouble("revenue"));

		}

		return topProducts;

	}



}
