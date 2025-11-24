<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<footer class="footer">
	<div class="footer-container">

		<!-- Cột 1: Thông tin liên hệ -->
		<div class="footer-col">
			<h3>Liên hệ</h3>
			<p>
				<i class="fa-solid fa-location-dot"></i> 123 Đường Nguyễn Huệ, Quận
				1, TP.HCM
			</p>
			<p>
				<i class="fa-solid fa-phone"></i> 0123 456 789
			</p>
			<p>
				<i class="fa-solid fa-envelope"></i> contact@bbqmaster.vn
			</p>
		</div>

		<!-- Cột 2: Liên kết nhanh -->
		<div class="footer-col">
		    <h3>Liên kết nhanh</h3>
		    <ul>
		        <li><a href="<c:url value='/index.jsp' />">Trang chủ</a></li>
		        <li><a href="<c:url value='/pages/menu.jsp' />">Thực đơn</a></li>
		        <li><a href="<c:url value='/pages/book-table.jsp' />">Đặt bàn</a></li>
		        <li><a href="<c:url value='/pages/book-event.jsp' />">Đặt tiệc</a></li>
		        <li><a href="<c:url value='/pages/contact.jsp' />">Liên hệ</a></li>
		    </ul>
		</div>

		<!-- Cột 3: Mạng xã hội -->
		<div class="footer-col">
			<h3>Kết nối</h3>
			<div class="social-links">
				<a href="#"><i class="fa-brands fa-facebook"></i></a> <a href="#"><i
					class="fa-brands fa-instagram"></i></a> <a href="#"><i
					class="fa-brands fa-tiktok"></i></a> <a href="#"><i
					class="fa-brands fa-youtube"></i></a>
			</div>
		</div>

		<!-- Cột 4: Newsletter -->
		<div class="footer-col">
			<h3>Nhận khuyến mãi</h3>
			<p>Đăng ký email để nhận ưu đãi mới nhất</p>
			<form>
				<input type="email" placeholder="Nhập email của bạn">
				<button type="submit">Đăng ký</button>
			</form>
		</div>
	</div>

	<!-- Bản quyền -->
	<div class="footer-bottom">
		<p>© 2025 BBQ Master. All rights reserved.</p>
	</div>
</footer>