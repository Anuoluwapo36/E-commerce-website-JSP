package com.centrum.servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Collection;

import com.centrum.bean.Product;
import com.centrum.bean.ProductImages;
import com.centrum.dao.CategoryDao;
import com.centrum.dao.ProductDao;
import com.centrum.dao.SellerDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/product/edit")
@MultipartConfig(fileSizeThreshold = 1024*1024, maxFileSize = 1024*1024*5, maxRequestSize = 1024*1024*25)
public class EditProductServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ProductDao productDao;
    private CategoryDao categoryDao;
    private SellerDAO sellerDAO;

    @Override
    public void init() throws ServletException {
        productDao = new ProductDao();
        categoryDao = new CategoryDao();
        sellerDAO = new SellerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("seller") == null) {
            response.sendRedirect(request.getContextPath() + "/logins");
            return;
        }

        int sellerId = (Integer) session.getAttribute("seller");

        String productIdStr = request.getParameter("id");
        if (productIdStr == null) {
            response.sendRedirect(request.getContextPath() + "/products/dashboard");
            return;
        }

        try {
            int productId = Integer.parseInt(productIdStr);
            Product product = productDao.getProductbyId(productId);

            if (product == null || product.getSeller_id() != sellerId) {
                response.sendRedirect(request.getContextPath() + "/products/dashboard");
                return;
            }

            request.setAttribute("product", product);
            request.getRequestDispatcher("/WEB-INF/seller/view/edit-product.jsp").forward(request, response);

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/products/dashboard?error=Cannot fetch product");
        }
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

        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            Product product = productDao.getProductbyId(productId);

            if (product == null || product.getSeller_id() != sellerId) {
                response.sendRedirect(request.getContextPath() + "/products/dashboard");
                return;
            }

            String productName = request.getParameter("productName");
            String description = request.getParameter("description");
            String categoryName = request.getParameter("category");
            double price = Double.parseDouble(request.getParameter("price"));
            int stockQuantity = Integer.parseInt(request.getParameter("stock"));

            int categoryId = categoryDao.getCategoryIdByName(categoryName);
            if (categoryId == 0) {
                categoryId = categoryDao.addCategory(categoryName);
            }
            int subcategoryId = categoryDao.addSubcategoryIfNotExists("Default", categoryId);

            product.setProduct_name(productName);
            product.setDescription(description);
            product.setCategory(categoryName);
            product.setPrice(price);
            product.setStock_quantity(stockQuantity);
            product.setCategory_id(categoryId);
            product.setSubcategory_id(subcategoryId);

            productDao.updateProduct(product);

            Collection<Part> parts = request.getParts();
            boolean firstImage = true;
            int imageCount = 0;
            for (Part part : parts) {
                if (part.getName().equals("images") && part.getSize() > 0) {
                    imageCount++;
                    if (imageCount > 5) break;

                    String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    if (!fileName.matches("(?i).*\\.(jpg|jpeg|png|gif|bmp)")) continue;

                    String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) uploadDir.mkdir();

                    part.write(uploadPath + File.separator + fileName);

                    ProductImages image = new ProductImages();
                    image.setProduct_Id(productId);
                    image.setImage_url("uploads/" + fileName);
                    image.setIs_Primary(firstImage);

                    productDao.addProductImage(image);
                    firstImage = false;
                }
            }

            response.sendRedirect(request.getContextPath() + "/products/dashboard?success=Product updated successfully");

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to update product: " + e.getMessage());
            doGet(request, response);
        }
    }
}
