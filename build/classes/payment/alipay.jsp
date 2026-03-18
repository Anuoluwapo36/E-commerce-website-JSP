<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.centrum.bean.User"%>
<%@ include file="/WEB-INF/fragments/loading-overlay.jsp"%>

<%
User loggedInUser = (User) session.getAttribute("loggedInUser");
if (loggedInUser == null) {
	response.sendRedirect(request.getContextPath() + "/login-user.jsp?error= please login first");
	return;
}
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Alipay Payment</title>
</head>
<body>
	<div class="alipay-form">
		<input type="hidden" name="paymentType" value="alipay">
		<h4>Pay with Alipay</h4>
		<p>Scan the QR code below to complete payment</p>

		<div class="alipay-qr">
			<img src="${pageContext.request.contextPath}/Image/alipay-qr.png"
				alt="Alipay Payment QR Code">
		</div>

		<div class="alipay-info">
			<p>Open Alipay → Scan QR Code → Confirm Payment</p>
			<p class="alipay-expiry">QR code expires in 15 minutes</p>
		</div>
	</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    if (window.hideLoadingOverlay) {
        hideLoadingOverlay();
    }
});
</script>

</body>
</html>
