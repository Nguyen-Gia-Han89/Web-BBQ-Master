<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="login-modal" id="loginModal" style="display:block;">
    <div class="modal-content">
        <span class="close" onclick="window.location='index.jsp'">&times;</span>
        <h2>Đăng nhập</h2>
        <form action="LoginServlet" method="post">
            <input type="email" name="email" placeholder="Email" required>
            <input type="password" name="password" placeholder="Mật khẩu" required>
            <button type="submit" class="btn">Đăng nhập</button>
            <p>Chưa có tài khoản? 
                <a href="AuthServlet?action=register">Đăng ký ngay</a>
            </p>
        </form>
    </div>
</div>

<link rel="stylesheet" href="css/style.css">