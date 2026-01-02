<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- Lấy trang hiện tại --%>
<c:set var="currentPage" value="${pageContext.request.servletPath}" />

<%-- URL dùng chung (qua Servlet) --%>
<c:url var="homeUrl" value="/index.jsp" />
<c:url var="menuUrl" value="/menu" />
<c:url var="bookTableUrl" value="/booking-table" />
<c:url var="bookPartyUrl" value="/pages/party-booking.jsp" />
<c:url var="promotionUrl" value="/promotions" />
<c:url var="contactUrl" value="/contact/contact.jsp" />
<c:url var="cartUrl" value="/cart" />
<c:url var="logoUrl" value="/images/Logo.jpg" />

<%-- Active class theo servlet --%>
<c:set var="homeActive" value="${currentPage == '/index.jsp' ? 'active' : ''}" />
<c:set var="menuActive" value="${currentPage == '/menu' ? 'active' : ''}" />
<c:set var="promotionActive" value="${currentPage == '/promotions' ? 'active' : ''}" />
<c:set var="contactActive" value="${currentPage == '/contact/contact.jsp' ? 'active' : ''}" />

<c:set var="serviceActiveClass" value="" />
<c:if test="${currentPage == '/booking-table' 
          or currentPage == '/pages/party-booking.jsp'}">
    <c:set var="serviceActiveClass" value="active" />
</c:if>

<c:set var="bookTableActive" value="${currentPage == '/booking-table' ? 'active' : ''}" />
<c:set var="bookPartyActive" value="${currentPage == '/pages/party-booking.jsp' ? 'active' : ''}" />



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
			            <a href="${bookPartyUrl}" class="${bookPartyActive}">
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
		    <span id="cart-badge" class="cart-badge">
		        <c:out value="${not empty sessionScope.cart ? sessionScope.cart.totalQuantity : 0}" />
		    </span>
		</a>


        <!-- LOGIN / AVATAR -->
     <c:choose>
    <c:when test="${empty sessionScope.customer}">
        <button class="login-btn" id="openLogin">
            <i class="fa-solid fa-user"></i> Đăng nhập
        </button>
    </c:when>
    <c:otherwise>
        <c:set var="firstChar" value="${fn:toUpperCase(fn:substring(sessionScope.customer.fullName, 0, 1))}" />
        
        <div class="user-profile-container" id="avatarToggle">
            <div class="user-avatar-circle">${firstChar}</div>
            
            <div class="user-dropdown-menu" id="userDropdown">
                <div class="dropdown-header">
                    <strong>${sessionScope.customer.fullName}</strong>
                    <span>${sessionScope.customer.email}</span>
                </div>
                
                <hr>
                
                <a href="${pageContext.request.contextPath}/profile" class="logout-item">
                    <i class="fa-solid fa-address-card"></i> Thông tin cá nhân
                </a>
                <hr>
                
                <a href="${pageContext.request.contextPath}/logout" class="logout-item">
                    <i class="fa-solid fa-right-from-bracket"></i> Đăng xuất
                </a>
            </div>
        </div>
    </c:otherwise>
</c:choose>
    </div>
</header>

<jsp:include page="/includes/loginPopup.jsp" />


<script>
    // Ép kiểu contextPath thành biến toàn cục để file cart.js dùng được ở mọi nơi
	window.contextPath = "${pageContext.request.contextPath}";
</script>
<script src="<c:url value='/js/login.js' />"></script>
<script src="<c:url value='/js/header.js' />"></script>