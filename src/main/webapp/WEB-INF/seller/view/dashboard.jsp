<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.centrum.bean.Order" %>
<%@ page import="com.centrum.bean.Product" %>
<%@ page import="com.centrum.bean.Order_items" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Dashboard | Centrum Seller Portal</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller-dashboard.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
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
        <!-- Top Bar -->
        <header class="top-bar">
            <h1 class="page-title">Dashboard</h1>
            <div class="top-bar-actions">
               
                <div class="user-profile">
                    <div class="user-avatar">S</div>
                </div>
            </div>
        </header>

        <div class="content-area">
            <div class="stats-grid">
                <div class="card stat-card">
                    <div class="stat-icon"><i class="fas fa-shopping-cart"></i></div>
                    <div class="stat-value"><%= request.getAttribute("totalOrders") %></div>
                    <div class="stat-label">Total Orders</div>
                </div>
                <div class="card stat-card">
                    <div class="stat-icon"><i class="fas fa-dollar-sign"></i></div>
                    <div class="stat-value">$<%= request.getAttribute("totalRevenue") %></div>
                    <div class="stat-label">Total Revenue</div>
                </div>
                <div class="card stat-card">
                    <div class="stat-icon"><i class="fas fa-box"></i></div>
                    <div class="stat-value"><%= request.getAttribute("totalProducts") %></div>
                    <div class="stat-label">Products</div>
                </div>
               
            </div>

            <!-- Recent Orders -->
            <div class="card mb-lg">
                <div class="card-header">
                    <h2 class="card-title">Recent Orders</h2>
                </div>
                <div class="card-body">
                    <div class="table-container">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Order ID</th>
                                    <th>Customer</th>
                                    <th>Date</th>
                                    <th>Amount</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    List<Order> recentOrders = (List<Order>) request.getAttribute("recentOrders");
                                    if (recentOrders != null) {
                                        for (Order order : recentOrders) {
                                %>
                                <tr>
                                    <td>#ORD-<%= order.getOrder_id() %></td>
                                    <td>User <%= order.getUser_id() %></td>
                                    <td><%= order.getCreate_at() %></td>
                                    <td>$<%= order.getTotal_amount() %></td>
                                    <td>
                                        <span class="status-badge <%= 
                                            "Processing".equals(order.getStatus()) ? "status-active" : 
                                            "Cancelled".equals(order.getStatus()) ? "status-cancelled" : 
                                            "status-pending" %>">
                                            <%= order.getStatus() %>
                                        </span>
                                    </td>
                                   
                                </tr>
                                <%
                                        }
                                    }
                                %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <!-- Low Stock Products -->
            <div class="card">
                <div class="card-header">
                    <h2 class="card-title">Low Stock Products</h2>
                </div>
                <div class="card-body">
                    <div class="table-container">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Product</th>
                                    <th>SKU</th>
                                    <th>Current Stock</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    List<Product> lowStockProducts = (List<Product>) request.getAttribute("lowStockProducts");
                                    if (lowStockProducts != null) {
                                        for (Product p : lowStockProducts) {
                                %>
                                <tr>
                                    <td><%= p.getProduct_name() %></td>
                                    <td><%= p.getProduct_id() %></td>
                                    <td><%= p.getStock_quantity() %></td>
                                    <td><a href="<%=request.getContextPath()%>/product/edit?id=<%=p.getProduct_id()%>"class="btn btn-secondary btn-sm">Restock</a></td>
                                </tr>
                                <%
                                        }
                                    }
                                %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        </div>
    </main>
</div>
</body>
</html>
