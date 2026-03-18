package com.centrum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.centrum.bean.ProductImages;
import com.centrum.connections.MySQLcnn;

public class ProductImageDao {

	public List<ProductImages> getImagesProductId(int product_id) {

		String sql = "SELECT * FROM product_images WHERE product_id =?";
		MySQLcnn dbConnection = new MySQLcnn();
		List<ProductImages> image = new ArrayList<>();

		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, product_id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				ProductImages productimg = new ProductImages();
				productimg.setProductimage_id(resultSet.getInt("productImage_id"));
				productimg.setProduct_Id(resultSet.getInt("product_id"));
				productimg.setImage_url(resultSet.getString("image_url"));
				productimg.setIs_Primary(resultSet.getBoolean("is_primary"));

				image.add(productimg);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}

		return image;
	}

}
