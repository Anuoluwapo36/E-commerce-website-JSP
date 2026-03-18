package com.centrum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.centrum.bean.Cart;
import com.centrum.connections.MySQLcnn;

public class CartDao {

	/**
	 * this method here crate the magic i have been working on after two days face
	 * lot of issue before i get it right and with tons of research this method
	 * receive accept the incoming value coming from cart servlet and and add them
	 * to correct column and row in the cart table.
	 * 
	 * @param cart
	 * @throws SQLException
	 */

	public void addCart(Cart cart) throws SQLException {
		String sql = "INSERT INTO carts(user_id ,product_id ,cart_status, quantity, added_at)" + " VALUE(?,?,?,?,?)";

		MySQLcnn dbConnection = new MySQLcnn();
		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, cart.getUser_id());
			statement.setInt(2, cart.getProduct_id());
			statement.setString(3, cart.getCart_status());
			statement.setInt(4, cart.getQuantity());
			statement.setTimestamp(5, cart.getAdded_at());

			statement.executeUpdate();
		}
	}

	/**
	 * after cart had been had to database when user click add to cart this method
	 * here enable all to get all the data from the cart table and display it in our
	 * jsp page this way user can see there items they add to cart
	 *
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public List<Cart> getAllCart(int userId) throws SQLException {

		String sql = "SELECT c.* , p.product_name, p.price, pi.image_url FROM carts c"
				+ " JOIN product p ON c.product_id = p.product_id "
				+ "LEFT JOIN(SELECT product_id, MAX(image_url) AS image_url FROM product_images "
				+ "GROUP BY product_id) pi ON p.product_id = pi.product_id"
				+ " WHERE c.user_id=? AND c.cart_status='active'";

		MySQLcnn dbConnection = new MySQLcnn();
		List<Cart> cartItems = new ArrayList<>();

		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, userId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Cart items = new Cart();

				items.setCart_id(resultSet.getInt("cart_id"));
				items.setUser_id(resultSet.getInt("user_id"));
				items.setProduct_id(resultSet.getInt("product_id"));
				items.setQuantity(resultSet.getInt("quantity"));
				items.setProduct_name(resultSet.getString("product_name"));
				items.setPrice(resultSet.getDouble("price"));
				items.setImage_url(resultSet.getString("image_url"));
				items.setAdded_at(resultSet.getTimestamp("added_at"));
				System.out.println("DAO: User ID = " + userId);
				System.out.println("DAO: Cart Items Found = " + cartItems.size());

				cartItems.add(items);

			}

		}

		return cartItems;

	}

	/**
	 * use to delete product from cart and to delete we use the cart id this get the
	 * cart id from the cart table and delete the row of product
	 * 
	 * @param cartId
	 * @throws SQLException
	 */
	public void deleteCartById(int cartId) throws SQLException {
		String sql = "DELETE FROM carts WHERE cart_Id =?";
		MySQLcnn dbConnection = new MySQLcnn();
		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, cartId);
			statement.executeUpdate();
		}

	}

	/**
	 * in the font end in cart page their is a check-box to clear all cart user have
	 * this method is to serve the purpose this method delete cart base on logged in
	 * user,it target a user who login and delete there cart base on there command
	 * and return back number of rows that was deleted from the database next will
	 * implement this method in cartServlet to carry out the execution for deleting
	 * all cart data.
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */

	public int deleteAllCartsByUser(int userId) throws SQLException {
		String sql = "DELETE FROM carts WHERE user_id = ?";
		MySQLcnn dbConnection = new MySQLcnn();
		int rowDeleted = 0;
		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, userId);
			rowDeleted = statement.executeUpdate();

		}
		return rowDeleted;

	}

	/**
	 * this is to get get price display in the page
	 * 
	 * @param price
	 * @return
	 * @throws SQLException
	 */


	/**
	 * this method purpose check if user already has a active cart entry for
	 * specific product prevent duplicate cart entries when user trying to add
	 * product to their cart this method check if product already in the active cart
	 * 
	 * @param userId
	 * @param productId
	 * @return
	 * @throws SQLException
	 */

	public Cart findCartItems(int userId, int productId) throws SQLException {
		String sql = "SELECT cart_id, quantity FROM carts WHERE user_id = ? AND product_id =? AND cart_status ='active'";
		MySQLcnn dbConnection = new MySQLcnn();
		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, userId);
			statement.setInt(2, productId);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				Cart items = new Cart();
				items.setCart_id(resultSet.getInt("cart_id"));
				items.setQuantity(resultSet.getInt("quantity"));
				return items;

			}

		}
		return null;
	}

	/**
	 * this method is used you adjust the quantity of an existing product in user
	 * active cart when they add same product again
	 * 
	 * @param cartId
	 * @param newTotalQuantity
	 * @throws SQLException
	 */

	public void updateCartQuantity(int cartId, int newTotalQuantity) throws SQLException {
		String sql = "UPDATE carts SET quantity =? WHERE cart_id =? AND cart_status = 'active' ";
		MySQLcnn dbConnection = new MySQLcnn();
		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, newTotalQuantity);
			statement.setInt(2, cartId);
			statement.executeUpdate();

		}
	}
	
	public List<Cart> getActiveCartItemsByUser(int userId) throws SQLException {
	    String sql = "SELECT c.product_id, c.quantity, p.price " +
	                 "FROM carts c JOIN product p ON c.product_id = p.product_id " +
	                 "WHERE c.user_id = ? AND c.cart_status = 'active'";

	    List<Cart> cartItems = new ArrayList<>();
	    MySQLcnn db = new MySQLcnn();

	    try (Connection con = db.getCnn();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, userId);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Cart c = new Cart();
	            c.setProduct_id(rs.getInt("product_id"));
	            c.setQuantity(rs.getInt("quantity"));
	            c.setPrice(rs.getDouble("price"));
	            cartItems.add(c);
	        }
	    }
	    return cartItems;
	}

	
	public Cart getCartById(int cartId) throws SQLException {
	    String sql = "SELECT c.*, p.price, p.product_name FROM carts c "
	               + "JOIN product p ON c.product_id = p.product_id "
	               + "WHERE c.cart_id = ?";
	    MySQLcnn db = new MySQLcnn();

	    try (Connection con = db.getCnn();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, cartId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            Cart c = new Cart();
	            c.setCart_id(rs.getInt("cart_id"));
	            c.setUser_id(rs.getInt("user_id"));
	            c.setProduct_id(rs.getInt("product_id"));
	            c.setQuantity(rs.getInt("quantity"));
	            c.setPrice(rs.getDouble("price"));
	            c.setProduct_name(rs.getString("product_name"));
	            return c;
	        }
	    }
	    return null;
	}

}
