<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Reports & Analytics | Centrum Seller Portal</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/seller-dashboard.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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
				<h1 class="page-title">Reports & Analytics</h1>
				<div class="top-bar-actions">
					<div class="user-profile">
						<div class="user-avatar">S</div>
						<span>${email}</span>
					</div>
				</div>
			</header>

			<div class="content-area">
				<!-- Key Metrics -->
				<div class="stats-grid mb-lg">
					<div class="card stat-card">
						<div class="stat-icon">
							<i class="fas fa-dollar-sign"></i>
						</div>
						<div class="stat-value">
							$<%=String.format("%.2f", request.getAttribute("totalRevenue"))%></div>
						<div class="stat-label">Total Revenue</div>
					</div>

					<div class="card stat-card">
						<div class="stat-icon">
							<i class="fas fa-shopping-cart"></i>
						</div>
						<div class="stat-value"><%=request.getAttribute("totalOrders")%></div>
						<div class="stat-label">Total Orders</div>
					</div>

					<div class="card stat-card">
						<div class="stat-icon">
							<i class="fas fa-chart-line"></i>
						</div>
						<div class="stat-value">
							$<%=String.format("%.2f", request.getAttribute("avgOrderValue"))%></div>
						<div class="stat-label">Avg. Order Value</div>
					</div>
				</div>

				<!-- Charts -->
				<div class="grid mb-lg"
					style="grid-template-columns: 2fr 1fr; gap: var(--spacing-lg);">
					<!-- Sales Chart -->
					<div class="card">
						<div class="card-header">
							<h2 class="card-title">Revenue by Month</h2>
						</div>
						<div class="card-body">
							<canvas id="salesChart"></canvas>
						</div>
					</div>

					<!-- Top Products Chart -->
					<div class="card">
						<div class="card-header">
							<h2 class="card-title">Top Products</h2>
						</div>
						<div class="card-body">
							<canvas id="topProductsChart"></canvas>
						</div>
					</div>
				</div>

				<!-- Revenue by Category -->
				<div class="card">
					<div class="card-header">
						<h2 class="card-title">Revenue by Category</h2>
					</div>
					<div class="card-body">
						<canvas id="categoryChart"></canvas>
					</div>
				</div>

				<!-- Order Status Distribution -->
				<div class="card">
					<div class="card-header">
						<h2 class="card-title">Order Status</h2>
					</div>
					<div class="card-body">
						<canvas id="ordersChart"></canvas>
					</div>
				</div>
			</div>
		</main>
	</div>

	<script>
		// Top Products from servlet
		const topProductsData =
	<%=new com.google.gson.Gson().toJson(request.getAttribute("topProducts"))%>
		;
		const revenueByCategoryData =
	<%=new com.google.gson.Gson().toJson(request.getAttribute("revenueByCategory"))%>
		;
		const orderStatusData =
	<%=new com.google.gson.Gson().toJson(request.getAttribute("orderStatus"))%>
		;

		// Sales chart (dummy monthly revenue example; ideally fetch from DAO for monthly breakdown)
		const salesCtx = document.getElementById('salesChart').getContext('2d');
		const salesChart = new Chart(salesCtx, {
			type : 'line',
			data : {
				labels : [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul',
						'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ],
				datasets : [ {
					label : 'Revenue ($)',
					data : [ 1200, 1500, 1800, 2200, 2400, 2600, 3000, 2800,
							3100, 3300, 3500, 4000 ],
					borderColor : '#000000',
					backgroundColor : 'rgba(0,0,0,0.1)',
					fill : true,
					tension : 0.3
				} ]
			}
		});

		// Top Products Chart
		const topProductsCtx = document.getElementById('topProductsChart')
				.getContext('2d');
		new Chart(topProductsCtx, {
			type : 'bar',
			data : {
				labels : Object.keys(topProductsData),
				datasets : [ {
					label : 'Revenue ($)',
					data : Object.values(topProductsData),
					backgroundColor : 'rgba(0,0,0,0.6)'
				} ]
			}
		});

		// Revenue by Category Chart
		const categoryCtx = document.getElementById('categoryChart')
				.getContext('2d');
		new Chart(categoryCtx, {
			type : 'doughnut',
			data : {
				labels : Object.keys(revenueByCategoryData),
				datasets : [ {
					data : Object.values(revenueByCategoryData),
					backgroundColor : [ '#000000', '#333333', '#666666',
							'#999999', '#CCCCCC' ]
				} ]
			}
		});

		// Order Status Chart
		const ordersCtx = document.getElementById('ordersChart').getContext(
				'2d');
		new Chart(ordersCtx, {
			type : 'pie',
			data : {
				labels : Object.keys(orderStatusData),
				datasets : [ {
					data : Object.values(orderStatusData),
					backgroundColor : [ 'green', 'orange', 'blue', 'purple',
							'red' ]
				} ]
			}
		});
	</script>
</body>
</html>
