<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.centrum.bean.User"%>

<%@ include file="/WEB-INF/fragments/loading-overlay.jsp" %>

<%
User loggedInUser = (User) session.getAttribute("loggedInUser");

if (loggedInUser == null) {
	response.sendRedirect(request.getContextPath() + "/login-user.jsp?error= please login first");
	return;
}
%>
<div class="wechat-form">
	<input type="hidden" name="paymentType" value="wechat">
	<h4>Pay with WeChat Pay</h4>
	<p>Scan the QR code below to complete payment</p>

	<div class="wechat-qr">
		<img src="${pageContext.request.contextPath}/Image/wechat-qr.png"
			alt="WeChat Payment QR Code">
	</div>

	<div class="wechat-info">
		<p>Open WeChat → Discover → Scan → Confirm Payment</p>
	</div>

	<div class="wechat-tip">Tip: Ensure your WeChat wallet has
		sufficient balance or linked bank card</div>
</div>