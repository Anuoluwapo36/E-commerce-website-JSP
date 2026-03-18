package com.centrum.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.centrum.bean.Order;
import com.centrum.connections.MySQLcnn;

public class TrackingDao {

	public boolean setShipping(int orderId, String trackingNumber, String courier) {
		String sql = "UPDATE orders SET tracking_number=? , courier =? , shipping_status ='shipped' , current_lat = NULL , current_lng = NULL WHERE order_id = ?";

		MySQLcnn dbConnection = new MySQLcnn();
		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, trackingNumber);
			statement.setString(2, courier);
			statement.setInt(3, orderId);

			return statement.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateLocation(BigDecimal current_lat, BigDecimal current_lng, int orderId) {

		String sql = "UPDATE orders SET current_lat =? ,current_lng = ? , shipping_status  = 'in_transit' WHERE order_id = ?";

		MySQLcnn dbConnection = new MySQLcnn();
		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setBigDecimal(1, current_lat);
			statement.setBigDecimal(2, current_lng);
			statement.setInt(3, orderId);

			return statement.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean markAsDelivered(int orderId) {

		String sql = "UPDATE orders SET  shipping_status  = 'delivered', current_lat = NULL , current_lng = NULL WHERE order_id = ?";

		MySQLcnn dbConnection = new MySQLcnn();
		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, orderId);

			return statement.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public Order getTRackDetails(int orderId) {

		String sql = "SELECT tracking_number , courier , shipping_status, current_lat , current_lng  FROM orders WHERE order_id = ?";

		MySQLcnn dbConnection = new MySQLcnn();
		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, orderId);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				Order order = new Order();
				order.setTrackingNumber(resultSet.getString("tracking_number"));
				order.setCourier(resultSet.getString("courier"));
				order.setShippingStatus(resultSet.getString("shipping_status"));
				order.setCurrentLat(resultSet.getBigDecimal("current_lat"));
				order.setCurrentLng(resultSet.getBigDecimal("current_lng"));
				return order;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Order> getUserOrderTrackingStatuses(int userId) {

		String sql = "SELECT order_id, tracking_number , courier, shipping_status FROM orders WHERE user_id = ? ORDER BY create_at DESC";
		MySQLcnn dbConnection = new MySQLcnn();

		List<Order> trackList = new ArrayList<>();
		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, userId);

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {

				Order order = new Order();
				order.setOrder_id(resultSet.getInt("order_id"));
				order.setTrackingNumber(resultSet.getString("tracking_number"));
				order.setCourier(resultSet.getString("courier"));
				order.setShippingStatus(resultSet.getString("shipping_status"));
				trackList.add(order);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return trackList;
	}

}
