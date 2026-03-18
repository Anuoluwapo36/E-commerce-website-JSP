<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Add Product | Centrum Seller Portal</title>
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

		<!-- Main Content -->
		<main class="main-content">
			<header class="top-bar">
				<h1 class="page-title">Add Product</h1>
				<div class="top-bar-actions">
					
					<div class="user-profile">
						<div class="user-avatar">S</div>
					</div>
				</div>
			</header>

			<div class="content-area">
				<div class="card">
					<div class="card-header">
						<h2 class="card-title">Product Information</h2>
					</div>
					<%
					String error = (String) request.getAttribute("error");
					String success = request.getParameter("success");

					if (error != null) {
					%>
					<div
						style="padding: 10px; background-color: #f8d7da; color: #721c24; border-radius: 5px; margin-bottom: 15px;">
						<%=error%>
					</div>
					<%
					}

					if (success != null) {
					%>
					<div
						style="padding: 10px; background-color: #d4edda; color: #155724; border-radius: 5px; margin-bottom: 15px;">
						<%=success%>
					</div>
					<%
					}
					%>

					<div class="card-body">
						<form action="<%=request.getContextPath()%>/product/add"
							method="POST" enctype="multipart/form-data">
							<!-- Basic Information -->
							<div class="mb-lg">
								<h3 class="mb-md">Basic Information</h3>
								<div class="form-group">
									<label for="productName" class="form-label">Product
										Name</label> <input type="text" id="productName" name="productName"
										class="form-control" required>
									<div class="form-text">Enter the name of your product</div>
								</div>


								<div class="form-group">
									<label for="category" class="form-label">Category</label> <select
										id="category" name="category" class="form-control" required>
										<option value="">Select Category</option>
										<option value="electronics">Electronics</option>
										<option value="fashion">Fashion</option>
										<option value="home">Home & Garden</option>
										<option value="beauty">Beauty</option>
										<option value="sports">Sports</option>
									</select>
								</div>


								<div class="form-group">
									<label for="description" class="form-label">Description</label>
									<textarea id="description" name="description"
										class="form-control" rows="4"></textarea>
								</div>
							</div>

							<!-- Pricing & Inventory -->
							<div class="mb-lg">
								<h3 class="mb-md">Pricing & Inventory</h3>
								<div class="grid"
									style="grid-template-columns: 1fr 1fr; gap: var(--spacing-md);">
									<div class="form-group">
										<label for="price" class="form-label">Price</label> <input
											type="number" id="price" name="price" class="form-control"
											step="0.01" required>
									</div>
									<div class="form-group">
										<label for="comparePrice" class="form-label">Compare
											at Price</label> <input type="number" id="comparePrice"
											name="comparePrice" class="form-control" step="0.01">
									</div>
									<div class="form-group">
										<label for="cost" class="form-label">Cost per Item</label> <input
											type="number" id="cost" name="cost" class="form-control"
											step="0.01">
									</div>
									<div class="form-group">
										<label for="stock" class="form-label">Stock Quantity</label> <input
											type="number" id="stock" name="stock" class="form-control"
											required>
									</div>
								</div>
							</div>

							<!-- Images -->
							<div class="mb-lg">
								<h3 class="mb-md">Product Images</h3>
								<div class="form-group">
									<label for="images" class="form-label">Upload Images</label> <input
										type="file" id="images" name="images" class="form-control"
										multiple accept="image/*">
									<div class="form-text">Upload product images (max 5
										files, 5MB each)</div>
								</div>
							</div>

							<!-- Form Actions -->
							<div class="flex gap-md">
								<button type="submit" class="btn btn-primary">
									<i class="fas fa-save"></i> Save Product
								</button>
								<a href="products.jsp" class="btn btn-secondary">Cancel</a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</main>
	</div>
</body>
</html>