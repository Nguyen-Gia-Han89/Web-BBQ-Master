document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("loginModal");
    const btn = document.getElementById("openLogin");
    const closeBtn = document.querySelector(".close");
    const loginBox = document.getElementById("loginBox");
    const registerBox = document.getElementById("registerBox");
    const openRegister = document.getElementById("openRegister");
    const openLoginForm = document.getElementById("openLoginForm");
    const registerError = document.getElementById("registerError");

    if(btn) btn.onclick = () => modal.style.display = "block";
    if(closeBtn) closeBtn.onclick = () => modal.style.display = "none";
    window.onclick = (e) => { if(e.target === modal) modal.style.display = "none"; };

    if(openRegister) openRegister.onclick = (e) => {
        e.preventDefault();
        loginBox.style.display = "none";
        registerBox.style.display = "block";
        registerError.textContent = "";
    };

    if(openLoginForm) openLoginForm.onclick = (e) => {
        e.preventDefault();
        registerBox.style.display = "none";
        loginBox.style.display = "block";
        registerError.textContent = "";
    };

    // Xử lý đăng ký localStorage (demo)
    if(registerBox){
        const registerForm = registerBox.querySelector("form");
        registerForm.addEventListener("submit", e => {
            e.preventDefault();
            const username = registerForm.querySelector('input[type="text"]').value.trim();
            const email = registerForm.querySelector('input[type="email"]').value.trim();
            const password = registerForm.querySelectorAll('input[type="password"]')[0].value;
            const confirmPassword = registerForm.querySelectorAll('input[type="password"]')[1].value;

            if(password !== confirmPassword){
                registerError.textContent = "Mật khẩu xác nhận không khớp!";
                return;
            }

            const users = JSON.parse(localStorage.getItem("users") || "[]");
            if(users.some(u=>u.username === username)){
                registerError.textContent = "Tên đăng nhập đã tồn tại!";
                return;
            }

            users.push({username,email,password});
            localStorage.setItem("users",JSON.stringify(users));

            registerError.style.color="#4caf50";
            registerError.textContent="Đăng ký thành công!";

            setTimeout(()=>{
                registerBox.style.display="none";
                loginBox.style.display="block";
                registerError.style.color="#ff6b6b";
                registerForm.reset();
            },1200);
        });
    }
});
