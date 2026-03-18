package com.centrum.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Arrays;

import org.mindrot.jbcrypt.BCrypt;

import com.centrum.bean.User;
import com.centrum.bean.UserProfileImages;
import com.centrum.bean.User_address;
import com.centrum.dao.UserDao;
import com.centrum.util.AddressService;
import com.centrum.util.UserProfileServices;
import com.centrum.util.ValidateSettings;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import jakarta.servlet.annotation.MultipartConfig;

@MultipartConfig(maxFileSize = 5242880, maxRequestSize = 5242880)
public class SettingServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	private static final String LOGIN_PAGE = "/login-user.jsp";
	private static final String SETTING_PAGE = "/buyer/settings.jsp";
	private static final String SETTING_PAGE_SERVLET = "/setting";

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try {
			userDao = new UserDao();

		} catch (Exception e) {
			throw new ServletException("failed to initilize userDao for settings:", e);
		}

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doGet(req, resp);

		fetchUserImage(request, response);

	}

	/**
	 * Fetches a logged-in user's default address and default profile image, sets
	 * them as request attributes, and forwards to the settings page. Redirects to
	 * login if no user is logged in, and redirects with error messages if no
	 * default address or profile image is found.
	 * 
	 * @param request  HttpServletRequest containing the user's session (to retrieve
	 *                 logged-in user)
	 * @param response HttpServletResponse used for redirects (login/settings with
	 *                 errors) and forwarding to settings page
	 * @throws ServletException If a servlet-related error occurs (e.g., request
	 *                          dispatching failure)
	 * @throws IOException      If an I/O error occurs (e.g., redirect failure)
	 * 
	 *                          Key Behaviors: 1. Redirects unauthenticated users to
	 *                          login with a "Please log in" error. 2. Fetches
	 *                          default address via AddressService and default
	 *                          profile image via UserDao. 3. Sets fetched address
	 *                          (as "address") and profile image (as "userImages")
	 *                          as request attributes for the settings JSP. 4.
	 *                          Redirects to settings page with specific errors if:
	 *                          - No default address is found ("No default address
	 *                          found. Please add one") - No default profile image
	 *                          is found ("No profile image set. Upload one in
	 *                          settings") 5. Forwards to settings page if all data
	 *                          is fetched successfully (or after handling nulls via
	 *                          redirect). 6. Catches SQLException (database errors)
	 *                          and prints stack trace (for debugging, no
	 *                          user-facing feedback).
	 */
	private void fetchUserImage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + LOGIN_PAGE + "?error=Please log in to access settings");
			return;
		}

		String successMsg = request.getParameter("success");
		if (successMsg != null) {
			request.setAttribute("success", successMsg);
		}

		try {

			User_address address = null;
			UserProfileImages userProfileImages = null;
			UserProfileServices profileServices = new UserProfileServices();

			loggedInUser = userDao.getUserById(loggedInUser.getUser_id());
			session.setAttribute("loggedInUser", loggedInUser);
			address = AddressService.getDefaultAddress(loggedInUser);
			userProfileImages = profileServices.getDefaultProfile(loggedInUser.getUser_id());

			request.setAttribute("loggedInUser", loggedInUser);

			if (address == null) {
//				String contextPath = request.getContextPath();
				request.setAttribute("error", "No default address found. Please add one.");

			} else {
				request.setAttribute("address", address);

			}

			if (userProfileImages == null) {
//				String contextPath = request.getContextPath();
				request.setAttribute("error", "No profile image set. Upload one in settings.");

			} else {
				request.setAttribute("userImages", userProfileImages);

			}

//		
////		    request.getRequestDispatcher(SETTING_PAGE).forward(request, response);
////			return;
//			
			request.getRequestDispatcher(SETTING_PAGE).forward(request, response);
//			request.getRequestDispatcher(SETTING_PAGE).forward(request, response);
//			response.sendRedirect(request.getContextPath() + SETTING_PAGE);
			//
			//
			return;

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("error", "Failed to retrive  profile. Please try again.");
			request.getRequestDispatcher(SETTING_PAGE).forward(request, response);
			return;
//			response.sendRedirect(request.getContextPath() + SETTING_PAGE);

		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doPost(req, resp);
		updateInformation(request, response);

	}

	/**
	 * Handles updates to a logged-in user's profile (name, email, password) and
	 * profile image. Validates input, processes image uploads (if provided), saves
	 * changes to the database, refreshes the user's session, and forwards back to
	 * the settings page with success/error feedback.
	 * 
	 * @param request  HttpServletRequest containing form data (full_name, email,
	 *                 password, file) and user session
	 * @param response HttpServletResponse used for redirects/forwards to the
	 *                 settings page
	 * @throws ServletException If a servlet-related error occurs (e.g., request
	 *                          dispatching failure)
	 * @throws IOException      If an I/O error occurs (e.g., image file writing,
	 *                          stream handling)
	 * 
	 *                          Key Behaviors: 1. Redirects to login if no user is
	 *                          logged in (session lacks "loggedInUser"). 2. Uses
	 *                          partial updates: empty fields retain the user's
	 *                          existing data (e.g., empty password = no change). 3.
	 *                          Validates input (name/email format, password
	 *                          strength) via ValidateSettings; forwards errors to
	 *                          settings page. 4. Processes image uploads
	 *                          (JPG/PNG/GIF only, max 5MB): generates unique
	 *                          filename, saves to project folder, and updates the
	 *                          user's default image in the user_images table. 5.
	 *                          Refreshes the user's session with updated profile
	 *                          data and re-fetches their default address. 6.
	 *                          Forwards to settings page with success message (on
	 *                          success) or error message (on SQL failure).
	 */
	private void updateInformation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			String contexPath = request.getContextPath();
			response.sendRedirect(contexPath + LOGIN_PAGE + "?error= Account not Found");
			return;
		}

		try {

			// getting paramter from settings.jsp input promote

			String newFullname = getTextPartValue(request.getPart("full_name"));
			String newEmail = getTextPartValue(request.getPart("email"));
			String newPassword = getTextPartValue(request.getPart("password"));

			String finalFullname = newFullname.isEmpty() ? loggedInUser.getFull_name() : newFullname;
			String finalEmail = newEmail.isEmpty() ? loggedInUser.getEmail() : newEmail;

			String errorMsg = ValidateSettings.validateSettingsInput(finalFullname, finalEmail, newPassword);
			String hashedNewPassword = null;
			if (!newPassword.isEmpty()) {
				hashedNewPassword = BCrypt.hashpw(newPassword.trim(), BCrypt.gensalt(10));
			}

			// display error message in jsp
			if (errorMsg != null) {

				request.setAttribute("errorMsg", errorMsg);
				response.sendRedirect(request.getContextPath() + SETTING_PAGE + "?error=" + errorMsg);
				return;
			}

			// populating bean User bean setter

			User user = new User();
			user.setUser_id(loggedInUser.getUser_id());
			user.setFull_name(finalFullname);
			user.setEmail(finalEmail);
			if (hashedNewPassword != null) {
				user.setPassword(hashedNewPassword);

			}

			// for updating user image add image to there profile by calling method in
			// userDao updateuserimage
			Part imagePart = request.getPart("file");
			String imageFilename = null;
			String imagePath = null;

			if (imagePart != null && imagePart.getSize() > 0) {
				String originalFilename = imagePart.getSubmittedFileName();
				String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

				// Allow only image file types (JPG, PNG, GIF)
				String[] allowedExtensions = { ".jpg", ".jpeg", ".png", ".gif" };
				if (!Arrays.asList(allowedExtensions).contains(fileExtension.toLowerCase())) {
					response.sendRedirect(request.getContextPath() + SETTING_PAGE
							+ "?error=Only JPG, PNG, or GIF images are allowed!");
					return;
				}

				// Limit file size to 5MB
				long maxFileSize = 5 * 1024 * 1024;
				if (imagePart.getSize() > maxFileSize) {
					response.sendRedirect(
							request.getContextPath() + SETTING_PAGE + "?error=Image size cannot exceed 5MB");
					return;
				}

				imageFilename = loggedInUser.getUser_id() + "_" + System.currentTimeMillis() + fileExtension;

				String webappRoot = getServletContext().getRealPath("/");
				String existFolder = webappRoot + "uploads/avatar/";

				File uploadFolder = new File(existFolder);
				if (!uploadFolder.exists()) {
					uploadFolder.mkdirs();
					System.out.println("Created folder in DEFAULT webapp: " + existFolder);
				}

				imagePath = existFolder + imageFilename;
				System.out.println("DEBUG: Image saved to → " + imagePath);
				imagePart.write(imagePath);

				String relativeImagePath = "uploads/avatar/" + imageFilename;

				// Save to database
				userDao.updateUserImage(loggedInUser.getUser_id(), imageFilename, relativeImagePath);
			}

			// here save to dao and from dao to database table by calling the method in
			// userDAo

			userDao.updateUser(user);
			User updateUser = userDao.getUserById(loggedInUser.getUser_id());
			System.out.println("Updated Name from DB: " + updateUser.getFull_name());

			session.setAttribute("loggedInUser", updateUser);
			User_address address = AddressService.getDefaultAddress(updateUser);
			UserProfileImages updatedImage = new UserProfileServices().getDefaultProfile(updateUser.getUser_id());
			request.setAttribute("userImages", updatedImage);

			request.setAttribute("address", address);

			request.setAttribute("success", "Profile updated successfully!");
//			request.getRequestDispatcher(SETTING_PAGE + "?success=Profile updated successfully!").forward(request, response);
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + SETTING_PAGE_SERVLET + "?success=Profile updated successfully!");
//			response.sendRedirect(request.getContextPath() + SETTING_PAGE + "?success=Profile updated successfully!");

			return;

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("error", "Failed to update profile. Please try again.");
			response.sendRedirect(
					request.getContextPath() + SETTING_PAGE + "?error=Failed to update profile. Please try again.");
		}

	}

	/**
	 * Reads and returns the text value from a multipart form Part (used for text
	 * fields in file-upload forms). Handles null parts gracefully and trims
	 * whitespace from the final value.
	 * 
	 * @param part The multipart form Part to read text from (e.g., "full_name" or
	 *             "email" field from settings form)
	 * @return Trimmed text value from the Part; empty string if the Part is null or
	 *         contains only whitespace
	 * @throws IOException If an I/O error occurs while reading the Part's input
	 *                     stream (e.g., stream failure)
	 */

	private String getTextPartValue(Part part) throws IOException {
		if (part == null) {
			return "";
		}
		try (InputStream is = part.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString().trim();
		}
	}

}
