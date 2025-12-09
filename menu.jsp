<div class="menu-section" style="max-width: 900px; margin: 40px auto; font-family: Arial;">
    <h2 style="text-align: center; margin-bottom: 20px;">THỰC ĐƠN MÓN ĂN</h2>
<c:forEach var="f" items="${foods}">
    <div class="card">
        <img src="${f.image}" />
        <h3>${f.name}</h3>
        <p>${f.price}đ</p>
    </div>
</c:forEach>

    <!-- Món nướng -->
    <h3>🔥 Món Nướng</h3>
    <ul>
        <li>Bò nướng sốt BBQ — <b>95.000đ</b></li>
        <li>Ba chỉ heo nướng — <b>79.000đ</b></li>
        <li>Gà nướng mật ong — <b>85.000đ</b></li>
        <li>Hải sản nướng tổng hợp — <b>129.000đ</b></li>
    </ul>

    <!-- Món lẩu -->
    <h3>🍲 Món Lẩu</h3>
    <ul>
        <li>Lẩu thái chua cay — <b>159.000đ</b></li>
        <li>Lẩu kim chi — <b>149.000đ</b></li>
        <li>Lẩu nấm thanh đạm — <b>139.000đ</b></li>
    </ul>

    <!-- Đồ uống -->
    <h3>🥤 Đồ Uống</h3>
    <ul>
        <li>Trà tắc — <b>25.000đ</b></li>
        <li>Nước ngọt các loại — <b>20.000đ</b></li>
        <li>Bia Tiger — <b>22.000đ</b></li>
        <li>Trà đào cam sả — <b>35.000đ</b></li>
    </ul>

    <!-- Combo -->
    <h3>🍱 Combo</h3>
    <ul>
        <li>Combo 2 người — <b>299.000đ</b></li>
        <li>Combo 4 người — <b>599.000đ</b></li>
        <li>Combo gia đình — <b>799.000đ</b></li>
    </ul>
</div>
