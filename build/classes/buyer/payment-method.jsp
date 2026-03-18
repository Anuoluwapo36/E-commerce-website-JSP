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





<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>centrum.carts</title>
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

</head>
<body>
	<div class="structure-container">
		<%@ include file="/WEB-INF/header.jsp"%>




		<div class="frame-container">

			<aside class="left-side-bar">

				<ul class="payment-sidebar">
					<li class="sidebar-section"><strong>Your Account</strong>
						<ul>
							<li><a
								href="${pageContext.request.contextPath}/product-browsing">Home</a></li>

							<li><a href="${pageContext.request.contextPath}/profile">Profile</a></li>
							<li><a href="${pageContext.request.contextPath}/tracking">Order
									History</a></li>
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
							<li><a href="/settings/security">Personal settings</a></li>
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

			<div class="structure-main">

				<div class="card-head">

					<div class="card-hold">
						<div class="text-btn">
							<h4>Your Shipping Address</h4>
							<button class="input-address" id="change-address" type="button">Add
								New Address</button>
						</div>

						<div class="address-container">
							<div class="address-display-body">
								<p class="txt">Default Address Information</p>

								<%
								List<Order_items> orderItems = (List<Order_items>) request.getAttribute("order_items");
								Order order = (Order) request.getAttribute("order");
								%>

							  <%
    User_address address = (User_address) request.getAttribute("address");
    if (address != null) {
    %>
        <div class="address-item">
            <p><strong>Phone:</strong> <%= address.getPhone_number() %></p>
            <p><strong>Address:</strong> <%= address.getDetails_address() %></p>
            <p><strong>City:</strong> <%= address.getCity() %></p>
            <p><strong>ZIP:</strong> <%= address.getPostal_code() %></p>
        </div>
    <%
    } else {
    %>
        <p>No default address found. Please add one.</p>
    <%
    }
    %>

							</div>
						</div>
					</div>

				</div>

				<div class="show-up">



					<jsp:include page="../buyer/address-form.jsp" />


				</div>

				<div
					class="show-payment-success ${param.success == 'true'? 'active' : '' }">

					<jsp:include page="../buyer/payment-success.jsp" />

				</div>
				<div id="messageContainer" class="message-container hidden">
					<%
					String successMsg = request.getParameter("success");
					if (successMsg != null && !successMsg.isEmpty()) {
					%>
					<div id="successMessage" class="message success">
						<%=successMsg%>
					</div>
					<%
					}
					%>

					<%
					String errorMsg = request.getParameter("error");
					if (errorMsg != null && !errorMsg.isEmpty()) {
					%>
					<div id="errorMessage" class="message error">
						<%=errorMsg%>
					</div>
					<%
					}
					%>
				</div>

				<div class="payment-space">
					<div class="text-p">
						<h4>Payment Method</h4>
					</div>

					<div class="payment-options">
						<div class="payment-option">
							<input type="radio" name="payment" id="credit" checked
								onclick="showPaymentForm('credit')"> <label for="credit">Credit/Debit
								Card</label>
						</div>
						<div class="payment-option">
							<input type="radio" name="payment" id="alipay"
								onclick="showPaymentForm('alipay')"> <label for="alipay">Alipay</label>
						</div>
						<div class="payment-option">
							<input type="radio" name="payment" id="wechat"
								onclick="showPaymentForm('wechat')"> <label for="wechat">Wechat</label>
						</div>
					</div>

					<!-- Hidden forms (loaded upfront) -->
					<div id="payment-form-container" class="payment-form-container">


						<!-- Credit Form (visible by default) -->
						<div id="form-credit" class="payment-form">
							<jsp:include page="../payment/credit.jsp" />
						</div>



						<!-- Alipay Form (hidden) -->
						<div id="form-alipay" class="payment-form" style="display: none;">
							<jsp:include page="../payment/alipay.jsp" />
							<!-- Add this file (even blank) -->
						</div>

						<!-- Wechat Form (hidden) -->
						<div id="form-wechat" class="payment-form" style="display: none;">
							<jsp:include page="../payment/wechat.jsp" />
							<!-- Add this file (even blank) -->
						</div>
					</div>


				</div>

			</div>

			<aside class="right-side-bar">

				<div class="sub-side">
					<h4>Order summary</h4>

					<%
					if (order != null && orderItems != null && !orderItems.isEmpty()) {
						double totalAmount = 0;
						for (Order_items item : orderItems) {
							Product product = item.getProduct();
							totalAmount += item.getPrice() * item.getQuantity();
					%>

					<div class="hold">
						<div class="summary-name">
							<p class="product-name"><%=product.getProduct_name()%>
								<span class="unit">x<%=item.getQuantity()%></span>
							</p>
							<span class="ttl">$<%=item.getPrice() * item.getQuantity()%></span>
						</div>
						<div class="summary-total">
							<p class="product-name-sub"><%=product.getProduct_name()%></p>
							<span class="unit-sub"><%=item.getQuantity()%></span> <span
								class="price-sub">$<%=product.getPrice()%></span>
						</div>
					</div>

					<%
					}
					%>

					<div class="hold-2">
						<div class="payable-total">
							Subtotal
							<p>
								$<%=totalAmount%></p>
						</div>
						<div class="payable-total">
							Shipping
							<p>$0</p>
						</div>
						<div class="payable-total">
							Total
							<p>
								$<%=totalAmount%></p>
						</div>
					</div>

					<%
					} else {
					%>
					<p class="product-name-error">Order can't be found!!</p>
					<%
					}
					%>
				</div>

			</aside>


		</div>
	</div>




	<%@ include file="/WEB-INF/footer.jsp"%>


	<script>
		const contextPath = '${pageContext.request.contextPath}';
	</script>
	<script src="${pageContext.request.contextPath}/js/payment-method.js"></script>
	<script>
		if (window.formLoaded) {
			window.formLoaded('credit'); // or 'alipay', 'wechat'
		}
	</script>

</body>

</html>