<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.centrum.bean.User"%>
<%@ page import="com.centrum.bean.Product"%>
<%@ page import="com.centrum.bean.Order"%>
<%@ page import="com.centrum.bean.Order_items"%>
<%@ page import="com.centrum.bean.User_address"%>

<%@ page import="java.util.List"%>
<%@ page import="com.centrum.bean.ProductImages"%>
<%@ page import="com.centrum.bean.RecommendProduct"%>

<%@ page import="com.centrum.bean.Cart"%>
<%@ page import="java.text.SimpleDateFormat"%>






<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>centrum.OrderHistoryandTracking</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/tracking.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/payment-method.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/address.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/credit.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/Qr.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/payment-success.css">
<link
	href="https://fonts.googleapis.com/css2?family=Pacifico&display=swap"
	rel="stylesheet">


<link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"
      integrity="sha256-p4NxAoJBhIIN+hmNHrzRCf9tD/miZyoHS5obTRR9BMY=" crossorigin="" />

<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"
        integrity="sha256-20nQCchB9co0qIjJZRGuk2/Z9VM+kNiyxNV1lvTlZBo=" crossorigin=""></script>
</head>
<body>
	<%@ include file="/WEB-INF/header.jsp"%>

	<div class="structure-container">



	
		<div class="frame-order-container">

			<aside class="left-side-bar">

				<ul class="payment-sidebar">
					<li class="sidebar-section"><strong>Your Account</strong>
						<ul>
							<li><a
								href="${pageContext.request.contextPath}/product-browsing">Home</a></li>

							<li><a href="/profile">Profile</a></li>
							<li><a href="/profile/orders">Order History</a></li>
						</ul></li>

					<li class="sidebar-section"><strong>Payments</strong>
						<ul>
							<li><a href="/payment-methods" class="active">Payment
									Methods</a></li>
							<li><a href="/wallet">Wallet</a></li>
							<li><a href="/payment-history">Payment Records</a></li>
						</ul></li>

					<li class="sidebar-section"><strong>Settings</strong>
						<ul>
							<li><a href="/settings/security">Security</a></li>
							<li><a href="/settings/notifications">Notifications</a></li>
							<li><a href="/settings/privacy">Privacy</a></li>
						</ul></li>

					<li class="sidebar-section"><strong>Help</strong>
						<ul>
							<li><a href="/help/payments">FAQs</a></li>
							<li><a href="/help/contact">Support</a></li>
						</ul></li>
				</ul>

			</aside>

			<div class="tracking-main">
				<div class="order-head">
					<%-- Map commented out for now --%>
					<div class="tracking" id="tracking-map"></div>
				</div>

				<%-- Error/Empty State --%>
				<%
				List<Order> orderHistory = (List<Order>) request.getAttribute("orderHistory");
				%>
				<%
				String error = (String) request.getAttribute("error");
				%>
				<%
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				%>

				<%
				if (error != null && !error.isEmpty()) {
				%>
				<div style="text-align: center; color: red; padding: 10px;">
					<%=error%>
				</div>
				<%
				} else if (orderHistory == null || orderHistory.isEmpty()) {
				%>
				<div style="text-align: center; padding: 10px;">No orders
					found yet. Start shopping!</div>
				<%
				} else {
				%>
				<%-- Single Table  --%>
				<div class="order-space">
					<div class="card-hold">
						<div class="text-btn">
							<h4>order management history</h4>
						</div>
						<div class="order-history-container">
							<table class="order-history-table">
								<thead>
									<tr>
										<th>Product Image</th>
										<th>Name</th>
										<th>Description</th>
										<th>Price</th>
										<th>Quantity</th>
										<th>Total Payment</th>
										<th>Status</th>
										<th>Order Date</th>
										<th>Shipping Status</th>
										<th>Courier</th>
										<th>Tracking Number</th>
										<th>Payment Method</th>
									</tr>
								</thead>
								<tbody>
									<%-- Outer Loop for my reminder not to forget  Each Order --%>
									<%
									for (Order order : orderHistory) {
									%>
									<%
									List<Product> products = order.getProducts();
									%>
									<%
									
									
									if (products != null && !products.isEmpty()) {
									%>
									<%-- Inner Loop for Each Product in the Order --%>
									<%
									for (Product product : products) {
									%>
									<%
									Order_items orderItem = product.getOrderItem();
									%>
									<%
									String productImageUrl = null;
									%>
									<%-- Get image URL from ProductImages  --%>
									<%
									if (product.getOrderItem() != null && product.getOrderItem().getProduct() != null) {
									%>
									<%
									ProductImages pi = product.getOrderItem().getProduct().getProductImages();
									%>
									<%
									productImageUrl = (pi != null) ? pi.getImage_url() : null;
									%>
									<%
									}
									%>
									<%
									int quantity = (orderItem != null) ? orderItem.getQuantity() : 1;
									%>
									<%
									double totalPayment = product.getPrice() * quantity;
									%>

									<tr>
										<%-- Dynamic Data Here  --%>
										<td>
											<div class="product-image-container">
												<img
													src="<%=productImageUrl != null ? request.getContextPath() + "/" + productImageUrl : request.getContextPath() + "/Image/placeholder.png"%>"
													alt="<%=product.getProduct_name()%>" class="product-image">
											</div>
										</td>
										<td><%=product.getProduct_name()%></td>
										<td class="product-desc-truncated"
											title="<%=product.getDescription() != null ? product.getDescription() : "No description"%>">
											<%=product.getDescription() != null ? product.getDescription() : "No description"%>
										</td>
										<td>$<%=String.format("%.2f", product.getPrice())%></td>
										<td><%=quantity%></td>
										<td>$<%=String.format("%.2f", totalPayment)%></td>
										<td>
											<%
											String status = order.getStatus() != null ? order.getStatus().trim() : "Unknown";
											boolean isPending = status.equalsIgnoreCase("Pending");
											%> 
											<%
											if (isPending) {
											%> <a
											href="${pageContext.request.contextPath}/payment-method?order_id=<%=order.getOrder_id()%>"
											class="status-badge" data-status="<%=status%>"
											>
												<%=status%>
										</a> <%
 } else {
 %> <span class="status-badge"
											data-status="<%=status%>"> <%=status%>
										</span> <%
 }
 %>
										</td>
										<td><%=dateFormat.format(order.getCreate_at())%></td>
										<td><%=order.getShippingStatus() != null ? order.getShippingStatus() : "N/A"%></td>
										<td><%=order.getCourier() != null ? order.getCourier() : "N/A"%></td>
										<td><%=order.getTrackingNumber() != null ? order.getTrackingNumber() : "N/A"%></td>
										<td><%=order.getPayment_method() != null ? order.getPayment_method() : "N/A"%></td>
									</tr>
									<%
									}
									%>
									<%
									}
									%>
									<%
									}
									%>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<%
				}
				%>
			</div>



		</div>
	</div>




	<%@ include file="/WEB-INF/footer.jsp"%>


	<script>
		const contextPath = '${pageContext.request.contextPath}';
	</script>
	<script src="${pageContext.request.contextPath}/js/payment-method.js"></script>
	<script src="${pageContext.request.contextPath}/js/tracking-map.js"></script>

</body>

</html>