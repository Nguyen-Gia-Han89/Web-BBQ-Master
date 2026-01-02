<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Ưu đãi - BBQ Master</title>

    <!-- CSS chung -->
    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/header.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/footer.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/responsive.css'/>">

    <!-- CSS riêng -->
    <link rel="stylesheet" href="<c:url value='/css/promotion.css'/>">

    <!-- Icon -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>

    <!-- Font -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
</head>

<body>

<!-- Header -->
<jsp:include page="/includes/header.jsp"/>

<!-- Banner Ưu Đãi -->
<section class="promo-banner">
    <div class="banner-container">
        <div class="banner-image">
            <img src="https://atuankhang.vn/wp-content/uploads/2020/05/cach-uop-thit-bo-nuong-ngon-nhat.jpg"
                 alt="Thịt nướng bò">
        </div>
        <div class="banner-text">
            <h2>Siêu Ưu Đãi: Giảm 30% Cho Thịt Nướng Bò!</h2>
            <p>
                Thịt bò tươi ngon, nướng vàng giòn với gia vị đặc biệt.
                Áp dụng cho đơn hàng từ 500k.
            </p>
            <span class="urgency">
                <i class="fa-regular fa-calendar"></i>
                Kết thúc: 15/12/2023
            </span>
            <a href="#" class="banner-btn">Áp dụng ngay</a>
        </div>
    </div>
</section>

<!-- CONTENT -->
<section class="promotion-content">
    <div class="container">
        <div class="promotion-grid">

            <!-- Ưu đãi 1 -->
            <article class="promo-card">
                <div class="promo-image">
                    <img src="https://static.vinwonders.com/production/quan-nuong-nha-trang-1.jpg"
                         alt="Thịt nướng bò">
                    <span class="promo-badge">-30%</span>
                </div>
                <div class="promo-body">
                    <h3>Happy Hour</h3>
                    <p>
                        Thịt bò tươi ngon, nướng vàng giòn với gia vị đặc biệt.
                        Áp dụng cho đơn hàng từ 500k.
                    </p>
                    <div class="promo-info">
                        <span>
                            <i class="fa-regular fa-calendar"></i>
                            15/12/2023
                        </span>
                        <span class="status active">Đang diễn ra</span>
                    </div>
                    <a href="#" class="promo-btn">Áp dụng ngay</a>
                </div>
            </article>

            <!-- Ưu đãi 2 -->
            <article class="promo-card">
                <div class="promo-image">
                    <img src="https://statics.vincom.com.vn/xu-huong/chi_tiet_xu_huong/buffet-lau-nuong-da-dang-hap-dan-1649053624.jpg"
                         alt="Combo Cặp Đôi">
                    <span class="promo-badge">-20%</span>
                </div>
                <div class="promo-body">
                    <h3>Combo Cặp Đôi</h3>
                    <p>
                       Không gian lãng mạn + menu đặc biệt cho 2 người
                    </p>
                    <div class="promo-info">
                        <span>
                            <i class="fa-regular fa-calendar"></i>
                            20/12/2023
                        </span>
                        <span class="status active">Đang diễn ra</span>
                    </div>
                    <a href="#" class="promo-btn">Áp dụng ngay</a>
                </div>
            </article>

            <!-- Ưu đãi 3 -->
            <article class="promo-card">
                <div class="promo-image">
                    <img src="https://hotdeal.vn/images/uploads/2016/Thang%2011/18/306310/306310-combo-lau-nuong-nha-hang-lau-ngon-124-lang-ha-body-1.jpg"
                         alt="Gia vị BBQ">
                    <span class="promo-badge">-15%</span>
                </div>
                <div class="promo-body">
                    <h3>Combo Gia Đình</h3>
                    <p>
                        Giảm 15% cuối tuần
                    </p>
                    <div class="promo-info">
                        <span>
                            <i class="fa-regular fa-calendar"></i>
                            10/12/2023
                        </span>
                        <span class="status expired">Đã hết hạn</span>
                    </div>
                    <a href="#" class="promo-btn">Xem chi tiết</a>
                </div>
            </article>

        </div>
    </div>
</section>

<!-- Footer -->
<jsp:include page="/includes/footer.jsp"/>

</body>
</html>
