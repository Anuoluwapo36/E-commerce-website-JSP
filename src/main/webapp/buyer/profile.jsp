<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.centrum.bean.User"%>
<%@ page import="com.centrum.bean.Product"%>
<%@ page import="com.centrum.bean.Order"%>
<%@ page import="com.centrum.bean.Order_items"%>
<%@ page import="com.centrum.bean.User_address"%>

<%@ page import="java.util.List"%>
<%@ page import="com.centrum.bean.ProductImages"%>
<%@ page import="com.centrum.bean.RecommendProduct"%>

<%@ page import="com.centrum.bean.Cart"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.centrum.bean.UserProfileImages"%>






<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>centrum.profile.me</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/profile.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/address.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/credit.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/Qr.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/payment-success.css">
<link
	href="https://fonts.googleapis.com/css2?family=Pacifico&display=swap"
	rel="stylesheet">

</head>
<body>
	<%@ include file="/WEB-INF/header.jsp"%>

	<div class="structure-container">



		<div class="frame-container">

			<aside class="left-side-bar">

				<ul class="payment-sidebar">
					<li class="sidebar-section"><strong>Your Account</strong>
						<ul>
							<li><a
								href="${pageContext.request.contextPath}/product-browsing">Home</a></li>

							<li><a href="/profile" class="active">Profile</a></li>
							<li><a href="${pageContext.request.contextPath}/tracking">Order
									History</a></li>
						</ul></li>

					<li class="sidebar-section"><strong>Payments</strong>
						<ul>
							<li><a href="/payment-history">Payment Records</a></li>
						</ul></li>

					<li class="sidebar-section"><strong>Settings</strong>
						<ul>
							<li><a href="${pageContext.request.contextPath}/setting">Personal
									settings</a></li>
							<li><a href="/settings/notifications">Notifications</a></li>
							<li><a href="/settings/privacy">Privacy</a></li>
						</ul></li>

					<li class="sidebar-section"><strong>Help</strong>
						<ul>
							<li><a href="/help/payments">FAQs</a></li>
							<li><a href="/help/contact">Support</a></li>
							<li><a href="/help/contact">LogOut</a></li>

						</ul></li>
				</ul>

			</aside>

			<div class="structure-main">

				<div class="card-head">

					<div class="card-hold">


						<div class="profile-container">


							<div class="profile-wrapper">
								<%
								UserProfileImages dpImg = (UserProfileImages) request.getAttribute("userImages");
								%>

								<%
								if (dpImg == null || dpImg.getImageFilename() == null || dpImg.getImageFilename().isEmpty()) {
								%>
								<img class="display-profile-image" alt="Default Profile Image"
									src="${pageContext.request.contextPath}/product/Image/centrum_default.svg">
								<%
								} else {
								%>
								<img class="display-profile-image" alt="Profile Image"
									
										src="${pageContext.request.contextPath}/uploads/avatar/<%= dpImg.getImageFilename() %>   ">
								<%
								}
								%>


								<%
								User user = (User) request.getAttribute("profileUser");
								%>


								<%
								if (user != null) {
								%>




								<div>
									<p><%=user.getFull_name()%></p>
									<p><%=user.getEmail()%></p>


									<p><%=user.getPhone_number() != null ? user.getPhone_number() : "No phone number"%></p>
								</div>






								<%
								}
								%>

							</div>

							<div class="redirect-btn">


								<button class="redirect"
									onclick="window.location.href='${pageContext.request.contextPath}/tracking'">Order
									History/Tracking</button>
								<button class="redirect" onclick="window.location.href=`${pageContext.request.contextPath}/cart`">cart record</button>

							</div>

						</div>
						<div class="text-btn">
							<button class="input-address" id="change-address" type="button">
								Add address</button>
						</div>
					</div>


				</div>
				<%
				String redirectUrl = "buyer/profile.jsp"; 
				request.setAttribute("redirectUrl", redirectUrl);
				%>

				<div class="show-up">
					<%
					User_address address = (User_address) request.getAttribute("address");
					request.setAttribute("address", address);

					request.setAttribute("redirectUrl", "profile"); 
					request.setAttribute("fallbackUrl", "profile"); 
					request.setAttribute("order", null);
					%>
				


				</div>





			</div>

			<aside class="right-side-bar">
				<div class="address-container">
					<div class="address-display-body">
						<p class="txt">Default Address Information</p>

						

						<%
						if (address == null) {
						%>
						<p>No address added yet. Click "Add New Address" to set up
							shipping.</p>
						<%
						} else {
						%>



						<div class="address-list">
							<p><%=address.getDetails_address()%></p>
							<p><%=address.getCity()%>,
								<%=address.getState()%></p>
							<p><%=address.getCountry()%>
								-
								<%=address.getPostal_code()%></p>
							<p>
								Phone:
								<%=address.getPhone_number()%></p>
						</div>
						<%
						}
						%>
					</div>

				</div>
			</aside>

		</div>
	</div>


	<%@ include file="/WEB-INF/footer.jsp"%>



	<script>
		const contextPath = '${pageContext.request.contextPath}';
	</script>
	<script src="${pageContext.request.contextPath}/js/profile.js"></script>

</body>

</html>