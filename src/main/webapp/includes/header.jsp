<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<header class="header">
    <div class="logo">
        <a href="<c:url value='/index.jsp' />" class="logo-link">
            <img src="<c:url value='/images/Logo.jpg' />" alt="Logo BBQ Master">
            <span>BBQ Master</span>
        </a>
    </div>

    <!-- Lấy URL trang hiện tại -->
	<c:set var="currentPage" value="${pageContext.request.servletPath}" />
	
	<nav>
	    <ul>
	        <li><a href="<c:url value='/index.jsp' />" class="${currentPage == '/index.jsp' ? 'active' : ''}">Trang chủ</a></li>
	        <li><a href="<c:url value='/pages/menu.jsp' />" class="${currentPage == '/pages/menu.jsp' ? 'active' : ''}">Thực đơn</a></li>
	        <li><a href="<c:url value='/pages/book-table.jsp' />" class="${currentPage == '/pages/book-table.jsp' ? 'active' : ''}">Đặt bàn</a></li>
	        <li><a href="<c:url value='/pages/book-event.jsp' />" class="${currentPage == '/pages/book-event.jsp' ? 'active' : ''}">Đặt tiệc</a></li>
	        <li><a href="<c:url value='/pages/contact.jsp' />" class="${currentPage == '/pages/contact.jsp' ? 'active' : ''}">Liên hệ</a></li>
	    </ul>
	</nav>


    <div class="header-actions">
        <a href="<c:url value='/pages/cart.jsp' />" class="cart-icon">
            <i class="fa-solid fa-cart-shopping"></i>

            <!-- Hiển thị số lượng giỏ hàng -->
            <c:if test="${not empty sessionScope.cart}">
                <span class="cart-badge">
                    ${sessionScope.cart.totalQuantity}
                </span>
            </c:if>
        </a>

        <!-- Hiển thị nút login hoặc avatar -->
        <c:choose>
            <c:when test="${empty sessionScope.customer}">
                <button class="login-btn" id="openLogin">
                    <i class="fa-solid fa-user"></i> Đăng nhập
                </button>
            </c:when>

            <c:otherwise>
                <c:set var="firstChar"
                       value="${fn:toUpperCase(fn:substring(sessionScope.customer.fullName, 0, 1))}" />
                <div class="user-info">
                    <div class="user-avatar-circle">${firstChar}</div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</header>

<jsp:include page="loginPopup.jsp" />
