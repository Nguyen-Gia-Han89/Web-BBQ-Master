<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

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

            <c:choose>
                <c:when test="${not empty sessionScope.cart.bookingDetails}">
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
                            <c:forEach var="item" items="${sessionScope.cart.bookingDetails}">
                                <tr>
                                    <td>
                                        <img src="${item.dish.imageUrl}" alt="${item.dish.name}" style="width:50px;">
                                        ${item.dish.name}
                                    </td>
                                    <td><fmt:formatNumber value="${item.price}" pattern="#,##0"/> đ</td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/update-cart" method="post" class="qty-form">
                                            <input type="hidden" name="dishId" value="${item.dish.dishId}">
                                            <button type="submit" name="action" value="minus" class="qty-btn">-</button>
                                            <input type="text" name="quantity" value="${item.quantity}" size="2" class="qty-input">
                                            <button type="submit" name="action" value="plus" class="qty-btn">+</button>
                                        </form>
                                    </td>
                                    <td><fmt:formatNumber value="${item.total}" pattern="#,##0"/> đ</td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/remove-cart" method="post" class="remove-form">
                                            <input type="hidden" name="dishId" value="${item.dish.dishId}">
                                            <button type="submit" class="remove-btn">Xóa</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot>
                            <tr>
                                <td colspan="3">Tổng cộng</td>
                                <td colspan="2">
                                    <fmt:formatNumber value="${sessionScope.cart.totalAmount}" pattern="#,##0"/> đ
                                </td>
                            </tr>
                        </tfoot>
                    </table>
                </c:when>
                <c:otherwise>
                    <p class="empty-cart">Không có sản phẩm nào trong giỏ hàng</p>
                </c:otherwise>
            </c:choose>

            <!-- Nút đặt bàn luôn hiển thị -->
            <div class="checkout-btn-container">
                <form action="${pageContext.request.contextPath}/booking-table" method="get">
				    <button type="submit" class="checkout-btn">Đặt bàn ngay</button>
				</form>

            </div>
        </div>
    </section>

    <!-- Footer -->
    <jsp:include page="../includes/footer.jsp" />

    <script src="<c:url value='/js/main.js'/>"></script>
</body>
</html>
