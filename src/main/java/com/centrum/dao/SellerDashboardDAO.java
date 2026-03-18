

package com.centrum.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.centrum.bean.Order;
import com.centrum.bean.Order_items;
import com.centrum.bean.Product;
import com.centrum.connections.MySQLcnn;




public class SellerDashboardDAO {

	private MySQLcnn db = new MySQLcnn();



	public int getTotalOrders(int sellerId) {

		String sql = "SELECT COUNT(DISTINCT o.order_id) AS total " + "FROM orders o "
				+ "JOIN order_item oi ON o.order_id = oi.order_id "
				+ "JOIN product p ON oi.product_id = p.product_id " + "WHERE p.seller_id = ?";

		try (Connection conn = db.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setInt(1, sellerId);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt("total");

		} catch (Exception e) {


			e.printStackTrace();

		}

		return 0;

	}



	public double getTotalRevenue(int sellerId) {

		String sql = "SELECT SUM(oi.price * oi.quantity) AS revenue " + "FROM order_item oi "
				+ "JOIN product p ON oi.product_id = p.product_id " + "WHERE p.seller_id = ?";

		try (Connection conn = db.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setInt(1, sellerId);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return rs.getDouble("revenue");

		} catch (Exception e) {


			e.printStackTrace();

		}

		return 0;

	}



	public int getTotalProducts(int sellerId) {

		String sql = "SELECT COUNT(*) AS total FROM product WHERE seller_id = ?";

		try (Connection conn = db.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setInt(1, sellerId);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt("total");

		} catch (Exception e) {


			e.printStackTrace();

		}

		return 0;

	}



	

	public List<Order> getRecentOrders(int sellerId, int limit) {

	    List<Order> orders = new ArrayList<>();
	    String sqlOrders = "SELECT DISTINCT o.order_id, o.user_id, o.total_amount, o.status, o.create_at " +
	                       "FROM orders o " +
	                       "JOIN order_item oi ON o.order_id = oi.order_id " +
	                       "JOIN product p ON oi.product_id = p.product_id " +
	                       "WHERE p.seller_id = ? " +
	                       "ORDER BY o.create_at DESC LIMIT ?";

	    try (Connection conn = db.getCnn(); PreparedStatement ps = conn.prepareStatement(sqlOrders)) {

	        ps.setInt(1, sellerId);
	        ps.setInt(2, limit);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Order order = new Order();
	            order.setOrder_id(rs.getInt("order_id"));
	            order.setUser_id(rs.getInt("user_id"));
	            order.setTotal_amount(rs.getDouble("total_amount"));
	            order.setStatus(rs.getString("status"));
	            order.setCreate_at(rs.getTimestamp("create_at"));

	            List<Product> products = new ArrayList<>();

	            String sqlItems = "SELECT oi.*, p.product_name, pi.image_url " +
	                              "FROM order_item oi " +
	                              "JOIN product p ON oi.product_id = p.product_id " +
	                              "LEFT JOIN product_images pi ON p.product_id = pi.product_id AND pi.is_primary = 1 " +
	                              "WHERE oi.order_id = ?";

	            try (PreparedStatement psItems = conn.prepareStatement(sqlItems)) {
	                psItems.setInt(1, order.getOrder_id());
	                ResultSet rsItems = psItems.executeQuery();

	                while (rsItems.next()) {
	                    Product p = new Product();
	                    p.setProduct_id(rsItems.getInt("product_id"));
	                    p.setProduct_name(rsItems.getString("product_name"));
	                    p.setPrice(rsItems.getDouble("price"));
	                    p.setImage_url(rsItems.getString("image_url")); 

	                    Order_items oi = new Order_items();
	                    oi.setOrder_item_id(rsItems.getInt("order_item_id"));
	                    oi.setQuantity(rsItems.getInt("quantity"));
	                    oi.setPrice(rsItems.getDouble("price"));
	                    oi.setProduct(p);

	                    p.setOrderItem(oi);
	                    products.add(p);
	                }
	            }

	            order.setProducts(products);
	            orders.add(order);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return orders;
	}




	public List<Product> getLowStockProducts(int sellerId, int threshold) {

		List<Product> products = new ArrayList<>();
		String sql = "SELECT * FROM product WHERE seller_id = ? AND stock_quantity <= ?";

		try (Connection conn = db.getCnn(); PreparedStatement ps = conn.prepareStatement(sql)) {


			ps.setInt(1, sellerId);
			ps.setInt(2, threshold);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {


				Product p = new Product();
				p.setProduct_id(rs.getInt("product_id"));
				p.setProduct_name(rs.getString("product_name"));
				p.setStock_quantity(rs.getInt("stock_quantity"));
				p.setPrice(rs.getDouble("price"));
				products.add(p);

			}

		} catch (Exception e) {


			e.printStackTrace();

		}

		return products;

	}



}
