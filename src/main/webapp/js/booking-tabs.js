

/*************************************************
 * 3. CHUYỂN TAB
 *************************************************/
function showTab(tabId) {
    const contents = document.querySelectorAll(".tab-content");
    const tabs = document.querySelectorAll(".tab");

    contents.forEach(c => {
        c.classList.remove("active");
        c.style.display = "none";
    });
    tabs.forEach(t => t.classList.remove("active"));

    const targetContent = document.getElementById(tabId);
    if (targetContent) {
        targetContent.classList.add("active");
        targetContent.style.display = "block";
        if (tabId === "tab3") calculateGrandTotal();
    }

    const targetTab = document.querySelector(
        `.tab[data-tab="${tabId}"]`
    );
    if (targetTab) targetTab.classList.add("active");
}

/*************************************************
 * 4. CẬP NHẬT GIỎ HÀNG (AJAX, KHÔNG RELOAD)
 *************************************************/
function updateBookingCart(dishId, action) {
    const path = window.location.pathname;
    const contextPath =
        path.split("/").length > 2 ? "/" + path.split("/")[1] : "";

    const url = `${contextPath}/add-to-cart?dishId=${dishId}&action=${action}&ajax=true`;

    fetch(url, { method: "POST" })
        .then(res => {
            if (!res.ok) throw new Error("Network error");
            return res.json(); // server trả về JSON giỏ hàng mới
        })
        .then(cart => {
            // Cập nhật tổng tiền món ăn
            const foodTotalElem = document.getElementById("foodTotalDisplay");
            foodTotalElem.dataset.foodTotal = cart.totalAmount;
            foodTotalElem.innerText = cart.totalAmount.toLocaleString("vi-VN") + "đ";

            // Cập nhật số lượng và thành tiền từng món
            cart.bookingDetails.forEach(item => {
                const qtyInput = document.querySelector(
                    `.qty-input[data-dish-id="${item.dish.dishId}"]`
                );
                if (qtyInput) {
                    qtyInput.value = item.quantity;
                    const totalCell = qtyInput.closest("tr").querySelector("td:last-child");
                    totalCell.innerText = item.total.toLocaleString("vi-VN") + "đ";
                }
            });

            // Tính lại tổng thanh toán (bao gồm dịch vụ)
            calculateGrandTotal();
        })
        .catch(err => {
            console.error("Lỗi cập nhật giỏ hàng:", err);
            alert("Có lỗi khi cập nhật giỏ hàng, vui lòng thử lại.");
        });
}

/*************************************************
 * 5. KHỞI TẠO KHI TRANG LOAD
 *************************************************/
document.addEventListener("DOMContentLoaded", function () {

    /* ---- TÍNH TIỀN BAN ĐẦU ---- */
    calculateGrandTotal();

    /* ---- KHÔI PHỤC TAB ---- */
    const savedTab = localStorage.getItem("currentTab");
    if (savedTab) {
        showTab(savedTab);
        localStorage.removeItem("currentTab");
    }

    /* ---- NÚT NEXT / BACK ---- */
    document.querySelectorAll(".btn-next").forEach(btn => {
        btn.addEventListener("click", () => {
            const current = btn.closest(".tab-content");
            const contents = [...document.querySelectorAll(".tab-content")];
            const idx = contents.findIndex(c => c.id === current.id);
            if (idx < contents.length - 1) {
                showTab(contents[idx + 1].id);
                window.scrollTo(0, 0);
            }
        });
    });

    document.querySelectorAll(".btn-back").forEach(btn => {
        btn.addEventListener("click", () => {
            const current = btn.closest(".tab-content");
            const contents = [...document.querySelectorAll(".tab-content")];
            const idx = contents.findIndex(c => c.id === current.id);
            if (idx > 0) {
                showTab(contents[idx - 1].id);
                window.scrollTo(0, 0);
            }
        });
    });

    /* ---- CHỌN GIỜ ---- */
    const timeInput = document.getElementById("time");
    document.querySelectorAll(".time-slot-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            document
                .querySelectorAll(".time-slot-btn")
                .forEach(b => b.classList.remove("active"));
            btn.classList.add("active");
            timeInput.value = btn.dataset.time;
        });
    });

    /* ---- LỌC BÀN THEO KHÔNG GIAN ---- */
    const tableBtns = document.querySelectorAll(".table-btn");

    function filterTables(spaceId) {
        tableBtns.forEach(btn => {
            btn.style.display =
                btn.dataset.space === spaceId ? "inline-block" : "none";
        });
    }

    document.querySelectorAll(".space-radio").forEach(radio => {
        radio.addEventListener("change", () => filterTables(radio.value));
    });

    /* ---- CHỌN BÀN ---- */
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

    /* ---- VALIDATE FORM ---- */
    const bookingForm = document.getElementById("bookingForm");
    if (bookingForm) {
		bookingForm.addEventListener("submit", function(e) {
		    const dateInput = document.getElementById("date");
		    if (!dateInput.value) {
		        alert("Vui lòng chọn ngày!");
		        e.preventDefault();
		    }
		});
    }

    /* ---- LƯU DỊCH VỤ ĐÃ CHỌN ---- */
    const serviceSelect = document.getElementById("service");
    if (serviceSelect) {
        serviceSelect.addEventListener("change", function () {
            localStorage.setItem("selectedService", this.value);
            calculateGrandTotal();
        });

        const savedService = localStorage.getItem("selectedService");
        if (savedService) {
            serviceSelect.value = savedService;
        }
    }
});