<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.centrum.bean.Product"%>
<%
Product product = (Product) request.getAttribute("product");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Edit Product | Centrum Seller Portal</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/seller-dashboard.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
	<div class="dashboard-container">
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
                    <div class="user-name"><%= request.getAttribute("shopName") %></div>
                    <div class="user-email"><%= request.getAttribute("email") %></div>
                </div>
                <a href="<%=request.getContextPath()%>/logout-account" class="action-btn">
                    <i class="fas fa-sign-out-alt"></i>
                </a>
            </div>
        </div>
    </aside>
		<main class="main-content">
			<header class="top-bar">
				<h1 class="page-title">Edit Product</h1>
			</header>

			<div class="content-area">
				<div class="card">
					<div class="card-header">
						<h2 class="card-title">Edit Product Details</h2>
						<span class="form-text">ID: PROD-<%=product.getProduct_id()%></span>
					</div>
					<div class="card-body">
						<form action="${pageContext.request.contextPath}/product/edit"
							method="POST" enctype="multipart/form-data">
							<input type="hidden" name="productId"
								value="<%=product.getProduct_id()%>">

							<div class="form-group">
								<label for="productName" class="form-label">Product Name</label>
								<input type="text" id="productName" name="productName"
									class="form-control" value="<%=product.getProduct_name()%>"
									required>
							</div>

							<div class="grid"
								style="grid-template-columns: 1fr 1fr; gap: var(--spacing-md);">
						
								<div class="form-group">
									<label for="category" class="form-label">Category</label> <select
										id="category" name="category" class="form-control" required>
										<option value="Electronics"
											<%="Electronics".equalsIgnoreCase(product.getCategory()) ? "selected" : ""%>>Electronics</option>
										<option value="Fashion"
											<%="Fashion".equalsIgnoreCase(product.getCategory()) ? "selected" : ""%>>Fashion</option>
										<option value="Home"
											<%="Home".equalsIgnoreCase(product.getCategory()) ? "selected" : ""%>>Home
											& Garden</option>
									</select>
								</div>
							</div>

							<div class="grid"
								style="grid-template-columns: 1fr 1fr; gap: var(--spacing-md);">
								<div class="form-group">
									<label for="price" class="form-label">Price</label> <input
										type="number" id="price" name="price" class="form-control"
										value="<%=product.getPrice()%>" step="0.01" required>
								</div>
								<div class="form-group">
									<label for="stock" class="form-label">Stock Quantity</label> <input
										type="number" id="stock" name="stock" class="form-control"
										value="<%=product.getStock_quantity()%>" required>
								</div>
							</div>

							<div class="form-group">
								<label for="description" class="form-label">Description</label>
								<textarea id="description" name="description"
									class="form-control" rows="4"><%=product.getDescription() != null ? product.getDescription() : ""%></textarea>
							</div>

							<!-- Form Actions -->
							<div class="flex gap-md mt-lg">
								<button type="submit" class="btn btn-primary">
									<i class="fas fa-save"></i> Update Product
								</button>
								<a href="${pageContext.request.contextPath}/products/dashboard"
									class="btn btn-secondary">Cancel</a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</main>
	</div>
</body>
</html>
