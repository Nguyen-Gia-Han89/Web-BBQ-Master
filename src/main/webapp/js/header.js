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
	document.addEventListener('DOMContentLoaded', function () {
	    const avatarToggle = document.getElementById('avatarToggle');
	    const userDropdown = document.getElementById('userDropdown');
	    const serviceDropdown = document.getElementById('serviceDropdown'); // Giả định id menu dịch vụ của bạn

	    // Mở menu Avatar khi click
	    if (avatarToggle) {
	        avatarToggle.addEventListener('click', function (e) {
	            e.stopPropagation();
	            userDropdown.classList.toggle('show');
	            // Nếu có menu khác đang mở thì nên đóng lại
	            if(serviceDropdown) serviceDropdown.classList.remove('open');
	        });
	    }

	    // --- CLICK RA NGOÀI THÌ ĐÓNG TẤT CẢ ---
	    document.addEventListener('click', function (e) {
	        // Đóng menu Dịch vụ (nếu click ra ngoài nó)
	        if (serviceDropdown && !serviceDropdown.contains(e.target)) {
	            serviceDropdown.classList.remove('open');
	        }
	        // Đóng menu Avatar (nếu click ra ngoài nó)
	        if (userDropdown && !avatarToggle.contains(e.target)) {
	            userDropdown.classList.remove('show');
	        }
	    });
	});
});