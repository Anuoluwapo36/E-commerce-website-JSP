package com.centrum.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

import com.centrum.bean.User;
import com.centrum.bean.UserProfileImages;
import com.centrum.bean.User_address;
import com.centrum.dao.UserDao;
import com.centrum.util.AddressService;
import com.centrum.util.UserProfileServices;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ProfileServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	
	
	private static final String LOGIN_PAGE = "/login-user.jsp";


	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try {
			userDao = new UserDao();
		} catch (Exception e) {
			throw new ServletException("falled to initilize userDao: ", e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doGet(req, resp);
		
		
		fetchUserInformation(request, response);
	}

	
	
	/**
     * Servlet to handle the user profile page.
     * - Responds to GET requests (loads profile data).
     * - Validates user session (redirects to login if not authenticated).
     * - Fetches user data via UserDao and forwards to profile.jsp for display.
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void fetchUserInformation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if(loggedInUser == null) {
			String encodedError = URLEncoder.encode("Account not found", "UTF-8");
			response.sendRedirect(request.getContextPath() + LOGIN_PAGE + "?error=" + encodedError);
			return;
		}
		
		try {
			User user = null;
			UserProfileImages userProfileImages = null;
			UserProfileServices profileServices = new UserProfileServices();

			user = userDao.getUserById(loggedInUser.getUser_id());
			
			if(user == null) {
				session.invalidate();
				response.sendRedirect(request.getContextPath() + LOGIN_PAGE + "?error= Accoubt not found");
				return;
			}
			User_address address = AddressService.getDefaultAddress(loggedInUser);
			userProfileImages=profileServices.getDefaultProfile(loggedInUser.getUser_id());
			request.setAttribute("address", address);

			request.setAttribute("profileUser", user);
			if(userProfileImages == null) {
			    request.setAttribute("error", "No profile image set. Upload one in settings.");
				
			}else {
				request.setAttribute("userImages", userProfileImages);

			}
			
			
			request.getRequestDispatcher("/buyer/profile.jsp").forward(request, response);

			
			
			
		}catch(SQLException  e) {
			e.printStackTrace();
		    request.setAttribute("profileError", "Failed to load your profile. Please try again later.");

		}
		

	}

}
