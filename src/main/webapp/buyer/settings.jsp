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
<%@ page import="com.centrum.bean.UserProfileImages"%>


<%@ page import="java.text.SimpleDateFormat"%>




<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>centrum.settings</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/settings.css">
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
<meta http-equiv="Cache-Control"
	content="no-cache, no-store, must-revalidate">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

</head>
<body>
	<%@ include file="/WEB-INF/header.jsp"%>

	<div class="structure-container">




		<div class="frame-container">

			<aside class="left-side-bar">

				<ul class="payment-sidebar">




					<li class="back">
						<button
							onclick="window.location.href='${pageContext.request.contextPath}/profile'">Back</button>
					</li>







					<li class="sidebar-section"><strong>Exit Account</strong>
						<ul>

							<li><a href="${pageContext.request.contextPath}/logout">LogOut</a></li>

						</ul></li>
				</ul>

			</aside>

			<div class="structure-main">

				<%
				String error = (String) request.getAttribute("error");
				%>
				<%
				if (error != null && !error.isEmpty()) {
				%>
				<div
					style="color: red; background: #ffebee; padding: 10px; border-radius: 5px; margin: 10px 0;">
					<%=error%>
				</div>
				<%
				}
				%>

				<div class="card-head">
					<h4>Profile Account Settings</h4>

					<form action="${pageContext.request.contextPath}/setting"
						method="post" enctype="multipart/form-data">


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
										"
										src="${pageContext.request.contextPath}/uploads/avatar/<%= dpImg.getImageFilename() %>   ">
									<%
									}
									%>

									<div class="change-details">


										<div class="modify-wrapper">
											<label> fullname </label> <input type="text"
												value="${loggedInUser.full_name}" name="full_name"
												placeholder="modify fullname">

										</div>
										<div class="modify-wrapper">
											<label> email </label><input type="text"
												value="${loggedInUser.email}" name="email"
												placeholder="modify email">



										</div>
										<div class="modify-wrapper">

											<label> password</label><input type="text" name="password"
												placeholder="Leave empty to keep current password">




										</div>




									</div>

								</div>

								<div class="redirect-btn">


									<div class="profile-image-upload">
										<input type="file" name="file" accept="image/*"
											id="profile-image-input"> <label
											for="profile-image-input"> Profile Image <span
											class="file-name" id="file-name-preview"></span>
										</label>
									</div>

									<button class="btn-opeartion" type="submit">Update</button>



								</div>

							</div>

						</div>

					</form>



				</div>


				<div class="card-head">
					<h4>General</h4>

					<form action="" method="post">


						<div class="card-hold">


							<div class="profile-container">




								<div class="redirect-btn">

									<p>change address</p>



									<p class="input-address" id="change-address">edit</p>



								</div>

							</div>

						</div>

					</form>



				</div>
				<div class="show-up">
					<%
					User_address address = (User_address) request.getAttribute("address");
					request.setAttribute("address", address);

					request.setAttribute("redirectUrl", "setting");
					request.setAttribute("fallbackUrl", "setting");

					request.setAttribute("order", null);
					%>

					<jsp:include page="../buyer/address-form.jsp" />

				</div>




				<div class="card-head">
					<h4>Payment method</h4>

					<form action="" method="post">


						<div class="card-hold">


							<div class="profile-container">


								<div class="profile-wrapper"></div>

								<div class="redirect-btn">


									<p>in production join watchlist</p>


								</div>

							</div>

						</div>

					</form>



				</div>




				<div class="card-head">
					<h4>Account Managment</h4>

					<form action="" method="post">


						<div class="card-hold">


							<div class="profile-container">




								<div class="redirect-btn">
									<p class="deactivate">Deactivate Account</p>

									<button class="btn-opeartion" id="accountdelete" type="button">yes</button>



								</div>

							</div>

						</div>

					</form>



				</div>

				<div class="deactivate-show">
					<jsp:include page="../buyer/deactivate-account.jsp" />

				</div>

			</div>

			<aside class="right-side-bar">
				<div class="address-container">
					<div class="address-display-body">
						<p class="txt">Default Address Information</p>
						<%
						User user = (User) request.getAttribute("profileUser");
						%>

						<%
						if (address != null) {
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
						} else {
						%>
						<p>No address added yet. Click "Add New Address" to set up
							shipping.</p>
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