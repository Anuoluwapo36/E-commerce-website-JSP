package com.centrum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.centrum.bean.User;
import com.centrum.bean.User_address;
import com.centrum.connections.MySQLcnn;

public class AddressDao {

	/**
	 * Saves a new user address to the database.
	 * 
	 * This method inserts a new record into the `user_address` table, associating
	 * the address with a specific user (via `user_id`) and storing all relevant
	 * address details (country, state, city, etc.). It uses a prepared statement to
	 * prevent SQL injection and manages database connections via the `MySQLcnn`
	 * utility.
	 * 
	 * @param address A `User_address` object containing the address details to be
	 *                saved, including: - user_id (ID of the user the address
	 *                belongs to) - country, state, city (location information) -
	 *                details_address (street/address details) - postal_code
	 *                (ZIP/postal code) - phone_number (contact phone number) -
	 *                default_address (boolean flag indicating if this is the user's
	 *                default address)
	 * @throws SQLException If a database access error occurs ( connection issues,
	 *                      invalid SQL, or mismatched data types), or if the
	 *                      prepared statement execution fails.
	 */
	public void createAddress(User_address address) throws SQLException {

		String sql = "INSERT INTO user_address(user_id , country, state_province, city, detail_address, postal_code, phone_number,default_address)"
				+ "VALUES(?,?,?,?,?,?,?,?)";

		MySQLcnn dbConnection = new MySQLcnn();
		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, address.getUser_id());
			statement.setString(2, address.getCountry());
			statement.setString(3, address.getState());
			statement.setString(4, address.getCity());
			statement.setString(5, address.getDetails_address());
			statement.setString(6, address.getPostal_code());
			statement.setString(7, address.getPhone_number());
			statement.setBoolean(8, address.isDefault_address());

			statement.executeUpdate();

		}

	}

	/**
	 * Retrieves the saved address for a specific user from the database.
	 * 
	 * This method queries the `user_address` table to fetch the address associated
	 * with the given user's ID. It returns a populated `User_address` object if an
	 * address exists for the user; otherwise, it returns null.
	 * 
	 * @param user The `User` object for whom to retrieve the address (uses the
	 *             user's ID via `user.getUser_id()`)
	 * @return A `User_address` object containing the user's address details
	 *         (address ID, country, city, etc.) if found; null if no address is
	 *         associated with the user in the database
	 * @throws SQLException If a database access error occurs ( connection issues,
	 *                      invalid SQL syntax, or missing columns in the result
	 *                      set), which propagates to the caller for handling
	 */
	public User_address getAddress(int loggedInUser) throws SQLException {

		String sql = "SELECT address_id, user_id, country, state_province, city, detail_address, postal_code, phone_number, default_address FROM user_address WHERE user_id = ? AND default_address = 1 LIMIT 1";

		MySQLcnn dbConnection = new MySQLcnn();
		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, loggedInUser);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				User_address address = new User_address();
				address.setAddress_id(resultSet.getInt("address_id"));
				address.setUser_id(resultSet.getInt("user_id"));

				address.setDetails_address(resultSet.getString("detail_address"));
				address.setCity(resultSet.getString("city"));
				address.setCountry(resultSet.getString("country"));
				address.setState(resultSet.getString("state_province"));
				address.setPostal_code(resultSet.getString("postal_code"));
				address.setPhone_number(resultSet.getString("phone_number"));
				address.setDefault_address(resultSet.getBoolean("default_address"));

				return address;
			}

		}
		return null;

	}

	public void updateDefaultAddress(User user, User_address newAddress) throws SQLException {
		String sql = "UPDATE user_address SET detail_address=?, state_province=?, city=?, country=?, phone_number=?, postal_code=? "
				+ "WHERE user_id=? AND default_address=1";

		try (Connection connection = new MySQLcnn().getCnn();
				PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setString(1, newAddress.getDetails_address());
			stmt.setString(2, newAddress.getState());
			stmt.setString(3, newAddress.getCity());
			stmt.setString(4, newAddress.getCountry());
			stmt.setString(5, newAddress.getPhone_number());
			stmt.setString(6, newAddress.getPostal_code());
			stmt.setInt(7, user.getUser_id());

			int updated = stmt.executeUpdate();
			System.out.println("Updated " + updated + " default address for user " + user.getUser_id());
		}
	}

	/**
	 * unsave code i wrote it and it delete user order from the table cause when
	 * user enter new address the old m=will be deleted but this method was unsafe
	 * and i had top use diffrent method above Deletes all addresses in the
	 * `user_address` table associated with a specific user. This ensures a user has
	 * no duplicate addresses, typically called before saving a new address.
	 * 
	 * @param user The logged-in User object whose addresses will be deleted. Must
	 *             contain a valid, positive user ID (via `getUser_id()`).
	 * @throws SQLException             If a database error occurs (e.g., connection
	 *                                  failure, invalid SQL).
	 * @throws IllegalArgumentException If the input `user` is null or has an
	 *                                  invalid user ID (<= 0).
	 */
	public void deleteAddressByUserId(User user) throws SQLException {
		String sql = "DELETE FROM user_address WHERE user_id = ? AND default_address = 1";
		MySQLcnn dbConnection = new MySQLcnn();

		if (user == null || user.getUser_id() <= 0) {
			throw new IllegalArgumentException("Invalid user or user ID");
		}

		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, user.getUser_id());
			int rowsDeleted = statement.executeUpdate();
			System.out.println("Deleted " + rowsDeleted + " old default address(es) for user " + user.getUser_id());
		}
	}

}
