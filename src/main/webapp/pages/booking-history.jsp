<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Lịch sử đặt bàn | BBQ Master</title>
    
    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/header.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/footer.css'/>">
    
    <link rel="stylesheet" href="<c:url value='/css/booking-history.css'/>">
    
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
    
    <jsp:include page="/includes/header.jsp" />

    <main class="container history-section">
        <h2 class="history-title">
            <i class="fa-solid fa-clock-rotate-left"></i> Lịch sử đặt bàn của bạn
        </h2>
        
        <table class="admin-table">
            <thead>
                <tr>
                    <th>Mã Đơn</th>
                    <th>Thời Gian Đặt</th>
                    <th>Số Khách</th>
                    <th>Tổng Tiền</th>
                    <th>Trạng Thái</th>
                    <th>Hành Động</th> 
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty history}">
                        <c:forEach var="b" items="${history}">
                            <tr>
                                <td><span class="order-id">#${b.orderCode}</span></td>
                                
                                <td>
                                    <strong>${b.bookingTime.dayOfMonth}/${b.bookingTime.monthValue}/${b.bookingTime.year}</strong>
                                    <span class="time-text">
                                        <i class="fa-regular fa-clock"></i> 
                                        ${b.bookingTime.hour}:${b.bookingTime.minute < 10 ? '0' : ''}${b.bookingTime.minute}
                                    </span>
                                </td>

                                <td>${b.numberOfGuests} khách</td>

                                <td><strong><fmt:formatNumber value="${b.totalAmount}" pattern="#,###" />đ</strong></td>

                                <td>
                                    <c:choose>
                                        <c:when test="${b.status == 'PENDING'}">
                                            <span class="badge bg-warning">Đang xử lý</span>
                                        </c:when>
                                        <c:when test="${b.status == 'CONFIRMED'}">
                                            <span class="badge bg-info">Đã xác nhận</span>
                                        </c:when>
                                        <c:when test="${b.status == 'COMPLETED'}">
                                            <span class="badge bg-success">Hoàn thành</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-danger">Đã hủy</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <td>
                                    <c:if test="${b.status == 'PENDING'}">
                                        <a href="javascript:void(0)" onclick="confirmCancel(${b.bookingId})" class="btn-cancel-small">
                                            <i class="fa-solid fa-trash-can"></i> Hủy
                                        </a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="6">
                                <i class="fa-solid fa-inbox"></i>
                                Bạn chưa có đơn đặt bàn nào.
                                <br><br>
                                <a href="<c:url value='/booking-table'/>">Đặt bàn ngay</a>
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </main>

    <jsp:include page="/includes/footer.jsp" />

    <script src="<c:url value='/js/booking-history.js'/>"></script>
</body>
</html>