<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="com.centrum.bean.User"%>
	


<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>

<%
User loggedInUser = (User) session.getAttribute("loggedInUser");
if (loggedInUser != null) {
    response.sendRedirect(request.getContextPath() + "/dashboard.jsp"); 
    return; 
}
%>

<%@ include file="/WEB-INF/fragments/loading-overlay.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>centrum.login page</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
	<div class="structure-wrapper">


		<header>

			<div class="logo">
				<div>
					
					<h5>Centrum</h5>
				</div>
			</div>
		</header>





		<main class="from-wrapper">
			<div class="form-container">

				<h3>login into your account</h3>
				<%
				String error = request.getParameter("error");
				if (error != null && !error.isEmpty()) {
					error = java.net.URLDecoder.decode(error, "UTF-8");
				%>
				<p class="error"><%=error%></p>
				<%
				}
				%>

				<%
				String message = request.getParameter("message");
				if (message != null && !message.isEmpty()) {
					message = java.net.URLDecoder.decode(message, "UTF-8");
				%>
				<p class="success"><%=message%></p>
				<%
				}
				%>

				<form action="${pageContext.request.contextPath}/login-user"
					method="post">
					<div>
						<input type="email" name="email" placeholder="Enter your email">

					</div>

					<div>
						<input type="password" name="password"
							placeholder="Create Password">

					</div>
					<div>


						<div class="forget-password">
							<a id="forget-password" href=""> forget password?</a>



						</div>


						<button class="submit-btn" type="submit">login</button>

					</div>







				</form>

				<div>
					<div id="login-text">
						<p>Don't have an account?</p>
						<a href="${pageContext.request.contextPath }/sign-up.jsp"
							id="signup">signup</a>
					</div>


					<button
						onclick="window.location.href='${pageContext.request.contextPath}/sign-up.jsp'"
						class="create-btn" type="submit">Create account</button>


				</div>



			</div>
		</main>



	</div>
	<script>
    if (window.hideLoadingOverlay) {
        setTimeout(() => {
            hideLoadingOverlay();
        }, 500);
    }
</script>
</body>
</html>