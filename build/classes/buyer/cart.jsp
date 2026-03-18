<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.centrum.bean.User"%>
<%@ page import="com.centrum.bean.Product"%>
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
	href="${pageContext.request.contextPath}/css/cart.css">
</head>
<body>
	<%@ include file="/WEB-INF/header.jsp"%>

	<div class="structure-container">







		<div class="frame-container">


			<div class="structure-main">
				<main>
					<div>
						<div class="action-check">
							<form action="${pageContext.request.contextPath}/cart"
								method="post">
							
								<div class="check-box">
									<input type="hidden" name="action" value="clearAll"> <input
										type="checkbox" name="clear_all" id="clear-all"> <span
										for="clear-all">clear-all</span>
								</div>
								<div id="no-selection-error">Please select at least
									"clear-all" to proceed.</div>
								<button type="button" class="trigger-btn-display">Apply</button>




								<div class="clear-cart-btn">
									<div class="body-clear">




										<div class="center-row">



											<div class="complete">
												<h1>clear all carts</h1>


												<div class="order-details">
													<p>Are you want to clear all cart</p>


												</div>
												<div class="clear-btn-all">

													<button type="submit" class=" btn trigger-btn-yes">yes</button>

													<button type="button" class=" btn trigger-btn-no">no</button>
												</div>


											</div>

											<p class="thank-you">Continue shooping and more cart
												later, for quick check out ${loggedInUser.full_name}! 😊</p>




										</div>

									</div>
								</div>
							</form>


						</div>
					</div>



					<%
					String loadCartError = (String) request.getAttribute("errorMessage");
					if (loadCartError != null && !loadCartError.isEmpty()) {
					%>
					<div class="load-cart-error">
						<%=loadCartError%>
					</div>
					<%
					}
					%>

					<%
					String successMsg = request.getParameter("success");
					if (successMsg != null && !successMsg.isEmpty()) {
					%>
					<div class="success-mgs">
						<%=successMsg%>
					</div>
					<%
					}
					%>

					<%
					String errorMsg = request.getParameter("error");
					if (errorMsg != null && !errorMsg.isEmpty()) {
					%>
					<div class="error">
						<%=errorMsg%>
					</div>
					<%
					}
					%>

					<%
					String infoMsg = request.getParameter("info");
					if (infoMsg != null && !infoMsg.isEmpty()) {
					%>
					<div class="info-message">
						<%=infoMsg%>
					</div>
					<%
					}
					%>




					<%
					List<Cart> cartItems = (List<Cart>) request.getAttribute("cartItems");
					double total = 0.0;
					%>

					<%
					if (cartItems == null || cartItems.isEmpty()) {
					%>
					<div class="empty-cart-message">
						<p>Your cart is empty!</p>
						<p>Add some products to get started.</p>
						<a
							href="${pageContext.request.contextPath}/buyer/productbrowsing.jsp">Start
							shopping</a>
					</div>
					<%
					} else {
					%>
					<%
					for (Cart c : cartItems) {
						total += c.getPrice() * c.getQuantity();
					%>



					<div class="cart-space">


						<a
							href="${pageContext.request.contextPath}/confirmation?product_id=<%=c.getProduct_id() %>">
							<img class="cart-image" alt="product image"
							src="${pageContext.request.contextPath}/<%= c.getImage_url() %>">
							<div class="holder-col">
								<div>
									<p><%=c.getProduct_name()%></p>
									<p>
										$<%=c.getPrice()%></p>
								</div>

							</div>




						</a>

						<div class="quanitity">
							<div class="unit-flow">
								<button class="minus-btn" type="button"
									data-cart-id="<%=c.getCart_id()%>">-</button>
								<p id="quantity-value-<%=c.getCart_id()%>"><%=c.getQuantity()%></p>

								<button class="plus-btn" type="button"
									data-cart-id="<%=c.getCart_id()%>">+</button>
							</div>
						</div>


						<input type="hidden" name="updated_quantity_<%=c.getCart_id()%>"
							id="updated-quantity-<%=c.getCart_id()%>"
							value="<%=c.getQuantity()%>">
						<div class="delete">
							<p>delete items</p>
						</div>


						<div class="delete-overlay">
							<div class="middle">
								<div class="lay">
									<div class="delete-container">
										<div class="complete">
											<h1>Cart items delection</h1>


											<div class="order-details">
												<p>Are you sure you want to delete order</p>


											</div>
											<form action="${pageContext.request.contextPath}/cart"
												method="post">
												<input type="hidden" name="cart_id"
													value="<%=c.getCart_id()%>">
												<button class="btn yes" type="submit">yes</button>
												<button class="btn no" type="button">no</button>
											</form>
										</div>
										<p class="thank-you">Thanks for shopping with us,
											${loggedInUser.full_name}! Continue Shopping 😊</p>
									</div>

								</div>

							</div>
						</div>
					</div>



					<%
					}
					%>
					<script>
						if (window.hideLoadingOverlay) {
							hideLoadingOverlay();
						}
					</script>
					<%
					}
					%>
					<div class="payment">


						<div class="paymet-trigger">
							<p>
								Total: $<%=total%></p>
							<form action="${pageContext.request.contextPath}/cart/checkout"
								method="post">
								<button type="submit" class="pay-now">Checkout All</button>
							</form>

						</div>





					</div>
				</main>




				<h4 class="product-suggestion">Global Popularity Recommendation</h4>


				<div class="product-display">


					<%
					List<RecommendProduct> recommendations = (List<RecommendProduct>) request.getAttribute("recommendations");

					if (recommendations != null && !recommendations.isEmpty()) {
						int count = 0;
						for (RecommendProduct rec : recommendations) {
							if (count >= 50)
						break;

							Product product = rec.getProduct();
							ProductImages primaryImage = rec.getPrimaryImages();
					%>
					<a
						href="<%=request.getContextPath()%>/product-details?product_id=<%=product.getProduct_id()%>">
						<div class="product-roll">
							<div>
								<div class="product-image">
									<img
										src="<%=request.getContextPath()%>/<%=primaryImage.getImage_url()%>"
										alt="<%=product.getProduct_name()%>">
								</div>
							</div>
							<div class="product-details">
								<h4><%=product.getProduct_name()%></h4>
								<p><%=product.getDescription() != null
		? product.getDescription().substring(0, Math.min(product.getDescription().length(), 50)) + "..."
		: ""%></p>
								Price: $<%=product.getPrice()%></p>
							</div>
							<div class="shop-name">seller shop name</div>
						</div>
					</a>
					<%
					count++;
					}
					} else {
					%>
					<p>No recommendations available.</p>
					<%
					}
					%>
				</div>
			</div>






		</div>



	</div>

	<%@ include file="/WEB-INF/footer.jsp"%>

	<script>
		const contextPath = '${pageContext.request.contextPath}';
	</script>
	<script src="${pageContext.request.contextPath}/js/cart.js"></script>
	<script>
        window.addEventListener('load', () => {
            setTimeout(() => {
                const loadingOverlay = document.getElementById('loading-overlay');
                if (loadingOverlay && !loadingOverlay.classList.contains('hidden')) {
                    loadingOverlay.classList.add('hidden');
                    loadingOverlay.style.opacity = "0";
                    loadingOverlay.style.visibility = "hidden";
                }
            }, 500);
        });
    </script>


</body>

</html>