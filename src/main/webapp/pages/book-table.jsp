<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đặt bàn - BBQ Master</title>
    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/header.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/booking.css?v=' /><%= System.currentTimeMillis() %>">
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
        <form action="${pageContext.request.contextPath}/booking" method="post" id="bookingForm">

            <!-- Tab 1: Thông tin & Thời gian -->
            <div class="tab-content active" id="tab1">
                <!-- Khung 1: Thông tin cá nhân -->
                <div class="booking-card">
                    <h3>Thông tin cá nhân <span class="required">*</span></h3>
                    <c:set var="customer" value="${sessionScope.customer}" />
                    <label>Họ và tên:</label>
                    <input type="text" name="name"
                           value="${customer.fullName}"
                           placeholder="Nhập họ và tên"
                           <c:if test="${not empty customer}">readonly</c:if>
                           required>

                    <label>Số điện thoại:</label>
                    <input type="tel" name="phone"
                           value="${customer.phoneNumber}"
                           pattern="\d{9,11}"
                           <c:if test="${not empty customer}">readonly</c:if>
                           required>

                    <label>Email:</label>
                    <input type="email" name="email"
                           value="${customer.email}"
                           required>
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
                            <div id="timeSlots" class="time-slots">
                                <c:forEach var="h" begin="10" end="21">
                                    <button type="button" class="time-slot-btn" data-time="${h}:00">
                                        ${h}:00
                                    </button>
                                    <button type="button" class="time-slot-btn" data-time="${h}:30">
                                        ${h}:30
                                    </button>
                                </c:forEach>
                            </div>
                            <input type="hidden" id="time" name="time" required>
                        </div>
                    </div>
                </div>

                <button type="button" class="btn-next">Tiếp tục</button>
            </div>

            <!-- Tab 2: Chọn bàn -->
            <div class="tab-content" id="tab2">
                <div class="booking-card">
                    <h3>Chọn không gian</h3>
                    <div class="space-options-wrapper">
					    <c:forEach var="sp" items="${spaces}">
					        <input type="radio" class="space-radio" name="spaceId" 
					               id="space-${sp.spaceId}" value="${sp.spaceId}">
					        <label for="space-${sp.spaceId}" class="space-option">
					            <strong>${sp.name}</strong><br>
					            <span>${sp.description}</span>
					        </label>
					    </c:forEach>
                        <c:if test="${empty spaces}">
                            <p>Không có dữ liệu khu vực</p>
                        </c:if>
                    </div>
                </div>

                <fieldset>
                    <legend>Chọn bàn</legend>
                    <p>Bàn đã chọn:
                        <span id="selectedTableDisplay">Chưa chọn</span>
                    </p>
                    <input type="hidden" id="selectedTable" name="tableId">

                    <div class="map-container" id="mapContainer" style="margin-top:16px;">
                        <div class="grid-container">
                            <c:forEach var="t" items="${availableTables}">
                                <button type="button"
                                        class="table-btn available"
                                        data-id="${t.tableId}"
                                        data-space="${t.spaceId}">
                                    ${t.tableName}
                                    <br>
                                    <span>(${t.seats} người)</span>
                                </button>
                            </c:forEach>
                            <c:if test="${empty availableTables}">
                                <p>Không có bàn trống trong khung giờ đã chọn</p>
                            </c:if>
                        </div>
                    </div>
                </fieldset>

                <div class="tab-buttons">
                    <button type="button" class="btn-back">Quay lại</button>
                    <button type="button" class="btn-next">Tiếp tục</button>
                </div>
            </div>

            <!-- Tab 3: Dịch vụ & Xác nhận -->
            <div class="tab-content" id="tab3">
			    <fieldset>
			        <legend>Danh sách món ăn đã đặt</legend>
			        
			        <table class="cart-table">
			            <thead>
			                <tr>
			                    <th>Sản phẩm</th>
			                    <th>Đơn giá</th>
			                    <th style="width: 100px;">Số lượng</th>
			                    <th>Thành tiền</th>
			                </tr>
			            </thead>
			            <tbody>
			                <c:choose>
			                    <c:when test="${not empty sessionScope.cart.bookingDetails}">
			                        <c:forEach var="item" items="${sessionScope.cart.bookingDetails}">
			                            <tr>
			                                <td>
			                                    <div style="display: flex; align-items: center; gap: 10px;">
			                                        <img src="${item.dish.imageUrl}" width="50" style="border-radius: 5px;">
			                                        <span>${item.dish.name}</span>
			                                    </div>
			                                </td>
			                                <td><fmt:formatNumber value="${item.price}" pattern="#,##0"/>đ</td>
			                                <td>
			                                    <div class="qty-wrapper">
			                                        <button type="button" class="qty-btn" onclick="updateBookingCart(${item.dish.dishId}, 'minus')">-</button>
			                                        <input type="text" value="${item.quantity}" class="qty-input" readonly size="1">
			                                        <button type="button" class="qty-btn" onclick="updateBookingCart(${item.dish.dishId}, 'plus')">+</button>
			                                    </div>
			                                </td>
			                                <td><fmt:formatNumber value="${item.total}" pattern="#,##0"/>đ</td>
			                            </tr>
			                        </c:forEach>
			                        <tr style="background: #f9f9f9; font-weight: bold;">
			                            <td colspan="3" style="text-align: right;">Tổng tiền món:</td>
			                            <td style="color: red;"><fmt:formatNumber value="${sessionScope.cart.totalAmount}" pattern="#,##0"/>đ</td>
			                        </tr>
			                    </c:when>
			                    <c:otherwise>
			                        <tr>
			                            <td colspan="4" style="text-align: center; padding: 20px;">
			                                Giỏ hàng trống. <a href="menu">Quay lại chọn món</a>
			                            </td>
			                        </tr>
			                    </c:otherwise>
			                </c:choose>
			            </tbody>
			        </table>
			
			        <label for="service">Dịch vụ đi kèm:</label>
			        <select id="service" name="serviceId">
			            <option value="">-- Chọn dịch vụ --</option>
			            <c:forEach var="sv" items="${services}">
			                <option value="${sv.serviceID}">${sv.name} (+<fmt:formatNumber value="${sv.extraFee}" pattern="#,##0"/>đ)</option>
			            </c:forEach>
			        </select>
			
			        <label for="note">Ghi chú đặc biệt:</label>
			        <textarea id="note" name="note" rows="3" placeholder="Ví dụ: Trang trí sinh nhật, ít cay..."></textarea>
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