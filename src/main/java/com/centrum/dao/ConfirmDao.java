package com.centrum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.centrum.bean.Product;
import com.centrum.connections.MySQLcnn;

public class ConfirmDao {
	
	/**
	 * this method help get product by id example from product-browsing.jsp when
	 * user seller the product this query the database and send this product to
	 * product to the product_details.jsp and it get just a single product not all
	 * product like i said that method get all product can't be use for all page
	 * 
	 * @param productId
	 * @return
	 * @throws SQLException
	 */
	public Product getProductbyId(int productId) throws SQLException {
		String sql = "SELECT product_id, seller_id, product_name, description, price,stock_quantity, category FROM product WHERE product_id = ?";
		MySQLcnn dbConnection = new MySQLcnn();
		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, productId);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {

				Product product = new Product();

				product.setProduct_id(resultSet.getInt("product_id"));
				product.setSeller_id(resultSet.getInt("seller_id"));
				product.setProduct_name(resultSet.getString("product_name"));
				product.setDescription(resultSet.getString("description"));
				product.setPrice(resultSet.getDouble("price"));
				product.setStock_quantity(resultSet.getInt("stock_quantity"));
				product.setCategory(resultSet.getString("category"));

				return product;
			} else {
				return null;

			}
		}
	}
	public List<Product> getProductsByCartIds(List<Integer> cartIds) throws SQLException {
	    if (cartIds == null || cartIds.isEmpty()) return new ArrayList<>();
	    
	    StringBuilder placeholders = new StringBuilder();
	    for (int i = 0; i < cartIds.size(); i++) {
	        placeholders.append("?");
	        if (i < cartIds.size() - 1) placeholders.append(",");
	    }

	    String sql = "SELECT product_id, seller_id, product_name, description, price, stock_quantity, category "
	               + "FROM product WHERE product_id IN (" + placeholders + ")";
	    
	    MySQLcnn dbConnection = new MySQLcnn();
	    List<Product> products = new ArrayList<>();
	    
	    try (Connection connection = dbConnection.getCnn();
	         PreparedStatement statement = connection.prepareStatement(sql)) {
	         
	        for (int i = 0; i < cartIds.size(); i++) {
	            statement.setInt(i + 1, cartIds.get(i));
	        }
	        
	        ResultSet rs = statement.executeQuery();
	        while (rs.next()) {
	            Product product = new Product();
	            product.setProduct_id(rs.getInt("product_id"));
	            product.setSeller_id(rs.getInt("seller_id"));
	            product.setProduct_name(rs.getString("product_name"));
	            product.setDescription(rs.getString("description"));
	            product.setPrice(rs.getDouble("price"));
	            product.setStock_quantity(rs.getInt("stock_quantity"));
	            product.setCategory(rs.getString("category"));
	            products.add(product);
	        }
	    }
	    return products;
	}


}
