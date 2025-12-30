/**
 * 
 */
function calculateGrandTotal() {
    const foodElem = document.getElementById("foodTotalDisplay");
    let foodTotal = foodElem ? parseInt(foodElem.dataset.foodTotal) || 0 : 0;

    const serviceSelect = document.getElementById("service");
    let serviceFee = 0;
    if (serviceSelect && serviceSelect.selectedIndex !== -1) {
        serviceFee = parseInt(serviceSelect.options[serviceSelect.selectedIndex].getAttribute("data-fee")) || 0;
    }

    const grandTotal = foodTotal + serviceFee;

    // Cập nhật hiển thị
    document.getElementById("serviceFeeDisplay").innerText = serviceFee.toLocaleString("vi-VN") + "đ";
    document.getElementById("grandTotalDisplay").innerText = grandTotal.toLocaleString("vi-VN") + "đ";
    document.getElementById("finalAmountInput").value = grandTotal;

    // In ra console để kiểm tra
    console.log(">>> DEBUG TÍNH TIỀN <<<");
    console.log("Tiền món ăn:", foodTotal);
    console.log("Phí dịch vụ:", serviceFee);
    console.log("Tổng thanh toán:", grandTotal);
}

document.addEventListener("DOMContentLoaded", function () {
    const serviceSelect = document.getElementById("service");
    if (serviceSelect) {
        serviceSelect.addEventListener("change", calculateGrandTotal);
    }
    calculateGrandTotal(); // chạy ngay khi load
});