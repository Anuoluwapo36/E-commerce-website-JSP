<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.centrum.bean.Product"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Products | Centrum Seller Portal</title>
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
				<h1 class="page-title">Products</h1>
				<div class="top-bar-actions">
					
					
				</div>
			</header>

			<div class="content-area">
				<%
				String success = request.getParameter("success");
				String error = request.getParameter("error");

				if ("deleted".equals(success)) {
				%>
				<div class="alert alert-success">Product deleted successfully.</div>
				<%
				} else if ("notfound".equals(error)) {
				%>
				<div class="alert alert-warning">Product not found or you
					don't have permission.</div>
				<%
				} else if ("db".equals(error)) {
				%>
				<div class="alert alert-danger">Database error occurred while
					deleting product.</div>
				<%
				}
				%>
				<div class="card mb-lg">
					<div class="card-body">
						<div class="flex items-center justify-between">
							<div>
								<h2 class="card-title mb-sm">Manage Your Products</h2>
								<p class="form-text">
									You have
									<%
								List<Product> products = (List<Product>) request.getAttribute("products");
								int count = (products != null) ? products.size() : 0;
								out.print(count);
								%>
									products in your catalog
								</p>
							</div>
							<div class="flex gap-md">
								<div class="form-group mb-0">
									<input type="text" class="form-control"
										placeholder="Search products...">
								</div>
								<a href="<%=request.getContextPath()%>/product/add" class="btn btn-primary"> <i
									class="fas fa-plus"></i> Add Product
								</a>
							</div>
						</div>
					</div>
				</div>

				<!-- Products Table -->
				<div class="card">
					<div class="card-body">
						<div class="table-container">
							<table class="table">
								<thead>
									<tr>
										<th>Product</th>
										<th>SKU</th>
										<th>Category</th>
										<th>Price</th>
										<th>Stock</th>
										<th>Status</th>
										<th>Actions</th>
									</tr>
								</thead>
								<tbody>
									<%
									if (products != null && !products.isEmpty()) {


										for (Product product : products) {
									%>
									<tr>
										<td>
											<div class="flex items-center gap-sm">
												<div
													style="width: 40px; height: 40px; border-radius: 4px; overflow: hidden;">
													<img
														src="<%=request.getContextPath()%>/<%=product.getImage_url()%>"
														alt="<%=product.getProduct_name()%>"
														style="width: 100%; height: 100%; object-fit: cover;">

												</div>
												<div>
													<div><%=product.getProduct_name()%></div>

												</div>
											</div>
										</td>
										<td>P-<%=product.getProduct_id()%></td>
										<td><%=product.getCategory()%></td>
										<td>$<%=String.format("%.2f", product.getPrice())%></td>
										<td><%=product.getStock_quantity()%></td>
										<td><span
											class="status-badge <%=product.getStock_quantity() > 0 ? "status-active" : "status-inactive"%>">
												<%=product.getStock_quantity() > 0 ? "Active" : "Out of stock"%>
										</span></td>
										<td>
											<div class="btn-group">
												<a
													href="<%=request.getContextPath()%>/product/edit?id=<%=product.getProduct_id()%>"
													class="action-btn edit" title="Edit"> <i
													class="fas fa-edit"></i>
												</a>
												<form action="<%=request.getContextPath()%>/products/delete"
													method="post" style="display: inline;">
													<input type="hidden" name="productId"
														value="<%=product.getProduct_id()%>" />
													<button class="action-btn delete" title="Delete"
														onclick="return confirm('Delete this product and all its images?');">
														<i class="fas fa-trash"></i>
													</button>
												</form>

											
											</div>
										</td>
									</tr>
									<%
									}

									} else {
									%>
									<tr>
										<td colspan="7" style="text-align: center;">No products
											found.</td>
									</tr>
									<%
									}
									%>
								</tbody>
							</table>
						</div>

						<div class="pagination">
							<a href="#" class="page-link"> <i class="fas fa-chevron-left"></i>
							</a> <a href="#" class="page-link active">1</a> <a href="#"
								class="page-link">2</a> <a href="#" class="page-link">3</a> <a
								href="#" class="page-link"> <i class="fas fa-chevron-right"></i>
							</a>
						</div>
					</div>
				</div>
			</div>
		</main>
	</div>
</body>
</html>
