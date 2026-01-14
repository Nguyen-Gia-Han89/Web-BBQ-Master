document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    
    // --- 1. Khai báo các phần tử ---
    const dateInput = document.getElementById("bookingDate");
    const guestInput = document.querySelector('input[name="guests"]');
    const tableIdInput = document.getElementById("selectedTable");
    const tableBtns = document.querySelectorAll(".table-btn");
    const timeSlotBtns = document.querySelectorAll(".time-slot-btn");
    const form = document.getElementById("bookingForm") || document.getElementById("partyForm");

    // --- 2. Xử lý Ngày (Chặn quá khứ) ---
    if (dateInput) {
        const today = new Date().toISOString().split('T')[0];
        dateInput.setAttribute("min", today);
        dateInput.addEventListener("change", function() {
            if (this.value < today) {
                alert("Không thể chọn ngày trong quá khứ!");
                this.value = today;
            }
        });
    }

    // --- 3. Chặn Enter & Giới hạn người ---
    if (form) {
        form.addEventListener("keydown", (e) => {
            if (e.key === "Enter") e.preventDefault();
        });
    }

    if (guestInput) {
        guestInput.addEventListener("input", function() {
            const isParty = !!document.getElementById("partyForm");
            if (!isParty && this.value > 10) {
                alert("Bàn thường tối đa 10 người!");
                this.value = 10;
            }
        });
    }

    // --- 4. Xử lý Chọn Giờ (Chỉ Đặt bàn) ---
    timeSlotBtns.forEach(btn => {
        btn.addEventListener("click", function () {
            const selectedDate = dateInput.value;
            if (!selectedDate) { alert("Vui lòng chọn ngày!"); return; }
            reloadPage(selectedDate, this.getAttribute("data-time"));
        });
    });

	// --- Cập nhật trạng thái nút Giờ từ URL ---
	const timeFromUrl = urlParams.get("time");
	if (timeFromUrl) {
	    const timeInput = document.getElementById("selectedTime");
	    if (timeInput) timeInput.value = timeFromUrl; // Lưu vào input ẩn

	    timeSlotBtns.forEach(btn => {
	        if (btn.getAttribute("data-time") === timeFromUrl) {
	            btn.classList.add("active"); // Active lại nút đã chọn
	        }
	    });
	}
	
    // --- 5. Lọc và Chọn bàn (QUAN TRỌNG) ---
    const spaceRadios = document.querySelectorAll(".space-radio");
    function filterTables() {
        const checkedSpace = document.querySelector("input[name='spaceId']:checked");
        if (!checkedSpace || tableBtns.length === 0) return;
        tableBtns.forEach(btn => {
            btn.style.display = (btn.getAttribute("data-space") === checkedSpace.value) ? "inline-block" : "none";
        });
    }
    spaceRadios.forEach(r => r.addEventListener("change", filterTables));
    filterTables(); // Chạy lần đầu khi load

    tableBtns.forEach(btn => {
        if (!btn.disabled) {
            btn.addEventListener("click", function () {
                tableBtns.forEach(b => b.classList.remove("selected"));
                this.classList.add("selected");
                if (tableIdInput) {
                    tableIdInput.value = this.getAttribute("data-id");
                    const display = document.getElementById("selectedTableDisplay");
                    if (display) display.innerText = this.innerText.trim();
                }
            });
        }
    });

    // --- 6. Điều hướng Tab ---
    setupNavigation();
    setupTabClick();

    if (urlParams.has("time") && document.getElementById("tab2")) showTab("tab2");
});

// --- CÁC HÀM LOGIC ---

function setupTabClick() {
    document.querySelectorAll(".tab").forEach(tabBtn => {
        tabBtn.addEventListener("click", function() {
            const target = this.getAttribute("data-tab");
            if (target === "tab2" && validateContact()) showTab("tab2");
            if (target === "tab3" && validateContact() && validateTable()) showTab("tab3");
            if (target === "tab1") showTab("tab1");
        });
    });
}

function setupNavigation() {
    document.querySelectorAll(".btn-next").forEach(btn => {
        btn.addEventListener("click", function () {
            const currentTab = document.querySelector(".tab-content.active").id;
            if (currentTab === "tab1" && validateContact()) showTab("tab2");
            else if (currentTab === "tab2" && document.getElementById("tab3") && validateTable()) showTab("tab3");
        });
    });

    document.querySelectorAll(".btn-back").forEach(btn => {
        btn.addEventListener("click", function () {
            const currentTab = document.querySelector(".tab-content.active").id;
            if (currentTab === "tab3") showTab("tab2");
            else if (currentTab === "tab2") showTab("tab1");
        });
    });
}

function validateContact() {
    const tab1 = document.getElementById("tab1");
    const name = tab1.querySelector('input[name="name"]')?.value.trim();
    const phone = tab1.querySelector('input[name="phone"]')?.value.trim();
    const email = tab1.querySelector('input[name="email"]')?.value.trim();
    const date = tab1.querySelector('input[name="date"]')?.value;

    if (!name || !phone || !email || !date) {
        alert("Vui lòng điền đủ thông tin liên hệ và ngày!");
        return false;
    }
    return true;
}

function validateTable() {
    const tableIdInput = document.getElementById("selectedTable");
    if (!tableIdInput || tableIdInput.value) return true;

    if (confirm("Bạn chưa chọn bàn. Để nhà hàng tự sắp xếp nhé?")) {
        tableIdInput.value = "0";
        const display = document.getElementById("selectedTableDisplay");
        if (display) display.innerText = "Nhân viên tự sắp xếp";
        return true;
    }
    return false;
}

function showTab(tabId) {
    if (!document.getElementById(tabId)) return;
    document.querySelectorAll(".tab").forEach(t => t.classList.toggle("active", t.getAttribute("data-tab") === tabId));
    document.querySelectorAll(".tab-content").forEach(c => {
        c.style.display = (c.id === tabId) ? "block" : "none";
        c.classList.toggle("active", c.id === tabId);
    });
}

function reloadPage(date, time) {
    const url = new URL(window.location.href);
    url.searchParams.set("date", date);
    url.searchParams.set("time", time);
    window.location.href = url.toString();
}