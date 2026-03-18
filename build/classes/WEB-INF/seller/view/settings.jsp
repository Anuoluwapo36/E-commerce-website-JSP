<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Settings | Centrum Seller Portal</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/seller-dashboard.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
	<div class="dashboard-container">
		<!-- Sidebar -->
  <aside class="sidebar">
        <div class="sidebar-header">
            <div class="brand">
                <div class="brand-logo">C</div>
                <div class="brand-name">Centrum</div>
            </div>
        </div>

        <nav class="sidebar-nav">
            <ul class="nav-list">
                <li class="nav-item"><a href="<%=request.getContextPath()%>/dashboard" class="nav-link active">
                    <i class="fas fa-tachometer-alt"></i> Dashboard
                </a></li>
                <li class="nav-item"><a href="<%=request.getContextPath()%>/products/dashboard" class="nav-link">
                    <i class="fas fa-box"></i> Products
                </a></li>
                <li class="nav-item"><a href="<%=request.getContextPath()%>/product/add" class="nav-link">
                    <i class="fas fa-plus-circle"></i> Add Product
                </a></li>
                <li class="nav-item"><a href="<%=request.getContextPath()%>/orders" class="nav-link">
                    <i class="fas fa-shopping-cart"></i> Orders
                </a></li>
                <li class="nav-item"><a href="<%=request.getContextPath() %>/reports" class="nav-link">
                    <i class="fas fa-chart-bar"></i> Reports
                </a></li>
                <li class="nav-item"><a href="<%=request.getContextPath()%>/Acoount/settings" class="nav-link">
                    <i class="fas fa-cog"></i> Settings
                </a></li>
                <li class="nav-item"><a href="<%=request.getContextPath() %>/Account/profile" class="nav-link">
                    <i class="fas fa-user"></i> Profile
                </a></li>
            </ul>
        </nav>

        <div class="sidebar-footer">
            <div class="user-profile">
                <div class="user-avatar">S</div>
                <div class="user-info">
                    <div class="user-name">${seller.shopName}</div>
                    <div class="user-email">${seller.email}</div>
                </div>
                <a href="<%=request.getContextPath()%>/logout-account" class="action-btn">
                    <i class="fas fa-sign-out-alt"></i>
                </a>
            </div>
        </div>
    </aside>
		<main class="main-content">
			<header class="top-bar">
				<h1 class="page-title">Settings</h1>
				<div class="top-bar-actions">
				
				
				</div>
			</header>

			<div class="content-area">
				<div class="grid"
					style="grid-template-columns: 250px 1fr; gap: var(--spacing-xl);">

					<div class="card">
						<div class="card-header">
							<h2 class="card-title">Store Settings</h2>
						</div>
						<div class="card-body">


							<form action="<%=request.getContextPath()%>/Acoount/settings" method="POST">
								<div class="form-group">
									<label for="shopName" class="form-label">Store Name</label> <input
										type="text" id="shopName" name="shopName" class="form-control"
										value="${seller.shopName}" required>
								</div>

								<div class="form-group">
									<label for="ownerName" class="form-label">Owner Name</label> <input
										type="text" id="ownerName" name="ownerName"
										class="form-control" value="${seller.ownerName}" >
								</div>

								<div class="form-group">
									<label for="phone" class="form-label">Phone</label> <input
										type="tel" id="phone" name="phone" class="form-control"
										value="${seller.phone}">
								</div>

					
								<div class="flex gap-md mt-lg">
									<button type="submit" class="btn btn-primary">
										<i class="fas fa-save"></i> Save Changes
									</button>
									<button type="reset" class="btn btn-secondary">Reset</button>
								</div>
							</form>
						</div>
					</div>

				</div>
			</div>
		</main>
	</div>
</body>
</html>
