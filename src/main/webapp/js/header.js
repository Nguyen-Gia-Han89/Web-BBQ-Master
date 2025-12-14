document.addEventListener('DOMContentLoaded', function () {
    const dropdown = document.querySelector('.has-dropdown');
    if (!dropdown) return;

    const toggle = dropdown.querySelector('a');
    const menu = dropdown.querySelector('.dropdown-menu');

    // Click mở / đóng
    toggle.addEventListener('click', function (e) {
        e.preventDefault();
        dropdown.classList.toggle('open');
    });

    // Click ngoài thì đóng
    document.addEventListener('click', function (e) {
        if (!dropdown.contains(e.target)) {
            dropdown.classList.remove('open');
        }
    });
});
