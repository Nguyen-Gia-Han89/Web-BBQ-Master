/**
 * booking-tabs.js - BBQ Master (Fixed)
 */
document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const dateInput = document.getElementById("bookingDate");
    const timeInput = document.getElementById("selectedTime");
    const tableBtns = document.querySelectorAll(".table-btn");
    const tableIdInput = document.getElementById("selectedTable");

    // --- 1. Reload khi đổi Ngày/Giờ ---
    if (dateInput) {
        dateInput.addEventListener("change", () => reloadPage(dateInput.value, timeInput.value));
    }
    document.querySelectorAll(".time-slot-btn").forEach(btn => {
        btn.addEventListener("click", function () {
            reloadPage(dateInput.value, this.getAttribute("data-time"));
        });
    });

    // --- 2. Lọc bàn theo khu vực ---
    const spaceRadios = document.querySelectorAll(".space-radio");
    function filterTables() {
        const selectedRadio = document.querySelector("input[name='spaceId']:checked");
        const selectedId = selectedRadio ? selectedRadio.value : "";
        tableBtns.forEach(btn => {
            btn.style.display = (btn.getAttribute("data-space") === selectedId) ? "inline-block" : "none";
        });
    }
    spaceRadios.forEach(r => r.addEventListener("change", filterTables));
    filterTables();

    // --- 3. Chọn bàn (Logic quan trọng) ---
    tableBtns.forEach(btn => {
        if (!btn.disabled) {
            btn.addEventListener("click", function () {
                tableBtns.forEach(b => b.classList.remove("selected"));
                this.classList.add("selected");
                
                // Cập nhật giá trị vào input ẩn
                tableIdInput.value = this.getAttribute("data-id");
                document.getElementById("selectedTableDisplay").innerText = this.innerText.trim();
            });
        }
    });

    // --- 4. Điều hướng Tab & Setup Click Tab phía trên ---
    setupNavigation();
    setupTabClick();

    // Nếu vừa reload thì nhảy sang Tab 2 (Chọn bàn)
    if (urlParams.has("date") || urlParams.has("time")) {
        showTab("tab2");
    }
});

/**
 * Xử lý bấm trực tiếp vào các Tab trên Header
 */
function setupTabClick() {
    document.querySelectorAll(".tab").forEach(tabBtn => {
        tabBtn.addEventListener("click", function() {
            const targetTabId = this.getAttribute("data-tab");
            const currentTabId = document.querySelector(".tab-content.active").id;

            // Nếu muốn nhảy tới Tab 2 hoặc 3, phải qua kiểm tra
            if (targetTabId === "tab2") {
                if (validateContact()) showTab("tab2");
            } else if (targetTabId === "tab3") {
                if (validateContact() && validateTable()) showTab("tab3");
            } else {
                showTab("tab1"); // Quay về tab 1 thì luôn cho phép
            }
        });
    });
}

/**
 * Kiểm tra xem đã chọn bàn chưa
 */
function validateTable() {
    const tableId = document.getElementById("selectedTable").value;
    if (!tableId) {
        alert("Vui lòng chọn một bàn còn trống trước khi tiếp tục!");
        return false;
    }
    return true;
}

/**
 * Logic nút Tiếp tục / Quay lại
 */
function setupNavigation() {
    document.querySelectorAll(".btn-next").forEach(btn => {
        btn.addEventListener("click", function () {
            const activeTabId = document.querySelector(".tab-content.active").id;
            
            if (activeTabId === "tab1") {
                if (validateContact()) showTab("tab2");
            } else if (activeTabId === "tab2") {
                if (validateTable()) showTab("tab3"); // Chỉ đổi tab khi đã chọn bàn
            }
        });
    });

    document.querySelectorAll(".btn-back").forEach(btn => {
        btn.addEventListener("click", function () {
            const activeTabId = document.querySelector(".tab-content.active").id;
            if (activeTabId === "tab3") showTab("tab2");
            else if (activeTabId === "tab2") showTab("tab1");
        });
    });
}

function showTab(tabId) {
    // Active tiêu đề tab
    document.querySelectorAll(".tab").forEach(t => {
        t.classList.toggle("active", t.getAttribute("data-tab") === tabId);
    });
    // Hiển thị nội dung tab
    document.querySelectorAll(".tab-content").forEach(c => {
        const isActive = (c.id === tabId);
        c.style.display = isActive ? "block" : "none";
        c.classList.toggle("active", isActive);
    });
}

function reloadPage(date, time) {
    const cp = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 1));
    window.location.href = cp + "/booking-table?date=" + date + "&time=" + time;
}

// Giả định hàm validateContact của bạn đã có, nếu chưa hãy đảm bảo nó return true/false
function validateContact() {
    // Logic validate tên, sđt...
    return true; 
}