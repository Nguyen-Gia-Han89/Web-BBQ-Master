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

				
				
			    <button type="button" class="btn-next">Tiếp tục</button>
			</div>


            <!-- Tab 2: Chọn bàn -->
            <div class="tab-content" id="tab2" style="display:none;">
              
                <div class="booking-card">
				    <h3>Chọn không gian</h3>
				
				    <label><input type="radio" name="space2" value="lau1"> Lầu 1</label>
				    <label><input type="radio" name="space2" value="vip"> VIP</label>
				    <label><input type="radio" name="space2" value="sanvuon"> Sân vườn</label>
				
				</div>
				
                <fieldset>
                    <legend>Chọn bàn trực tiếp</legend>
                   
                    <p>Bàn đã chọn: <span id="selectedTableDisplay">Chưa chọn</span></p>
                    <input type="hidden" id="selectedTable" name="selectedTable">
                		
                		<!-- SƠ ĐỒ BÀN (ẨN BAN ĐẦU) -->
			        <div class="map-container" id="mapContainer" style="display:none; margin-top:16px;">
			            
			            <!-- LẦU 1 -->
			            <div id="floor1-map" class="floor-layout">
			                <div class="grid-container">
			                    <button type="button" class="table-btn available" data-id="A01">
			                        A01<br><span>(4 người)</span>
			                    </button>
			
			                    <button type="button" class="table-btn occupied" disabled>
			                        A02<br><span>(Đã đặt)</span>
			                    </button>
			                </div>
			            </div>
			
			            <!-- SÂN VƯỜN -->
			            <div id="garden-map" class="floor-layout" style="display:none;">
			                <div class="garden-flex">
			                    <button type="button" class="table-btn available" data-id="SV01">SV01</button>
			                    <div class="long-table">Bàn tiệc</div>
			                    <button type="button" class="table-btn available" data-id="SV02">SV02</button>
			                </div>
			            </div>
			
			        </div>
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
<script src="<c:url value='/js/booking-tabs.js'/>"></script>

</body>
</html>
