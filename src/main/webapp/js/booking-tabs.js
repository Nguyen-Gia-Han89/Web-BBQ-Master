/**
 * booking-tabs.js
 * BBQ Master - Logic điều hướng và lọc sơ đồ bàn theo khu vực
 */
document.addEventListener("DOMContentLoaded", function () {
    // 1. KHỞI TẠO GIÁ TRỊ MẶC ĐỊNH
    initDefaultValues();

    // 2. CHỌN GIỜ (DẠNG NÚT BẤM)
    var timeBtns = document.querySelectorAll(".time-slot-btn");
    var timeInput = document.getElementById("selectedTime");
    
    timeBtns.forEach(function (btn) {
        btn.addEventListener("click", function () {
            timeBtns.forEach(function (b) { b.classList.remove("active"); });
            btn.classList.add("active");
            if (timeInput) timeInput.value = btn.dataset.time;
        });
    });

    // 3. CHỌN KHÔNG GIAN & LỌC SƠ ĐỒ BÀN (FIX LỖI HIỂN THỊ TẤT CẢ)
    var spaceRadios = document.querySelectorAll(".space-radio");
    var tableBtns = document.querySelectorAll(".table-btn");

    function filterTablesBySpace() {
        // Lấy radio khu vực đang được chọn (Tầng 1, Tầng 2, Sân vườn)
        var selectedRadio = document.querySelector('input[name="spaceId"]:checked');
        var selectedSpaceId = selectedRadio ? selectedRadio.value.toString().trim() : "";
        
        tableBtns.forEach(function (btn) {
            // Lấy mã không gian từ data-space của nút bàn trong mã HTML của bạn
            var btnSpaceId = btn.getAttribute("data-space") ? btn.getAttribute("data-space").toString().trim() : "";
            
            if (btnSpaceId === selectedSpaceId && selectedSpaceId !== "") {
                // Hiển thị bàn đúng khu vực
                btn.style.setProperty("display", "inline-block", "important");
            } else {
                // Ẩn các bàn không thuộc khu vực
                btn.style.setProperty("display", "none", "important");
                
                // Nếu bàn đang chọn bỗng nhiên bị ẩn (do đổi khu vực) thì reset chọn bàn
                if (btn.classList.contains("selected")) {
                    btn.classList.remove("selected");
                    if (document.getElementById("selectedTable")) document.getElementById("selectedTable").value = "";
                    if (document.getElementById("selectedTableDisplay")) document.getElementById("selectedTableDisplay").innerText = "Chưa chọn";
                }
            }
        });
    }

    // Chạy lọc ngay lập tức để khớp với khu vực mặc định khi load trang
    filterTablesBySpace();

    // Lắng nghe khi khách hàng đổi khu vực
    spaceRadios.forEach(function (radio) {
        radio.addEventListener("change", filterTablesBySpace);
    });

    // 4. CHỌN BÀN CỤ THỂ TRÊN SƠ ĐỒ
    var selectedTableInput = document.getElementById("selectedTable");
    var selectedTableDisplay = document.getElementById("selectedTableDisplay");

    tableBtns.forEach(function (btn) {
        btn.addEventListener("click", function () {
            // Xóa trạng thái chọn của các bàn khác
            tableBtns.forEach(function (b) { b.classList.remove("selected"); });
            
            // Đánh dấu bàn hiện tại
            btn.classList.add("selected");
            
            // Lưu ID vào input ẩn để Servlet xử lý (tableId)
            if (selectedTableInput) selectedTableInput.value = btn.getAttribute("data-id");
            
            // Hiển thị tên bàn lên giao diện (Ví dụ: A01 (4 người))
            if (selectedTableDisplay) selectedTableDisplay.innerText = btn.innerText.replace(/\s+/g, ' ').trim();
        });
    });

    // 5. ĐIỀU HƯỚNG TAB & KIỂM TRA DỮ LIỆU
    var tabs = document.querySelectorAll(".tab");
    tabs.forEach(function (tab) {
        tab.style.cursor = "pointer";
        tab.addEventListener("click", function () {
            var targetTabId = tab.getAttribute("data-tab");

            if (targetTabId !== "tab1" && !validateContactInfo()) return;

            // Nếu sang Tab xác nhận (tab3), phải chọn bàn trước
            if (targetTabId === "tab3" && document.getElementById("selectedTable")) {
                if (!document.getElementById("selectedTable").value) {
                    alert("Vui lòng chọn một bàn cụ thể!");
                    showTab("tab2");
                    return;
                }
            }
            showTab(targetTabId);
        });
    });

    // Nút Tiếp tục
    document.querySelectorAll(".btn-next").forEach(function (btn) {
        btn.addEventListener("click", function () {
            var currentTab = document.querySelector(".tab-content.active");
            var currentTabId = currentTab ? currentTab.id : "";
            
            if (currentTabId === "tab1") {
                if (validateContactInfo()) showTab("tab2");
            } else if (currentTabId === "tab2") {
                if (selectedTableInput && !selectedTableInput.value) {
                    alert("Bạn chưa chọn bàn nào trên sơ đồ!");
                } else {
                    showTab("tab3");
                }
            }
            window.scrollTo(0, 0);
        });
    });

    // Nút Quay lại
    document.querySelectorAll(".btn-back").forEach(function (btn) {
        btn.addEventListener("click", function () {
            var currentTabId = document.querySelector(".tab-content.active").id;
            if (currentTabId === "tab3") showTab("tab2");
            else if (currentTabId === "tab2") showTab("tab1");
            window.scrollTo(0, 0);
        });
    });
});

/**
 * Khởi tạo Ngày/Giờ mặc định
 */
function initDefaultValues() {
    var dateInput = document.getElementById('bookingDate');
    if (dateInput && !dateInput.value) {
        var today = new Date();
        var yyyy = today.getFullYear();
        var mm = String(today.getMonth() + 1).padStart(2, '0');
        var dd = String(today.getDate()).padStart(2, '0');
        var formattedToday = yyyy + "-" + mm + "-" + dd;
        dateInput.value = formattedToday;
        dateInput.min = formattedToday;
    }

    var firstTimeBtn = document.querySelector(".time-slot-btn");
    var timeInput = document.getElementById("selectedTime");
    if (firstTimeBtn && timeInput) {
        firstTimeBtn.classList.add("active");
        timeInput.value = firstTimeBtn.dataset.time;
    }
}

/**
 * Kiểm tra thông tin liên hệ
 */
function validateContactInfo() {
    // Lấy phần tử trước
    var nameInput = document.querySelector('input[name="name"]');
    var phoneInput = document.querySelector('input[name="phone"]');
    
    // Kiểm tra phần tử tồn tại rồi mới lấy giá trị .value
    var name = nameInput ? nameInput.value.trim() : "";
    var phone = phoneInput ? phoneInput.value.trim() : "";
    
    if (!name || !phone) {
        alert("Vui lòng nhập đầy đủ Họ tên và Số điện thoại!");
        return false;
    }
    return true;
}

/**
 * Hiển thị Tab
 */
function showTab(tabId) {
    document.querySelectorAll(".tab").forEach(function (t) {
        t.classList.toggle("active", t.getAttribute("data-tab") === tabId);
    });

    document.querySelectorAll(".tab-content").forEach(function (content) {
        if (content.id === tabId) {
            content.style.display = "block";
            content.classList.add("active");
        } else {
            content.style.display = "none";
            content.classList.remove("active");
        }
    });

    if (typeof calculateGrandTotal === "function") {
        calculateGrandTotal();
    }
}