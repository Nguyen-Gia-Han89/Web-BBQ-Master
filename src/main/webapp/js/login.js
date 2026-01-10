/**
 * FILE: login.js
 */
{
    document.addEventListener("DOMContentLoaded", () => {
        console.log("==> BBQ Master: Hệ thống đăng nhập đã sẵn sàng!");

        // 1. Khai báo biến
        const modal = document.getElementById("loginModal");
        const loginBox = document.getElementById("loginBox");
        const registerBox = document.getElementById("registerBox");

        // 2. Hàm mở Modal (dùng chung cho mọi nút)
        const openLoginModal = () => {
            if (modal) {
                modal.style.display = "block";
                modal.style.zIndex = "99999";
                if (loginBox) loginBox.style.display = "block";
                if (registerBox) registerBox.style.display = "none";
            }
        };

        // 3. Lắng nghe TẤT CẢ các nút cần đăng nhập
        // Thêm id hoặc class của nút Đặt bàn, Đặt tiệc vào đây
        const triggers = document.querySelectorAll("#openLogin, .btn-dat-ban, .btn-dat-tiec");
        
        triggers.forEach(btn => {
            btn.onclick = (e) => {
                e.preventDefault();
                console.log("Đã kích hoạt yêu cầu đăng nhập từ:", btn.innerText);
                openLoginModal();
            };
        });

        // 4. Đóng Modal
        const closeBtn = document.querySelector(".close");
        if (closeBtn) closeBtn.onclick = () => modal.style.display = "none";
        window.onclick = (e) => { if (e.target === modal) modal.style.display = "none"; };

        // 5. Chuyển đổi Form Đăng ký/Đăng nhập
        const toReg = document.getElementById("openRegister");
        const toLog = document.getElementById("openLoginForm");
        if (toReg) toReg.onclick = () => { loginBox.style.display = "none"; registerBox.style.display = "block"; };
        if (toLog) toLog.onclick = () => { registerBox.style.display = "none"; loginBox.style.display = "block"; };

        // 6. Tự động mở nếu URL có yêu cầu
        const urlParams = new URLSearchParams(window.location.search);
        if (urlParams.get('requireLogin') === 'true' || urlParams.get('loginRequired') === 'true') {
            openLoginModal();
        }
    });
}