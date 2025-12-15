<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
	<meta charset="UTF-8">
	<title>Đặt bàn - BBQ Master</title>
	<link rel="stylesheet" href="<c:url value='/css/base.css'/>">	<link rel="stylesheet" href="<c:url value='/css/header.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/booking.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/footer.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/responsive.css'/>">
	<link rel="stylesheet"
			href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
	
	<!-- HEADER -->
	<jsp:include page="/includes/header.jsp" />
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
			    <!-- Khung 1: Thông tin cá nhân -->
			    <div class="booking-card">
			        <h3>Thông tin cá nhân <span class="required">*</span></h3>
			        <label for="name">Họ và tên:</label>
			        <input type="text" id="name" name="name"
			               value="${not empty sessionScope.customer ? sessionScope.customer.fullName : ''}"
			               placeholder="Nhập họ và tên" required
			               ${not empty sessionScope.customer ? 'readonly' : ''}>
			
			        <label for="phone">Số điện thoại:</label>
			        <input type="tel" id="phone" name="phone"
			               value="${not empty sessionScope.customer ? sessionScope.customer.phoneNumber : ''}"
			               placeholder="Nhập số điện thoại" pattern="\d{9,11}" required
			               ${not empty sessionScope.customer ? 'readonly' : ''}>
			
			        <label for="email">Email:</label>
			        <input type="email" id="email" name="email"
			               value="${not empty sessionScope.customer ? sessionScope.customer.email : ''}"
			               placeholder="Nhập email" required>
			    </div>
			
			    <!-- Khung 2: Thời gian -->
			    <div class="booking-card">
			        <h3>Thời gian <span class="required">*</span></h3>
			        <label for="guests">Số người (1-20):</label>
			        <input type="number" id="guests" name="guests" min="1" max="20" value="1" required>

					<div class="date-time-row">
					    <div class="date-group">
					        <label for="date">Chọn ngày:</label>
					        <input type="date" id="date" name="date" required>
					    </div>
					    <div class="time-group">
					        <label>Chọn giờ:</label>
					        <div id="timeSlots" class="time-slots"></div>
					        <input type="time" id="time" name="time" required step="1800" min="10:00" max="22:00">
					    </div>
					</div>

			    </div>
			
			    
				<!-- Khung 3: Không gian -->
				<div class="booking-card">
				  	<h3>🏠 Chọn Không gian Ăn uống <span class="required">*</span></h3>
					  <p class="hint">*Các lựa chọn sẽ thay đổi theo số lượng người (Ngưỡng 20 khách).</p>
					
						  <fieldset class="space-options-wrapper" id="spaceOptionsContainer" aria-labelledby="spaceLegend">
							    <legend id="spaceLegend" class="sr-only">Chọn không gian ăn uống</legend>
							
							    <!-- Lầu 1 -->
							    <input type="radio" id="space-lau1" name="space" value="lau1" class="space-radio" required>
							    <label for="space-lau1" class="space-option">Lầu 1</label>
							
							    <!-- VIP -->
							    <input type="radio" id="space-vip" name="space" value="vip" class="space-radio">
							    <label for="space-vip" class="space-option">VIP</label>
							
							    <!-- Sân vườn -->
							    <input type="radio" id="space-sanvuon" name="space" value="sanvuon" class="space-radio">
							    <label for="space-sanvuon" class="space-option">Sân vườn</label>
						  </fieldset>
					
					  <p>Bạn đã chọn: <span id="selectedSpace">Chưa chọn</span></p>
				</div>

				
				
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
