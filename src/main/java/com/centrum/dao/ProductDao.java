package com.centrum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.centrum.bean.Product;
import com.centrum.bean.ProductImages;
import com.centrum.bean.RecommendProduct;
import com.centrum.connections.MySQLcnn;

public class ProductDao {

	/**
	 * productDao help query the database and send the data to our servlet related
	 * to this page.
	 * 
	 */

	/**
	 * this method get all product from the database and to the list using ArrayList
	 * to store and display all product in the product-browsing.jsp,
	 * search-product.jsp as well. this can't be use in all page why cause some page
	 * might need just only single product to display not all
	 * example(product_details.jsp)
	 * 
	 * @return
	 * @throws SQLException
	 */

	public List<Product> getAllProduct() throws SQLException {
		String sql = "SELECT product_id, product_name, description, price  FROM product ORDER BY RAND()";
		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			ArrayList<Product> list = new ArrayList<>();

			ResultSet resultset = statement.executeQuery();
			while (resultset.next()) {
				Product product = new Product();
				product.setProduct_id(resultset.getInt("product_id"));
				product.setProduct_name(resultset.getString("product_name"));
				product.setDescription(resultset.getString("description"));
				product.setPrice(resultset.getDouble("price"));

				list.add(product);
			}

			return list;

		}

	}

	/***
	 * this method is used to fetch product image, cause i change the table that
	 * store image for product why) cause seller seller product might have many
	 * picture for a single product with to show different option to buyer but this
	 * method is use for product_browsing.jsp and it have limit to display only
	 * single image that will show in the home page the primary key limit is (1)
	 * 
	 * @param productID
	 * @return
	 * @throws SQLException
	 */
	public String getPrimaryImageUrl(int productID) throws SQLException {
		String primaryImageUrl = null;
		String sql = "SELECT image_url FROM product_images WHERE product_id = ? AND  is_primary = 1 LIMIT 1";
		MySQLcnn dbConnection = new MySQLcnn();
		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, productID);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {

				primaryImageUrl = resultSet.getString("image_url");

			}
		}
		return primaryImageUrl;

	}

	/**
	 * this is connected with the java script and it query the database to get auto
	 * name suggestion for user while searching particular product make user
	 * friendly fast and efficient, the goal is to make the web friendly to users
	 * 
	 * @param query
	 * @return
	 * @throws SQLException
	 */

	public List<Product> getProductNameSuggestion(String query) throws SQLException {
		String sql = "SELECT product_name FROM product WHERE product_name LIKE ?";
		MySQLcnn dbConnection = new MySQLcnn();
		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			ArrayList<Product> suggestionList = new ArrayList<>();
			statement.setString(1, "%" + query + "%");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Product productName = new Product();
				productName.setProduct_name(resultSet.getString("product_name"));

				suggestionList.add(productName);

			}
			return suggestionList;

		}

	}

	/**
	 * fetch product from database after user search and display them in the search
	 * page fast and user friendly
	 * 
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public List<Product> searchProduct(String query) throws SQLException {
		String sql = "SELECT product_id , seller_id, product_name, description, price , stock_quantity, category FROM product WHERE product_name LIKE ? ";
		MySQLcnn dbConnection = new MySQLcnn();
		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			ArrayList<Product> searchProductList = new ArrayList<>();
			statement.setString(1, "%" + query + "%");
			ResultSet resultSet;
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Product searchItems = new Product();
				searchItems.setProduct_id(resultSet.getInt("product_id"));
				searchItems.setSeller_id(resultSet.getInt("seller_id"));
				searchItems.setProduct_name(resultSet.getString("product_name"));
				searchItems.setDescription(resultSet.getString("description"));
				searchItems.setPrice(resultSet.getDouble("price"));
				searchItems.setStock_quantity(resultSet.getInt("stock_quantity"));
				searchItems.setCategory(resultSet.getString("category"));

				searchProductList.add(searchItems);

			}
			return searchProductList;

		}

	}

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

	/**
	 * this display product with multiple image in product_details.jsp according one
	 * product id can have more than one image
	 * 
	 * @param productID
	 * @return
	 * @throws SQLException
	 */
	public List<ProductImages> getAllProductImages(int productID) throws SQLException {
		String sql = "SELECT productImage_id,product_id, image_url, is_primary FROM PRODUCT_images WHERE product_id = ?";
		MySQLcnn dbConnection = new MySQLcnn();
		List<ProductImages> productImages = new ArrayList<>();

		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, productID);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				ProductImages productImage = new ProductImages();
				productImage.setProductimage_id(resultSet.getInt("productImage_id"));
				productImage.setProduct_Id(resultSet.getInt("product_id"));
				productImage.setImage_url(resultSet.getString("image_url"));
				productImage.setIs_Primary(resultSet.getBoolean("is_primary"));
				productImages.add(productImage);
			}

		}

		return productImages;

	}

	/**
	 * this track how many time each product is view , i trying to create a
	 * recommendation system so when user click product this allow us to check and
	 * able to recommend to user of different product.
	 * 
	 * @param productId
	 * @throws SQLException
	 */

	public void incrementViewCount(int productId) throws SQLException {
		String sql = "UPDATE product SET view_count = view_count + 1 WHERE product_id = ?";
		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection conn = dbConnection.getCnn(); PreparedStatement statement = conn.prepareStatement(sql)) {
			statement.setInt(1, productId);
			statement.executeUpdate();
		}
	}

	/**
	 * Finds the most popular products in the system ones that lots of users have
	 * viewed and lists them as top viewed recommendations.
	 * 
	 * Only includes products that are in stock (have more than 0 items) and have
	 * been viewed at least once. Results are sorted by how many times they’ve been
	 * viewed (most views first) and limited to 50 products.
	 * 
	 * Each recommendation includes the product’s details (ID, name, price,
	 * description, view count) and its primary image (or a default image if none
	 * exists).
	 * 
	 * @return a list of RecommendProduct objects with popular products and their
	 *         primary images
	 * @throws SQLException if there’s a problem connecting to or querying the
	 *                      database
	 */
	public List<RecommendProduct> recommendationSystem() throws SQLException {
		String sql = "SELECT p.product_id, p.product_name,p.price, p.description,p.view_count, "
				+ "pi.image_url FROM product p LEFT JOIN(SELECT product_id,image_url FROM product_images WHERE is_primary = true) "
				+ "pi ON p.product_id = pi.product_id WHERE "
				+ " p.product_id != 0 AND p.stock_quantity > 0  And view_count > 0 ORDER  BY p.view_count DESC LIMIT 50";

		MySQLcnn dbConnection = new MySQLcnn();

		List<RecommendProduct> recommendations = new ArrayList<>();

		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Product product = new Product();
				product.setProduct_id(resultSet.getInt("product_id"));
				product.setProduct_name(resultSet.getString("product_name"));
				product.setPrice(resultSet.getDouble("price"));
				product.setDescription(resultSet.getString("description"));
				product.setView_count(resultSet.getInt("view_count"));

				ProductImages primaryImage = new ProductImages();
				String imageUrl = resultSet.getString("image_url");

				primaryImage.setImage_url(imageUrl != null ? imageUrl : "/product/Image/centrum_default.svg");

				primaryImage.setIs_Primary(true);
				primaryImage.setProduct_Id(resultSet.getInt("product_id"));

				RecommendProduct recon = new RecommendProduct(product, primaryImage);
				recommendations.add(recon);

			}
		}
		return recommendations;

	}

	/**
	 * Fetches a list of Product objects from the database filtered by the specified
	 * category.
	 * 
	 * <p>
	 * This method executes a parameterized SQL query to retrieve all products where
	 * the 'category' column matches the input category string. It maps the
	 * ResultSet from the query to Product objects, populating core fields (ID,
	 * name, price, category, stock quantity, image URL) and returns the list of
	 * matching products. If no products match the category, an empty list is
	 * returned.
	 * 
	 * <p>
	 * Handles SQL exceptions by printing the stack trace (for debugging) and
	 * re-throwing the exception to allow upstream error handling (e.g., in
	 * servlets) for user-friendly error messages.
	 * 
	 * @param category The product category to filter by (e.g., "Phones", "Laptops",
	 *                 "MenClothing"). Must match the values stored in the
	 *                 'category' column of the 'product' table.
	 * @return A non-null List<Product> containing all products matching the
	 *         specified category. Returns an empty list if no matches are found.
	 * @throws SQLException If a database access error occurs (e.g., invalid SQL,
	 *                      connection failure, or column name mismatch). Re-thrown
	 *                      to upstream caller for handling.
	 */

	public List<Product> fetchByCategory(String categoryName) throws SQLException {

		List<Product> filteredProducts = new ArrayList<>();
		String sql = "SELECT p.*, c.category_name " + 
                "FROM product p " +
                "JOIN categories c ON p.category_id = c.category_id " + 
                "WHERE c.category_name = ?";
		MySQLcnn dbConnection = new MySQLcnn();

		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, categoryName.trim());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Product product = new Product();
				product.setProduct_id(resultSet.getInt("product_id"));
				product.setProduct_name(resultSet.getString("product_name"));
				product.setPrice(resultSet.getDouble("price"));
				product.setCategory(resultSet.getString("category"));
				product.setStock_quantity(resultSet.getInt("stock_quantity"));
				product.setImage_url(resultSet.getString("image_url"));
				
	            product.setCategory(resultSet.getString("category_name")); 

				filteredProducts.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return filteredProducts;

	}

	/**
	 * Retrieves the top 3 most purchased (best-selling) products from the last 7
	 * days.
	 * 
	 * <p>
	 * This method joins three tables to get complete product data with sales
	 * counts: 1. `product`: Core product details (ID, name, price). 2. `payment`:
	 * Tracks sales (uses `product_id` to link to products and `payment_date` to
	 * filter weekly sales). 3. `product_image`: Retrieves the primary image for
	 * each product (via `is_primary = 1` flag).
	 * 
	 * <p>
	 * Key logic: - Filters sales to only the last 7 days using `CURRENT_DATE -
	 * INTERVAL 7 DAY`. - Counts total sales per product with `COUNT(pa.product_id)
	 * AS total_sales`. - Groups results by product to ensure one row per product. -
	 * Sorts by total sales (descending) and limits to top 3 products. - Uses `LEFT
	 * JOIN` for product images to include products with no primary image (falls
	 * back to default SVG).
	 * 
	 * @return A non-null List<Product> containing up to 3 products (top weekly
	 *         sellers). Returns an empty list if: - No products were sold in the
	 *         last 7 days. - No products match the join conditions (e.g., no
	 *         primary image).
	 * @throws SQLException If a database access error occurs, including: - Invalid
	 *                      SQL syntax (fixed in corrected code). - Missing
	 *                      table/columns (verify `product`, `payment`,
	 *                      `product_image` exist). - Connection failure (check
	 *                      MySQLcnn configuration). Re-thrown to allow upstream
	 *                      error handling.
	 */

	public List<Product> getMostPurchasedWeekly() throws SQLException {
		List<Product> bannertopSales = new ArrayList<>();
		String sql = "SELECT " + "p.product_id, " + "p.product_name, " + "i.image_url, "
				+ "COUNT(oi.product_id) AS total_sales " 
				+"FROM product p "
				+"JOIN order_item oi ON p.product_id = oi.product_id "
				+ "JOIN payment pa ON oi.order_id = pa.order_id "
				+ "LEFT JOIN product_images i ON p.product_id = i.product_id AND i.is_primary = 1 "
				+ "WHERE pa.payment_date >= CURRENT_DATE - INTERVAL 7 DAY "
				+ "GROUP BY p.product_id, p.product_name, i.image_url " + "ORDER BY total_sales DESC " + "LIMIT 8";

		MySQLcnn dbConnection = new MySQLcnn();
		try (Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			int count = 0;
			while (resultSet.next()) {
				Product product = new Product();
				product.setProduct_id(resultSet.getInt("product_id"));
				product.setProduct_name(resultSet.getString("product_name"));

				String imageUrl = resultSet.getString("image_url");
				product.setImage_url(imageUrl != null ? imageUrl : "/product/Image/centrum_default.svg");

				bannertopSales.add(product);
				count++;
				System.out.println("DAO: Fetched product - ID: " + product.getProduct_id() + ", Name: "
						+ product.getProduct_name());

			}
	        System.out.println("DAO: Total top products fetched: " + count); 
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return bannertopSales;

	}
	
	
	/**
	 * Retrieves a list of "You May Also Like" product recommendations for a given product.
	 * Recommendations are based on products in the SAME subcategory as the current product,
	 * sorted by popularity (highest purchase count first), and exclude the current product itself.
	 * Uses the `alsolike` view to simplify data retrieval (pre-joins product and product image data).
	 *
	 * @param currentproductId The ID of the product the user is currently viewing (source of recommendations)
	 * @return A List of Product objects containing recommended products (max 5 items).
	 *         Returns an empty list if no matching recommendations are found (e.g., no other products in the subcategory).
	 * @throws SQLException If a database access error occurs (e.g., invalid SQL, connection issues, missing parameters)
	 */
	public  List<Product> mayalsoLike (int currentproductId)throws SQLException {
		List<Product> recommendendProduct = new ArrayList<>();
		String sql = "SELECT product_id , product_name , price, image_url FROM alsolike "
				+ "WHERE subcategory_id = ( SELECT subcategory_id FROM alsolike WHERE product_id = ?) "
				+ "AND product_id != ? ORDER BY purchase_count DESC LIMIT 5 ";
		
		MySQLcnn dbConnection = new MySQLcnn();
		
		
		try(Connection connection = dbConnection.getCnn();
				PreparedStatement statement = connection.prepareStatement(sql) ){
			
			statement.setInt(1, currentproductId);
			statement.setInt(2, currentproductId);

			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				
				Product mayLike = new Product();
				mayLike.setProduct_id(resultSet.getInt("product_id"));
				mayLike.setProduct_name(resultSet.getString("product_name"));
				mayLike.setPrice(resultSet.getDouble("price"));
				mayLike.setImage_url(resultSet.getString("image_url"));
				
				recommendendProduct.add(mayLike);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
		return recommendendProduct;
		
	}
	
	
	//seller start addoing product 
	
	public int addProduct(Product product) throws SQLException {
	    String sql = "INSERT INTO product (seller_id, product_name, description, price, stock_quantity, category, category_id, subcategory_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	    MySQLcnn dbConnection = new MySQLcnn();

	    try (Connection connection = dbConnection.getCnn();
	         PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

	        statement.setInt(1, product.getSeller_id());
	        statement.setString(2, product.getProduct_name());
	        statement.setString(3, product.getDescription());
	        statement.setDouble(4, product.getPrice());
	        statement.setInt(5, product.getStock_quantity());
	        statement.setString(6, product.getCategory());
	        statement.setInt(7, product.getCategory_id());
	        statement.setInt(8, product.getSubcategory_id());

	        int rows = statement.executeUpdate();
	        if (rows > 0) {
	            ResultSet rs = statement.getGeneratedKeys();
	            if (rs.next()) {
	                return rs.getInt(1);
	        }
	    }
	    return 0;
	    }
	}
	    public boolean addProductImage(ProductImages image) throws SQLException {
	        String sql = "INSERT INTO product_images (product_id, image_url, is_primary) VALUES (?, ?, ?)";
	        MySQLcnn dbConnection = new MySQLcnn();

	        try (Connection connection = dbConnection.getCnn();
	             PreparedStatement statement = connection.prepareStatement(sql)) {

	            statement.setInt(1, image.getProduct_Id());
	            statement.setString(2, image.getImage_url());
	            statement.setBoolean(3, image.isIs_Primary());

	            return statement.executeUpdate() > 0;
	        }
	    }
	    /**
	     * Fetches all products for a specific seller, including their primary image.
	     * This method is used for seller dashboard (products.jsp) to display product list.
	     *
	     * @param sellerId ID of the seller
	     * @return List<Product> containing all products for this seller with primary image set
	     * @throws SQLException if a database access error occurs
	     */
	    public List<Product> getProductsBySeller(int sellerId) throws SQLException {
	        String sql = "SELECT p.product_id, p.product_name, p.stock_quantity, p.price, " +
	                     "c.category_name, pi.image_url AS primary_image " +
	                     "FROM product p " +
	                     "JOIN categories c ON p.category_id = c.category_id " +
	                     "LEFT JOIN product_images pi ON p.product_id = pi.product_id AND pi.is_primary = 1 " +
	                     "WHERE p.seller_id = ? " +
	                     "ORDER BY p.product_id DESC";

	        MySQLcnn dbConnection = new MySQLcnn();
	        List<Product> products = new ArrayList<>();

	        try (Connection connection = dbConnection.getCnn();
	             PreparedStatement statement = connection.prepareStatement(sql)) {

	            statement.setInt(1, sellerId);
	            ResultSet rs = statement.executeQuery();

	            while (rs.next()) {
	                Product product = new Product();
	                product.setProduct_id(rs.getInt("product_id"));
	                product.setProduct_name(rs.getString("product_name"));
	                product.setStock_quantity(rs.getInt("stock_quantity"));
	                product.setPrice(rs.getDouble("price"));
	                product.setCategory(rs.getString("category_name"));

	                String imageUrl = rs.getString("primary_image");
	                product.setImage_url(imageUrl != null ? imageUrl : "/product/Image/centrum_default.svg");

	                products.add(product);
	            }
	        }

	        return products;
	    }
	    /**
	     * Deletes a product and all its associated images for a given seller.
	     * Ensures that only the seller who owns the product can delete it.
	     *
	     * @param productId ID of the product to delete
	     * @param sellerId  ID of the seller attempting the deletion
	     * @return true if the product was deleted successfully, false otherwise
	     * @throws SQLException if a database access error occurs
	     */
	    public boolean deleteProductWithImages(int productId, int sellerId) throws SQLException {
	        // SQL to delete images only if product belongs to the seller
	        String deleteImagesSQL = "DELETE FROM product_images " +
	                                 "WHERE product_id = ? AND product_id IN " +
	                                 "(SELECT product_id FROM product WHERE seller_id = ?)";
	        
	        String deleteProductSQL = "DELETE FROM product WHERE product_id = ? AND seller_id = ?";

	        MySQLcnn dbConnection = new MySQLcnn();

	        try (Connection connection = dbConnection.getCnn()) {
	            connection.setAutoCommit(false); 
	            try (PreparedStatement psImages = connection.prepareStatement(deleteImagesSQL);
	                 PreparedStatement psProduct = connection.prepareStatement(deleteProductSQL)) {

	                psImages.setInt(1, productId);
	                psImages.setInt(2, sellerId);
	                psImages.executeUpdate();

	                psProduct.setInt(1, productId);
	                psProduct.setInt(2, sellerId);
	                int affectedRows = psProduct.executeUpdate();

	                connection.commit();
	                return affectedRows > 0;

	            } catch (SQLException e) {
	                connection.rollback(); 
	                e.printStackTrace();
	                return false;
	            } finally {
	                connection.setAutoCommit(true);
	            }
	        }
	    }

	    /**
	     * Fetch all image paths for a product.
	     * Used to delete actual image files from server after database deletion.
	     *
	     * @param productId ID of the product
	     * @return List of image URLs
	     * @throws SQLException
	     */
	    public List<String> getAllImagePaths(int productId) throws SQLException {
	        List<String> paths = new ArrayList<>();
	        String sql = "SELECT image_url FROM product_images WHERE product_id = ?";
	        MySQLcnn dbConnection = new MySQLcnn();

	        try (Connection connection = dbConnection.getCnn();
	             PreparedStatement ps = connection.prepareStatement(sql)) {

	            ps.setInt(1, productId);
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                paths.add(rs.getString("image_url"));
	            }
	        }
	        return paths;
	    }

	    public boolean updateProduct(Product product) throws SQLException {
	        String sql = "UPDATE product SET product_name = ?, description = ?, price = ?, "
	                   + "stock_quantity = ?, category = ?, category_id = ?, subcategory_id = ? "
	                   + "WHERE product_id = ?";

	        MySQLcnn dbConnection = new MySQLcnn();

	        try (Connection connection = dbConnection.getCnn();
	             PreparedStatement ps = connection.prepareStatement(sql)) {

	            ps.setString(1, product.getProduct_name());
	            ps.setString(2, product.getDescription());
	            ps.setDouble(3, product.getPrice());
	            ps.setInt(4, product.getStock_quantity());
	            ps.setString(5, product.getCategory());
	            ps.setInt(6, product.getCategory_id());
	            ps.setInt(7, product.getSubcategory_id());
	            ps.setInt(8, product.getProduct_id());

	            return ps.executeUpdate() > 0;
	        }
	    }




}
