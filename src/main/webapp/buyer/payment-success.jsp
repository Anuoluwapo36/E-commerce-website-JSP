<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.centrum.bean.User"%>
<%@ page import="com.centrum.bean.Product"%>
<%@ page import="java.util.List"%>
<%@ page import="com.centrum.bean.ProductImages"%>
<%@ page import="com.centrum.bean.RecommendProduct"%>

<%@ page import="com.centrum.bean.Cart"%>





<div class="light-pop-container">




	<%
	User loggedInUser = (User) session.getAttribute("loggedInUser");

	if (loggedInUser == null) {
		response.sendRedirect(request.getContextPath() + "/login-user.jsp?error= please login first");
		return;
	}
	%>


	<div class="pop-container">


		<div class="success-content">
			<div class="complete">
				<h1>Payment Successful!</h1>
				<h2>$${order.total_amount}</h2>


				<div class="order-details">
					<p>
						Order #<span>${order.order_id}</span> • Placed on <span>${order.create_at}</span>
					</p>
					<p>
						Shipped to: <span>${address.details_address},
							${address.city}, ${address.country}</span>
					</p>
				</div>

				<div class="next-steps">
					<p>Your order is being processed and will ship within 2
						business days.</p>
					<p>Check your email for shipping updates and tracking details!</p>
				</div>



				<div class="success-buttons">

					<a
						href="${pageContext.request.contextPath}/tracking?order_id=${order.order_id}"
						class="btn view-order">View Order Details</a> <a
						href="${pageContext.request.contextPath}/product-browsing"
						class="btn continue-shopping">Continue Shopping</a>
				</div>

				<p class="thank-you">Thanks for shopping with us,
					${loggedInUser.full_name}! 😊</p>

			</div>





		</div>






	</div>



</div>



