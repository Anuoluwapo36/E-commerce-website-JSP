

package com.centrum.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.centrum.connections.MySQLcnn;




public class CategoryDao {

	private MySQLcnn db = new MySQLcnn();



	public int addCategory(String categoryName) throws SQLException {

		String sql = "INSERT INTO categories (category_name) VALUES (?)";

		try (Connection conn = db.getCnn();
				PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {


			ps.setString(1, categoryName);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next())
				return rs.getInt(1);
			return 0;

		}

	}



	public int addSubcategory(String subcategoryName, int categoryId) throws SQLException {

		String sql = "INSERT INTO subcategories (subcategory_name, category_id) VALUES (?, ?)";

		try (Connection conn = db.getCnn();
				PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {


			ps.setString(1, subcategoryName);
			ps.setInt(2, categoryId);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next())
				return rs.getInt(1);
			return 0;

		}

	}



	public int getCategoryIdByName(String categoryName) throws SQLException {

		String sql = "SELECT category_id FROM categories WHERE category_name = ?";

		try (Connection conn = db.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setString(1, categoryName);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt("category_id");
			return 0;

		}

	}



	public int addSubcategoryIfNotExists(String subcategoryName, int categoryId) throws SQLException {

		String checkSql = "SELECT subcategory_id FROM subcategories WHERE subcategory_name = ? AND category_id = ?";
		String insertSql = "INSERT INTO subcategories (subcategory_name, category_id) VALUES (?, ?)";

		try (Connection conn = db.getCnn()) {


			try (PreparedStatement psCheck = conn.prepareStatement(checkSql)) {


				psCheck.setString(1, subcategoryName);
				psCheck.setInt(2, categoryId);
				ResultSet rs = psCheck.executeQuery();

				if (rs.next()) {


					return rs.getInt("subcategory_id");

				}

			}

			try (PreparedStatement psInsert = conn.prepareStatement(insertSql,
					PreparedStatement.RETURN_GENERATED_KEYS)) {


				psInsert.setString(1, subcategoryName);
				psInsert.setInt(2, categoryId);
				psInsert.executeUpdate();
				ResultSet rs = psInsert.getGeneratedKeys();

				if (rs.next()) {


					return rs.getInt(1); // Return new ID

				}

			}

		}

		return 0;

	}



}
