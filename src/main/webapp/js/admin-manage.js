/**
 * 
 */
/**
 * Hàm tìm kiếm nhanh trong bảng đơn hàng
 */
function searchTable() {
    let input = document.getElementById("searchInput").value.toUpperCase();
    let table = document.getElementById("bookingTable");
    let tr = table.getElementsByTagName("tr");

    for (let i = 1; i < tr.length; i++) {
        let textContent = tr[i].textContent || tr[i].innerText;
        tr[i].style.display = textContent.toUpperCase().includes(input) ? "" : "none";
    }
}

/**
 * Hàm xác nhận và gửi yêu cầu cập nhật trạng thái
 */
function updateStatus(id, status, contextPath) {
    const statusVN = status === 'COMPLETED' ? 'Hoàn thành' : 'Hủy';
    if(confirm(`Xác nhận thay đổi đơn #${id} sang trạng thái: ${statusVN}?`)) {
        window.location.href = contextPath + '/admin/update-status?id=' + id + '&status=' + status;
    }
}

/**
 * Toggle menu hành động
 */
function toggleMenu(btn) {
    const content = btn.nextElementSibling;
    document.querySelectorAll('.dropdown-content').forEach(el => {
        if (el !== content) el.classList.remove('show');
    });
    content.classList.toggle('show');
}