/**
 * 
 */
function addToCart(dishId) {
    fetch('add-to-cart?dishId=' + dishId + '&ajax=true')
        .then(response => response.json())
        .then(data => {

            const badge = document.getElementById("cart-badge");

            // cập nhật số lượng
            badge.textContent = data.count;

            // ẩn/hiện badge
            if (data.count === 0) {
                badge.style.display = "none";
            } else {
                badge.style.display = "inline-block";
            }
        })
        .catch(err => console.error(err));
}