<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 1. Lấy URI hiện tại để xử lý Active Class --%>
<c:set var="uri" value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:if test="${empty uri}">
    <c:set var="uri" value="${pageContext.request.requestURI}" />
</c:if>

<%-- 2. Khai báo URL dùng chung --%>
<c:url var="homeUrl" value="/index.jsp" />
<c:url var="menuUrl" value="/menu" />
<c:url var="bookTableUrl" value="/booking-table" />
<c:url var="bookPartyUrl" value="/party-booking" />
<c:url var="promotionUrl" value="/khuyen-mai" />
<c:url var="contactUrl" value="/contact/contact.jsp" />
<c:url var="cartUrl" value="/cart" />
<c:url var="logoUrl" value="/images/Logo.jpg" />

<%-- 3. Logic định nghĩa Active Class dựa trên URI --%>
<c:set var="homeActive" value="${uri == pageContext.request.contextPath or uri == (pageContext.request.contextPath += '/') or fn:contains(uri, 'index.jsp') ? 'active' : ''}" />
<c:set var="menuActive" value="${fn:contains(uri, '/menu') ? 'active' : ''}" />
<c:set var="promotionActive" value="${fn:contains(uri, '/khuyen-mai') ? 'active' : ''}" />
<c:set var="contactActive" value="${fn:contains(uri, '/contact') ? 'active' : ''}" />

<%-- Xử lý riêng cho Dropdown Dịch vụ --%>
<c:set var="bookTableActive" value="${fn:contains(uri, '/booking-table') ? 'active' : ''}" />
<c:set var="bookPartyActive" value="${fn:contains(uri, '/party-booking') ? 'active' : ''}" />
<c:set var="serviceActive" value="${not empty bookTableActive or not empty bookPartyActive ? 'active' : ''}" />

<header class="header">
    <div class="logo">
        <a href="${homeUrl}" class="logo-link">
            <img src="${logoUrl}" alt="Logo BBQ Master">
            <span>BBQ Master</span>
        </a>
    </div>

    <nav>
        <ul>
            <li><a href="${homeUrl}" class="${homeActive}">Trang chủ</a></li>
            <li><a href="${menuUrl}" class="${menuActive}">Thực đơn</a></li>
            
            <li class="has-dropdown ${serviceActive}">
                <a href="javascript:void(0)" class="${serviceActive}">
                    Dịch vụ <i class="fa-solid fa-chevron-down"></i>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="${bookTableUrl}" class="${bookTableActive}">Đặt bàn</a>
                    </li>
                    <li>
                        <a href="${bookPartyUrl}" class="${bookPartyActive}">Đặt tiệc</a>
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
			    
			    <c:if test="${sessionScope.customer.role == 'administrator'}">
				    <a href="${pageContext.request.contextPath}/admin/dashboard" class="logout-item">
				        <i class="fa-solid fa-user-shield"></i> Trang quản trị
				    </a>
				    <hr>
				</c:if>
			    
			    <a href="${pageContext.request.contextPath}/profile" class="logout-item">
			        <i class="fa-solid fa-address-card"></i> Thông tin cá nhân
			    </a>
			
			    <a href="${pageContext.request.contextPath}/booking-history" class="logout-item">
			        <i class="fa-solid fa-clock-rotate-left"></i> Lịch sử đặt bàn
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