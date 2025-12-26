document.addEventListener("DOMContentLoaded", function () {
    const tabs = document.querySelectorAll(".tab");
    const contents = document.querySelectorAll(".tab-content");

    function showTab(id) {
        contents.forEach(c => c.classList.remove("active"));
        tabs.forEach(t => t.classList.remove("active"));

        const targetContent = document.getElementById(id);
        if (targetContent) targetContent.classList.add("active");

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
            }
            btn.classList.add("clicked");
            setTimeout(() => btn.classList.remove("clicked"), 300);
        });
    });

    // Nút Quay lại
    document.querySelectorAll(".btn-back").forEach(btn => {
        btn.addEventListener("click", () => {
            const current = btn.closest(".tab-content");
            const index = Array.from(contents).findIndex(c => c.id === current.id);
            if (index > 0) {
                showTab(contents[index - 1].id);
            }
            btn.classList.add("clicked");
            setTimeout(() => btn.classList.remove("clicked"), 300);
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

	// Chọn khu vực
	document.querySelectorAll('.space-radio').forEach(radio => {
	    radio.addEventListener('change', function() {
	        const selectedSpace = this.value;
	        document.querySelectorAll('.table-btn').forEach(btn => {
	            if (btn.getAttribute('data-space') === selectedSpace) {
	                btn.style.display = 'inline-block';
	            } else {
	                btn.style.display = 'none';
	            }
	        });
	    });
	});
    /* ===== CHỌN BÀN ===== */
    const tableBtns = document.querySelectorAll(".table-btn");
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