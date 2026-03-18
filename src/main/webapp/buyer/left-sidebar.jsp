<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Import CSS (adjust path if needed) -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-sidebar.css">

<!-- Product Categories Section -->
<div class="tb-category-section">
    <h4>Product Categories</h4>
    
    <!-- 1. Electronics (OPEN BY DEFAULT) -->
    <div class="tb-main-category">
        <div class="tb-main-cat-title" onclick="toggleSubcat(this)">
            <span>Electronics</span>
            <svg class="tb-arrow-icon" width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="#666" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <polyline points="6 9 12 15 18 9"></polyline>
            </svg>
        </div>
        <div class="tb-subcategories" style="display: block;"> <!-- Open by default -->
            <a href="${pageContext.request.contextPath}/filterProducts?category=Phones">Phones</a>
            <a href="${pageContext.request.contextPath}/filterProducts?category=Laptops">Laptops</a>
            <a href="${pageContext.request.contextPath}/filterProducts?category=Tablets">Tablets</a>
            <a href="${pageContext.request.contextPath}/filterProducts?category=Gadgets">Gadgets & Wearables</a>
        </div>
    </div>

    <!-- 2. Clothing & Apparel -->
    <div class="tb-main-category">
        <div class="tb-main-cat-title" onclick="toggleSubcat(this)">
            <span>Clothing & Apparel</span>
            <svg class="tb-arrow-icon" width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="#666" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <polyline points="6 9 12 15 18 9"></polyline>
            </svg>
        </div>
        <div class="tb-subcategories" style="display: block;">
            <a href="${pageContext.request.contextPath}/filterProducts?category=MenClothing">Men's Clothing</a>
            <a href="${pageContext.request.contextPath}/filterProducts?category=WomenClothing">Women's Clothing</a>
            <a href="${pageContext.request.contextPath}/filterProducts?category=KidsClothing">Kids' Clothing</a>
            <a href="${pageContext.request.contextPath}/filterProducts?category=Shoes">Shoes & Footwear</a>
            <a href="${pageContext.request.contextPath}/filterProducts?category=Accessories">Bags, Hats & Jewelry</a>
        </div>
    </div>

  </div>

<!-- Import JS (no changes needed) -->
<script src="${pageContext.request.contextPath}/js/product-sidebar.js"></script>