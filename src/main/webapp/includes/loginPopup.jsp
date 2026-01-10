<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<div class="login-modal" id="loginModal">
	    <div class="modal-content">
	        <span class="close">&times;</span>
	        
	        <div class="form-box" id="loginBox">
			    <h2>Đăng nhập</h2>
			    <form action="<c:url value='/login' />" method="post">
			        <input type="text" id="loginEmail" name="email" placeholder="Email" autocomplete="username" required>
			        <input type="password" id="loginPass" name="password" placeholder="Mật khẩu" autocomplete="current-password" required>
			        <button type="submit" class="btn">Đăng nhập</button>
			        <p>Chưa có tài khoản? <a href="#" id="openRegister">Đăng ký ngay</a></p>
			    </form>
			</div>
	
	        <div class="form-box" id="registerBox" style="display: none;">
	            <h2>Đăng ký</h2>
	            <form action="${pageContext.request.contextPath}/RegisterServlet" method="post">
	                <input type="text" name="fullName" placeholder="Họ và tên" required>
	                <input type="text" name="phoneNumber" placeholder="Số điện thoại" required>
	                <input type="email" name="email" placeholder="Email" required>
	                <input type="password" name="password" id="password" placeholder="Mật khẩu" required>
	                <input type="password" name="confirmPassword" id="confirmPassword" placeholder="Xác nhận mật khẩu" required>
	                
	                <div id="registerError" style="color: red; font-size: 14px; margin-bottom: 10px;"></div>
	                
	                <button type="submit" class="btn">Đăng ký</button>
	                <p>Đã có tài khoản? <a href="#" id="openLoginForm">Đăng nhập</a></p>
	            </form>
	        </div>
	    </div>
	</div>


