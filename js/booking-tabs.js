/**
 * 
 */
document.addEventListener("DOMContentLoaded", () => {
    const tabs = document.querySelectorAll(".tab");
    const tabContents = document.querySelectorAll(".tab-content");
    
    function showTab(tabId) {
        tabContents.forEach(tc => {
            tc.style.display = (tc.id === tabId) ? "block" : "none";
        });
    }

    tabs.forEach(tab => {
        tab.addEventListener("click", () => {
            showTab(tab.dataset.tab);
        });
    });

    document.querySelectorAll(".btn-next").forEach(btn => {
        btn.addEventListener("click", () => {
            const currentTab = btn.closest(".tab-content");
            const currentId = currentTab.id;
            let found = false;
            tabContents.forEach(tc => {
                if(found) {
                    showTab(tc.id);
                    found = false;
                    return;
                }
                if(tc.id === currentId) found = true;
            });
        });
    });

    document.querySelectorAll(".btn-back").forEach(btn => {
        btn.addEventListener("click", () => {
            const currentTab = btn.closest(".tab-content");
            const currentId = currentTab.id;
            let prevId = null;
            for (const tc of tabContents) {
                if(tc.id === currentId && prevId) {
                    showTab(prevId);
                    break;
                }
                prevId = tc.id;
            }
        });
    });

    showTab("tab1"); // mặc định hiển thị tab 1
});
