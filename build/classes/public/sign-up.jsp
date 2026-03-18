<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/loading-overlay.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/register.css">
</head>
<body>

	<div class="structure-wrapper">
		<div class="top-head">

			<header>
				<div class="logo">
					<div>
						
						<h5>Centrum</h5>
					</div>
				</div>
				
			</header>


		</div>




		<div class="form-container">

			<h3>Create Account</h3>
			<%
			String error = request.getParameter("error");
			if (error != null && !error.isEmpty()) {
				error = java.net.URLDecoder.decode(error, "UTF-8");
			%>
			<p class="error"><%=error%></p>
			<!-- Shows error if it exists -->
			<%
			}
			%>



			<form action="${pageContext.request.contextPath}/sign-up-user"
				method="post">
				<div>
					<input type="text" name="fullname" placeholder="Enter fullName">

				</div>
				<div>
					<input type="email" name="email" placeholder="Enter your email">

				</div>
				<div>
					<input type="password" name="password"
						placeholder="Create Password">

				</div>
				<div>


					<div class="checkbox">
						<label for="agree"> <input type="checkbox"
							name="agreeTerms" id="agree">
							<p>i agree to the terms and conditions</p>
						</label>


					</div>


					<button class="submit-btn" type="submit">Create Account</button>

				</div>







			</form>

			<div id="login-text">
				<p>Already have an account?</p>
				<a href="${pageContext.request.contextPath}/login-user.jsp">login</a>

			</div>



		</div>

	</div>



</body>
</html>