<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BBQ Master - Trang chủ</title>

    <!-- CSS -->
    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/header.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/home.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/footer.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/responsive.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body>
    <!-- Header -->
    <jsp:include page="includes/header.jsp" />

    <!-- Hero -->
    <section class="hero">
        <div class="hero-content">
            <h1><span class="bbq">BBQ Master</span></h1>
            <p>Trải nghiệm BBQ ngoài trời tuyệt vời nhất</p>
            <button class="btn btn-primary" onclick="location.href='<c:url value='/pages/book-table.jsp'/>'">Đặt bàn ngay</button>
            <button class="btn btn-primary" onclick="location.href='<c:url value='/pages/menu.jsp'/>'">Xem thực đơn</button>
        </div>
    </section>

    <!-- Ưu đãi nổi bật -->
    <section class="promotion">
        <div class="container">
            <h2>Ưu đãi nổi bật</h2>
            <div class="promo-row">
                <jsp:useBean id="promoList" class="beans.PromotionList" scope="session" />
                <c:forEach var="promo" items="${promoList.promotions}">
                    <div class="promo-card" onclick="this.classList.toggle('active')">
                        <img src="${promo.imageUrl}" alt="${promo.promoName}" />
                        <div class="promo-info">
                            <h3>${promo.promoName}</h3>
                            <div class="details">
                                <p>${promo.description}</p>
                                <p><strong>Giá giảm tới:</strong> ${promo.discountPercent}%</p>
                                <p><strong>Chỉ từ ngày:</strong> 
                                    <fmt:formatDate value="${promo.startDate}" pattern="dd/MM/yyyy"/> đến 
                                    <fmt:formatDate value="${promo.endDate}" pattern="dd/MM/yyyy"/>
                                </p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>

    <!-- Giới thiệu -->
	<section class="about">
	    <div class="container">
	        <h2>Chào mừng đến với BBQ Master</h2>
	        <p>
	            BBQ Master tự hào mang đến trải nghiệm ẩm thực nướng đậm chất BBQ.
	            Không chỉ là món ăn, chúng tôi tạo ra không gian thoải mái và ấm cúng
	            để bạn tận hưởng cùng gia đình và bạn bè.
	        </p>
	
	        <div class="about-content">
	            <jsp:useBean id="spaceDAO" class="dao.SpaceDAO" scope="request" />
	            <c:forEach var="space" items="${spaceDAO.allSpaces}" varStatus="loop">
	                <div class="about-item ${loop.index % 2 == 1 ? 'reverse' : ''}">
	                    <div class="about-img">
	                        <img src="${space.imageUrl}" alt="${space.name}">
	                    </div>
	                    <div class="about-text">
	                        <h3>${space.name}</h3>
	                        <p>${space.description}</p>
	                    </div>
	                </div>
	            </c:forEach>
	        </div>
	    </div>
	</section>
    <!-- Combo Đề cử -->
    <section class="combo-section">
        <h2>Combo Đề Cử</h2>
        <div class="combo-list">
            <jsp:useBean id="dishList" class="beans.DishList" scope="session" />
            <c:forEach var="dish" items="${dishList.dishes}">
                <c:if test="${dish.combo and dish.available}">
                    <div class="combo-card">
                        <img src="${dish.imageUrl}" alt="${dish.name}">
                        <h3>${dish.name}</h3>
                        <p>${dish.description}</p>
                        <div class="combo-footer">
                            <span class="price"><fmt:formatNumber value="${dish.price}" pattern="#,##0"/>đ</span>
                            <form action="<c:url value='/add-to-cart'/>" method="post">
                                <input type="hidden" name="dishId" value="${dish.dishId}" />
                                <button type="submit" class="btn-order">Đặt ngay</button>
                            </form>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </section>

    <!-- Footer -->
    <jsp:include page="includes/footer.jsp" />

    <script src="<c:url value='/js/main.js'/>"></script>
</body>
</html>
