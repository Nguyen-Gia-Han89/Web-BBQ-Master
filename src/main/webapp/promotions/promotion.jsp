<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Ưu đãi - BBQ Master</title>

    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/header.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/footer.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/responsive.css'/>">

    <link rel="stylesheet" href="<c:url value='/css/promotion.css'/>">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
</head>

<body>

<jsp:include page="/includes/header.jsp"/>

<c:if test="${not empty promotions}">
    <c:set var="b" value="${promotions[0]}" />
    <section class="promo-banner">
        <div class="banner-container">
            <div class="banner-image">
                <img src="${b.imageUrl}" alt="${b.promoName}">
            </div>
            <div class="banner-text">
                <h2>${b.promoName}</h2>
                <p>${b.description}</p>
                <span class="urgency">
                    <i class="fa-regular fa-calendar"></i>
                    Kết thúc: <fmt:formatDate value="${b.endDate}" pattern="dd/MM/yyyy"/>
                </span>
            </div>
        </div>
    </section>
</c:if>

<section class="promotion-content">
    <div class="container">
        <div class="promotion-grid">

            <%-- Duyệt danh sách promotions từ Servlet --%>
            <c:forEach var="p" items="${promotions}">
                <article class="promo-card">
                    <div class="promo-image">
                        <img src="${p.imageUrl}" alt="${p.promoName}">
                        <span class="promo-badge">-<fmt:formatNumber value="${p.discountPercent}" maxFractionDigits="0"/>%</span>
                    </div>
                    <div class="promo-body">
                        <h3>${p.promoName}</h3>
                        <p>${p.description}</p>
                        <div class="promo-info">
                            <span>
                                <i class="fa-regular fa-calendar"></i>
                                <fmt:formatDate value="${p.endDate}" pattern="dd/MM/yyyy"/>
                            </span>
                            
                            <%-- Hiển thị status dựa trên dữ liệu --%>
                            <c:choose>
                                <c:when test="${p.status eq 'Active'}">
                                    <span class="status active">Đang diễn ra</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="status expired">Đã hết hạn</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </article>
            </c:forEach>

            <c:if test="${empty promotions}">
                <p>Không có chương trình ưu đãi nào để hiển thị.</p>
            </c:if>

        </div>
    </div>
</section>

<jsp:include page="/includes/footer.jsp"/>

</body>
</html>