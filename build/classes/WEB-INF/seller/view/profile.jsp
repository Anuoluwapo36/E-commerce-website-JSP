<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Profile | Centrum Seller Portal</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/seller-dashboard.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
	<div class="dashboard-container">
		<!-- Sidebar (Same as dashboard) -->
		<aside class="sidebar">
			<div class="sidebar-header">
				<div class="brand">
					<div class="brand-logo">C</div>
					<div class="brand-name">Centrum</div>
				</div>
			</div>

			<nav class="sidebar-nav">
				<ul class="nav-list">
					<li class="nav-item"><a
						href="<%=request.getContextPath()%>/dashboard"
						class="nav-link active"> <i class="fas fa-tachometer-alt"></i>
							Dashboard
					</a></li>
					<li class="nav-item"><a
						href="<%=request.getContextPath()%>/products/dashboard"
						class="nav-link"> <i class="fas fa-box"></i> Products
					</a></li>
					<li class="nav-item"><a
						href="<%=request.getContextPath()%>/product/add" class="nav-link">
							<i class="fas fa-plus-circle"></i> Add Product
					</a></li>
					<li class="nav-item"><a
						href="<%=request.getContextPath()%>/orders" class="nav-link">
							<i class="fas fa-shopping-cart"></i> Orders
					</a></li>
					<li class="nav-item"><a
						href="<%=request.getContextPath()%>/reports" class="nav-link">
							<i class="fas fa-chart-bar"></i> Reports
					</a></li>
					<li class="nav-item"><a
						href="<%=request.getContextPath()%>/Acoount/settings"
						class="nav-link"> <i class="fas fa-cog"></i> Settings
					</a></li>
					<li class="nav-item"><a
						href="<%=request.getContextPath()%>/Account/profile"
						class="nav-link"> <i class="fas fa-user"></i> Profile
					</a></li>
				</ul>
			</nav>

			<div class="sidebar-footer">
				<div class="user-profile">
					<div class="user-avatar">S</div>
					<div class="user-info">
						<div class="user-name">logout</div>
					</div>
					<a href="<%=request.getContextPath()%>/logout-account"
						class="action-btn"> <i class="fas fa-sign-out-alt"></i>
					</a>
				</div>
			</div>
		</aside>

		<main class="main-content">
			<header class="top-bar">
				<h1 class="page-title">Profile</h1>
				<div class="top-bar-actions">
					
					
				</div>
			</header>

			<div class="content-area">
				<div class="card">

					<div class="card-body">

						<div class="mt-xl">
							<h3 class="mb-md">Security</h3>
							<div class="card">
								<div class="card-body">

									<%
									if (request.getAttribute("success") != null) {
									%>
									<div class="alert alert-success">
										<%=request.getAttribute("success")%>
									</div>
									<%
									} else if (request.getAttribute("error") != null) {
									%>
									<div class="alert alert-danger">
										<%=request.getAttribute("error")%>
									</div>
									<%
									}
									%>

									<form action="<%=request.getContextPath()%>/profile/password" method="POST">
										<div class="form-group">
											<label for="currentPassword" class="form-label">Current
												Password</label> <input type="password" id="currentPassword"
												name="currentPassword" class="form-control">
										</div>

										<div class="grid"
											style="grid-template-columns: 1fr 1fr; gap: var(--spacing-md);">
											<div class="form-group">
												<label for="newPassword" class="form-label">New
													Password</label> <input type="password" id="newPassword"
													name="newPassword" class="form-control">
											</div>
											<div class="form-group">
												<label for="confirmPassword" class="form-label">Confirm
													New Password</label> <input type="password" id="confirmPassword"
													name="confirmPassword" class="form-control">
											</div>
										</div>

										<button type="submit" class="btn btn-primary">
											<i class="fas fa-key"></i> Change Password
										</button>
									</form>
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
		</main>
	</div>
</body>
</html>