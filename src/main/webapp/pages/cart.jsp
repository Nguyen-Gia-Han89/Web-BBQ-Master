<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Booking, model.BookingDetail"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<!DOCTYPE html>
<html lang="vi">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>BBQ Master - Giỏ hàng</title>
	<link rel="stylesheet" href="<c:url value='/css/base.css'/>">
	<link rel="stylesheet" href="<c:url value='/css/header.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/cart.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/footer.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/responsive.css'/>">
    
	<link rel="stylesheet"
			href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
		
</head>

<body>
	<!-- Header -->
	<jsp:include page="../includes/header.jsp" />

	<section class="cart">
		<div class="container">
			<h2>Giỏ hàng của bạn</h2>

			<jsp:useBean id="cart" class="model.Booking" scope="session"/>
			<%
    BookingDetail[] items = cart.getBookingDetailsArray();
%>

<table class="cart-table">
    <thead>
        <tr>
            <th>Sản phẩm</th>
            <th>Đơn giá</th>
            <th>Số lượng</th>
            <th>Số tiền</th>
            <th>Thao tác</th>
        </tr>
    </thead>
    <tbody>
        <%
            for(BookingDetail item : cart.getBookingDetails()) {
        %>
        <tr>
            <td>
                <img src="<%= item.getDish().getImageUrl() %>" alt="" style="width:50px;">
                <%= item.getDish().getName() %>
            </td>
            <td><%= item.getPrice() %> đ</td>
            <td>
                <form action="update-cart" method="post">
                    <input type="hidden" name="dishId" value="<%= item.getDish().getDishId() %>">
                    <button type="submit" name="action" value="minus">-</button>
                    <input type="text" name="quantity" value="<%= item.getQuantity() %>" size="2">
                    <button type="submit" name="action" value="plus">+</button>
                </form>
            </td>
            <td><%= item.getTotal() %> đ</td>
            <td>
                <form action="remove-from-cart" method="post">
                    <input type="hidden" name="dishId" value="<%= item.getDish().getDishId() %>">
                    <button type="submit">Xóa</button>
                </form>
            </td>
        </tr>
        <%
            }
            if(cart.getBookingDetails().isEmpty()){
        %>
        <tr>
            <td colspan="5" style="text-align:center;">Không có sản phẩm nào trong giỏ hàng</td>
        </tr>
        <%
            }
        %>
    </tbody>
    <tfoot>
        <tr>
            <td colspan="3">Tổng cộng</td>
            <td colspan="2"><%= cart.getTotalAmount() %> đ</td>
        </tr>
    </tfoot>
</table>


		</div>
	</section>


	<!-- Footer -->
	<jsp:include page="../includes/footer.jsp"></jsp:include>

	<script src="js/main.js"></script>
</body>
</html>