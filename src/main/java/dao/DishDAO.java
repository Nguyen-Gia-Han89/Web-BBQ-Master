package dao;

import java.util.ArrayList;
import java.util.List;
import model.Dish;

public class DishDAO {

    // Danh sách tĩnh chứa TẤT CẢ món ăn
    private static List<Dish> dishes = new ArrayList<>();
    
    // Các giá trị mặc định cho các trường thiếu trong dữ liệu mới
    private static final String DEFAULT_DESCRIPTION = "Món ăn hấp dẫn, chuẩn vị nhà hàng.";
    private static final String DEFAULT_STATUS = "active";
    private static int idCounter = 1; // Khởi tạo bộ đếm ID

    static {
        // --- 1. DỮ LIỆU GỐC (Combo & Pizza) ---
        dishes.add(new Dish(idCounter++, "Combo FA", 899000, "Phù hợp cho 4-6 người, gồm nhiều món nướng và lẩu.",
                "https://images.unsplash.com/photo-1543353071-873f17a7a088", "BBQ", "combo", "active"));

        dishes.add(new Dish(idCounter++, "Combo Gia Đình", 899000, "Phù hợp cho 4-6 người, gồm nhiều món nướng và lẩu.",
                "https://images.unsplash.com/photo-1543353071-873f17a7a088", "BBQ", "combo", "active"));

        dishes.add(new Dish(idCounter++, "Combo Bạn Bè", 499000, "Phù hợp cho 2-4 người, nhiều món nướng hấp dẫn.",
                "https://images.unsplash.com/photo-1543353071-873f17a7a088", "BBQ", "combo", "active"));

        dishes.add(new Dish(idCounter++, "Combo Cặp Đôi", 299000, "Thực đơn lãng mạn dành cho 2 người.",
                "https://images.unsplash.com/photo-1543353071-873f17a7a088", "BBQ", "combo", "active"));
        
        dishes.add(new Dish(idCounter++, "Pizza Pepperoni", 150000, "Pizza Pepperoni size M", 
        			"https://file.hstatic.net/1000389344/article/pepperoni_5_1c9ba759196f480eba397d628ac958ed_1024x1024.jpg", "Pizza", "pizza", "active"));
        
        // ID hiện tại: idCounter = 6

        // --- 2. DỮ LIỆU MỚI (Thịt, Rau củ, Lẩu, Thức uống) ---
        
        // --- 2.1. THỊT & HẢI SẢN (meat) ---
        // Sử dụng "meat" làm category, "single" làm dishType (trừ lẩu)
        String meatCategory = "meat";
        String singleDishType = "single";
        
        dishes.add(new Dish(idCounter++, "Bò nướng lá lốt", 150000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Bo+Nuong+La+Lot", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Gà nướng sả", 120000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Ga+Nuong+Sa", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Sườn nướng BBQ", 180000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Suon+Nuong+BBQ", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Tôm nướng muối ớt", 220000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Tom+Nuong+Muoi+Ot", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Bò cuộn nấm kim châm", 165000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Bo+Cuon+Nam", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Bạch tuộc nướng sa tế", 195000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Bach+Tuoc+Nuong+Sa+Te", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lòng non nướng ngũ vị", 110000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Long+Non+Nuong", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Heo tộc nướng riềng mẻ", 210000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Heo+Toc+Nuong+Rieng+Me", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Mực một nắng nướng", 280000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Muc+Mot+Nang+Nuong", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Ba chỉ sốt cay HQ", 170000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Thit+Ba+Chi+Sot+Cay", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Cá hồi sốt chanh dây", 250000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Ca+Hoi+Nuong+Sot+Chanh+Day", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Bò nướng tiêu đen", 230000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=De+Nuong+Cheo", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Nghêu hấp sả", 100000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Ngheu+Hap+Xa", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Ốc hương rang muối", 350000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Oc+Huong+Rang+Muoi", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Bò Fillet sốt tiêu đen", 260000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Bo+File+Sot+Tieu+Den", meatCategory, singleDishType, DEFAULT_STATUS));

        // --- 2.2. RAU CỦ (veg) ---
        String vegCategory = "veg";
        
        dishes.add(new Dish(idCounter++, "Bắp nướng bơ tỏi", 50000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Bap+Nuong", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Nấm chiên lắc phô mai", 80000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Nam+Nuong+Pho+Mai", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Cà tím nướng mỡ hành", 65000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Ca+Tim+Nuong+Mo+Hanh", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Khoai lang nướng mật ong", 55000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Khoai+Lang+Nuong", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Đậu bắp nướng chao", 70000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Dau+Bap+Nuong+Chao", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Salad trộn dầu giấm", 75000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Salad+Tron+Dau+Giam", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Khoai tây chiên phô mai", 60000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Khoai+Tay+Chien+Pho+Mai", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Bánh mì nướng bơ tỏi", 45000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Banh+Mi+Nuong+Bo+Toi", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Canh chua rau củ", 85000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Canh+Chua+Rau+Cu", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Củ từ nướng", 50000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Cu+Tu+Nuong", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Salad rau củ", 45000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Dua+Leo+Tron+Dau+Phong", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Bông cải xào tỏi", 75000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Bong+Cai+Xao+Toi", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Chân gà sốt thái", 90000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Chan+Ga+Sot+Thai", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Súp bắp cua (Chay)", 60000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Sup+Bap+Cua", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Xôi chiên sốt cua", 70000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Xoi+Chien+Phong", vegCategory, singleDishType, DEFAULT_STATUS));

        // --- 2.3. LẨU (soup) ---
        String soupCategory = "soup";
        String hotpotDishType = "hotpot";
        
        dishes.add(new Dish(idCounter++, "Lẩu hải sản chua cay", 250000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Lau+Hai+San", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu bò khoai môn", 280000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Lau+Bo+Khoai+Mon", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu gà lá é", 240000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Lau+Ga+La+E", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu nấm chay thanh đạm", 190000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Lau+Nam+Chay", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu tôm chua cay", 270000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Lau+Tom+Chua+Cay", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu mắm miền Tây", 320000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Lau+Mam+Mien+Tay", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu cá kèo lá giang", 260000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Lau+Ca+Khu+To", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu gà thuốc bắc", 290000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Lau+Duong+Ham+Thuoc+Bac", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu thập cẩm", 310000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Lau+De+Thap+Cam", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu Sate bò", 275000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Lau+Sate+Bo", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu Kimchi Hàn Quốc", 230000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Lau+Kimchi", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu cù lao", 285000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Lau+Chao+Hai+San", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu cá đuối", 295000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Lau+Oc+Bong", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu uyên ương (2 ngăn)", 330000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Lau+Duong+Phe+Lien+Hoa", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu vịt om sấu", 255000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Lau+Vit+Om+Sau", soupCategory, hotpotDishType, DEFAULT_STATUS));

        // --- 2.4. THỨC UỐNG (drink) ---
        String drinkCategory = "drink";
        
        dishes.add(new Dish(idCounter++, "Coca-Cola (Lon)", 20000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Coca+Cola", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Bia Sài Gòn (Chai/Lon)", 25000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Bia+Sai+Gon", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Trà Đào Cam Sả", 45000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Tra+Dao+Cam+Sa", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Nước ép Cam tươi", 50000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Nuoc+Ep+Cam", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Sữa đậu nành", 30000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Sua+Dau+Nanh", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Nước lọc (Chai)", 15000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Nuoc+Loc+Dong+Chai", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Soda chanh tươi", 35000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Sode+Chanh", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Trà Atiso (Lạnh/Nóng)", 30000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Tra+Atiso", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Trà dâu", 35000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Tra+Dau", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Trà tắc", 40000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Tra+Tac", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Trà Lipton đá", 30000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Lipton+Da", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Bia Heineken (Chai/Lon)", 35000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Bia+Heineken", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Nước dừa tươi", 40000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Nuoc+Dua+Tuoi", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Trà Vải", 45000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Tra+Vai", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Nước chanh dây", 40000, DEFAULT_DESCRIPTION, 
                "https://via.placeholder.com/280x180?text=Nuoc+Chanh+Day", drinkCategory, singleDishType, DEFAULT_STATUS));
    }

    /**
     * Lấy tất cả món ăn đang hoạt động (bao gồm combo, pizza, món lẻ, lẩu).
     * @return List<Dish>
     */
    public List<Dish> getAllDishes() {
        return dishes;
    }
    
    /**
     * Lấy danh sách các Combo đang hoạt động.
     * 
     */
    public List<Dish> getActiveCombos() {
        List<Dish> combos = new ArrayList<>();
        for (Dish dish : dishes) {
            // Giả định isCombo() kiểm tra dishType là "combo" hoặc "hotpot"
            if ((dish.getDishType().equalsIgnoreCase("combo") || dish.getDishType().equalsIgnoreCase("hotpot")) && dish.isAvailable()) {
                combos.add(dish);
            }
        }
        return combos;
    }
    
    /**
     * Lấy 1 món ăn theo ID.
     * 
     */
    public Dish getDishById(int id) {
        for (Dish dish : dishes) {
            if (dish.getDishId() == id) {
                return dish;
            }
        }
        return null;
    }
    
}