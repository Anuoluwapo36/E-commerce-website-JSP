

package com.centrum.servlet;



import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Collection;

import com.centrum.bean.Product;
import com.centrum.bean.ProductImages;
import com.centrum.dao.ProductDao;
import com.centrum.dao.SellerDAO;
import com.centrum.dao.CategoryDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;




@WebServlet("/product/add")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 25)
public class AddProductServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ProductDao productDao = new ProductDao();
	private SellerDAO sellerDAO = new SellerDAO();
	private CategoryDao categoryDao = new CategoryDao();



	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("seller") == null) {


			response.sendRedirect(request.getContextPath() + "/logins");
			return;

		}

		int sellerId = (Integer) session.getAttribute("seller");
		String shopName = sellerDAO.getShopNameBySellerId(sellerId);

		if (shopName == null || shopName.isBlank()) {


			shopName = "Your Shop";

		}

		String email = sellerDAO.loginSellerById(sellerId).getEmail();

		request.setAttribute("shopName", shopName);
		request.setAttribute("email", email);

		request.getRequestDispatcher("/WEB-INF/seller/view/add-product.jsp").forward(request, response);

	}



	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("seller") == null) {


			response.sendRedirect(request.getContextPath() + "/logins");
			return;

		}

		int sellerId = (Integer) session.getAttribute("seller");

		String productName = request.getParameter("productName");
		String description = request.getParameter("description");
		String categoryName = request.getParameter("category");
		String priceStr = request.getParameter("price");
		String stockStr = request.getParameter("stock");

		String errorMessage = null;

		if (productName == null || productName.trim().isEmpty()) {


			errorMessage = "Product name is required.";

		} else if (categoryName == null || categoryName.trim().isEmpty()) {


			errorMessage = "Category is required.";

		} else if (priceStr == null || priceStr.trim().isEmpty()) {


			errorMessage = "Price is required.";

		} else if (stockStr == null || stockStr.trim().isEmpty()) {


			errorMessage = "Stock quantity is required.";

		}

		double price = 0;
		int stockQuantity = 0;

		try {


			price = Double.parseDouble(priceStr);
			if (price < 0)
				errorMessage = "Price cannot be negative.";

		} catch (NumberFormatException e) {


			errorMessage = "Invalid price format.";

		}

		try {


			stockQuantity = Integer.parseInt(stockStr);
			if (stockQuantity < 0)
				errorMessage = "Stock quantity cannot be negative.";

		} catch (NumberFormatException e) {


			errorMessage = "Invalid stock quantity format.";

		}

		if (errorMessage != null) {


			request.setAttribute("error", errorMessage);
			doGet(request, response);
			return;

		}

		try {


			int categoryId = categoryDao.getCategoryIdByName(categoryName);

			if (categoryId == 0) {


				categoryId = categoryDao.addCategory(categoryName);

			}

			int subcategoryId = categoryDao.addSubcategoryIfNotExists("Default", categoryId);

			Product product = new Product();
			product.setSeller_id(sellerId);
			product.setProduct_name(productName.trim());
			product.setDescription(description != null ? description.trim() : "");
			product.setCategory(categoryName.trim());
			product.setPrice(price);
			product.setStock_quantity(stockQuantity);
			product.setCategory_id(categoryId);
			product.setSubcategory_id(subcategoryId);

			int productId = productDao.addProduct(product);

			Collection<Part> parts = request.getParts();
			boolean firstImage = true;
			int imageCount = 0;

			for (Part part : parts) {


				if (part.getName().equals("images") && part.getSize() > 0) {


					imageCount++;
					if (imageCount > 5)
						break;

					String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
					if (!fileName.matches("(?i).*\\.(jpg|jpeg|png|gif|bmp)"))
						continue;

					String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
					File uploadDir = new File(uploadPath);
					if (!uploadDir.exists())
						uploadDir.mkdir();

					part.write(uploadPath + File.separator + fileName);

					ProductImages image = new ProductImages();
					image.setProduct_Id(productId);
					image.setImage_url("uploads/" + fileName);
					image.setIs_Primary(firstImage);

					productDao.addProductImage(image);
					firstImage = false;

				}

			}

			response.sendRedirect(request.getContextPath() + "/product/add?success=Product added successfully");

		} catch (SQLException e) {


			e.printStackTrace();
			request.setAttribute("error", "Failed to add product: " + e.getMessage());
			doGet(request, response);

		}

	}



}
