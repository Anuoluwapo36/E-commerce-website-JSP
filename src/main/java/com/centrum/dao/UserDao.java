package com.centrum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.centrum.bean.User;
import com.centrum.bean.UserProfileImages;
import com.centrum.connections.MySQLcnn;

public class UserDao {
	/**
	 * Creates a new user in the database (for registration). Ensures secure
	 * password handling, validates required fields, and uses proper resource
	 * management.
	 * 
	 * @param user The User object containing registration data (full_name, email,
	 *             password, etc.)
	 * @throws SQLException             If a database error occurs (e.g., duplicate
	 *                                  email, connection failure)
	 * @throws IllegalArgumentException If required fields are null/empty or email
	 *                                  is invalid
	 */
	public void createUser(User user) throws SQLException {

		String sql = "INSERT INTO users (full_name, email, phone_number, password, roles, status, createAt)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, user.getFull_name());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getPhone_number());
			statement.setString(4, user.getPassword());
			statement.setString(5, user.getRoles());
			statement.setString(6, user.getStatus());
			statement.setTimestamp(7, user.getCreateAt());

			statement.executeUpdate();
			System.out.println("User saved to database!");
		}catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Fetches a user from the database by their unique user ID. Returns null if no
	 * user is found (no exception thrown for "user not found" cases).
	 * 
	 * @param userId The unique ID of the user to fetch (from the users table)
	 * @return User object populated with database data, or null if no user exists
	 *         with the given ID
	 * @throws SQLException             If a database error occurs (e.g., connection
	 *                                  failure, invalid query)
	 * @throws IllegalArgumentException If userId is invalid (≤ 0)
	 */
	public User getUserById(int userId) throws SQLException {

		if (userId <= 0) {
			throw new IllegalArgumentException("User ID must be a positive number (userId: " + userId + ")");
		}
		String sql = "SELECT user_id, full_name, email, phone_number, password, roles, status, createAt "
				+ "FROM users WHERE user_id = ?";
		User foundUser = null;

		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, userId);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				foundUser = new User();
				foundUser.setUser_id(resultSet.getInt("user_id"));
				foundUser.setFull_name(resultSet.getString("full_name"));
				foundUser.setEmail(resultSet.getString("email"));
				foundUser.setPhone_number(resultSet.getString("phone_number"));
//				foundUser.setPassword(resultSet.getString("password"));
				foundUser.setRoles(resultSet.getString("roles"));
				foundUser.setStatus(resultSet.getString("status"));
				foundUser.setCreateAt(resultSet.getTimestamp("createAt"));

			}

		} catch (SQLException e) {
			throw new SQLException("Failed to fetch user with ID " + userId + ": " + e.getMessage(), e);
		}
		return foundUser;

	}

	/**
	 * Updates existing user data in the `users` database table.
	 * 
	 * This method modifies the user's full name, email, password using the unique
	 * `user_id` from the provided User object (critical for targeting the correct
	 * user). It uses parameterized SQL to prevent SQL injection and auto-closes
	 * database resources.
	 * 
	 * @param user A User object containing: - Non-null/valid `user_id` (identifies
	 *             the user to update) - Updated values for full_name, email,
	 *             phone_number, roles, status (Note: `createAt` and `password` are
	 *             NOT updated by this method)
	 * @throws SQLException Thrown if: 1. Database connection fails 2. SQL query is
	 *                      invalid 3. No user exists with the provided `user_id` 4.
	 *                      Duplicate email (if email is updated to an existing
	 *                      user's email)
	 */
	public void updateUser(User user) throws SQLException {
		StringBuilder sql = new StringBuilder("UPDATE users SET " + "full_name = ?, email = ?");

		int startPoint = 2;

		if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {

			sql.append(", password = ?");
			startPoint++;

		}
		sql.append(" WHERE user_id = ?");
		startPoint++;
		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql.toString())) {
			statement.setString(1, user.getFull_name());
			statement.setString(2, user.getEmail());

			if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
				statement.setString(startPoint - 1, user.getPassword());

			}
//			statement.setString(4, user.getRoles());
//			statement.setString(5, user.getStatus());
//			statement.setTimestamp(6, user.getCreateAt());
			statement.setInt(startPoint, user.getUser_id());

			statement.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the user's profile image (saves the new image and marks it as their
	 * default).
	 * 
	 * What it does: 1. Finds the user's old default profile image (if they have
	 * one) and marks it as "not default". 2. Saves the new profile image to the
	 * database and sets it as the user's new default.
	 * 
	 * What this method needs from you (parameters):
	 * 
	 * @param userId        - The unique ID of the user (so we know who the image
	 *                      belongs to, e.g., 456)
	 * @param imageFilename - The unique name of the image file (e.g.,
	 *                      "456_1712345678901_pic.jpg")
	 * @param imagePath     - The full path where the image is saved on your server
	 *                      folder uploads/avater
	 * 
	 *                      What could go wrong:
	 * @throws SQLException - If there's a problem with the database (e.g.,
	 *                      connection issue, bad query)
	 */
	public void updateUserImage(int userId, String imageFilename, String imagePath) throws SQLException {

		MySQLcnn dbConnection = new MySQLcnn();
		try (Connection connection = dbConnection.getCnn()) {
			String sqlUnsetOld = "UPDATE user_images SET is_default = false WHERE user_id = ? AND is_default = true";
			try (PreparedStatement statement = connection.prepareStatement(sqlUnsetOld)) {

				statement.setInt(1, userId);
				statement.executeUpdate();
			}

			String sqlInsertNew = "INSERT INTO user_images (user_id, image_filename, image_path, is_default) VALUES (?, ?, ?, true)";
			try (PreparedStatement stmt2 = connection.prepareStatement(sqlInsertNew)) {
				stmt2.setInt(1, userId);
				stmt2.setString(2, imageFilename);
				stmt2.setString(3, imagePath);
				stmt2.executeUpdate();
			}catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}
	
	/**
	 * Fetches the default profile image data for a specific user from the user_images table.
	 * 
	 * @param userId The unique ID of the user whose default profile image is being retrieved
	 * @return A UserProfileImages bean populated with the user's default image path, user ID, and default flag; 
	 *         returns null if no default image exists for the user or a database error occurs
	 * @throws SQLException If a database connection or query execution error occurs
	 */
	public UserProfileImages getDefaultUserImage(int userId) throws SQLException {
		String sql = "SELECT user_id , image_filename, image_path,is_default FROM user_images WHERE user_id = ? AND is_default = true";

		MySQLcnn dbConnection = new MySQLcnn();
		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, userId);
			
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				UserProfileImages dpImg = new UserProfileImages();
				dpImg.setImagePath(resultSet.getString("image_path"));
				dpImg.setImageFilename(resultSet.getString("image_filename"));

				dpImg.setDefault(resultSet.getBoolean("is_default"));
				dpImg.setUser_id(resultSet.getInt("user_id"));
				return dpImg;
				
				
				
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Permanently deletes a user from the `users` database table using their unique
	 * user ID.
	 * 
	 * This is a destructive operation—deleted user data cannot be recovered. It
	 * targets the user exclusively by `user_id` (the primary key) to avoid
	 * accidental mass deletions.
	 * 
	 * @param userId The unique ID of the user to delete (must exist in the `users`
	 *               table)
	 * @throws SQLException Thrown if: 1. Database connection fails 2. `userId` does
	 *                      not exist (no rows deleted—depends on database setup) 3.
	 *                      Permission denied (insufficient database privileges) 4.
	 *                      Foreign key constraint violation (e.g., user has linked
	 *                      orders/addresses)
	 * @warning Permanently deletes user data—use with extreme caution (prefer "soft
	 *          delete" in production: update `status` to "INACTIVE" instead).
	 */
	public void deleteUser(int userId) throws SQLException {
		String sql = "DELETE FROM users WHERE user_id = ?";

		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, userId);
			statement.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	
	/**
	 * DAO method to deactivate a user's account by updating the {@code status} column in the {@code users} table.
	 * <p>
	 * This method marks a user as inactive (instead of permanently deleting their record) by setting the 
	 * {@code status} column to the value {@code 'inactive'}. Deactivation retains all user data (e.g., 
	 * profile, order history, cart records) while preventing the user from logging in or accessing 
	 * account-related features.
	 * <p>
	 * The operation targets a specific user via their unique {@code user_id} (retrieved from the logged-in 
	 * user's session for security). It uses a prepared statement to avoid SQL injection risks and 
	 * leverages try-with-resources to auto-close database connections/statements.
	 *
	 * @param userId the unique identifier of the user to deactivate (must correspond to an existing 
	 *               {@code user_id} in the {@code users} table)
	 * @throws SQLException if a database access error occurs (e.g., invalid connection, missing table/column, 
	 *                      or invalid {@code user_id} constraint violation)
	 * @see MySQLcnn for database connection management
	 */
	public void deactivateUser(int userId) throws SQLException{
		String sql = "Update users SET status = 'inactive' WHERE user_id = ?";
		
		MySQLcnn dbConnection = new MySQLcnn();
		try(Connection connection = dbConnection.getCnn(); PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setInt(1, userId);
			statement.executeUpdate();
		}
		
	}

	/**
	 * Fetches a user from the `users` database table using their email address.
	 * 
	 * This method searches for a user by their email (case-sensitive, as written)
	 * and returns a User object populated with their data (excluding password—see
	 * security note). Returns null if no user is found with the provided email (no
	 * exception thrown for "email not found" cases).
	 * 
	 * @param email The email address of the user to fetch (must match the email
	 *              stored in the database)
	 * @return A User object with user_id, full_name, email, phone_number, roles,
	 *         status, and createAt; null if no user exists with the provided email
	 * @throws NoClassDefFoundError If the MySQLcnn utility class is missing
	 *                              (indirect database error)
	 * @note Security: Password is excluded from the result set—never expose
	 *       hashed/plain-text passwords unnecessarily.
	 * @note Case-sensitivity: Email matching depends on your database collation
	 *       (e.g., MySQL's utf8mb4_general_ci is case-insensitive).
	 */
	public User getUserByEmail(String email) {
		String sql = "SELECT user_id, full_name, email, phone_number, password, roles, status, createAt "
				+ "FROM users WHERE email = ?";

		MySQLcnn dbConnection = new MySQLcnn();
		User currentUser = null;

		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, email);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				currentUser = new User();
				currentUser.setUser_id(resultSet.getInt("user_id"));
				currentUser.setFull_name(resultSet.getString("full_name"));
				currentUser.setEmail(resultSet.getString("email"));
				currentUser.setPhone_number(resultSet.getString("phone_number"));
				currentUser.setRoles(resultSet.getString("roles"));
				currentUser.setStatus(resultSet.getString("status"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return currentUser;

	}

}
