<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.centrum.bean.User"%>
<%@ page import="com.centrum.bean.Product"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>centrum.search</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/search.css">
</head>
<body>
	<div class="structure-container">
	<%@ include file="/WEB-INF/header.jsp"%>






		<div class="frame-container">


			<main>





				<div></div>
				<div class="list">

					<%
					String error = request.getParameter("error");
					if (error != null && !error.isEmpty()) {
						error = java.net.URLDecoder.decode(error, "UTF-8");
					%>
					<p class="error"><%=error%></p>
					<%
					}
					%>
				</div>

				<div class="product-display">

					<%
					List<Product> productFetch = (List<Product>) request.getAttribute("productFetch");
					System.out.println("JSP received productList: " + (productFetch != null ? productFetch.size() : "null"));

					if (productFetch != null) {
						for (Product p : productFetch) {
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
							<div class="shop-name">seller shop name</div>
						</div>
					</a>


					<%
					}
					} else {
					out.println("No products found");
					%>
					<div class="no-products">
						<img
							src="${pageContext.request.contextPath}/Image/no-products-found.png"
							alt="No products found" class="no-products-img">
						<p class="no-products-text">No products found matching your
							search.</p>
					</div>

					<%
					}
					%>




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