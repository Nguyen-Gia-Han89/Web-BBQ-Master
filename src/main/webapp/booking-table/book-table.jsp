<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
        <form action="${pageContext.request.contextPath}/booking-table" method="post" id="bookingForm">

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
                    <label for="guests">Số người (1-10):</label>
                    <input type="number" id="guests" name="guests" min="1" max="10" value="1" required>
						
					<div class="date-time-row">
					    <label>Ngày:</label>
					    <input type="date" id="bookingDate" name="date" 
					           value="${selectedDate != null ? selectedDate : ''}" required>
					    
					    <label>Giờ:</label>
					    <div class="time-slots-container" id="timeSlots">
					        <c:forEach var="h" begin="10" end="21" varStatus="status">
					            <button type="button" class="time-slot-btn" data-time="${h}:00">${h}:00</button>
					            <button type="button" class="time-slot-btn" data-time="${h}:30">${h}:30</button>
					        </c:forEach>
					    </div>
					    
					    <input type="hidden" name="time" id="selectedTime" value="${selectedTime != null ? selectedTime : '10:00'}">
					</div>
				</div>

                <div class="tab-buttons">
				    <button type="button" class="btn-next">Tiếp tục</button>
				</div>

            </div>

            <!-- Tab 2: Chọn bàn -->
            <div class="tab-content" id="tab2">
			    <div class="booking-card">
			        <h3>Chọn không gian</h3>
			        <div class="space-options-container">
			            <c:forEach var="sp" items="${spaces}" varStatus="status">
			                <input type="radio" class="space-radio" name="spaceId" 
			                       id="space-${sp.spaceId}" value="${sp.spaceId}" 
			                       ${status.first ? 'checked' : ''} required>
			                <label for="space-${sp.spaceId}" class="space-card">
			                    <strong>${sp.name}</strong>
			                    <div class="check-mark"><i class="fa-solid fa-circle-check"></i></div>
			                </label>
			            </c:forEach>
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
						                class="table-btn table-item space-${t.spaceId} ${t.isBooked ? 'occupied' : 'available'}" 
						                ${t.isBooked ? 'disabled' : ''} 
						                data-id="${t.tableId}" 
						                data-space="${t.spaceId}">
						            ${t.tableName}
						            <c:if test="${t.isBooked}"><br><small>(Hết chỗ)</small></c:if>
						        </button>
						    </c:forEach>
						</div>
                    </div>
                </fieldset>

                <div class="tab-buttons">
                    <button type="button" class="btn-back">Quay lại</button>
                    <button type="button" class="btn-next">Tiếp tục</button>
                </div>
            </div>

             <!-- Tab 3: Dịch vụ & Xác nhận -->
            <div id="tab3" class="tab-content">
			    <fieldset>
			        <legend>Chi tiết thực đơn</legend>
			        <table class="cart-table">
			            <thead>
			                <tr>
			                    <th>Sản phẩm</th>
			                    <th>Đơn giá</th>
			                    <th>Số lượng</th>
			                    <th>Thành tiền</th>
			                </tr>
			            </thead>
			            <tbody>
			                <c:if test="${not empty sessionScope.cart.bookingDetails}">
			                    <c:forEach var="item" items="${sessionScope.cart.bookingDetails}">
			                        <tr>
			                            <td>
			                                <div class="cart-item">
			                                    <img src="${item.dish.imageUrl}" class="cart-item-img" alt="${item.dish.name}">
			                                    <span>${item.dish.name}</span>
			                                </div>
			                            </td>
			                            <td><fmt:formatNumber value="${item.price}" pattern="#,##0"/>đ</td>
			                            <td>
			                                <div class="qty-wrapper">
			                                    <button type="button" class="qty-btn" onclick="updateBookingCart(${item.dish.dishId}, 'minus')">-</button>
			                                    <input type="text" value="${item.quantity}" class="qty-input" readonly>
			                                    <button type="button" class="qty-btn" onclick="updateBookingCart(${item.dish.dishId}, 'plus')">+</button>
			                                </div>
			                            </td>
			                            <td><fmt:formatNumber value="${item.total}" pattern="#,##0"/>đ</td>
			                        </tr>
			                    </c:forEach>
			                </c:if>
			                <tr>
			                    <td colspan="4">
			                        <a href="${pageContext.request.contextPath}/menu" class="add-food-link">+ Thêm món ăn mới</a>
			                    </td>
			                </tr>
			            </tbody>
			        </table>
			    </fieldset>
			
			    <fieldset>
				    <legend>Dịch vụ bổ sung</legend>
				    <label for="service">Chọn dịch vụ đi kèm:</label>
				    <select id="service" name="serviceId">
					    <c:forEach var="sv" items="${services}" varStatus="status">
					        <c:set var="totalFee" value="${isParty ? (sv.extraFee + 500000) : sv.extraFee}" />
					        <option value="${sv.serviceID}" data-fee="${totalFee}" ${status.first ? 'selected' : ''}>
					            ${sv.name} (+<fmt:formatNumber value="${totalFee}" pattern="#,##0"/>đ)
					        </option>
					    </c:forEach>
					</select>
				</fieldset>

			
			    <fieldset>
			        <legend>Ghi chú cho nhà hàng</legend>
			        <textarea id="note" name="note" rows="3" placeholder="Ví dụ: Bàn gần cửa sổ, không ăn cay..."></textarea>
			    </fieldset>
			
			    <div class="summary-container">
				    <div class="summary-line">
				        <span>Tiền món ăn:</span>
				        <strong id="foodTotalDisplay" data-food-total="${sessionScope.cart.totalAmount}">
				            <fmt:formatNumber value="${sessionScope.cart.totalAmount}" pattern="#,##0"/>đ
				        </strong>
				    </div>
				
				    <div class="summary-line">
				        <span>Phí dịch vụ:</span>
				        <strong id="serviceFeeDisplay">0đ</strong>
				    </div>
				
				    <div class="summary-line">
				        <span>Tổng thanh toán:</span>
				        <strong id="grandTotalDisplay">0đ</strong>
				    </div>
				
				    <div class="grand-total-line">
				        <span>Tiền cọc (30%):</span>
				        <strong id="depositDisplay">0đ</strong>
				    </div>
				</div>
				
				<!-- finalAmountInput = tiền cọc (sẽ thanh toán) -->
				<input type="hidden" name="amount" id="finalAmountInput" value="0">
				<!-- grandTotalInput = tổng tiền để server lưu -->
				<input type="hidden" name="grandTotal" id="grandTotalInput" value="0">

			    <div class="tab-buttons">
			        <button type="button" class="btn-back">Quay lại</button>
			        <c:choose>
			            <c:when test="${not empty sessionScope.cart.bookingDetails}">
			                <button type="submit" name="action" value="BOOK_AND_PAY" class="btn-book">Tiếp tục thanh toán</button>
			            </c:when>
			            <c:otherwise>
			                <button type="submit" name="action" value="BOOK_ONLY" class="btn-book">Xác nhận đặt bàn ngay</button>
			            </c:otherwise>
			        </c:choose>
			    </div>
			</div>
        </form>
    </div>
</section>

<jsp:include page="../includes/footer.jsp"></jsp:include>
<script src="<c:url value='/js/booking-tabs.js?v=' /><%= System.currentTimeMillis() %>"></script>
<script src="<c:url value='/js/booking-total.js?v=' /><%= System.currentTimeMillis() %>"></script>


</body>
</html>