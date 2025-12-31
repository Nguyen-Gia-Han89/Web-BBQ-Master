document.addEventListener('DOMContentLoaded', function () {
    
    // --- 1. XỬ LÝ DROPDOWN "DỊCH VỤ" ---
    const serviceDropdown = document.querySelector('.has-dropdown');
    if (serviceDropdown) {
        const serviceToggle = serviceDropdown.querySelector('a');
        serviceToggle.addEventListener('click', function (e) {
            e.preventDefault();
            serviceDropdown.classList.toggle('open');
            // Đóng dropdown avatar nếu đang mở
            if (userDropdown) userDropdown.classList.remove('show');
        });
    }

    // --- 2. XỬ LÝ DROPDOWN "AVATAR" (MỚI THÊM) ---
    const avatarToggle = document.getElementById("avatarToggle");
    const userDropdown = document.getElementById("userDropdown");

    if (avatarToggle) {
        avatarToggle.addEventListener('click', function (e) {
            e.stopPropagation(); // Ngăn sự kiện click lan ra document
            userDropdown.classList.toggle('show');
            // Đóng dropdown dịch vụ nếu đang mở
            if (serviceDropdown) serviceDropdown.classList.remove('open');
        });
    }

    // --- 3. CLICK RA NGOÀI THÌ ĐÓNG TẤT CẢ ---
    document.addEventListener('click', function (e) {
        // Đóng menu Dịch vụ
        if (serviceDropdown && !serviceDropdown.contains(e.target)) {
            serviceDropdown.classList.remove('open');
        }
        // Đóng menu Avatar
        if (userDropdown && !avatarToggle.contains(e.target)) {
            userDropdown.classList.remove('show');
        }
    });
});