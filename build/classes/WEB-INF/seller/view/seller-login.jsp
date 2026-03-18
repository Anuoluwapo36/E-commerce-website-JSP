<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login | Centrum Seller Portal</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/seller-dashboard.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/seller-login.css">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

</head>
<body class="login-page">
	<div class="login-container">
		<div class="login-card">
			<div class="login-header">
				<div class="login-logo">
					<div class="logo-icon">C</div>
					<div>
						<div style="font-size: 20px; font-weight: 600;">Centrum</div>
						<div style="font-size: 14px; color: #666666;">Seller Portal</div>
					</div>
				</div>
				<h1 class="login-title">Welcome Back</h1>
				<p class="login-subtitle">Sign in to your seller account</p>
			</div>

			<div class="login-body">
				<%
				String error = (String) request.getAttribute("error");
				if (error != null) {
				%>
				<div class="alert alert-danger">
					<i class="fas fa-exclamation-circle"></i> <span><%=error%></span>
				</div>
				<%
				}
				%>

				<form action="<%=request.getContextPath()%>/logins" method="POST">
					<div class="form-group">
						<label for="email" class="form-label">Email Address</label> <input
							type="email" id="email" name="email" class="form-control"
							placeholder="you@company.com">
					</div>

					<div class="form-group">
						<label for="password" class="form-label">Password</label>
						<div class="password-input-wrapper">
							<input type="password" id="password" name="password"
								class="form-control" placeholder="Enter your password">
							<button type="button" class="password-toggle"
								onclick="togglePassword()">
								<i class="fas fa-eye"></i>
							</button>
						</div>
					</div>

					<div class="form-options">
						<label class="remember-me"> <input type="checkbox"
							name="remember"> Remember me
						</label> <a href="forgot-password.jsp" class="forgot-password">Forgot
							password?</a>
					</div>

					<button type="submit" class="btn btn-primary">
						<i class="fas fa-sign-in-alt"></i> Sign In
					</button>
				</form>

				<div class="divider">
					<span class="divider-text">Or</span>
				</div>

				<a href="<%=request.getContextPath()%>/register"
					class="btn btn-secondary"> <i class="fas fa-user-plus"></i>
					Create New Account
				</a>
			</div>

			<!-- Footer -->
			<div class="login-footer">
				<p>
					Need help? <a href="mailto:support@centrum.com">Contact Support</a>
				</p>
			</div>
		</div>
	</div>

	<script>
		// Toggle password visibility
		function togglePassword() {
			const passwordInput = document.getElementById('password');
			const toggleIcon = document.querySelector('.password-toggle i');

			if (passwordInput.type === 'password') {
				passwordInput.type = 'text';
				toggleIcon.className = 'fas fa-eye-slash';
			} else {
				passwordInput.type = 'password';
				toggleIcon.className = 'fas fa-eye';
			}
		}

		// Form validation
		document.querySelector('form').addEventListener('submit', function(e) {
			const email = document.getElementById('email').value;
			const password = document.getElementById('password').value;

			// Basic email validation
			const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
			if (!emailRegex.test(email)) {
				e.preventDefault();
				alert('Please enter a valid email address');
				return;
			}

			// Password validation
			if (password.length < 6) {
				e.preventDefault();
				alert('Password must be at least 6 characters long');
				return;
			}
		});
	</script>
</body>
</html>