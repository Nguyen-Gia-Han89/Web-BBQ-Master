<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thực đơn</title>

    <!-- CSS -->
    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/header.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/footer.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/responsive.css'/>">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body>

<jsp:include page="../includes/header.jsp" />

<div class="main-content">
    <div class="menu-container">
        <h2><i class="fas fa-fire"></i> THỰC ĐƠN</h2>
        <p class="intro">
            Thưởng thức những món nướng & lẩu hấp dẫn chuẩn hương vị nhà hàng.
            Chọn món yêu thích và đặt hàng ngay!
        </p>

        <!-- FILTER -->
        <div class="filter-buttons">
            <a href="${pageContext.request.contextPath}/menu?category=all"
               class="filter-btn ${selectedCategory == 'all' ? 'active' : ''}">Tất cả</a>

            <a href="${pageContext.request.contextPath}/menu?category=meat"
               class="filter-btn ${selectedCategory == 'meat' ? 'active' : ''}">Thịt & Hải sản</a>

            <a href="${pageContext.request.contextPath}/menu?category=veg"
               class="filter-btn ${selectedCategory == 'veg' ? 'active' : ''}">Rau củ</a>

            <a href="${pageContext.request.contextPath}/menu?category=soup"
               class="filter-btn ${selectedCategory == 'soup' ? 'active' : ''}">Lẩu</a>

            <a href="${pageContext.request.contextPath}/menu?category=drink"
               class="filter-btn ${selectedCategory == 'drink' ? 'active' : ''}">Thức uống</a>
        </div>

        <!-- DANH SÁCH MÓN -->
        <div class="item-grid">

            <c:if test="${empty foods}">
                <p class="no-items-message">
                    <i class="fas fa-exclamation-triangle"></i>
                    Hiện tại thực đơn chưa có món ăn nào được tải lên.
                </p>
            </c:if>

            <c:if test="${not empty foods}">
                <c:forEach var="dish" items="${foods}">
                    <div class="item-card">

                        <!-- 🔥 FIX ẢNH: LINK ONLINE KHÔNG GHÉP CONTEXTPATH -->
                        <img src="${dish.imageUrl}" alt="${dish.name}">

                        <h4>${dish.name}</h4>
                        <p class="description">${dish.description}</p>

                        <p class="price">
                            <c:choose>
                                <c:when test="${dish.price > 0}">
                                    <fmt:formatNumber value="${dish.price}" pattern="#,##0"/>đ
                                </c:when>
                                <c:otherwise>
                                    (Liên hệ giá)
                                </c:otherwise>
                            </c:choose>
                        </p>

                        <form action="${pageContext.request.contextPath}/cart" method="post" style="display: inline;">
								<input type="hidden" name="dishId" value="${dish.dishId}"> <input
									type="hidden" name="action" value="add">
								<button type="submit" class="order-btn">Đặt món</button>
							</form>
                    </div>
                </c:forEach>
            </c:if>

        </div>
    </div>
</div>

<jsp:include page="../includes/footer.jsp" />

<!-- JS -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="<c:url value='/js/menu.js' />"></script>

</body>
</html>
