<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Đặt bàn - BBQ Master</title>
<link rel="stylesheet" href="<c:url value='/css/style.css' />">
<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
	
	<!-- HEADER -->
	<jsp:include page="../includes/header.jsp" />
<section class="booking-section">
    <div class="container">
        <h1>Đặt bàn</h1>

        <!-- Tabs -->
        <div class="tabs">
            <div class="tab" data-tab="tab1">Thông tin & Thời gian</div>
            <div class="tab" data-tab="tab2">Chọn bàn</div>
            <div class="tab" data-tab="tab3">Dịch vụ & Xác nhận</div>
        </div>

        <!-- Booking Form -->
        <form action="BookingServlet" method="post" id="bookingForm">

            <!-- Tab 1: Thông tin & Thời gian -->
            <div class="tab-content" id="tab1">
                <fieldset>
                    <legend>Thông tin cá nhân <span class="required">*</span></legend>
                    <label for="name">Họ và tên:</label>
                    <input type="text" id="name" name="name" 
                           value="${not empty sessionScope.customer ? sessionScope.customer.fullName : ''}" 
                           placeholder="Nhập họ và tên" required>

                    <label for="phone">Số điện thoại:</label>
                    <input type="tel" id="phone" name="phone" 
                           value="${not empty sessionScope.customer ? sessionScope.customer.phoneNumber : ''}" 
                           placeholder="Nhập số điện thoại" pattern="\d{9,11}" required>

                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" 
                           value="${not empty sessionScope.customer ? sessionScope.customer.email : ''}" 
                           placeholder="Nhập email" required>
                </fieldset>

                <fieldset>
                    <legend>Thời gian <span class="required">*</span></legend>
                    <label for="guests">Số người:</label>
                    <input type="number" id="guests" name="guests" min="1" max="10" value="1" required>

                    <label for="date">Chọn ngày:</label>
                    <input type="date" id="date" name="date" required>

                    <label>Chọn giờ:</label>
                    <div id="timeSlots" class="time-slots"></div>
                    <input type="hidden" id="time" name="time" required>
                </fieldset>

                <button type="button" class="btn-next">Tiếp tục</button>
            </div>

            <!-- Tab 2: Chọn bàn -->
            <div class="tab-content" id="tab2" style="display:none;">
                <fieldset>
                    <legend>Chọn bàn trực tiếp</legend>
                    <button type="button" class="btn-view-map">Xem sơ đồ bàn</button>
                    <p>Bàn đã chọn: <span id="selectedTableDisplay">Chưa chọn</span></p>
                    <input type="hidden" id="selectedTable" name="selectedTable">
                </fieldset>

                <div class="tab-buttons">
                    <button type="button" class="btn-back">Quay lại</button>
                    <button type="button" class="btn-next">Tiếp tục</button>
                </div>
            </div>

            <!-- Tab 3: Dịch vụ & Xác nhận -->
            <div class="tab-content" id="tab3" style="display:none;">
                <fieldset>
                    <legend>Dịch vụ & Xác nhận</legend>

                    <label for="space">Chọn không gian:</label>
                    <select id="space" name="space" required>
                        <option value="">-- Chọn không gian --</option>
                        <option value="lau1">Lầu 1</option>
                        <option value="vip">VIP</option>
                        <option value="sanvuon">Sân vườn</option>
                    </select>

                    <label for="service">Chọn dịch vụ:</label>
                    <select id="service" name="service" required>
                        <option value="">-- Chọn dịch vụ --</option>
                        <option value="tu-nuong">Khách tự nướng</option>
                        <option value="nuong-san">Yêu cầu nướng sẵn</option>
                    </select>

                    <label for="combo">Chọn combo / set món (nếu có):</label>
                    <select id="combo" name="combo">
                        <option value="">Không chọn</option>
                        <option value="combo1">Combo BBQ thập cẩm</option>
                        <option value="combo2">Set nướng gia đình</option>
                        <option value="combo3">Set nướng cặp đôi</option>
                    </select>

                    <label for="note">Ghi chú:</label>
                    <textarea id="note" name="note" rows="4" placeholder="Nhập yêu cầu đặc biệt (nếu có)"></textarea>
                </fieldset>

                <div class="tab-buttons">
                    <button type="button" class="btn-back">Quay lại</button>
                    <button type="submit" class="btn-book">Xác nhận đặt bàn</button>
                </div>
            </div>

        </form>
    </div>
</section>
<jsp:include page="../includes/footer.jsp"></jsp:include>
<script src="../js/booking-tabs.js"></script>


</body>
</html>
