package com.centrum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.centrum.bean.Logout;
import com.centrum.connections.MySQLcnn;

public class LogoutDao {

    /**
     * Saves user logout details to the {@code logout_user_history} table.
     * Inserts the user's ID, IP address, user agent, and logout timestamp into the database.
     * Uses try-with-resources to auto-close database connections/prepared statements (avoids resource leaks).
     * 
     * @param logout A {@link Logout} bean containing the logout details to persist:
     *               <ul>
     *                   <li>{@code user_id}: Unique ID of the logged-out user (foreign key to {@code users} table)</li>
     *                   <li>{@code ipAddress}: IP address of the user's device (may include proxy/CDN IP via X-Forwarded-For)</li>
     *                   <li>{@code userAgent}: Browser/device information from the HTTP User-Agent header</li>
     *                   <li>{@code logoutTimestamp}: Exact timestamp when the user initiated logout (SQL TIMESTAMP)</li>
     *               </ul>
     * @throws SQLException If a database access error occurs, including:
     *                      <ul>
     *                          <li>Failed to establish a database connection</li>
     *                          <li>Invalid SQL syntax or missing {@code logout_user_history} table/columns</li>
     *                          <li>Foreign key constraint violation (invalid {@code user_id})</li>
     *                          <li>Database server unreachable or offline</li>
     *                      </ul>
     *                      The exception includes a context message with the affected user ID for easier debugging.
     */

	public void saveLogout(Logout logout) throws SQLException {

		String sql = "INSERT INTO logout_user_history (user_id, ip_address, user_agent , logout_timestamp) VALUES(?,?,?,?) ";
		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, logout.getUser_id());
			statement.setString(2, logout.getIpAddress());
			statement.setString(3, logout.getUserAgent());
			statement.setTimestamp(4, logout.getLogoutTimestamp());

			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
            throw new SQLException("Failed to save logout for user ID: " + logout.getUser_id(), e);
		}

	}

}
