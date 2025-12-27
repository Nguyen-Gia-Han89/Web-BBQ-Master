<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%-- Lấy trang hiện tại --%>
<c:set var="currentPage" value="${pageContext.request.servletPath}" />

<%-- URL dùng chung --%>
<c:url var="homeUrl" value="/index.jsp" />
<c:url var="menuUrl" value="/menu" />
<c:url var="bookTableUrl" value="/booking-table" />
<c:url var="bookEventUrl" value="/pages/book-event.jsp" />
<c:url var="promotionUrl" value="/promotions" />
<c:url var="contactUrl" value="/pages/contact.jsp" />
<c:url var="cartUrl" value="/pages/cart.jsp" />
<c:url var="logoUrl" value="/images/Logo.jpg" />

<%-- Active class --%>
<c:set var="homeActive" value="${currentPage == '/index.jsp' ? 'active' : ''}" />
<c:set var="menuActive" value="${currentPage == '/menu' ? 'active' : ''}" />
<c:set var="bookTableActive" value="${currentPage == '/pages/book-table.jsp' ? 'active' : ''}" />
<c:set var="bookEventActive" value="${currentPage == '/pages/book-event.jsp' ? 'active' : ''}" />

<c:set var="serviceActiveClass" value="" />
<c:if test="${currentPage == '/pages/book-table.jsp'
          or currentPage == '/pages/book-event.jsp'}">
    <c:set var="serviceActiveClass" value="active" />
</c:if>


<c:set var="promotionActive" value="${currentPage == '/pages/promotion.jsp' ? 'active' : ''}" />

<c:set var="contactActive" value="${currentPage == '/pages/contact.jsp' ? 'active' : ''}" />

<header class="header">

    <!-- LOGO -->
    <div class="logo">
        <a href="${homeUrl}" class="logo-link">
            <img src="${logoUrl}" alt="Logo BBQ Master">
            <span>BBQ Master</span>
        </a>
    </div>

    <!-- NAV -->
    <nav>
        <ul>
            <li><a href="${homeUrl}" class="${homeActive}">Trang chủ</a></li>
            <li><a href="${menuUrl}" class="${menuActive}">Thực đơn</a></li>
          
          
            
            <!-- DỊCH VỤ -->
	        <li class="has-dropdown ${serviceActiveClass}">
			    <a href="javascript:void(0)">
			        Dịch vụ <i class="fa-solid fa-chevron-down"></i>
			    </a>
			    <ul class="dropdown-menu">
			        <li>
					    <a href="${bookTableUrl}" class="${bookTableActive}">Đặt bàn</a>
					</li>
			        <li>
			            <a href="${bookEventUrl}" class="${bookEventActive}">
			                Đặt tiệc
			            </a>
			        </li>
			    </ul>
			</li>

	        <li><a href="${promotionUrl}" class="${promotionActive}">Ưu đãi</a></li>
            <li><a href="${contactUrl}" class="${contactActive}">Liên hệ</a></li>
        </ul>
    </nav>

    <!-- ACTIONS -->
    <div class="header-actions">

        <!-- CART -->
        <a href="${cartUrl}" class="cart-icon">
		    <i class="fa-solid fa-cart-shopping"></i>
		    <c:choose>
		        <c:when test="${empty sessionScope.cart or sessionScope.cart.totalQuantity == 0}">
		            <span id="cart-badge" class="cart-badge" style="display:none;">0</span>
		        </c:when>
		        <c:otherwise>
		            <span id="cart-badge" class="cart-badge">
		                ${sessionScope.cart.totalQuantity}
		            </span>
		        </c:otherwise>
		    </c:choose>
		</a>


        <!-- LOGIN / AVATAR -->
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

<jsp:include page="/includes/loginPopup.jsp" />

<script src="<c:url value='/js/login.js' />"></script>
<script src="<c:url value='/js/header.js' />"></script>