<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.centrum.bean.User"%>


<%
User loggedInUser = (User) session.getAttribute("loggedInUser");

if (loggedInUser == null) {
	response.sendRedirect(request.getContextPath() + "/login-user.jsp?error= please login first");
	return;
}
%>
<div class="cb-card">
	<form class="credit-card-form"
		action="${pageContext.request.contextPath}/payment-success"
		method="post">


		<input type="hidden" name="action" value="processPayment">
		<div class="middle">


			<div class="card-logos">
				<img src="${pageContext.request.contextPath}/Image/visa-card.png"
					alt="Visa" class="card-logo"> <img
					src="${pageContext.request.contextPath}/Image/master-card.png"
					alt="Mastercard" class="card-logo"> <img
					src="${pageContext.request.contextPath}/Image/Quickteller-Logo.png"
					alt="Quickteller" class="card-logo"> <img
					src="${pageContext.request.contextPath}/Image/Moniepoint-Logo.png"
					alt="American Express" class="card-logo">
			</div>

			<input type="hidden" name="card" value="creditCard"> <input
				type="hidden" name="address_id" value="${address.address_id}"
				> <input type="hidden" name="order_id"
				value="${param.order_id}"> <input type="hidden"
				name="cardType" id="cardType" value="1">



			<div class="form-group">
				<label for="cardNumber">Card Number <span class="required"></span></label>
				<input type="text" id="cardNumber" name="cardNumber"
					class="payment-input" placeholder="1234-5678-9012-3456"
					maxlength="19" oninput="formatCardNumber(this)" > <span
					class="field-hint">16-19 digits (no spaces or symbols)</span>
			</div>

			<div class="form-row">
				<div class="form-group">
					<label for="expiryDate">Expiry Date <span class="required"></span></label>
					<input type="text" id="expiryDate" name="expiryDate"
						class="payment-input" placeholder="MM/YY" maxlength="5"
						oninput="formatExpiry(this)" > <span
						class="field-hint"> 09/28 for September 2028</span>
				</div>

				<div class="form-group">
					<label for="cvv">CVV/CVC <span class="required"></span></label> <input
						type="text" id="cvv" name="cvv" class="payment-input"
						placeholder="123" maxlength="4"
						oninput="this.value = this.value.replace(/\D/g, '')" >
					<span class="field-hint">3-4 digits on back of card</span>
				</div>
			</div>

			<div class="form-group">
				<label for="cardholderName">Cardholder Name <span
					class="required"></span></label> <input type="text" id="cardholderName"
					name="cardholderName" class="payment-input"
					placeholder="As shown on your card" >
			</div>

			<div class="form-group save-card-option">
				<button type="submit" class="pay-btn">Payment</button>
			</div>


		</div>



	</form>
</div>
