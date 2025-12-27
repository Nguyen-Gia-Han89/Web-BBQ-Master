document.addEventListener("DOMContentLoaded", function () {
    const tabs = document.querySelectorAll(".tab");
    const contents = document.querySelectorAll(".tab-content");

    function showTab(id) {
        contents.forEach(c => {
            c.classList.remove("active");
            c.style.display = "none"; // Đảm bảo ẩn hẳn
        });
        tabs.forEach(t => t.classList.remove("active"));

        const targetContent = document.getElementById(id);
        if (targetContent) {
            targetContent.classList.add("active");
            targetContent.style.display = "block"; // Hiện tab được chọn
        }

        const targetTab = document.querySelector(`.tab[data-tab="${id}"]`);
        if (targetTab) targetTab.classList.add("active");
    }

    // Click trực tiếp vào tab header
    tabs.forEach(tab => {
        tab.addEventListener("click", () => showTab(tab.dataset.tab));
    });

    // Nút Tiếp tục
    document.querySelectorAll(".btn-next").forEach(btn => {
        btn.addEventListener("click", () => {
            const current = btn.closest(".tab-content");
            const index = Array.from(contents).findIndex(c => c.id === current.id);
            if (index >= 0 && index < contents.length - 1) {
                showTab(contents[index + 1].id);
                window.scrollTo(0, 0); // Cuộn lên đầu trang khi sang tab mới
            }
        });
    });

    // Nút Quay lại
    document.querySelectorAll(".btn-back").forEach(btn => {
        btn.addEventListener("click", () => {
            const current = btn.closest(".tab-content");
            const index = Array.from(contents).findIndex(c => c.id === current.id);
            if (index > 0) {
                showTab(contents[index - 1].id);
                window.scrollTo(0, 0);
            }
        });
    });

    // Mặc định mở tab1
    showTab("tab1");

    /* ===== CHỌN GIỜ ===== */
    const timeBtns = document.querySelectorAll(".time-slot-btn");
    const timeInput = document.getElementById("time");

    timeBtns.forEach(btn => {
        btn.addEventListener("click", () => {
            timeBtns.forEach(b => b.classList.remove("active"));
            btn.classList.add("active");
            timeInput.value = btn.dataset.time;
        });
    });

    /* ===== LỌC BÀN THEO KHU VỰC ===== */
    const spaceRadios = document.querySelectorAll('.space-radio');
    const tableBtns = document.querySelectorAll('.table-btn');

    function filterTables(selectedSpace) {
        tableBtns.forEach(btn => {
            if (btn.getAttribute('data-space') === selectedSpace) {
                btn.style.display = 'inline-block';
            } else {
                btn.style.display = 'none';
            }
        });
    }

    spaceRadios.forEach(radio => {
        radio.addEventListener('change', function() {
            filterTables(this.value);
        });
    });

    // Tự động kích hoạt lọc cho khu vực đầu tiên khi load trang
    const firstSpace = document.querySelector('.space-radio:checked') || document.querySelector('.space-radio');
    if (firstSpace) {
        firstSpace.checked = true;
        filterTables(firstSpace.value);
    }

    /* ===== CHỌN BÀN ===== */
    const tableInput = document.getElementById("selectedTable");
    const tableDisplay = document.getElementById("selectedTableDisplay");

    tableBtns.forEach(btn => {
        btn.addEventListener("click", () => {
            tableBtns.forEach(b => b.classList.remove("selected"));
            btn.classList.add("selected");
            tableInput.value = btn.dataset.id;
            tableDisplay.textContent = btn.textContent.trim();
        });
    });
});

/* ===== THAY ĐỔI SỐ LƯỢNG COMBO (Nằm ngoài DOMContentLoaded) ===== */
function updateBookingCart(dishId, action) {
    const url = `${pageContext.request.contextPath}/add-to-cart?dishId=${dishId}&action=${action}&ajax=true`;

    fetch(url, { method: 'POST' })
    .then(response => response.json())
    .then(data => {
        // Lưu lại trạng thái đang ở Tab 3 để sau khi load lại không bị nhảy về Tab 1
        localStorage.setItem('currentTab', 'tab3');
        location.reload(); 
    })
    .catch(error => console.error('Error:', error));
}

// Logic khi trang load xong: Tự động nhảy về Tab khách đang đứng
document.addEventListener("DOMContentLoaded", function() {
    const savedTab = localStorage.getItem('currentTab');
    if (savedTab) {
        const tabToClick = document.querySelector(`[data-tab="${savedTab}"]`);
        if (tabToClick) tabToClick.click();
        localStorage.removeItem('currentTab');
    }
});