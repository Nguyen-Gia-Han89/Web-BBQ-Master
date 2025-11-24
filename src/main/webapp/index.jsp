<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.time.format.DateTimeFormatter, beans.PromotionList, model.Promotion, beans.DishList" %>

	
<!DOCTYPE html>
<html lang="vi">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>BBQ Master - Trang chủ</title>
	<link rel="stylesheet" href="css/style.css">
	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body>
	<!-- Header -->
	<jsp:include page="includes/header.jsp" />
	<!-- Hero -->
	<section class="hero">
		<div class="hero-content">
			<h1>
				<span class="bbq">BBQ Master</span>
			</h1>
			<p>Trải nghiệm BBQ ngoài trời tuyệt vời nhất</p>
			<button class="btn btn-primary" onclick="window.location.href='datban.jsp'">Đặt bàn ngay</button>
			<button class="btn btn-primary" onclick="window.location.href='thucdon.jsp'">Xem thực đơn</button>
		</div>
	</section>

	<!-- Ưu đãi nổi bật -->
	<section class="promotion">
	  <div class="container">
	    <h2>Ưu đãi nổi bật</h2>
	    <div class="promo-row">
	      <jsp:useBean id="promoList" class="beans.PromotionList" scope="session" />
		      <%
		      		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	                if (promoList != null && promoList.getPromotions() != null) {
	                    for (model.Promotion promo : promoList.getPromotions()) {
	            %>
	                <div class="promo-card" onclick="this.classList.toggle('active')">
	                    <img src="<%=promo.getImageUrl()%>" alt="<%=promo.getPromoName()%>" />
	                    <div class="promo-info">
	                        <h3><%=promo.getPromoName()%></h3>
	                        <div class="details">
	                            <p><%=promo.getDescription()%></p>
	                            <p><strong>Giá giảm tới:</strong> <%=promo.getDiscountPercent()%>%</p>
	                            <p><strong>Chỉ từ ngày:</strong>
	                                 <%=promo.getStartDate().format(formatter)%> đến 
                    					<%=promo.getEndDate().format(formatter)%>
	                            </p>
	                        </div>
	                    </div>
	                </div>
	            <%
	                    }
	                }
	            %>
	        </div>
	    </div>
	</section>



	<!--  Giới thiệu -->
	<section class="about">
		<div class="container">
			<h2>Chào mừng đến với BBQ Master</h2>
			<p>BBQ Master tự hào mang đến cho bạn trải nghiệm ẩm thực nướng
				đậm chất BBQ. Không chỉ là món ăn, chúng tôi tạo ra không gian thoải
				mái và ấm cúng để bạn tận hưởng cùng gia đình và bạn bè.</p>

			<div class="about-content">
				<div class="about-item">
					<div class="about-img">
						<img
							src="https://20sfvn.com/wp-content/uploads/2022/09/thiet-ke-nha-hang-lau-nuong.jpeg"
							alt="Không gian tầng 1">
					</div>
					<div class="about-text">
						<h3>Tầng 1 - Khu thường</h3>
						<ul>
							<li><i class="fa-regular fa-star"></i> Không gian gần gũi</li>
							<li><i class="fa-regular fa-star"></i> Thoải mái, thân mật</li>
							<li><i class="fa-regular fa-star"></i> Thích hợp cho gia
								đình, bạn bè</li>
						</ul>
					</div>
				</div>

				<div class="about-item reverse">
					<div class="about-img">
						<img
							src="https://20sfvn.com/wp-content/uploads/2022/09/thiet-ke-nha-hang-lau-nuong.jpeg"
							alt="Không gian VIP">
					</div>
					<div class="about-text">
						<h3>Tầng 2 - Khu VIP</h3>
						<ul>
							<li><i class="fa-regular fa-star"></i> Riêng tư, yên tĩnh</li>
							<li><i class="fa-regular fa-star"></i> Phù hợp tiếp khách</li>
							<li><i class="fa-regular fa-star"></i> Không gian sang trọng</li>
						</ul>
					</div>
				</div>

				<div class="about-item">
					<div class="about-img">
						<img
							src="https://20sfvn.com/wp-content/uploads/2022/09/thiet-ke-nha-hang-lau-nuong.jpeg"
							alt="Sân vườn BBQ">
					</div>
					<div class="about-text">
						<h3>Sân vườn ngoài trời</h3>
						<ul>
							<li><i class="fa-regular fa-star"></i> Thoáng mát, nhiều cây
								xanh</li>
							<li><i class="fa-regular fa-star"></i> Không khí lãng mạn</li>
							<li><i class="fa-regular fa-star"></i> Thích hợp tiệc ngoài
								trời</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</section>


	<!-- Combo Đề cử -->
	<section class="combo-section">
	    <h2>Combo Đề Cử</h2>
	    <div class="combo-list">
	        <jsp:useBean id="dishList" class="beans.DishList" scope="session" />
	        <%
	            if (dishList != null && dishList.getDishes() != null) {
	                for (model.Dish dish : dishList.getDishes()) {
	                    if(dish.isCombo() && dish.isAvailable()) {
	        %>
	        <div class="combo-card">
	            <img src="<%=dish.getImageUrl()%>" alt="<%=dish.getName()%>">
	            <h3><%=dish.getName()%></h3>
	            <p><%=dish.getDescription()%></p>
	            <div class="combo-footer">
	                <span class="price"><%=String.format("%,.0f", dish.getPrice())%>đ</span>
	                <!-- Form đặt món -->
	                <form action="add-to-cart" method="post">
	                    <input type="hidden" name="dishId" value="<%= dish.getDishId() %>" />
	                    <button type="submit" class="btn-order">Đặt ngay</button>
	                </form>
	            </div>
	        </div>
	        <%
	                    }
	                }
	            }
	        %>
	    </div>
	</section>


	<!-- Footer -->
	<jsp:include page="includes/footer.jsp"></jsp:include>

	<script src="js/main.js"></script>
</body>
</html>