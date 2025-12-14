/**
 * booking-logic.js: Quản lý logic Tabs, Ngày Giờ Mặc định và Chọn Giờ.
 */
document.addEventListener("DOMContentLoaded", () => {
    
    // --- 1. BIẾN GLOBAL VÀ THIẾT LẬP BAN ĐẦU ---
    const tabs = document.querySelectorAll(".tab");
    const tabContents = document.querySelectorAll(".tab-content");
    const dateInput = document.getElementById('date');
    const timeSlots = document.querySelectorAll('.time-slot-btn');
    const timeInput = document.getElementById('time');
    
    // --- 2. LOGIC CHUYỂN TAB ---
    
    function showTab(tabId) {
        tabContents.forEach(tc => {
            tc.style.display = (tc.id === tabId) ? "block" : "none";
        });
        tabs.forEach(tab => {
            tab.classList.toggle('active', tab.dataset.tab === tabId);
        });
    }

    // Xử lý click trên Tab Header
    tabs.forEach(tab => {
        tab.addEventListener("click", () => {
            showTab(tab.dataset.tab);
        });
    });

    // Xử lý nút "Tiếp tục"
    document.querySelectorAll(".btn-next").forEach(btn => {
        btn.addEventListener("click", () => {
            const currentTab = btn.closest(".tab-content");
            const currentId = currentTab.id;
            let nextTab = null;

            // Tìm tab tiếp theo
            for (let i = 0; i < tabContents.length; i++) {
                if (tabContents[i].id === currentId && i + 1 < tabContents.length) {
                    // TODO: Thêm logic validation form Tab 1, Tab 2 tại đây nếu cần
                    nextTab = tabContents[i + 1].id;
                    break;
                }
            }
            
            if (nextTab) {
                showTab(nextTab);
            }
        });
    });

    // Xử lý nút "Quay lại"
    document.querySelectorAll(".btn-back").forEach(btn => {
        btn.addEventListener("click", () => {
            const currentTab = btn.closest(".tab-content");
            const currentId = currentTab.id;
            let prevId = null;
            
            // Tìm tab trước đó
            for (let i = 0; i < tabContents.length; i++) {
                if(tabContents[i].id === currentId && i > 0) {
                    prevId = tabContents[i - 1].id;
                    break;
                }
            }

            if (prevId) {
                showTab(prevId);
            }
        });
    });
    
    // Thiết lập Tab ban đầu
    showTab("tab1"); 


    // --- 3. LOGIC NGÀY GIỜ MẶC ĐỊNH VÀ CHỌN GIỜ ---

    // Đặt Ngày mặc định là Hôm nay
    function setDefaultDate() {
        const today = new Date();
        const yyyy = today.getFullYear();
        const mm = String(today.getMonth() + 1).padStart(2, '0'); 
        const dd = String(today.getDate()).padStart(2, '0');
        const todayFormatted = `${yyyy}-${mm}-${dd}`;
        
        dateInput.value = todayFormatted;
        dateInput.min = todayFormatted;
    }
    setDefaultDate();


    // Logic Chọn Giờ
    timeSlots.forEach(slot => {
        slot.addEventListener('click', function() {
            // Xóa trạng thái active của các nút khác
            timeSlots.forEach(t => t.classList.remove('active'));
            
            // Đặt trạng thái active cho nút được chọn
            this.classList.add('active');
            
            // Cập nhật giá trị vào trường hidden
            timeInput.value = this.dataset.time;
        });
    });
    
    // Tự động chọn slot giờ đầu tiên nếu chưa có
    if (timeSlots.length > 0 && !timeInput.value) {
        timeSlots[0].click();
    }
});