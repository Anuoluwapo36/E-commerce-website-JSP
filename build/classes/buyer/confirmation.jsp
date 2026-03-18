<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.centrum.bean.User"%>
<%@ page import="com.centrum.bean.Product"%>
<%@ page import="java.util.List"%>
<%@ page import="com.centrum.bean.ProductImages"%>
<%@ page import="com.centrum.bean.User_address"%>

<%@ page import="com.centrum.bean.RecommendProduct"%>

<%@ page import="com.centrum.bean.Cart"%>




<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>centrum.carts</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/confirm.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/address.css">
</head>
<body>
	<%@ include file="/WEB-INF/header.jsp"%>

	<div class="structure-container">



		<%
		User_address address = (User_address) request.getAttribute("address");

	
		%>



		<div class="frame-container">


			<div class="structure-main">













				<%
				Product confirm = (Product) request.getAttribute("product");
				List<ProductImages> productImageList = (List<ProductImages>) request.getAttribute("productImage");

				String mainImageUrl = "/product/Image/centrum_default.svg";
				if (productImageList != null && !productImageList.isEmpty()) {
					mainImageUrl = productImageList.get(0).getImage_url();
					for (ProductImages img : productImageList) {
						if (img.isIs_Primary()) {
					mainImageUrl = img.getImage_url();
					break;
						}
					}
				}
				%>

				<%
				if (confirm != null) {
				%>






				<main>





					<div class="cart-space">
						<img class="cart-image"
							src="${pageContext.request.contextPath}/<%=mainImageUrl %>"
							alt="<%=confirm.getProduct_name()%>">

						<div class="holder-col">
							<div class="description">
								<p><%=confirm.getProduct_name()%></p>
								<p><%=confirm.getDescription()%></p>
								<p>
									$<%=confirm.getPrice()%></p>


							</div>
							<div class="quanitity">
								<div class="unit-flow">
									<button class="minus-btn">-</button>
									<p id="quantity-display"><%=request.getAttribute("quantity")%></p>
									<button class="plus-btn">+</button>
								</div>
							</div>
						</div>
					</div>






					<div class="payment">
						<!-- ---table head -->
						<h6 class="order-text">Confirm Order Details</h6>

						<table class="order-summary">
							<tr class="summary-header">
								<th class="header-name">Product Name</th>
								<th class="header-qty">Quantity</th>
								<th class="header-unit">Unit Price</th>
								<th class="header-total">Total</th>
							</tr>

							<!-- Data row -->
							<tr class="summary-row">
								<td class="data-name">
									<p><%=confirm.getProduct_name()%></p>
								</td>
								<td class="data-qty"><%=request.getAttribute("quantity")%></td>
								<td class="data-unit"><p>
										$<%=confirm.getPrice()%></p></td>
								<td class="data-total">$<%=confirm.getPrice() * (Integer) request.getAttribute("quantity")%></td>
							</tr>
						</table>








						<div class="store_payment">



							<table class="store-details">



								<tr>
									<th class="detail-label">Store Name</th>
									<td class="detail-value">centrum seller name</td>
								</tr>
								<tr>
									<th class="detail-label">Freight</th>
									<td class="detail-value">pending</td>
								</tr>
								<!-- In confirmation.jsp: Replace the old freight row -->
								<tr>
									<th class="detail-label">Freight (Note)</th>
									<td class="detail-value"><span class="pending-freight">To
											be calculated after quality check</span>
										<p class="freight-note">Our team will verify product
											quality at OUR warehouse, then calculate shipping fees based
											on the product’s size/weight and your location. You’ll
											receive a notification with the final shipping cost before
											shipping take off.</p></td>
								</tr>
								<tr>
									<th class="detail-label">Logistics</th>
									<td class="detail-value">Standard Shipping (3-5 days)
										cross border</td>
								</tr>
								<tr>
									<th class="detail-label">Estimated Delivery</th>
									<td class="detail-value">pending</td>
								</tr>
								<tr>
									<th class="detail-label">Seller Rating</th>
									<td class="detail-value">4.8 ★ (1,243 reviews)</td>
								</tr>
							</table>
							<div></div>





							<div class="paymet-trigger">
								<div class="paymentmethod-image">
									<img src="${pageContext.request.contextPath}/Image/warning.png"
										alt="Credit/Debit Card" class="payment-icon">

									<p class="catution-text">
										Please review your order details carefully to ensure the
										product, quantity, and total are correct. Once confirmed,
										you’ll be directed to select a payment method to complete your
										purchase. We support secure payment options including Alipay,
										debit/credit cards, and other trusted methods <strong>your
											financial information is never stored on our platform</strong>.
									</p>


								</div>


								<div class="payment-methods">
									<img
										src="${pageContext.request.contextPath}/Image/alipay-icon.png"
										alt="Alipay" class="payment-icon"> <img
										src="${pageContext.request.contextPath}/Image/wechat-pay-icon.png"
										alt="WeChat Pay" class="payment-icon"> <img
										src="${pageContext.request.contextPath}/Image/master-card.png"
										alt="Credit/Debit Card" class="payment-icon"> <img
										src="${pageContext.request.contextPath}/Image/Moniepoint-Logo.png"
										alt="Credit/Debit Card" class="payment-icon"> <img
										src="${pageContext.request.contextPath}/Image/Quickteller-Logo.png"
										alt="Credit/Debit Card" class="payment-icon"> <img
										src="${pageContext.request.contextPath}/Image/visa-card.png"
										alt="Credit/Debit Card" class="payment-icon">



								</div>

								<form action="${pageContext.request.contextPath}/payment-method"
									method="post" class="form-here">
									<input type="hidden" name="product_id"
										value="<%=confirm.getProduct_id()%>"> <input
										type="hidden" name="quantity" id="hidden-quantity"
										value="<%=request.getAttribute("quantity") != null ? request.getAttribute("quantity") : 1%>"><input
										type="hidden" name="total" value="0">


									<button class="pay-now" type="submit"
										<%=(address == null) ? "disabled" : ""%>>
										<%=(address == null) ? "Add Address to Proceed" : "Proceed to Payment"%>
									</button>
								</form>




							</div>
						</div>






					</div>
					<div id="product-data" data-price="<%=confirm.getPrice()%>"
						data-stock="<%=confirm.getStock_quantity()%>"
						data-initial-qty="<%=request.getAttribute("quantity")%>"
						data-product-id="<%=confirm.getProduct_id()%>">
					</div>
				</main>


				<aside class="right-side-bar">

					<div class="address-display-body">

						<%
						if (address != null) {
						%>
						<div class="address-list">
							<p class="txt">Default Address Information</p>
							<p><%=address.getDetails_address()%></p>
							<p><%=address.getCity()%></p>
							<p><%=address.getState()%></p>
							<p><%=address.getCountry()%></p>
							<p><%=address.getPostal_code()%></p>
							<p><%=address.getPhone_number()%></p>
						</div>
						<%
						} else {
						%>
						<div class="no-address">
							<p>No address added yet. enter address to make payment
								shipping.</p>
							
						</div>

						<%
						String productId = request.getParameter("product_id") != null
								? request.getParameter("product_id")
								: String.valueOf(confirm.getProduct_id());
						String quantity = request.getParameter("quantity") != null
								? request.getParameter("quantity")
								: String.valueOf(request.getAttribute("quantity"));
						String redirectUrl = "confirmation?product_id=" + productId + "&quantity=" + quantity;
						request.setAttribute("redirectUrl", redirectUrl);
						%>
						<div class="address-form-container">
							<h4>Add a Default Address</h4>
							<jsp:include page="../buyer/address-form.jsp" />
						</div>
						<%
						}
						%>
					</div>

					<%
					String successMsg = request.getParameter("success");
					if (successMsg != null && !successMsg.isEmpty()) {
					%>
					<div class="success-mgs"><%=successMsg%></div>
					<%
					}
					%>

					<%
					String errorMsg = request.getParameter("error");
					if (errorMsg != null && !errorMsg.isEmpty()) {
					%>
					<div class="error"><%=errorMsg%></div>
					<%
					}
					%>

				</aside>




			</div>

		</div>













		<%
		}
		%>








	</div>










	<%@ include file="/WEB-INF/footer.jsp"%>

	<script>
		window.appContextPath = '${pageContext.request.contextPath}';
	</script>
	<script src="${pageContext.request.contextPath}/js/confirmationSyc.js"></script>


</body>

</html>