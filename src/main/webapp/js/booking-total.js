function calculateGrandTotal() {
    // 1. Lấy tiền món ăn từ dataset
    const foodElem = document.getElementById("foodTotalDisplay");
    const foodTotal = foodElem ? parseInt(foodElem.dataset.foodTotal) || 0 : 0;

    // 2. Xác định loại hình (Tiệc hay Bàn lẻ)
    const isParty = document.getElementById("partyForm") !== null;

    // 3. Xử lý phí dịch vụ
    const serviceSelect = document.getElementById("service");
    let serviceFee = 0;

    if (serviceSelect) {
        // Nếu là tiệc và chưa chọn gì (selectedIndex = 0), hoặc chọn gói khác
        // data-fee trong HTML của trang Tiệc nên được set sẵn giá trị (VD: 500000)
        const selectedOption = serviceSelect.options[serviceSelect.selectedIndex];
        serviceFee = parseInt(selectedOption.getAttribute("data-fee")) || 0;
    }

    // 4. Tính tổng tiền và tiền cọc
    const grandTotal = foodTotal + serviceFee;
    const depositRate = isParty ? 0.5 : 0.3; // Tiệc 50%, Bàn lẻ 30%
    const deposit = Math.round(grandTotal * depositRate);

    // 5. Hiển thị lên giao diện (định dạng tiền Việt)
    const formatMoney = (num) => num.toLocaleString("vi-VN") + "đ";
    
    document.getElementById("serviceFeeDisplay").innerText = formatMoney(serviceFee);
    document.getElementById("grandTotalDisplay").innerText = formatMoney(grandTotal);
    document.getElementById("depositDisplay").innerText = formatMoney(deposit);

    // 6. Gán giá trị vào input hidden để nộp về Servlet
    document.getElementById("finalAmountInput").value = deposit;     
    document.getElementById("grandTotalInput").value = grandTotal;   

    // Log kiểm tra
    console.log(`[${isParty ? "PARTY" : "TABLE"}] Food: ${foodTotal}, Service: ${serviceFee}, Deposit: ${deposit}`);
}

// Khởi chạy ngay khi load trang để cộng phí mặc định
document.addEventListener("DOMContentLoaded", () => {
    calculateGrandTotal();

    // Lắng nghe sự kiện thay đổi dịch vụ
    const serviceSelect = document.getElementById("service");
    if (serviceSelect) {
        serviceSelect.addEventListener("change", calculateGrandTotal);
    }
});