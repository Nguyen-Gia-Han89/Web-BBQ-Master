<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Liên hệ - BBQ Master</title>
	<link rel="stylesheet" href="<c:url value='/css/base.css'/>">
	<link rel="stylesheet" href="<c:url value='/css/header.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/contact.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/footer.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/responsive.css'/>">
    
	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>
<body>
	<!-- Header -->
	<jsp:include page="/includes/header.jsp" />

	<!-- Hero Section -->
	<section class="hero"
		style="background-image: url('https://images.unsplash.com/photo-1555396273-367ea4eb4db5?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80'); height: 50vh;">
		<div class="hero-content">
			<h1>
				<span class="bbq">Liên hệ</span>
			</h1>
			<p>Hãy liên hệ để đặt bàn, đặt tiệc hoặc bất kỳ câu hỏi nào</p>
		</div>
	</section>

	<!-- Contact Section -->
	<section class="contact">
		<div class="container">
			<h2>Liên hệ với chúng tôi</h2>
			<p>Chúng tôi luôn sẵn sàng hỗ trợ bạn! Điền form bên dưới hoặc sử
				dụng thông tin liên hệ.</p>

			<div class="contact-content">
				<!-- Thông tin liên hệ -->
				<div class="contact-info">
					<div class="info-item">
						<i class="fa-solid fa-location-dot"></i>
						<div>
							<h3>Địa chỉ</h3>
							<p>123 Đường BBQ, Quận 1, TP. Hồ Chí Minh</p>
						</div>
					</div>

					<div class="info-item">
						<i class="fa-solid fa-phone"></i>
						<div>
							<h3>Số điện thoại</h3>
							<p>+84 123 456 789</p>
						</div>
					</div>

					<div class="info-item">
						<i class="fa-solid fa-envelope"></i>
						<div>
							<h3>Email</h3>
							<p>info@bbqmaster.vn</p>
						</div>
					</div>

					<div class="info-item">
						<i class="fa-solid fa-clock"></i>
						<div>
							<h3>Giờ mở cửa</h3>
							<p>10:00 - 22:00 (Hàng ngày)</p>
						</div>
					</div>
				</div>

				<!-- Form liên hệ -->
				<div class="contact-form">
					<form>
						<div class="form-group">
							<label for="name">Họ và tên *</label> <input type="text"
								id="name" name="name" required>
						</div>

						<div class="form-group">
							<label for="email">Email *</label> <input type="email" id="email"
								name="email" required>
						</div>

						<div class="form-group">
							<label for="phone">Số điện thoại</label> <input type="tel"
								id="phone" name="phone">
						</div>

						<div class="form-group">
							<label for="message">Tin nhắn *</label>
							<textarea id="message" name="message" rows="5" required
								placeholder="Ví dụ: Tôi muốn đặt bàn cho 4 người vào tối nay..."></textarea>
						</div>

						<button type="submit" class="btn btn-primary">Gửi tin
							nhắn</button>
					</form>
				</div>
			</div>

			<!-- Bản đồ -->
			<div class="map-container">
				<iframe
					src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.447!2d106.699!3d10.776!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31752f38e5a7c3d9%3A0x4d7b2e0f0a4a4b0!2sHo%20Chi%20Minh%20City!5e0!3m2!1sen!2s!4v1630000000000"
					width="100%" height="400" style="border: 0;" allowfullscreen=""
					loading="lazy"></iframe>
			</div>
		</div>
	</section>

	<!-- Footer -->
	<jsp:include page="/includes/footer.jsp" />

</body>
</html>
