<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

	<div class="login-modal" id="loginModal">
	    <div class="modal-content">
	        <span class="close">&times;</span>
	        <div class="form-box" id="loginBox">
	            <h2>Đăng nhập</h2>
	            <form action="<c:url value='/LoginServlet' />" method="post">
	                <input type="email" name="email" placeholder="Email đăng nhập" required>
	                <input type="password" name="password" placeholder="Mật khẩu" required>
	                <button type="submit" class="btn">Đăng nhập</button>
	                <p>Chưa có tài khoản? <a href="#" id="openRegister">Đăng ký ngay</a></p>
	            </form>
	        </div>
	        <div class="form-box" id="registerBox" style="display:none;">
	            <h2>Đăng ký</h2>
	            <form action="<c:url value='/RegisterServlet' />" method="post">
	                <input type="text" name="username" placeholder="Tên đăng nhập" required>
	                <input type="email" name="email" placeholder="Email" required>
	                <input type="password" name="password" placeholder="Mật khẩu" required>
	                <input type="password" name="confirmPassword" placeholder="Xác nhận mật khẩu" required>
	                <div class="error-message" id="registerError"></div>
	                <button type="submit" class="btn">Đăng ký</button>
	                <p>Đã có tài khoản? <a href="#" id="openLoginForm">Đăng nhập</a></p>
	            </form>
	        </div>
	    </div>
	</div>

<script src="<c:url value='/js/login.js' />"></script>

