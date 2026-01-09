<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tổng quan | BBQ Master Admin</title>
    <link rel="stylesheet" href="<c:url value='/css/admin.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <div class="admin-container">
        <aside class="admin-sidebar">
            <div class="sidebar-header">
                <i class="fa-solid fa-fire-burner"></i> BBQ MASTER
            </div>
            <nav class="sidebar-nav">
			    <ul>
			        <li class="${empty param.view || param.view == 'dashboard' ? 'active' : ''}">
			            <a href="${pageContext.request.contextPath}/admin/dashboard"><i class="fa-solid fa-chart-pie"></i> Bảng điều khiển</a>
			        </li>
			        <li class="${param.view == 'bookings' ? 'active' : ''}">
			            <a href="${pageContext.request.contextPath}/admin/manage-bookings?view=bookings">
			                <i class="fa-solid fa-calendar-days"></i> Quản lý đơn đặt bàn
			            </a>
			        </li>
			    </ul>
			</nav>
            <div class="sidebar-footer">
                <a href="<c:url value='/logout'/>" class="logout-link">
                    <i class="fa-solid fa-right-from-bracket"></i> Đăng xuất
                </a>
            </div>
        </aside>

        <main class="admin-content">
            <header class="content-header">
                <div class="header-title">
                    <h1>Hệ Thống Quản Trị</h1>
                    <p id="date-display"></p>
                </div>
                
                <div class="admin-user-card">
                    <div class="user-info">
                        <span class="user-name">${sessionScope.customer.fullName}</span>
                        <span class="user-status">● Trực tuyến</span>
                    </div>
                    <img src="https://ui-avatars.com/api/?name=${sessionScope.customer.fullName}&background=ff4757&color=fff&bold=true" 
                         alt="Avatar" class="user-avatar">
                </div>
            </header>
            
            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-icon icon-user"><i class="fa-solid fa-user-group"></i></div>
                    <div class="stat-detail">
                        <span class="label">KHÁCH HÀNG</span>
                        <strong class="value">${totalCustomers}</strong>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon icon-booking"><i class="fa-solid fa-calendar-check"></i></div>
                    <div class="stat-detail">
                        <span class="label">LƯỢT ĐẶT BÀN</span>
                        <strong class="value">${totalBookings}</strong>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon icon-revenue"><i class="fa-solid fa-money-bill-trend-up"></i></div>
                    <div class="stat-detail">
                        <span class="label">DOANH THU</span>
                        <strong class="value"><fmt:formatNumber value="${revenue}" pattern="#,###" /> <small>đ</small></strong>
                    </div>
                </div>
            </div>

            <section class="recent-bookings">
                <div class="section-header">
                    <h2>Giao dịch gần đây</h2>
                    <span class="update-tag">Cập nhật tự động</span>
                </div>
                
                <div class="table-container">
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <th>MÃ ĐƠN</th>
                                <th>KHÁCH HÀNG</th>
                                <th>SỐ KHÁCH</th>
                                <th>TRẠNG THÁI</th>
                                <th class="text-right">SỐ BÀN</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="b" items="${recentBookings}">
                                <tr>
                                    <td><span class="booking-id">#${b.orderCode}</span></td>
                                    <td>
                                        <div class="cust-name">${b.customer.fullName}</div>
                                        <div class="cust-time">${b.bookingTime}</div>
                                    </td>
                                    <td><span class="guest-count">${b.numberOfGuests} khách</span></td>
                                    <td>
                                        <span class="status-badge ${b.status.name().toLowerCase()}">
                                            ${b.status}
                                        </span>
                                    </td>
                                    
                                    <td class="text-right">
									    <div class="table-action-wrapper" style="display: flex; align-items: center; justify-content: flex-end; gap: 15px;">
									        <span class="table-tag">
									            <i class="fa-solid fa-chair"></i> ${b.table.tableName}
									        </span>
									
									        <div class="action-dropdown">
									            <button class="btn-more" onclick="toggleMenu(this)">
									                <i class="fa-solid fa-ellipsis-vertical"></i>
									            </button>
									            <div class="dropdown-content">
									                <a href="javascript:void(0)" onclick="updateStatus(${b.bookingId}, 'COMPLETED')">
									                    <i class="fa-solid fa-check-double"></i> Hoàn thành
									                </a>
									                <a href="javascript:void(0)" onclick="viewDetail(${b.bookingId})">
									                    <i class="fa-solid fa-eye"></i> Chi tiết
									                </a>
									                <a href="javascript:void(0)" onclick="updateStatus(${b.bookingId}, 'CANCELLED')" class="text-danger">
									                    <i class="fa-solid fa-xmark"></i> Hủy đơn
									                </a>
									            </div>
									        </div>
									    </div>
									</td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty recentBookings}">
                                <tr>
                                    <td colspan="5" style="text-align: center; padding: 30px; color: #a4b0be;">
                                        Không có giao dịch nào gần đây.
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </section>
        </main>
    </div>

   	<script src="<c:url value='/js/admin-dashboard.js'/>"></script>
</body>
</html>