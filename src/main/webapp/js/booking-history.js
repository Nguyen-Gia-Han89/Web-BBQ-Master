/**
 * 
 */
/**
 * Xác nhận hủy đặt bàn trước khi gửi yêu cầu lên Server
 * @param {number} id - ID của đơn đặt bàn
 */
function confirmCancel(id) {
    if (confirm("Xác nhận hủy đơn đặt bàn #" + id + "?")) {
        // Chuyển hướng đến Servlet xử lý hủy
        window.location.href = "cancel-booking?id=" + id;
    }
}

// Bạn có thể thêm các xử lý khác như lọc đơn hàng hoặc phân trang ở đây