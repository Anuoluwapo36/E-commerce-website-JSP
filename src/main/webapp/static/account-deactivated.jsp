<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.centrum.bean.User"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ include file="/WEB-INF/fragments/loading-overlay.jsp"%>


<!DOCTYPE html>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        response.sendRedirect(request.getContextPath() + "/login-user.jsp?error=please login first");
        return;
    }
%>
<html>
<head>
<meta charset="UTF-8">
<title>centrum.deactivate.account</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/account-deactivated.css">
</head>

<body>
	<div class="structure-wrapper">
		<header>

			<div class="logo">
				<div>
					<img id="logo-img"
						src="${pageContext.request.contextPath}/Image/centrum e wcommerce logo.png"
						alt="Centrum Logo">
					<h5>Centrum</h5>
				</div>
			</div>
		</header>

		<main>
			<div class="container">
				<div class="deactivated-card">
					<div class="icon">
						<svg width="64" height="64" viewBox="0 0 24 24" fill="none"
							stroke="currentColor">
                    <path
								d="M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z"></path>
                    <line x1="12" y1="9" x2="12" y2="13"></line>
                    <line x1="12" y1="17" x2="12.01" y2="17"></line>
                </svg>
					</div>
					<h1>Account Deactivated</h1>
					<p class="account-email">
						Hey  our valuable user
						<%=(loggedInUser.getEmail() != null) ? loggedInUser.getEmail() : "centrumGroup@centrum-ecom.com"%></p>

					<p class="message">We sad to notify you that your  account has been temporarily
						deactivated. This may be due to one this reasons</p>

					<ul class="reasons">
						<li>Inactivity for an extended period</li>
						<li>Violation of our terms of service</li>
						<li>Security concerns</li>
						<li>At your request</li>
					</ul>
					<div class="contact-info">
						<p>If you believe this is a mistake or would like to
							reactivate your account, please contact our support team.</p>
						<div class="contact-actions">
							<a href="mailto:centrumgroup@gmail.com" class="btn primary">Contact
								Support</a> <a
								href="${pageContext.request.contextPath}/baseloading.jsp"
								class="btn secondary">Return Home</a>
						</div>
					</div>
				</div>
			</div>

		</main>




	</div>


</body>
</html>