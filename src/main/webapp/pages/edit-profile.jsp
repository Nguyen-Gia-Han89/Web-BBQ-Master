<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa thông tin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
</head>
<body>

<c:if test="${not empty sessionScope.customer}">
    <div class="profile-container">

        <div class="profile-card">

            <!-- Nút quay lại nằm TRONG khung -->
            <a href="${pageContext.request.contextPath}/profile"
               class="btn-back inside-card">
                ←
            </a>

            <!-- Tiêu đề -->
            <h2 class="profile-title">Chỉnh sửa thông tin</h2>

            <!-- Form -->
            <form method="post"
                  action="${pageContext.request.contextPath}/edit-profile">

                <div class="info-group">
                    <label>Họ và tên</label>
                    <input type="text"
                           name="fullName"
                           value="${sessionScope.customer.fullName}"
                           required>
                </div>

                <div class="info-group">
                    <label>Email</label>
                    <input type="email"
                           value="${sessionScope.customer.email}"
                           disabled>
                </div>

                <div class="info-group">
                    <label>Số điện thoại</label>
                    <input type="text"
                           name="phoneNumber"
                           value="${sessionScope.customer.phoneNumber}">
                </div>

                <div class="profile-actions">
                    <button type="submit" class="btn btn-edit">Lưu</button>
                    <a href="${pageContext.request.contextPath}/profile"
                       class="btn btn-password">Hủy</a>
                </div>

            </form>
        </div>
    </div>
</c:if>

</body>
</html>
