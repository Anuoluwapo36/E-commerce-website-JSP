package com.centrum.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.Instant;

import com.centrum.bean.Logout;
import com.centrum.bean.User;
import com.centrum.dao.LogoutDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogOutServlet extends HttpServlet {

	/**
	 * Servlet for handling user logout functionality.
	 * Invalidates the user's session, records logout details (IP, user agent, timestamp) to the database,
	 * and redirects the user to the login page.
	 */
	
	
	private static final long serialVersionUID = 1L;
	private LogoutDao logoutDao;
	private static final String LOGIN_PAGE = "/login-user.jsp";

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try {
			logoutDao = new LogoutDao();

		} catch (Exception e) {
			throw new ServletException("failed to initilie logoutDao :", e);
		}
	}
	
	
    /**
     * Handles HTTP GET requests for user logout.
     * Delegates logout logic to the {@link #saveLogout(HttpServletRequest, HttpServletResponse)} method.
     * Triggered when the user clicks a logout link (e.g., {@code <a href="/logout">LogOut</a>}).
     *
     * @param request  The {@link HttpServletRequest} object containing client request data:
     *                 - Session data (logged-in user details)
     *                 - Request headers (X-Forwarded-For, User-Agent)
     * @param response The {@link HttpServletResponse} object for sending responses to the client:
     *                 - Redirect to login page
     * @throws ServletException If the servlet encounters a configuration or runtime error (e.g., LogoutDao initialization failure)
     * @throws IOException      If an I/O error occurs while handling the request/response (e.g., redirect failure)
     */

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		saveLogout(request, response);
	}
	
	
    /**
     * Core logout logic:
     * 1. Extracts the logged-in user from the session (if valid)
     * 2. Collects user metadata (IP address, user agent)
     * 3. Saves logout details to the database via {@link LogoutDao}
     * 4. Invalidates the user's session (even if database save fails)
     * 5. Redirects to the login page with a success message
     *
     * @param request  The {@link HttpServletRequest} object for accessing session, headers, and attributes
     * @param response The {@link HttpServletResponse} object for redirecting and setting response data
     * @throws ServletException If a servlet-related error occurs (e.g., invalid session state)
     * @throws IOException      If an I/O error occurs (e.g., redirect failure)
     */

	private void saveLogout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		User loggedInUser = null;
		if (session != null) {

			loggedInUser = (User) session.getAttribute("loggedInUser");

		}

		try {
			if (loggedInUser != null) {
				String ipAddress = request.getHeader("X-Forwarded-For");

				if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
					ipAddress = request.getRemoteAddr();
				}
				String userAgent = request.getHeader("User-Agent");
				Timestamp logoutTimestamp = Timestamp.from(Instant.now());
				Logout logout = new Logout();

				logout.setUser_id(loggedInUser.getUser_id());
				logout.setIpAddress(ipAddress);
				logout.setUserAgent(userAgent);
				logout.setLogoutTimestamp(logoutTimestamp);
				
				logoutDao.saveLogout(logout);

			}
		} catch (Exception e) {
			e.printStackTrace();
            request.setAttribute("error", "Logout successful, but we couldn't record your logout time.");

		} finally {
			if (session != null) {
				session.invalidate();
			}
			String successMessage = URLEncoder.encode("Successfully logged out", "UTF-8");
			response.sendRedirect(request.getContextPath() + LOGIN_PAGE + "?message=" + successMessage);
		}

	}

}
