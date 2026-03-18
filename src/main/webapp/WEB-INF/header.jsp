<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.centrum.bean.User"%>

<%@ include file="/WEB-INF/fragments/loading-overlay.jsp" %>
<head>
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <link href="https://fonts.googleapis.com/css2?family=Pacifico&display=swap" rel="stylesheet">
    <link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/header.css">
</head>

<header>

    <div class="logo">
        <div>
            
            <h5>Centrum</h5>
        </div>
    </div>

    <!-- Search Bar -->
    <div class="search-div">
        <form action="${pageContext.request.contextPath}/search" method="get">
            <svg class="search-icon" width="20" height="20" viewBox="0 0 24 24"
                 fill="none" stroke="#777" stroke-width="2" stroke-linecap="round"
                 stroke-linejoin="round">
                <circle cx="11" cy="11" r="7" />
                <line x1="21" y1="21" x2="16.65" y2="16.65" />
            </svg>
            <input type="search" name="query" id="searchInput"
                   placeholder="search product...."
                   oninput="showSuggestions(this.value)">
            <div id="suggestionsDropdown" class="suggestions"></div>
        </form>
    </div>

    <div class="nav-div">
        <nav class="nav-bar">
            <ol>
        
                <li>
                    <a href="${pageContext.request.contextPath}/cart" id="link-direction">
                        <img id="nav-icon" src="${pageContext.request.contextPath}/Image/cart-shopping.svg">
                    </a>
                    <span>cart</span>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/profile" id="link-direction">
                        <img id="nav-icon" src="${pageContext.request.contextPath}/Image/people.png">
                    </a>
                    <span>me</span>
                </li>
            </ol>
        </nav>
    </div>
</header>

<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        response.sendRedirect(request.getContextPath() + "/login-user.jsp?error=please login first");
        return;
    }
%>