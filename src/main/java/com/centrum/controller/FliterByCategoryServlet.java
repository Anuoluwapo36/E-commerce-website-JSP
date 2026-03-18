package com.centrum.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.centrum.bean.Product;
import com.centrum.dao.ProductDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FliterByCategoryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private ProductDao productDao;
	private static final String BROWSING_PAGE = "/buyer/productbrowsing.jsp";
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try {
			productDao = new ProductDao();
		}catch(Exception e) {
			throw new ServletException ("failed to initilize productDao for fliter by category: ", e);
		}
		
	}
	
	@Override
	protected void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doGet(req, resp);
		
		filterCategory(request,response);
	}
	
	
	
	 /* <p>Steps performed:
		 * 1. Extracts the "category" parameter from the HTTP request (sent by the sidebar filter links).
		 * 2. Uses ProductDao to fetch all products matching the specified category.
		 * 3. Populates each product's image URL (uses default image if no custom image exists).
		 * 4. Sets the filtered product list as a request attribute for the browsing page to display.
		 * 5. Forwards the request/response to the pre-defined browsing page (BROWSING_PAGE constant).
		 * 
		 * <p>Error handling: Catches SQLException (database access errors), sets an error message attribute,
		 * and forwards to the error page to notify users of the failure.
		 * 
		 * @param request  HTTP request object containing the "category" parameter from the sidebar filter.
		 * @param response HTTP response object used to forward to the browsing/error page.
		 * @throws ServletException If a servlet-related error occurs during request forwarding.
		 * @throws IOException      If an I/O error occurs during request forwarding.
		 * @see ProductDao#fetchByCategory(String) To retrieve products by category from the database.
		 * @see ProductDao#getPrimaryImageUrl(int) To fetch the primary image URL for each product.
		 */
	private void filterCategory(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException{
		
		String categoryName = request.getParameter("category");
		if(categoryName == null || categoryName.trim().isEmpty()) {
			response.sendRedirect(request.getContextPath() + BROWSING_PAGE);
			return;
		}
		
		try {
			List<Product> fliterProduct = productDao.fetchByCategory(categoryName.trim());
			
			for(Product p : fliterProduct) {
				String imgUrl=productDao.getPrimaryImageUrl(p.getProduct_id());
				p.setImage_url(imgUrl != null ? imgUrl : "/product/Image/centrum_default.svg");
			}
			
			
		    List<Product> banner = productDao.getMostPurchasedWeekly();
		    request.setAttribute("banner", banner);
		    
			request.setAttribute("productList", fliterProduct);
			request.setAttribute("currentCategory", categoryName);
			request.getRequestDispatcher(BROWSING_PAGE).forward(request, response);
		}catch(SQLException e) {
			e.printStackTrace();
            request.setAttribute("error", "Failed to load products for category: " + categoryName);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
		
		
	
	}

}
