/**
 * 
 */
function addToCart(dishId) {
    // 1. Tìm các phần tử quan trọng
    const dishBtn = document.querySelector(`button[onclick*='addToCart(${dishId})']`);
    const cartIcon = document.querySelector(".cart-icon");
    const badge = document.getElementById("cart-badge");

    if (!dishBtn || !cartIcon) {
        console.error("Không tìm thấy nút bấm hoặc icon giỏ hàng!");
        return;
    }

    const dishCard = dishBtn.closest('.combo-card');
    const dishImg = dishCard.querySelector('img');

    // 2. Lấy vị trí
    const rect = dishImg.getBoundingClientRect();
    const cartRect = cartIcon.getBoundingClientRect();

    // 3. Tạo ảnh bay (Tạo mới thay vì clone để đảm bảo src chuẩn)
    const flyImg = document.createElement('img');
    flyImg.src = dishImg.src; 
    flyImg.className = 'fly-to-cart';
    
    // Thiết lập vị trí xuất phát chính xác tuyệt đối
    Object.assign(flyImg.style, {
        position: 'fixed',
        top: rect.top + 'px',
        left: rect.left + 'px',
        width: rect.width + 'px',
        height: rect.height + 'px',
        zIndex: '100000',
        opacity: '1',
        pointerEvents: 'none',
        transition: 'all 0.8s cubic-bezier(0.42, 0, 0.58, 1)' // Gán trực tiếp vào style để chắc chắn
    });

    document.body.appendChild(flyImg);

    // 4. Ép trình duyệt nhận diện vị trí cũ trước khi bay (quan trọng nhất)
    requestAnimationFrame(() => {
        requestAnimationFrame(() => {
            Object.assign(flyImg.style, {
                top: cartRect.top + 'px',
                left: cartRect.left + 'px',
                width: '20px',
                height: '20px',
                opacity: '0.2',
                transform: 'rotate(360deg)' // Thêm hiệu ứng xoay cho sinh động
            });
        });
    });

    // 5. Xóa ảnh sau khi bay xong
    setTimeout(() => {
        if (flyImg.parentNode) flyImg.remove();
    }, 800);

    // 6. Gọi API cập nhật Badge (Giữ nguyên của bạn)
    fetch('add-to-cart?dishId=' + dishId + '&ajax=true')
        .then(response => response.json())
        .then(data => {
            if (badge) {
                badge.textContent = data.count;
                badge.style.display = data.count > 0 ? "inline-block" : "none";
                badge.classList.add('bounce');
                setTimeout(() => badge.classList.remove('bounce'), 300);
            }
        })
        .catch(err => console.error("Lỗi Fetch:", err));
}