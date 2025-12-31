/**
 * FILE: login.js
 * Chức năng: Xử lý đóng/mở Modal, chuyển đổi Form và Validation phía Client
 */
document.addEventListener("DOMContentLoaded", () => {
    // --- 1. KHAI BÁO CÁC BIẾN ---
    const modal = document.getElementById("loginModal");
    const btnOpen = document.getElementById("openLogin"); // Nút bấm ngoài trang chủ (nếu có)
    const closeBtn = document.querySelector(".close");
    
    const loginBox = document.getElementById("loginBox");
    const registerBox = document.getElementById("registerBox");
    
    const openRegisterLink = document.getElementById("openRegister");
    const openLoginLink = document.getElementById("openLoginForm");
    
    const registerError = document.getElementById("registerError");
    const registerForm = registerBox ? registerBox.querySelector("form") : null;

    // --- 2. XỬ LÝ ĐÓNG/MỞ MODAL ---
    // Mở modal khi nhấn vào nút đăng nhập ở Header
    if (btnOpen) {
        btnOpen.onclick = () => {
            modal.style.display = "block";
            // Luôn ưu tiên hiển thị form đăng nhập trước
            loginBox.style.display = "block";
            registerBox.style.display = "none";
        };
    }

    // Đóng modal khi nhấn dấu (x)
    if (closeBtn) {
        closeBtn.onclick = () => {
            modal.style.display = "none";
        };
    }

    // Đóng modal khi nhấn ra ngoài vùng nội dung
    window.onclick = (e) => {
        if (e.target === modal) {
            modal.style.display = "none";
        }
    };

    // --- 3. CHUYỂN ĐỔI QUA LẠI GIỮA 2 FORM ---
    // Từ Login sang Register
    if (openRegisterLink) {
        openRegisterLink.onclick = (e) => {
            e.preventDefault(); // Ngăn trình duyệt nhảy trang
            loginBox.style.display = "none";
            registerBox.style.display = "block";
            if (registerError) registerError.textContent = ""; // Xóa thông báo lỗi cũ
        };
    }

    // Từ Register sang Login
    if (openLoginLink) {
        openLoginLink.onclick = (e) => {
            e.preventDefault();
            registerBox.style.display = "none";
            loginBox.style.display = "block";
        };
    }

    // --- 4. XỬ LÝ VALIDATION ĐĂNG KÝ (QUAN TRỌNG) ---
    if (registerForm) {
        registerForm.addEventListener("submit", (e) => {
            // Lấy giá trị từ các ô input mật khẩu
            const password = document.getElementById("password").value;
            const confirmPassword = document.getElementById("confirmPassword").value;

            // Kiểm tra mật khẩu có khớp không
            if (password !== confirmPassword) {
                // CHẶN gửi form nếu không khớp
                e.preventDefault(); 
                
                if (registerError) {
                    registerError.textContent = "Mật khẩu xác nhận không khớp!";
                    registerError.style.color = "red";
                }
                console.warn("Đăng ký bị chặn: Mật khẩu không khớp.");
            } else {
                // NẾU KHỚP: Không gọi e.preventDefault()
                // Trình duyệt sẽ tự động thực hiện hành động 'post' lên RegisterServlet
                console.log("Dữ liệu hợp lệ, đang gửi yêu cầu tới RegisterServlet...");
            }
        });
    }

    // --- 5. TỰ ĐỘNG MỞ MODAL THEO URL (Dành cho đặt bàn) ---
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get('requireLogin') === 'true' && modal) {
        modal.style.display = "block";
        loginBox.style.display = "block";
        registerBox.style.display = "none";
        
        const loginTitle = loginBox.querySelector("h2");
        if (loginTitle) {
            loginTitle.textContent = "Vui lòng đăng nhập để tiếp tục";
            loginTitle.style.color = "#ff6b6b";
        }
    }
});