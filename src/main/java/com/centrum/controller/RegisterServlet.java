package com.centrum.controller;

import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.net.URLEncoder;
import com.centrum.bean.User;
import com.centrum.connections.MySQLcnn;
import com.centrum.dao.UserDao;
import com.centrum.util.validationUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class RegisterServlet extends HttpServlet {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	private static final int BCRYPT_WORK_FACTOR = 12;
	private MySQLcnn dbConnection;

	@Override
	public void init() throws ServletException {
		super.init();
		dbConnection = new MySQLcnn();
		userDao = new UserDao();
		System.out.println("userDao is initialized: " + (userDao != null));

	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.service(request, response);
		System.out.println("registerservlet handling request ");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatch = request.getRequestDispatcher("/sign-up-user");
		dispatch.forward(request, response);
		return;

	}
	
	
	

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		registerUser(request, response);
	}
	
	
	/**
	 * Handles user registration: Validates input, hashes passwords, saves the user to the database,
	 * and auto-logs the user in after successful registration.
	 * 
	 * Extracts registration form data (name, email, phone, password) from the request, validates it
	 * using {@link ValidationUtils#validateRegistrationInput}, hashes the password with BCrypt,
	 * creates a new User record, and redirects the user to product browsing (auto-logged in).
	 * 
	 * @param request  HttpServletRequest containing registration form data (fullname, email, phone_number, password, agreeTerms)
	 * @param response HttpServletResponse used to redirect to sign-up (on error) or product browsing (on success)
	 * @throws ServletException If a servlet-related error occurs (e.g., request dispatching fails)
	 * @throws IOException      If an I/O error occurs (e.g., redirect fails, URL encoding fails)
	 * @note Auto-logs the user in by creating a new session with the registered user's data.
	 * @note Passwords are hashed with BCrypt (10 work factor) for security—never stored as plain text.
	 * @note Redirects with URL-encoded error messages if validation or database operations fail.
	 */
	private void registerUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		
		String fullname = request.getParameter("fullname") != null ? request.getParameter("fullname").trim() : "";
		String email = request.getParameter("email") != null ? request.getParameter("email").trim() : "";
		String phone_number = request.getParameter("phone_number") != null ? request.getParameter("phone_number").trim()
				: "";
		String password = request.getParameter("password") != null ? request.getParameter("password").trim() : "";
		String agreeTerms = request.getParameter("agreeTerms");
		boolean agree = agreeTerms != null && agreeTerms.equals("on");

		String errorMessage = validationUtils.validateRegistrationInput(fullname, email, password, agree);

		if (errorMessage != null) {
			String encodedError = URLEncoder.encode(errorMessage, "UTF-8");

			response.sendRedirect(request.getContextPath() + "/sign-up.jsp?error=" + encodedError);
			return;
		}
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(BCRYPT_WORK_FACTOR));

		String roles = "buyer";
		String status = "active";

		User newUser = new User();
		newUser.setFull_name(fullname);
		newUser.setEmail(email);
		newUser.setPhone_number(phone_number);
		newUser.setPassword(hashedPassword);
		newUser.setRoles(roles);
		newUser.setStatus(status);
		newUser.setCreateAt(new Timestamp(System.currentTimeMillis()));

		try {
			userDao.createUser(newUser);

			User savedUser = userDao.getUserByEmail(email);
			if (savedUser == null) {
				throw new SQLException("User not found after registration");
			}
			HttpSession session = request.getSession();
			session.invalidate();
			HttpSession newSession = request.getSession(true);
			newSession.setAttribute("loggedInUser", savedUser);
			newSession.setMaxInactiveInterval(3600);
			response.sendRedirect(request.getContextPath() + "/product-browsing");

			return;
		} catch (SQLException e) {
			e.printStackTrace();
			String encodedError = URLEncoder.encode("Registration failed. please try again ", "UTF-8");

			response.sendRedirect(request.getContextPath() + "/sign-up.jsp?error=" + encodedError);
			return;

		}
		
	}

	@Override
	public void destroy() {
		super.destroy();

		try {
			if (dbConnection != null && !dbConnection.getCnn().isClosed()) {
				dbConnection.getCnn().close();
				System.out.println("db connection is destroy");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("registerservice shutting down");

	}

}
