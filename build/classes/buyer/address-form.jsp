<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.centrum.bean.User"%>
<%@ page import="com.centrum.bean.Order"%>



<%
User loggedInUser = (User) session.getAttribute("loggedInUser");

if (loggedInUser == null) {
	response.sendRedirect(request.getContextPath() + "/login-user.jsp?error= please login first");
	return;
}
%>




<div class="address-form-wrapper">
	<button class="close-x">back</button>

	<form action="${pageContext.request.contextPath}/address" method="post">


		<div class="address-details">
			<label> <span>address</span></label> <input type="text"
				value="${address.getDetails_address()}" name="address"
				placeholder="street address" required><span>enter a
				valid street address</span>

		</div>
		<%
		Order formOrder = (Order) request.getAttribute("order");
		System.out.println("Address Form: order = " + (formOrder != null ? formOrder.getOrder_id() : "NULL"));
		%>
		<input type="hidden" name="redirectUrl" value="${redirectUrl}">
		<input type="hidden" name="fallbackUrl" value="${fallbackUrl}">
	
			<input type="hidden" name="order_id" value="${order.getOrder_id()}">
			






		<div class="state-wrapper">

			<div class="state-details">
				<label> <span>state/province</span></label> <input type="text"
					name="state" placeholder="state/province"
					value="${address.getState()}" required><span>state/province</span>

			</div>

			<div class="city-details">
				<label> <span>city</span></label> <input type="text"
					value="${address.getCity()}" name="city" placeholder="city"
					required><span>city of resident</span>

			</div>
		</div>
		<div class="state-wrapper">

			<div class="country">
				<label> <span>country</span></label> <input type="text"
					value="${address.getCountry()}" name="country"
					placeholder="country" required><span>residental
					country</span>

			</div>



			<div class="phone-number">
				<label> <span>phone number </span></label> <input type="text"
					value="${address.getPhone_number()}" name="phone-number"
					placeholder="phone number " required><span>valid
					phone number</span>

			</div>
		</div>




		<div class="postal-code">
			<label> <span>postal/zip code </span></label> <input type="text"
				value="${address.getPostal_code()}" name="postal_code"
				placeholder="postal code" required><span>country
				postal code </span>

		</div>
		<div class="default-address">
			<label> <input type="checkbox" name="defaultAddress"
				value="true"> Set as default address
			</label>
		</div>


		<button type="submit">update</button>



	</form>


</div>




