/**
 * 
 */
/**
 * Quản lý logic hiển thị cho Dashboard Admin
 */
document.addEventListener('DOMContentLoaded', function() {
    // 1. Hiển thị ngày tháng hiện tại (Tiếng Việt)
    const dateDisplay = document.getElementById('date-display');
    if (dateDisplay) {
        const options = { 
            weekday: 'long', 
            year: 'numeric', 
            month: 'long', 
            day: 'numeric' 
        };
        const now = new Date();
        dateDisplay.innerHTML = now.toLocaleDateString('vi-VN', options);
    }

    // 2. Logic bổ sung (nếu cần trong tương lai)
    // Ví dụ: Xử lý sự kiện khi bấm vào nút "Hành động"
    const actionButtons = document.querySelectorAll('.btn-more');
    actionButtons.forEach(btn => {
        btn.addEventListener('click', function() {
            console.log('Bấm vào nút thao tác đơn hàng');
            // Bạn có thể thêm logic mở menu ngữ cảnh ở đây
        });
    });
});
// Đóng menu nếu bấm ra ngoài vùng menu
window.onclick = function(event) {
    if (!event.target.matches('.btn-more') && !event.target.matches('.fa-ellipsis-vertical')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        for (var i = 0; i < dropdowns.length; i++) {
            dropdowns[i].classList.remove('show');
        }
    }
}

// Hàm đóng/mở menu
function toggleMenu(btn) {
    // Đóng tất cả các menu khác đang mở
    var dropdowns = document.getElementsByClassName("dropdown-content");
    for (var i = 0; i < dropdowns.length; i++) {
        if (dropdowns[i] !== btn.nextElementSibling) {
            dropdowns[i].classList.remove('show');
        }
    }
    // Mở menu của nút vừa bấm
    btn.nextElementSibling.classList.toggle("show");
}

// Hàm cập nhật trạng thái đơn hàng (Dùng AJAX hoặc chuyển hướng)
function updateStatus(bookingId, status) {
    let message = status === 'COMPLETED' 
        ? "Xác nhận hoàn thành và thanh toán cho đơn #" + bookingId + "?" 
        : "Bạn có chắc chắn muốn HỦY đơn #" + bookingId + "?";

    if (confirm(message)) {
        // Gửi lệnh đến Servlet
        // Lưu ý: path phải khớp với @WebServlet("/admin/UpdateBookingStatus")
        window.location.href = "UpdateBookingStatus?id=" + bookingId + "&status=" + status;
    }
}

function viewDetail(bookingId) {
    alert("Chức năng xem chi tiết đơn hàng #" + bookingId + " đang được phát triển!");
}