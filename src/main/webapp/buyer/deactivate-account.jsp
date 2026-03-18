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
				<h1>Deactive Account</h1>

				<p>All data and personal information can't be retreview after
					deactivation of account.</p>
				<div class="order-details">
					<p>Are you sure you want to deactive your account</p>


				</div>

				<div class="success-buttons">
					<form action="${pageContext.request.contextPath}/deactivate-account"
						method="post">
						<input type="hidden" name="user_id"
							value="<%=((User) session.getAttribute("loggedInUser")).getUser_id()%>">

						<button type="Submit"  class="btn continue-shopping"
							name="action" value="deactivate">deactivate</button>


					</form>

					<button id="close" class="btn view-order">Cancel</button>




				</div>

				<p class="thank-you">Thanks for shopping with us,
					${loggedInUser.full_name}! see you next time 😊</p>

			</div>





		</div>






	</div>



</div>


