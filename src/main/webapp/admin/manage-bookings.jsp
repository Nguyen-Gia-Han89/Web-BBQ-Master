<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý đơn đặt bàn | BBQ Master Admin</title>
    <link rel="stylesheet" href="<c:url value='/css/admin.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/admin-manage.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <div class="admin-container">
        <aside class="admin-sidebar">
            <div class="sidebar-header"><i class="fa-solid fa-fire-burner"></i> BBQ MASTER</div>
            <nav class="sidebar-nav">
                <ul>
                    <li class="${param.view != 'bookings' ? 'active' : ''}">
                        <a href="${pageContext.request.contextPath}/admin/dashboard"><i class="fa-solid fa-chart-pie"></i> Bảng điều khiển</a>
                    </li>
                    <li class="${param.view == 'bookings' ? 'active' : ''}">
                        <a href="${pageContext.request.contextPath}/admin/manage-bookings?view=bookings"><i class="fa-solid fa-calendar-days"></i> Quản lý đơn đặt bàn</a>
                    </li>
                </ul>
            </nav>
            <div class="sidebar-footer">
                <a href="<c:url value='/logout'/>" class="logout-link"><i class="fa-solid fa-right-from-bracket"></i> Đăng xuất</a>
            </div>
        </aside>

        <main class="admin-content">
            <header class="content-header">
                <div class="header-title">
                    <h1>Danh sách đơn đặt bàn</h1>
                    <p>Tổng hợp tất cả giao dịch hệ thống</p>
                </div>
                <div class="admin-user-card">
                    <div class="user-info">
                        <span class="user-name">${sessionScope.customer.fullName}</span>
                        <span class="user-status">● Trực tuyến</span>
                    </div>
                    <img src="https://ui-avatars.com/api/?name=${sessionScope.customer.fullName}&background=ff4757&color=fff&bold=true" alt="Avatar" class="user-avatar">
                </div>
            </header>

            <div class="search-container">
                <input type="text" id="searchInput" onkeyup="searchTable()" placeholder="Tìm mã đơn, tên khách..." class="search-input">
            </div>

            <div class="table-container">
                <table class="admin-table" id="bookingTable">
                    <thead>
					    <tr>
					        <th>MÃ ĐƠN</th>
					        <th>KHÁCH HÀNG</th>
					        <th>SỐ KHÁCH</th>
					        <th>TRẠNG THÁI</th>
					        <th class="text-right">BÀN & THAO TÁC</th> </tr>
					</thead>
					
					<tbody>
					    <c:forEach var="b" items="${allBookings}">
					        <tr>
					            <td><strong>#${b.orderCode}</strong></td>
					            <td>
					                <div class="cust-name">${b.customer.fullName}</div>
					                <small>${b.bookingTime}</small>
					            </td>
					            <td>${b.numberOfGuests} khách</td>
					            <td>
					                <span class="status-badge ${b.status.name().toLowerCase()}">
					                    ${b.status}
					                </span>
					            </td>
					            <td class="text-right">
					                <div class="table-action-wrapper" style="display: flex; align-items: center; justify-content: flex-end; gap: 12px;">
					                    <span class="table-tag">
					                        <i class="fa-solid fa-chair"></i> ${b.table.tableName}
					                    </span>
					
					                    <div class="action-dropdown">
					                        <button class="btn-more" onclick="toggleMenu(this)">
					                            <i class="fa-solid fa-ellipsis-vertical"></i>
					                        </button>
					                        <div class="dropdown-content">
					                            <a href="javascript:void(0)" onclick="updateStatus(${b.bookingId}, 'COMPLETED', '${pageContext.request.contextPath}')">
					                                <i class="fa-solid fa-check"></i> Hoàn thành
					                            </a>
					                            <a href="javascript:void(0)" onclick="updateStatus(${b.bookingId}, 'CANCELLED', '${pageContext.request.contextPath}')" class="text-danger">
					                                <i class="fa-solid fa-xmark"></i> Hủy đơn
					                            </a>
					                        </div>
					                    </div>
					                </div>
					            </td>
					        </tr>
					    </c:forEach>
					</tbody>
                </table>
            </div>
        </main>
    </div>
    <script src="<c:url value='/js/admin-manage.js'/>"></script>
</body>
</html>