package com.centrum.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.centrum.bean.User;
import com.centrum.bean.User_address;
import com.centrum.util.AddressService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserAddressServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String LOGIN_PAGE = "/login-user.jsp";
	private static final String PAYMENT_METHOD_PAGE = "/payment-method";

	/**
	 * Fetches the logged-in user's address via AddressService, stores it in the
	 * request for JSP access, and handles authentication/errors.
	 * 
	 * @param request  To access session (logged-in user) and set address attribute
	 * @param response To redirect unauthenticated users or handle errors
	 * @throws ServletException If servlet processing fails
	 * @throws IOException      If redirect/IO operations fail
	 */
	public static void fetchAddress(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {

			String ContextPath = request.getContextPath();
			response.sendRedirect(ContextPath + LOGIN_PAGE + "?error=Login required");
			return;
		}
		try {

			// use the addressService to distruple the dao method from AdressDao to the
			// UserAdressServlet check Create OrderServlet line 88 and 89
			// you will see how it been distrubted as well
			// i write this cause i just learn it and not to forget why i did this
			User_address address = AddressService.getDefaultAddress(loggedInUser);
			request.setAttribute("address", address);

		} catch (SQLException e) {
			e.printStackTrace();
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + PAYMENT_METHOD_PAGE + "?error=Failed to load address. Try again.");
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		userAddress(request, response);

	}

	/**
	 * Processes user address submission from the payment page. Validates the
	 * address data, associates it with the logged-in user, saves it to the
	 * database, and redirects with success/error messages.
	 * 
	 * @param request  Contains client-submitted address data and session info
	 * @param response Used to redirect the user after processing
	 * @throws ServletException If there's an error in Servlet processing
	 * @throws IOException      If there's an input/output error (e.g., during
	 *                          redirect)
	 */
	
	
	
	private void userAddress(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    HttpSession session = request.getSession();
	    User loggedInUser = (User) session.getAttribute("loggedInUser");

	    if (loggedInUser == null) {
	        response.sendRedirect(request.getContextPath() + "/login-user.jsp?error=Login required");
	        return;
	    }

	    try {
	        // 1️⃣ Get form values
	        String addressStr = request.getParameter("address");
	        String stateStr = request.getParameter("state");
	        String cityStr = request.getParameter("city");
	        String countryStr = request.getParameter("country");
	        String phoneNumberStr = request.getParameter("phone-number");
	        String postalcodeStr = request.getParameter("postal_code");
	        String orderIdStr = request.getParameter("order_id"); // <-- get order_id
	        String redirectUrl = request.getParameter("redirectUrl");

	        boolean isDefault = "true".equals(request.getParameter("defaultAddress"));

	        // 2️⃣ Validate
	        if (addressStr == null || addressStr.isEmpty() ||
	            stateStr == null || stateStr.isEmpty() ||
	            cityStr == null || cityStr.isEmpty() ||
	            countryStr == null || countryStr.isEmpty() ||
	            phoneNumberStr == null || phoneNumberStr.isEmpty() ||
	            postalcodeStr == null || postalcodeStr.isEmpty()) {

	            String fallbackUrl = getSafeFallbackUrl(request);
	            response.sendRedirect(request.getContextPath() + "/" + fallbackUrl + "?error=Please enter your address&order_id=" + orderIdStr);
	            return;
	        }

	        // 3️⃣ Save address
	        User_address address = new User_address();
	        address.setUser_id(loggedInUser.getUser_id());
	        address.setDetails_address(addressStr);
	        address.setState(stateStr);
	        address.setCity(cityStr);
	        address.setCountry(countryStr);
	        address.setPhone_number(phoneNumberStr);
	        address.setPostal_code(postalcodeStr);
	        address.setDefault_address(isDefault);

	        User_address existingAddress = AddressService.getDefaultAddress(loggedInUser);

	        if (existingAddress != null) {
	            // update the existing default address
	            AddressService.updateDefaultAddress(loggedInUser, address);
	        } else {
	            // no default address, create a new one
	            AddressService.createAddress(address);
	        }


	        // 4️⃣ Redirect BACK to payment-method with order_id preserved
	        String successUrl;
	        if (redirectUrl != null && !redirectUrl.isEmpty() && !isExternalUrl(redirectUrl)) {
	            successUrl = request.getContextPath() + "/" + redirectUrl + "?order_id=" + orderIdStr;
	        } else {
	            successUrl = request.getContextPath() + "/" + getSafeFallbackUrl(request) + "?order_id=" + orderIdStr;
	        }

	        successUrl += "&success=Address saved successfully!";
	        response.sendRedirect(successUrl);

	    } catch (SQLException e) {
	        e.printStackTrace();
	        String fallbackUrl = getSafeFallbackUrl(request);
	        String orderIdStr = request.getParameter("order_id");
	        response.sendRedirect(request.getContextPath() + "/" + fallbackUrl + "?error=Database error. Try again.&order_id=" + orderIdStr);
	    }
	}

//	private void userAddress(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//		HttpSession session = request.getSession();
//		User loggedInUser = (User) session.getAttribute("loggedInUser");
//
//		if (loggedInUser == null) {
//			String ContextPath = request.getContextPath();
//			response.sendRedirect(ContextPath + LOGIN_PAGE + "?error=Login required");
//			return;
//		}
//
//		try {
//			String addressStr = request.getParameter("address");
//			String stateStr = request.getParameter("state");
//			String cityStr = request.getParameter("city");
//			String countryStr = request.getParameter("country");
//			String phoneNumberStr = request.getParameter("phone-number");
//			String postalcodeStr = request.getParameter("postal_code");
//			String orderId = request.getParameter("order_id");
//			String redirectUrl = request.getParameter("redirectUrl");
//
//			boolean ischeckDefault = "true".equals(request.getParameter("defaultAddress"));
//
//			if (addressStr == null || addressStr.isEmpty() || stateStr == null || stateStr.isEmpty() || cityStr == null
//					|| cityStr.isEmpty() || countryStr == null || countryStr.isEmpty() || phoneNumberStr == null
//					|| phoneNumberStr.isEmpty() || postalcodeStr == null || postalcodeStr.isEmpty()) {
//				String fallbackUrl = getSafeFallbackUrl(request);
//				String contextPath = request.getContextPath();
//				response.sendRedirect(contextPath + "/" + fallbackUrl + "?error=please enter your address");
//				return;
//			}
//
//			User_address address = new User_address();
//			address.setUser_id(loggedInUser.getUser_id());
//			address.setDetails_address(addressStr);
//			address.setState(stateStr);
//			address.setCity(cityStr);
//			address.setCountry(countryStr);
//			address.setPhone_number(phoneNumberStr);
//			address.setPostal_code(postalcodeStr);
//			address.setDefault_address(ischeckDefault);
//
////			AddressService.deleteUserOldAddress(loggedInUser);
//
//			AddressService.createAddress(address);
//
////			String contextPath = request.getContextPath();
////			String successUrl;
////
////			if (redirectUrl == null || redirectUrl.isEmpty() || isExternalUrl(redirectUrl)) {
////				successUrl = contextPath + "/" + getSafeFallbackUrl(request);
////			} else {
////				successUrl = contextPath + "/" + redirectUrl;
////			}
////
////			successUrl += (successUrl.contains("?"))
////					? "&success=Address saved successfully! You can now proceed to payment."
////					: "?success=Address saved successfully! You can now proceed to payment.";
////
////			response.sendRedirect(successUrl);
////			return;
////			
//			
//			String contextPath = request.getContextPath();
//			String successUrl;
//
//			if (redirectUrl != null && !redirectUrl.isEmpty() && !isExternalUrl(redirectUrl)) {
//			    successUrl = contextPath + "/" + redirectUrl;
//			} else {
////			    successUrl = contextPath + "/confirmation";
////			    successUrl = contextPath + "/payment-method";
//			    successUrl = contextPath + "/" + getSafeFallbackUrl(request);
//
//			}
//
//			successUrl += (successUrl.contains("?"))
//			        ? "&success=Address saved successfully! "
//			        : "?success=Address saved successfully! ";
//
//			response.sendRedirect(successUrl);
//			return;
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			String fallbackUrl = getSafeFallbackUrl(request);
//
//			String contextPath = request.getContextPath();
//			response.sendRedirect(contextPath + "/" + fallbackUrl
//					+ "?error=Database error: Could not save address. Try again later.");
//			return;
//
//		}
//
//	}

	private String getSafeFallbackUrl(HttpServletRequest request) {
		String redirectUrl = request.getParameter("redirectUrl");
		String samePageFallback = request.getParameter("fallbackUrl");
		String GLOBAL_FALLBACK = "buyer/settings.jsp";

		if (redirectUrl != null && !redirectUrl.isEmpty() && !isExternalUrl(redirectUrl)) {
			return redirectUrl;
		} else if (samePageFallback != null && !samePageFallback.isEmpty() && !isExternalUrl(samePageFallback)) {
			return samePageFallback;
		} else {
			return GLOBAL_FALLBACK;
		}
	}

	private boolean isExternalUrl(String url) {
		return url.startsWith("http://") || url.startsWith("https://") || url.startsWith("//");
	}

}
