package com.centrum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.centrum.bean.Payment;
import com.centrum.connections.MySQLcnn;

public class PaymentDao {

	public void createPayment(Payment payment) throws SQLException {

		String sql = "INSERT INTO payment ( user_id, order_id , address_id , payment_method, payment_status , amount, payment_date, card_last4, card_expiry,cardholder_name)"
				+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
		MySQLcnn dbConnection = new MySQLcnn();
		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, payment.getUser_id());
			statement.setInt(2, payment.getOrder_id());
			statement.setInt(3, payment.getAddress_id());
			statement.setInt(4, payment.getPayment_method());
			statement.setString(5, payment.getPayment_status());
			statement.setDouble(6, payment.getAmount());
			statement.setTimestamp(7, payment.getPayment_date());
			statement.setString(8, payment.getCard_last4());
			statement.setString(9, payment.getCard_expiry());
			statement.setString(10, payment.getCardholder_name());

			statement.executeUpdate();

		}

	}

}
