<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Đặt tiệc liên hoan - BBQ Master</title>
<link rel="stylesheet" href="<c:url value='/css/base.css'/>">
<link rel="stylesheet" href="<c:url value='/css/header.css'/>">
<link rel="stylesheet"
	href="<c:url value='/css/booking.css?v=' /><%=System.currentTimeMillis()%>">
<link rel="stylesheet" href="<c:url value='/css/footer.css'/>">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

	<jsp:include page="/includes/header.jsp" />

	<section class="booking-section">
		<div class="container">
			<h1>Đặt tiệc</h1>

			<div class="tabs">
				<div class="tab active" data-tab="tab1">1. Thông tin & Không
					gian</div>
				<div class="tab" data-tab="tab2">2. Thực đơn & Dịch vụ</div>
			</div>

			<form action="${pageContext.request.contextPath}/party-booking"
				method="post" id="partyForm">

				<div class="tab-content active" id="tab1">
					<div class="booking-grid">

						<div class="booking-column">
							<div class="booking-card">
								<h3>
									<i class="fa-solid fa-user"></i> Thông tin liên hệ
								</h3>
								<label>Họ và tên người đại diện:</label> <input type="text"
									name="name" value="${sessionScope.customer.fullName}" required>

								<label>Số điện thoại:</label> <input type="tel" name="phone"
									value="${sessionScope.customer.phoneNumber}" required>

								<label>Email nhận thông báo:</label> <input type="email"
									name="email" value="${sessionScope.customer.email}" required>
							</div>

							<div class="booking-card">
								<h3>
									<i class="fa-solid fa-clock"></i> Thời gian tổ chức
								</h3>
								<label>Số lượng khách (Min 10):</label> <input type="number"
									name="guests" min="10" value="10" required> 
								<label>Ngày:</label>
								<input type="date" id="bookingDate" name="date" required>
									
								<label>Giờ:</label>
								<select name="time" id="bookingTime" required>
									<c:forEach var="h" begin="10" end="21" varStatus="status">
										<option value="${h}:00" ${status.first ? 'selected' : ''}>${h}:00</option>
									    <option value="${h}:30">${h}:30</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="booking-column">
							<div class="booking-card space-selection-card">
								<h3>
									<i class="fa-solid fa-map-location-dot"></i> Chọn không gian
									tiệc
								</h3>
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
						</div>

					</div>

					<div class="tab-buttons">
						<button type="button" class="btn-next">
							Tiếp tục <i class="fa-solid fa-arrow-right"></i>
						</button>
					</div>
				</div>

				<div class="tab-content" id="tab2">
					<div class="booking-card">
						<h3>
							<i class="fa-solid fa-utensils"></i> Thực đơn đã chọn
						</h3>
						<table class="cart-table">
							<thead>
								<tr>
									<th>Món ăn</th>
									<th>Số lượng</th>
									<th>Thành tiền</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="item"
									items="${sessionScope.cart.bookingDetails}">
									<tr>
										<td>
											<div class="cart-item-info">
												<img src="${item.dish.imageUrl}" alt="${item.dish.name}"
													class="item-img"> <strong>${item.dish.name}</strong>
											</div>
										</td>
										<td>${item.quantity}</td>
										<td><fmt:formatNumber value="${item.total}"
												pattern="#,##0" />đ</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<a href="${pageContext.request.contextPath}/menu"
							class="edit-menu-link">Chỉnh sửa thực đơn</a>
					</div>

					<div class="booking-card">
						<h3>
							<i class="fa-solid fa-concierge-bell"></i> Dịch vụ & Phí tổ chức
						</h3>
						<label for="service">Gói dịch vụ (Chọn để thay đổi):</label> 
						<select id="service" name="serviceId">
							<c:forEach var="sv" items="${services}" varStatus="status">
								<option value="${sv.serviceID}"
									data-fee="${sv.extraFee + 500000}"
									${status.first ? 'selected' : ''}>${sv.name} (+
									<fmt:formatNumber value="${sv.extraFee + 500000}"
										pattern="#,##0" />đ)
								</option>
							</c:forEach>
						</select>
						<p class="service-note">* Đã bao gồm phí phục vụ tiệc mặc định
							500,000đ.</p>

						<label>Ghi chú cho nhà hàng:</label>
						<textarea name="note" rows="3"
							placeholder="Yêu cầu về không gian, món ăn..."></textarea>
					</div>

					<div class="summary-container">
						<div class="summary-line">
							<span>Tiền món ăn:</span> <strong id="foodTotalDisplay"
								data-food-total="${sessionScope.cart.totalAmount}"><fmt:formatNumber
									value="${sessionScope.cart.totalAmount}" pattern="#,##0" />đ</strong>
						</div>
						<div class="summary-line">
							<span>Phí dịch vụ:</span> <strong id="serviceFeeDisplay">0đ</strong>
						</div>
						<div class="summary-line total-highlight">
							<span>Tổng dự tính:</span> <strong id="grandTotalDisplay">0đ</strong>
						</div>
						<div class="grand-total-line">
							<span>Tiền cọc (50%):</span> <strong id="depositDisplay">0đ</strong>
						</div>
					</div>

					<input type="hidden" name="amount" id="finalAmountInput"> <input
						type="hidden" name="grandTotal" id="grandTotalInput">

					<div class="tab-buttons">
						<button type="button" class="btn-back">Quay lại</button>
						<button type="submit" class="btn-book">Xác nhận đặt tiệc</button>
					</div>
				</div>
			</form>
		</div>
	</section>

	<jsp:include page="../includes/footer.jsp"></jsp:include>

	<script src="<c:url value='/js/booking-tabs.js'/>"></script>
	<script src="<c:url value='/js/booking-total.js'/>"></script>

</body>
</html>