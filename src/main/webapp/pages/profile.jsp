<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thông tin tài khoản</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
</head>
<body>

<c:if test="${not empty sessionScope.customer}">
    <div class="profile-container">

        <div class="profile-card">

            <!-- Nút quay về trang chủ -->
            <a href="${pageContext.request.contextPath}/index.jsp"
               class="btn-back inside-card">
                ←
            </a>

            <!-- Tiêu đề -->
            <h2 class="profile-title">Thông tin tài khoản</h2>

            <!-- Avatar -->
            <div class="avatar-large">
                <c:choose>
                    <c:when test="${not empty sessionScope.customer.fullName}">
                        ${fn:toUpperCase(fn:substring(sessionScope.customer.fullName, 0, 1))}
                    </c:when>
                    <c:otherwise>?</c:otherwise>
                </c:choose>
            </div>

            <!-- Thông tin -->
            <div class="profile-details">
                <div class="info-group">
                    <label>Họ và tên</label>
                    <span>${sessionScope.customer.fullName}</span>
                </div>

                <div class="info-group">
                    <label>Email</label>
                    <span>${sessionScope.customer.email}</span>
                </div>

                <div class="info-group">
                    <label>Số điện thoại</label>
                    <span>
                        <c:choose>
                            <c:when test="${not empty sessionScope.customer.phoneNumber}">
                                ${sessionScope.customer.phoneNumber}
                            </c:when>
                            <c:otherwise>Chưa cập nhật</c:otherwise>
                        </c:choose>
                    </span>
                </div>
            </div>

            <!-- Hành động -->
            <div class="profile-actions">
                <a href="${pageContext.request.contextPath}/edit-profile"
                   class="btn btn-edit">
                    Chỉnh sửa thông tin
                </a>
            </div>

        </div>
    </div>
</c:if>

<c:if test="${empty sessionScope.customer}">
    <script>
        window.location.href = "${pageContext.request.contextPath}/login.jsp";
    </script>
</c:if>

</body>
</html>
