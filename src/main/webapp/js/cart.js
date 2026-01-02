// Dùng Event Delegation: Lắng nghe trên toàn bộ trang
document.addEventListener('click', function (e) {
    // Lắng nghe cả nút 'btn-add-to-cart' (class chung ta vừa thêm)
    if (e.target && e.target.classList.contains('btn-add-to-cart')) {
        const btn = e.target;
        const dishId = btn.getAttribute('data-id');
        
        // Tìm khung bao quanh (item-card ở menu hoặc combo-card ở index)
        const card = btn.closest('.item-card') || btn.closest('.combo-card');
        const imgToFly = card ? card.querySelector('img') : null;
        const badge = document.getElementById('cart-badge');

        if (imgToFly && badge) {
            performFlyAnimation(imgToFly, badge);
        }

        addToCartAjax(dishId);
    }
});

function addToCartAjax(dishId) {
    // Sử dụng window.contextPath đã khai báo ở Header
    const url = window.contextPath + "/cart?action=add&dishId=" + dishId;

    fetch(url, { method: 'POST' })
        .then(res => {
            if (res.ok) {
                // Cập nhật con số trên Header ngay lập tức
                const badge = document.getElementById('cart-badge');
                if (badge) {
                    let count = parseInt(badge.innerText) || 0;
                    badge.innerText = count + 1;
                    badge.style.display = 'block'; 
                }
            }
        })
        .catch(err => console.error("Lỗi đặt hàng:", err));
}

function performFlyAnimation(img, target) {
    const flyImg = img.cloneNode(true);
    const rect = img.getBoundingClientRect();
    const targetRect = target.getBoundingClientRect();

    Object.assign(flyImg.style, {
        position: 'fixed',
        top: rect.top + 'px',
        left: rect.left + 'px',
        width: rect.width + 'px',
        zIndex: '9999',
        transition: 'all 0.8s ease-in-out',
        borderRadius: '50%',
        pointerEvents: 'none'
    });

    document.body.appendChild(flyImg);

    setTimeout(() => {
        Object.assign(flyImg.style, {
            top: targetRect.top + 'px',
            left: targetRect.left + 'px',
            width: '20px',
            opacity: '0'
        });
    }, 50);

    setTimeout(() => flyImg.remove(), 800);
}