package com.centrum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.centrum.bean.User;
import com.centrum.connections.MySQLcnn;

public class LoginDao {
	/**
	 * Validates user login credentials (email + password) against the `users` database table.
	 * 
	 * Compares the provided password (from the login form) to the stored password in the database.
	 * Returns a `User` object with core user data (user_id, full_name, email, roles) if credentials are valid;
	 * returns null if the email is not found or the password is incorrect.
	 * 
	 * @param user A User object containing:
	 *             - Non-null/non-empty `email` (user's login email)
	 *             - Non-null/non-empty `password` (plain-text password from the login form)
	 * @return Logged-in User object (with user_id, full_name, email, roles) if credentials are valid; null otherwise
	 * @throws SQLException Thrown if:
	 *                      1. Database connection fails
	 *                      2. SQL query is invalid
	 *                      3. Database access is denied
	 * @warning CRITICAL SECURITY ISSUE: Uses plain-text password comparison! Passwords must be HASHED (e.g., BCrypt)
	 *          in the database, and this method should compare HASHES (not plain text).
	 * @note Does not return password (hashed or plain) in the User object—good security practice.
	 */
	public User validateCredential(User user) throws SQLException {
		String sql = "SELECT  user_id, full_name , email, password, roles, status  FROM users WHERE email=?";
		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setString(1, user.getEmail());
			ResultSet resultSet = statement.executeQuery();
			
			
			if (resultSet.next()) {

				String storeHashedPassword = resultSet.getString("password");
				
				
				String strightTextLoginPassword = user.getPassword().trim();
				
				boolean isPasswordValid = BCrypt.checkpw(strightTextLoginPassword, storeHashedPassword);

				if (isPasswordValid) {

					User loggedInUser = new User();

					loggedInUser.setUser_id(resultSet.getInt("user_id"));
					loggedInUser.setFull_name(resultSet.getString("full_name"));
					loggedInUser.setEmail(resultSet.getString("email"));
					loggedInUser.setRoles(resultSet.getString("roles"));
					loggedInUser.setStatus(resultSet.getString("status"));
					return loggedInUser;

				} else {
					return null;
				}

			}

		}

		return null;

	}
}
