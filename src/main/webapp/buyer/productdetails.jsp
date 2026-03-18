<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.centrum.bean.User"%>
<%@ page import="com.centrum.bean.Product"%>
<%@ page import="java.util.List"%>
<%@ page import="com.centrum.bean.ProductImages"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>centrum.product-details</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/productdetails.css">
</head>
<body>

	<%@ include file="/WEB-INF/header.jsp"%>

	<div class="structure-container">
		<div class="frame-container">
			<div class="structure-main">
				<main>
					<%
					Product productshow = (Product) request.getAttribute("productshow");
					List<ProductImages> productImageList = (List<ProductImages>) request.getAttribute("productImageList");

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

					if (productshow != null) {
						int initialQty = 1;
						if (request.getParameter("quantity") != null) {
							try {
						initialQty = Integer.parseInt(request.getParameter("quantity"));
						initialQty = Math.max(1, Math.min(initialQty, productshow.getStock_quantity()));
							} catch (Exception e) {
						initialQty = 1;
							}
						}
					%>

					<div id="product-data"
						data-productId="<%=productshow.getProduct_id()%>"
						data-price="<%=productshow.getPrice()%>"
						data-stock="<%=productshow.getStock_quantity()%>"
						data-initialQty="<%=initialQty%>"></div>

					<div class="product-page-layout">
						<aside class="left-side-bar">
							<div class="shop-details">
								<div class="shop-image">
									<img alt="Shop Logo"
										src="${pageContext.request.contextPath}/Image/shop-placeholder.svg">
								</div>
								<div>
									<h4 class="shop-name">StyleHub Grooming</h4>
								</div>
							</div>
							<div class="shop-about">
								<p class="shop-founded">Since: 2023</p>
								<p class="about-heading">About:</p>
								<p class="about-desc">Your trusted source for professional
									grooming tools—quality products for home and salon use, backed
									by 2+ years of customer satisfaction.</p>
							</div>
							<div class="sidebar-nav">
								<ol>
									<li><a href="${pageContext.request.contextPath}/">Shop
											Home Page</a></li>
									<li><a
										href="${pageContext.request.contextPath}/sales-rate">Product
											Sales Rate</a></li>
									<li><a
										href="${pageContext.request.contextPath}/customer-service">Customer
											Service</a></li>
								</ol>
							</div>
						</aside>

						<div class="product-view-grid">
							<div class="product-thumbnails">
								<%
								if (productImageList != null && !productImageList.isEmpty()) {
									for (ProductImages img : productImageList) {
								%>
								<div class="product-thumb-item">
									<img class="product-thumb-img"
										src="${pageContext.request.contextPath}/<%= img.getImage_url() %>"
										onclick="document.getElementById('product-image').src = '${pageContext.request.contextPath}<%= img.getImage_url() %>'">
								</div>
								<%
								}
								} else {
								%>
								<div class="product-thumb-item">
									<img class="product-thumb-img"
										src="${pageContext.request.contextPath}/<%= mainImageUrl %>">
								</div>
								<%
								}
								%>
							</div>

							<div class="product-main-image-container">
								<div class="image-zoom-wrapper">
									<img id="product-image" class="product-main-img"
										src="${pageContext.request.contextPath}/<%= mainImageUrl %>">
								</div>
								<div class="image-action-buttons">
									<button class="image-action-btn" title="Zoom">
										<svg xmlns="http://www.w3.org/2000/svg" width="18" height="18"
											fill="currentColor" viewBox="0 0 16 16">
                                        <path
												d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z" />
                                        <path
												d="M10.496 6.104a.5.5 0 1 1 .707.708L6.707 10H13a.5.5 0 0 1 0 1H6.707l3.789 3.789a.5.5 0 0 1-.707.708l-4-4a.5.5 0 0 1 0-.708l4-4z" />
                                    </svg>
									</button>
									<button class="image-action-btn" title="Save">
										<svg xmlns="http://www.w3.org/2000/svg" width="18" height="18"
											fill="currentColor" viewBox="0 0 16 16">
                                        <path
												d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707.707L12.707 2l4.495 4.495a.5.5 0 0 1 0 .707l-1.586 1.586a.5.5 0 0 1-.708 0L8 9.207l-1.958 1.958a.5.5 0 0 1-.707 0L1.313 8.7a.5.5 0 0 1 0-.707L3.302 6.31l-2.768-2.768a.5.5 0 0 1 .707-.707L2 4.293l1.946-1.946a.5.5 0 0 1 .707 0l1.586 1.586 1.958-1.958a.5.5 0 0 1 .707 0l1.586 1.586 1.946-1.946a.5.5 0 0 1 .707 0z" />
                                    </svg>
									</button>
								</div>
							</div>

							<div class="product-info-panel">
								<div class="seller-info">
									<span class="seller-badge">Sold by centrum sellers</span> <a
										href="#" class="seller-link">Visit store</a>
								</div>

								<h1 class="product-title"><%=productshow.getProduct_name()%></h1>

								<div class="price-display">
									<span class="current-price">$<%=productshow.getPrice()%></span>
									<span class="price-per-unit">Price per unit</span>
								</div>

								<div class="product-desc-section">
									<h3 class="section-heading">Product Description</h3>
									<p class="desc-text"><%=productshow.getDescription()%></p>
								</div>

								<div class="stock-status">
									<%
									if (productshow.getStock_quantity() > 0) {
									%>
									<span class="in-stock-badge">In Stock</span>
									<p class="stock-count">
										Available:
										<%=productshow.getStock_quantity()%>
										units
									</p>
									<%
									} else {
									%>
									<span class="out-of-stock-badge">Out of Stock</span>
									<%
									}
									%>
								</div>

								<div class="quantity-selector">
									<h3 class="section-heading">Quantity</h3>
									<div class="quantity-controls">
										<button type="button" class="quantity-minus minus-btn">-</button>
										<span id="quantity-value" class="quantity-display">1</span>
										<button type="button" class="quantity-plus plus-btn">+</button>
									</div>
									<span id="stock-alert" class="stock-warning">Max stock
										reached!</span>
								</div>

								<div class="action-buttons">
									<form id="cart-form"
										action="${pageContext.request.contextPath}/cart" method="post">
										<input type="hidden" name="product_id"
											value="<%=productshow.getProduct_id()%>"> <input
											type="hidden" id="hidden-quantity" name="quantity" value="1"
											min="1">
										<button id="add-cart-btn" class="btn-add-to-cart"
											type="submit" name="payment">
											<svg xmlns="http://www.w3.org/2000/svg" width="20"
												height="20" fill="currentColor" viewBox="0 0 16 16"
												class="btn-icon">
                                            <path
													d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 13H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z" />
                                        </svg>
											Add to Cart
										</button>
									</form>

									<form
										action="${pageContext.request.contextPath}/confirmation?product_id=<%=productshow.getProduct_id() %>"
										method="get" class="buy-now-form">
										<input type="hidden" name="product_id"
											value="<%=productshow.getProduct_id()%>"> <input
											type="hidden" id="confirm-hidden-quantity" name="quantity"
											value="1" min="1">
										<button type="submit" class="btn-buy-now" name="payment">
											Buy Now</button>
									</form>
								</div>

								<div class="total-summary">
									<div class="total-row">
										<span class="total-label">Total Price:</span> <span
											class="total-value" id="total-price">$<%=productshow.getPrice()%></span>
									</div>
									<p class="shipping-note">Free shipping on orders over $35</p>
								</div>
							</div>
						</div>
					</div>

					<%
					} else {
					%>

					<div class="product-page-layout">
						<aside class="left-side-bar">
							<div class="shop-details">
								<div class="shop-image">
									<img alt="Shop Logo"
										src="${pageContext.request.contextPath}/Image/shop-placeholder.svg">
								</div>
								<div>
									<h4 class="shop-name">StyleHub Grooming</h4>
								</div>
							</div>
							<div class="shop-about">
								<p class="shop-founded">Since: 2023</p>
								<p class="about-heading">About:</p>
								<p class="about-desc">Your trusted source for professional
									grooming tools—quality products for home and salon use, backed
									by 2+ years of customer satisfaction.</p>
							</div>
							<nav class="sidebar-nav">
								<ol>
									<li><a href="${pageContext.request.contextPath}/">Shop
											Home Page</a></li>
									<li><a
										href="${pageContext.request.contextPath}/sales-rate">Product
											Sales Rate</a></li>
									<li><a
										href="${pageContext.request.contextPath}/customer-service">Customer
											Service</a></li>
								</ol>
							</nav>
						</aside>

						<div class="error-container">
							<img src="${pageContext.request.contextPath}<%= mainImageUrl %>"
								alt="No product" class="error-img">
							<div class="error-content">
								<h2 class="error-heading">Product Not Found</h2>
								<p class="error-text">The product you're looking for doesn't
									exist or has been removed.</p>
								<a href="${pageContext.request.contextPath}/product-browsing"
									class="error-btn">Return to Home</a>
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
				</main>

				<div class="reviews-section review">
					<h2 class="reviews-heading">Customer Reviews</h2>
					<div class="reviews-placeholder">
						<p>No reviews yet. Be the first to review this product!</p>
						<button class="write-review-btn">Write a Review</button>
					</div>
				</div>
			</div>
		</div>

		<div class="list product-supplementary-section">
			<section class="supplementary-section product-details-expand">
				<h2 class="section-title">More About This Product</h2>
				<div class="details-grid">
					<div class="details-text">
						<h3 class="details-subtitle">Why Choose This Grooming Tool?</h3>
						<ul class="product-benefits">
							<li><strong>Professional Quality:</strong> Designed for
								salon and home use, with durable materials built to last.</li>
							<li><strong>Ergonomic Design:</strong> Comfortable grip for
								extended use, reducing hand fatigue.</li>
							<li><strong>Versatile Use:</strong> Perfect for styling,
								trimming, and shaping — meets all your grooming needs.</li>
							<li><strong>Easy to Clean:</strong> Removable parts for
								hassle-free maintenance and hygiene.</li>
							<li><strong>Brand Trust:</strong> Backed by StyleHub
								Grooming’s 2+ years of customer satisfaction.</li>
						</ul>

						<div class="product-specs">
							<h3 class="details-subtitle">Product Specifications</h3>
							<table class="specs-table">
								<tr>
									<td class="spec-label">Material:</td>
									<td class="spec-value">High-Grade Plastic + Metal</td>
								</tr>
								<tr>
									<td class="spec-label">Dimensions:</td>
									<td class="spec-value">8 x 3 x 2 inches</td>
								</tr>
								<tr>
									<td class="spec-label">Weight:</td>
									<td class="spec-value">0.5 lbs</td>
								</tr>
								<tr>
									<td class="spec-label">Warranty:</td>
									<td class="spec-value">1 Year Limited Warranty</td>
								</tr>
								<tr>
									<td class="spec-label">Power Source:</td>
									<td class="spec-value">Rechargeable Battery (USB-C)</td>
								</tr>
							</table>
						</div>
					</div>

					<div class="details-images">
						<div class="supplementary-img-wrapper">
							<img src="/product/Image/feature1.svg" alt="Product Side View"
								class="supplementary-img"
								onclick="document.getElementById('product-image').src = '/product/Image/feature1.svg'">
						</div>
						<div class="supplementary-img-wrapper">
							<img src="/product/Image/feature2.svg" alt="Product Close-Up"
								class="supplementary-img"
								onclick="document.getElementById('product-image').src = '/product/Image/feature2.svg'">
						</div>
						<div class="supplementary-img-wrapper">
							<img src="/product/Image/feature3.svg" alt="Product in Use"
								class="supplementary-img"
								onclick="document.getElementById('product-image').src = '/product/Image/feature3.svg'">
						</div>
					</div>
				</div>
			</section>

			<section class="supplementary-section related-products-ads">
				<h2 class="section-title">You May Also Like</h2>
				<div class="ads-carousel">
					<div class="carousel-track">
						<%
						List<Product> mayLike = (List<Product>) request.getAttribute("mayAlsoLikeProducts");
						%>
						<%
						if (mayLike != null && !mayLike.isEmpty()) {
						%>

						<%
						for (Product suggest : mayLike) {
						%>

						<div class="ad-card">
							<div class="ad-img-wrapper">
								<img
									src="${pageContext.request.contextPath}/<%=suggest.getImage_url()%>"
									alt="<%=suggest.getProduct_name() %>" class="ad-img">
							</div>
							<div class="ad-content">
								<h3 class="ad-title"><%=suggest.getProduct_name()%></h3>
								<p class="ad-price">$<%=suggest.getPrice()%></p>
								<a
									href="${pageContext.request.contextPath}/product-details?product_id=<%= suggest.getProduct_id() %>"
									class="ad-cta">View Product</a>
							</div>
						</div>

						<%
						}
						%>
						<%
						} else {
						%>

						<div class="ad-card fallback">
							<div class="ad-img-wrapper"></div>
							<div class="ad-content">
								<h3 class="ad-title"></h3>
								<p class="ad-price"></p>
								<a href="/product?product_id=101" class="ad-cta"></a>
							</div>
						</div>
						<div class="ad-card fallback">
							<div class="ad-img-wrapper"></div>
							<div class="ad-content">
								<h3 class="ad-title"></h3>
								<p class="ad-price"></p>
								<a href="/product?product_id=101" class="ad-cta"></a>
							</div>
						</div>
						<div class="ad-card fallback">
							<div class="ad-img-wrapper"></div>
							<div class="ad-content">
								<h3 class="ad-title"></h3>
								<p class="ad-price"></p>
								<a href="/product?product_id=101" class="ad-cta"></a>
							</div>
						</div>
						<div class="ad-card fallback">
							<div class="ad-img-wrapper"></div>
							<div class="ad-content">
								<h3 class="ad-title"></h3>
								<p class="ad-price"></p>
								<a href="/product?product_id=101" class="ad-cta"></a>
							</div>
						</div>


						<%
						}
						%>



					</div>

					<button class="carousel-btn prev-btn" aria-label="Previous Product">
						<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
							fill="currentColor" viewBox="0 0 16 16">
                    <path
								d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z" />
                </svg>
					</button>
					<button class="carousel-btn next-btn" aria-label="Next Product">
						<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
							fill="currentColor" viewBox="0 0 16 16">
                    <path
								d="M4.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L10.293 8 4.646 2.354a.5.5 0 0 1 0-.708z" />
                </svg>
					</button>
				</div>
			</section>

			<section class="supplementary-section brand-cta-ad">
				<div class="cta-ad-container">
					<div class="cta-ad-text">
						<h2 class="cta-ad-title">Join the centrum Grooming Community</h2>
						<p class="cta-ad-desc">Get 15% off your first order +
							exclusive grooming tips when you sign up for our newsletter!</p>
						<form class="cta-ad-form">
							<input type="email" placeholder="Enter your email" required
								class="cta-ad-input">
							<button type="submit" class="cta-ad-btn">Subscribe Now</button>
						</form>
					</div>
					<div class="cta-ad-img-wrapper">
						<img src="/product/Image/brand-cta.svg" alt="StyleHub Grooming"
							class="cta-ad-img">
					</div>
				</div>
			</section>

			<!-- 4. Additional Product Info (Optional) -->
			<section class="supplementary-section product-faq">
				<h2 class="section-title">Frequently Asked Questions</h2>
				<div class="faq-container">
					<div class="faq-item">
						<button class="faq-question">How long does the battery
							last on a single charge?</button>
						<div class="faq-answer">
							<p>The rechargeable battery lasts up to 4 hours of continuous
								use on a full charge. Charging time is approximately 2 hours
								with the included USB-C cable.</p>
						</div>
					</div>
					<div class="faq-item">
						<button class="faq-question">Is this product suitable for
							all hair types?</button>
						<div class="faq-answer">
							<p>Yes! This grooming tool works with all hair types — from
								thin to thick, straight to curly. The adjustable settings allow
								for customization based on your hair texture.</p>
						</div>
					</div>
					<div class="faq-item">
						<button class="faq-question">What is included in the box?</button>
						<div class="faq-answer">
							<p>The package includes the grooming tool, USB-C charging
								cable, cleaning brush, 2 interchangeable attachments, and a user
								manual.</p>
						</div>
					</div>
					<div class="faq-item">
						<button class="faq-question">How do I clean and maintain
							the tool?</button>
						<div class="faq-answer">
							<p>Remove the attachments and use the included cleaning brush
								to remove hair clippings. Wipe the body with a damp cloth (do
								not submerge in water). Store in a dry place when not in use.</p>
						</div>
					</div>
				</div>
			</section>
		</div>
	</div>
	<%@ include file="/WEB-INF/footer.jsp"%>

	<script>
		const contextPath = '${pageContext.request.contextPath}';
	</script>
	<script>
		document.addEventListener('DOMContentLoaded',
				function() {
	<%if (productshow != null) {%>
		initCartQuantity('.minus-btn', 'quantity-value',
							'.plus-btn', 'hidden-quantity', 'add-cart-btn',
							'cart-form',
	<%=productshow.getStock_quantity()%>
		,
							'confirm-hidden-quantity',
	<%=productshow.getProduct_id()%>
		);
	<%}%>
		});
	</script>
	<script src="${pageContext.request.contextPath}/js/productdetails.js"></script>


</body>

</html>