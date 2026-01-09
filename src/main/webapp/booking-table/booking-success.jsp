<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thành công - BBQ Master</title>
    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/header.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/footer.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/booking-success.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp" />

    <section class="success-wrapper">
        <div class="container">
            <div class="success-card">
                <div class="success-icon">
                    <i class="fa-solid fa-circle-check"></i>
                </div>
                
                <h1 class="success-title">Xác nhận thành công</h1>
                <p class="success-msg">Cảm ơn quý khách đã tin tưởng BBQ Master.</p>

                <div class="info-grid">
				    <div class="info-item header-item">
				        <span class="info-label">MÃ ĐƠN HÀNG:</span>
				        <span class="info-value">${sessionScope.ORDER_CODE}</span>
				    </div>
				
				    <div class="detail-list">
				        <div class="detail-row">
				            <span class="label">Ngày giờ:</span>
				            <span class="value">${sessionScope.BOOKING_TIME_STR}</span>
				        </div>
				        <div class="detail-row">
				            <span class="label">Khu vực:</span>
				            <span class="value">${sessionScope.SPACE_NAME}</span>
				        </div>
				        <div class="detail-row">
				            <span class="label">Bàn số:</span>
				            <span class="value">${sessionScope.TABLE_NAME}</strong></span>
				        </div>
				        <div class="detail-row">
				            <span class="label">Số khách:</span>
				            <span class="value">${sessionScope.GUESTS} người</span>
				        </div>
				    </div>
				
				    <div class="status-badge-centered">
				        <i class="fa-solid fa-circle-user" style="font-size: 14px; margin-right: 5px;"></i>
				        Đang chờ bạn đến cửa hàng
				    </div>
				</div>
				
				<div class="group-btns">
				    <a href="<c:url value='/booking-history'/>" class="btn-action btn-history">
				        <i class="fa-solid fa-clock-rotate-left"></i> Xem lịch sử
				    </a>
				    
				    <a href="<c:url value='/'/>" class="btn-action btn-home">
				        <i class="fa-solid fa-house"></i> Trang chủ
				    </a>
				</div>
            </div>
        </div>
    </section>

    <jsp:include page="/includes/footer.jsp" />
    
    <script src="<c:url value='/js/booking-success.js'/>"></script>
</body>
</html>