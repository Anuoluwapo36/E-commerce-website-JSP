package com.centrum.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLcnn {
	private static final String db_url = "jdbc:mysql://localhost:3306/centrum_db?serverTimezone=UTC";
	private static final String driver = "com.mysql.cj.jdbc.Driver";

	private static final String db_user = "root";
	private static final String db_password = "Majekobaje3666@";
	

	public Connection getCnn() throws SQLException {
		Connection connection = null;
		try {
			Class.forName(driver);
			System.out.println("Driver loaded successfully!");

			connection = DriverManager.getConnection(db_url, db_user, db_password);
			System.out.println("Connection to database successful!");

		} catch (ClassNotFoundException e) {
			throw new SQLException("MYsql JDBC Driver not found: " + e.getMessage());
		}
		return connection;
	}

}
