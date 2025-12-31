/**
 * 
 */
/* /js/booking-success.js */
document.addEventListener("DOMContentLoaded", function() {
    console.log("Booking Success Page Loaded");
    
    // Hiệu ứng số chạy (nếu muốn làm màu mè cho mã đơn hàng)
    const orderIdElement = document.querySelector('.info-value');
    if (orderIdElement) {
        orderIdElement.style.opacity = '0';
        setTimeout(() => {
            orderIdElement.style.transition = 'opacity 1s';
            orderIdElement.style.opacity = '1';
        }, 500);
    }
});