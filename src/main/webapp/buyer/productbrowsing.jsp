<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.centrum.bean.User"%>
<%@ page import="com.centrum.bean.Product"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>centrum.productbrowsing</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/productbrowsing.css">
</head>
<body>
	<%@ include file="/WEB-INF/header.jsp"%>

	<div class="structure-container">

		<div class="frame-container">


			<main>

			
				<div>

					<div class="banner-root">
						<aside class=left-side-bar>
							<jsp:include page="../buyer/left-sidebar.jsp" />

						</aside>
						<div class="banner-display">
							<div class="banner-carousel">
								<div class="carousel-slides">
									<%
									List<Product> bannerList = (List<Product>) request.getAttribute("banner");
									if (bannerList != null && !bannerList.isEmpty()) {
										for (int i = 0; i < bannerList.size(); i++) {
											Product b = bannerList.get(i);
											String activeClass = (i == 0) ? "active" : "";
									%>
									<div class="carousel-slide <%=activeClass%>">
										<a
											href="${pageContext.request.contextPath}/product-details?product_id=<%=b.getProduct_id()%>"
											class="banner-link"> <img alt="<%=b.getProduct_name()%>"
											src="<%=request.getContextPath() %>/<%=b.getImage_url()%>"
											class="banner-img">
											<div class="banner-content">
												
											</div>

										</a>
									</div>
									<%
									}
									}
									%>
								</div>

								<button class="carousel-prev">&larr;</button>
								<button class="carousel-next">&rarr;</button>

								<div class="carousel-dots">
									<%
									if (bannerList != null && !bannerList.isEmpty()) {
										for (int i = 0; i < bannerList.size(); i++) {
											String activeDot = (i == 0) ? "active" : "";
									%>
									<span class="carousel-dot <%=activeDot%>" data-index="<%=i%>"></span>
									<%
									}
									}
									%>
								</div>
							</div>




						</div>
						<div class="banner-list">
								<div id="greeting">
					<h2>
						</h2>
				</div>
						
						</div>
					

					</div>
					<div class="list"></div>

					<div class="product-display">
						<%
						List<Product> filteredProducts = (List<Product>) request.getAttribute("productList");
						List<Product> productList = (filteredProducts != null)
								? filteredProducts
								: (List<Product>) session.getAttribute("productList");

						if (productList != null && !productList.isEmpty()) {
							for (Product p : productList) {
						%>

						<a
							href="${pageContext.request.contextPath}/product-details?product_id=<%=p.getProduct_id() %>">
							<div class="product-roll">
								<div>
									<div class="product-image">
										<img
											src="${pageContext.request.contextPath}/<%=p.getImage_url()%>"
											alt="<%=p.getProduct_name()%>">
									</div>
								</div>
								<div class="product-details">
									<h4><%=p.getProduct_name()%></h4>
									<p><%=p.getDescription()%></p>
									<p>
										Price: $<%=p.getPrice()%></p>
								</div>
							</div>
						</a>

						<%
						}
						%>
						<script>
							if (window.hideLoadingOverlay) {
								hideLoadingOverlay();
							}
						</script>
						<%
						} else {
						String category = request.getParameter("category");
						if (category != null) {
							out.println(

							"<p style='text-align: center; padding: 30px; color: #666; font-size: 1.1rem;'>No products found for category: "
									+ category + "</p>");
						} else {
							out.println(
							"<p style='text-align: center; padding: 30px; color: #666; font-size: 1.1rem;'>No products available at the moment</p>");
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
					</div>
					</div>
			</main>



		</div>



	</div>

	<%@ include file="/WEB-INF/footer.jsp"%>

	<script>
		const contextPath = '${pageContext.request.contextPath}';
	</script>
	<script src="${pageContext.request.contextPath}/js/productbrowsing.js"></script>


</body>

</html>