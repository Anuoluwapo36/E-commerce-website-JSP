<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.centrum.bean.Order"%>
<%@ page import="com.centrum.bean.User"%>
<%
List<Order> orders = (List<Order>) request.getAttribute("orders");
int pendingCount = request.getAttribute("pendingCount") != null ? (int) request.getAttribute("pendingCount") : 0;
int processingCount = request.getAttribute("processingCount") != null ? (int) request.getAttribute("processingCount")
		: 0;
int shippedCount = request.getAttribute("shippedCount") != null ? (int) request.getAttribute("shippedCount") : 0;
int deliveredCount = request.getAttribute("deliveredCount") != null ? (int) request.getAttribute("deliveredCount") : 0;
%>
<%
String shopName = (String) request.getAttribute("shopName");

if (shopName == null || shopName.isEmpty()) {


	shopName = "S";

}

String shopInitial = shopName.substring(0, 1).toUpperCase();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Orders | Centrum Seller Portal</title>
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
				<h1 class="page-title">Orders</h1>
				<div class="top-bar-actions">
					<button class="notification-btn">
						<i class="fas fa-bell"></i> <span class="notification-badge">3</span>
					</button>
					<div class="user-profile">
						<div class="user-avatar">S</div>
					</div>
				</div>
			</header>

			<div class="content-area">
				<!-- Orders Stats -->
				<div class="stats-grid mb-lg">
					<div class="card stat-card">
						<div class="stat-icon">
							<i class="fas fa-clock"></i>
						</div>
						<div class="stat-value"><%=pendingCount%></div>
						<div class="stat-label">Pending</div>
					</div>
					<div class="card stat-card">
						<div class="stat-icon">
							<i class="fas fa-cog"></i>
						</div>
						<div class="stat-value"><%=processingCount%></div>
						<div class="stat-label">Processing</div>
					</div>
					<div class="card stat-card">
						<div class="stat-icon">
							<i class="fas fa-shipping-fast"></i>
						</div>
						<div class="stat-value"><%=shippedCount%></div>
						<div class="stat-label">Shipped</div>
					</div>
					<div class="card stat-card">
						<div class="stat-icon">
							<i class="fas fa-check-circle"></i>
						</div>
						<div class="stat-value"><%=deliveredCount%></div>
						<div class="stat-label">Delivered</div>
					</div>
				</div>

				<div class="card">
					<div class="card-header">
						<h2 class="card-title">Order Management</h2>
						<div class="flex gap-md">
							<select class="form-control" style="width: auto;">
								<option>All Status</option>
								<option>Pending</option>
								<option>Processing</option>
								<option>Shipped</option>
								<option>Delivered</option>
								<option>Cancelled</option>
							</select> <input type="date" class="form-control" style="width: auto;">
							<input type="date" class="form-control" style="width: auto;">
						</div>
					</div>
					<div class="card-body">
						<div class="table-container">
							<table class="table">
								<thead>
									<tr>
										<th>Order ID</th>
										<th>Customer</th>
										<th>Date</th>
										<th>Items</th>
										<th>Total</th>
										<th>Status</th>
										<th>Actions</th>
									</tr>
								</thead>
								<tbody>
									<%
									for (Order order : orders) {
									%>
									<tr>
										<td>#ORD-<%=order.getOrder_id()%></td>
										<td>
											<div>Customer Name</div>
											<div class="form-text">customer@email.com</div>
										</td>
										<td><%=order.getCreate_at()%></td>
										<td><%=order.getProducts().size()%> items</td>
										<td>$<%=order.getTotal_amount()%></td>
										<td colspan="2">
											<form method="post"
												action="<%=request.getContextPath()%>/orders"
												class="order-action-form">
												<input type="hidden" name="action" value="updateStatus">
												<input type="hidden" name="orderId"
													value="<%=order.getOrder_id()%>">

												<!-- STATUS DROPDOWN -->
												<select name="status" class="form-control"
													style="width: auto; padding: 4px 8px; display: inline-block;">
													<%
													if ("pending".equalsIgnoreCase(order.getStatus())) {
													%>
													<option value="pending" selected>Pending</option>
													<option value="processing">Processing</option>
													<option value="cancelled">Cancel</option>
													<%
													} else if ("processing".equalsIgnoreCase(order.getStatus())) {
													%>
													<option value="processing" selected>Processing</option>
													<option value="shipped">Shipped</option>
													<%
													} else if ("shipped".equalsIgnoreCase(order.getStatus())) {
													%>
													<option value="shipped" selected>Shipped</option>
													<option value="delivered">Delivered</option>
													<%
													} else if ("delivered".equalsIgnoreCase(order.getStatus())) {
													%>
													<option value="delivered" selected>Delivered</option>
													<%
													} else if ("cancelled".equalsIgnoreCase(order.getStatus())) {
													%>
													<option value="cancelled" selected>Cancelled</option>
													<%
													}
													%>
												</select>

												<div class="btn-group"
													style="display: inline-block; margin-left: 8px;">
													<%
													if ("pending".equalsIgnoreCase(order.getStatus())) {
													%>
													<button type="submit" class="action-btn" title="Process"
														onclick="this.form.status.value='processing'">
														<i class="fas fa-check"></i>
													</button>

													<button type="submit" class="action-btn delete"
														title="Cancel"
														onclick="this.form.status.value='cancelled'">
														<i class="fas fa-times"></i>
													</button>
													<%
													} else if ("processing".equalsIgnoreCase(order.getStatus())) {
													%>
													<button type="submit" class="action-btn" title="Ship"
														onclick="this.form.status.value='shipped'">
														<i class="fas fa-shipping-fast"></i>
													</button>
													<%
													} else if ("shipped".equalsIgnoreCase(order.getStatus())) {
													%>
													<button type="submit" class="action-btn"
														title="Mark Delivered"
														onclick="this.form.status.value='delivered'">
														<i class="fas fa-check-circle"></i>
													</button>
													<%
													} else {
													%>
													<button type="button" class="action-btn" title="View">
														<i class="fas fa-eye"></i>
													</button>
													<%
													}
													%>
												</div>
											</form>
										</td>

									</tr>
									<%
									}
									%>
								</tbody>
							</table>
						</div>

						<div class="pagination">
							<a href="#" class="page-link"><i class="fas fa-chevron-left"></i></a>
							<a href="#" class="page-link active">1</a> <a href="#"
								class="page-link">2</a> <a href="#" class="page-link">3</a> <a
								href="#" class="page-link"><i class="fas fa-chevron-right"></i></a>
						</div>
					</div>
				</div>
			</div>
		</main>
	</div>
</body>
</html>
