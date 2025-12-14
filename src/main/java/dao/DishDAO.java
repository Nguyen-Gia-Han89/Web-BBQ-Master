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
                "https://oms.hotdeal.vn/images/editors/sources/000365904896/365904-phu-nuong-combo-nuong-danh-cho-4-nguoi-body(6).jpg", "BBQ", "combo", "active"));

        dishes.add(new Dish(idCounter++, "Combo Gia Đình", 899000, "Phù hợp cho 4-6 người, gồm nhiều món nướng và lẩu.",
                "https://hotdeal.vn/images/uploads/2016/Thang%2011/18/306310/306310-combo-lau-nuong-nha-hang-lau-ngon-124-lang-ha-body-1.jpg", "BBQ", "combo", "active"));

        dishes.add(new Dish(idCounter++, "Combo Bạn Bè", 499000, "Phù hợp cho 2-4 người, nhiều món nướng hấp dẫn.",
                "https://static.hotdeal.vn/images/1385/1384835/500x500/311171-set-nuong-lau-an-tet-ga-cho-4-6-nguoi-tai-nha-hang-lau-69.jpg", "BBQ", "combo", "active"));

        dishes.add(new Dish(idCounter++, "Combo Cặp Đôi", 299000, "Thực đơn lãng mạn dành cho 2 người.",
                "https://statics.vincom.com.vn/xu-huong/chi_tiet_xu_huong/buffet-lau-nuong-da-dang-hap-dan-1649053624.jpg", "BBQ", "combo", "active"));
        
        dishes.add(new Dish(idCounter++, "Pizza Pepperoni", 150000, "Pizza Pepperoni size M", 
        			"https://file.hstatic.net/1000389344/article/pepperoni_5_1c9ba759196f480eba397d628ac958ed_1024x1024.jpg", "Pizza", "pizza", "active"));
        
        // ID hiện tại: idCounter = 6

        // --- 2. DỮ LIỆU MỚI (Thịt, Rau củ, Lẩu, Thức uống) ---
        
        // --- 2.1. THỊT & HẢI SẢN (meat) ---
        // Sử dụng "meat" làm category, "single" làm dishType (trừ lẩu)
        String meatCategory = "meat";
        String singleDishType = "single";
        
        dishes.add(new Dish(idCounter++, "Bò nướng lá lốt", 150000, DEFAULT_DESCRIPTION, 
                "https://fujifoods.vn/wp-content/uploads/2021/05/bo-nuong-la-lot-2-1.jpg", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Gà nướng sả", 120000, DEFAULT_DESCRIPTION, 
                "https://i.ytimg.com/vi/0Gmszm1DEuk/maxresdefault.jpg", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Sườn nướng BBQ", 180000, DEFAULT_DESCRIPTION, 
                "https://static-images.vnncdn.net/files/publish/2022/6/5/mon-ngon-238.jpg?width=0&s=ib9owDiJHph8bXyZ2TIWAQ", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Tôm nướng muối ớt", 220000, DEFAULT_DESCRIPTION, 
                "https://media.loveitopcdn.com/5827/kcfinder/upload/images/t%C3%B4m%20s%C3%BA%20n%C6%B0%E1%BB%9Bng%20sat%E1%BA%BF.jpg", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Bò cuộn nấm kim châm", 165000, DEFAULT_DESCRIPTION, 
                "https://thucpham3a.com/wp-content/uploads/2023/08/cach-lam-thit-bo-ba-chi-cuon-nam-kim-cham-1.jpeg", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Bạch tuộc nướng sa tế", 195000, DEFAULT_DESCRIPTION, 
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSz4X_JGqjI8Kufqfoo7jxXKVP9_vcv6cNYqA&s", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lòng non nướng ngũ vị", 110000, DEFAULT_DESCRIPTION, 
                "https://i.ytimg.com/vi/nb9NldR1Wlk/sddefault.jpg", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Heo tộc nướng riềng mẻ", 210000, DEFAULT_DESCRIPTION, 
                "https://nhahangcantho.com/watermark/product/540x540x2/upload/product/image001-7912.jpg", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Mực một nắng nướng", 280000, DEFAULT_DESCRIPTION, 
                "https://quadanang.com/wp-content/uploads/2022/05/muc-mot-nang-gia-re-Da-Nang.jpg", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Ba chỉ sốt cay HQ", 170000, DEFAULT_DESCRIPTION, 
                "https://hanyori.com/wp-content/uploads/2021/03/ba-chi-nuong-2.jpg", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Cá hồi sốt chanh dây", 250000, DEFAULT_DESCRIPTION, 
                "https://lahata.vn/files/admin/2022/03/15/cach-lam-ca-hoi-sot-chanh-leo-thom-ngon-03-1649.jpg", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Bò nướng tiêu đen", 230000, DEFAULT_DESCRIPTION, 
                "https://www.panasonic.com/content/dam/Panasonic/vn/vi/recipe/healthy-everyday/healthy-everyday-recipes/bo-nuong-sot-tieu-den/pub-bo-nuong-sot-tieu-den_main_thumbnail_square.jpg", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Nghêu hấp sả", 100000, DEFAULT_DESCRIPTION, 
                "https://cdn.hstatic.net/products/200000626331/ngh_u_h_p_s__877898da6f254e5481e2d314b1b97358_master.jpg", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Ốc hương rang muối", 350000, DEFAULT_DESCRIPTION, 
                "https://chefdzung.com.vn/uploads/images/ngoc-linh/oc-huong-rang-muoi-chef-dzung.jpg", meatCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Bò Fillet sốt tiêu đen", 260000, DEFAULT_DESCRIPTION, 
                "https://barona.vn/storage/meo-vat/53/bo-sot-tieu-den.jpg", meatCategory, singleDishType, DEFAULT_STATUS));

        // --- 2.2. RAU CỦ (veg) ---
        String vegCategory = "veg";
        
        dishes.add(new Dish(idCounter++, "Bắp nướng bơ tỏi", 50000, DEFAULT_DESCRIPTION, 
                "https://www.cgvdt.vn/files/ckfinder/images/2017/G%C4%90/2122/Hinh%20Bap%20nuong%20bo%20toi%20-%20Goc%20bep.jpg", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Nấm chiên lắc phô mai", 80000, DEFAULT_DESCRIPTION, 
                "https://cdn.tgdd.vn/2021/10/CookRecipe/GalleryStep/thanh-pham-1189.jpg", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Cà tím nướng mỡ hành", 65000, DEFAULT_DESCRIPTION, 
                "https://file.hstatic.net/200000700229/article/lam-ca-tim-nuong-mo-hanh-bang-noi-chien-khong-dau_95456b83f1fa4910a60342e76347b907.jpg", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Khoai lang nướng mật ong", 55000, DEFAULT_DESCRIPTION, 
                "https://kenh14cdn.com/203336854389633024/2024/9/19/untitled-1-09020008-1726747604687-17267476048672124928330.jpg", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Đậu bắp nướng chao", 70000, DEFAULT_DESCRIPTION, 
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAaMgI4YDVgPbMt5OM0XgBRXSmi3wFjnTNnQ&s", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Salad trộn dầu giấm", 75000, DEFAULT_DESCRIPTION, 
                "https://beptruong.edu.vn/wp-content/uploads/2016/01/mon-salad-dau-giam.jpg", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Khoai tây chiên phô mai", 60000, DEFAULT_DESCRIPTION, 
                "https://daylambanh.edu.vn/wp-content/uploads/2020/02/khoai-tay-lac-pho-mai.jpg", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Bánh mì nướng bơ tỏi", 45000, DEFAULT_DESCRIPTION, 
                "https://daylambanh.edu.vn/wp-content/uploads/2019/01/banh-mi-nuong-bo-toi-600x400.jpg", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Canh chua rau củ", 85000, DEFAULT_DESCRIPTION, 
                "https://hoichay.com/wp-content/uploads/2025/04/canh-chua-nam-bo.jpg", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Củ từ nướng", 50000, DEFAULT_DESCRIPTION, 
                "https://suckhoedoisong.qltns.mediacdn.vn/zoom/600_315/Images/_OLD/2015/khoai-tu-1440579618668-crop1440579626154p.jpg", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Salad rau củ", 45000, DEFAULT_DESCRIPTION, 
                "https://cdn.zsoft.solutions/poseidon-web/app/media/Kham-pha-am-thuc/04.2024/120424-3-mon-salad-buffet-poseidon-04.jpg", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Bông cải xào tỏi", 75000, DEFAULT_DESCRIPTION, 
                "https://anhoquan.com/watermark/product/540x540x1/upload/product/maxresdefault-8499.jpg", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Chân gà sốt thái", 90000, DEFAULT_DESCRIPTION, 
                "https://cdn.tgdd.vn/2021/10/CookRecipe/Avatar/chan-ga-sot-thai-thumbnail.jpg", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Súp bắp cua (Chay)", 60000, DEFAULT_DESCRIPTION, 
                "https://cdn2.fptshop.com.vn/unsafe/1920x0/filters:format(webp):quality(75)/2024_3_5_638452458194464535_cach-nau-sup-chay.jpg", vegCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Xôi chiên sốt cua", 70000, DEFAULT_DESCRIPTION, 
                "https://bizweb.dktcdn.net/100/442/328/products/xoi-cua-com-nieu-sai-gon.jpg?v=1721792313897", vegCategory, singleDishType, DEFAULT_STATUS));

        // --- 2.3. LẨU (soup) ---
        String soupCategory = "soup";
        String hotpotDishType = "hotpot";
        
        dishes.add(new Dish(idCounter++, "Lẩu hải sản chua cay", 250000, DEFAULT_DESCRIPTION, 
                "https://beptruong.edu.vn/wp-content/uploads/2021/03/lau-hai-san-chua-cay-ngon.jpg", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu bò khoai môn", 280000, DEFAULT_DESCRIPTION, 
                "https://cdn.tgdd.vn/2021/09/CookProduct/laubokhoaimon-1200x676.jpg", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu gà lá é", 240000, DEFAULT_DESCRIPTION, 
                "https://cdn2.fptshop.com.vn/unsafe/1920x0/filters:format(webp):quality(75)/2023_10_10_638325588933867737_lau-ga-la-e-0.jpeg", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu nấm chay thanh đạm", 190000, DEFAULT_DESCRIPTION, 
                "https://lh7-us.googleusercontent.com/Pl5Y8x_ZuKNi4l5lj-Wf7NzFeZPi24x601-dEJeH_vzo-3XI_8w1TruDDlMUTbqLcMRxX0bp5w2W_nvghqjPASIJaAZRM1wH99W1h_9fJ3jiGXPtSHpIUNtWUSnJodYZbFRk0oOX1eE9qoRjJ6leRQ4", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu tôm chua cay", 270000, DEFAULT_DESCRIPTION, 
                "https://i.ytimg.com/vi/YKXBZ6eHkwQ/maxresdefault.jpg", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu mắm miền Tây", 320000, DEFAULT_DESCRIPTION, 
                "https://daotaobeptruong.vn/wp-content/uploads/2021/03/cach-nau-lau-mam-don-gian.jpg", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu cá kèo lá giang", 260000, DEFAULT_DESCRIPTION, 
                "https://www.nhahangquangon.com/wp-content/uploads/2013/11/lau-ca-keo-la-giang.jpg", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu gà thuốc bắc", 290000, DEFAULT_DESCRIPTION, 
                "https://www.vinmec.com/static/uploads/20211217_152615_279014_cach_lam_lau_ga_thu_max_1800x1800_jpg_36d845ea0f.jpg", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu thập cẩm", 310000, DEFAULT_DESCRIPTION, 
                "https://daotaobeptruong.vn/wp-content/uploads/2019/11/lau-thap-cam.jpg", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu Sate bò", 275000, DEFAULT_DESCRIPTION, 
                "https://vcdn1-giadinh.vnecdn.net/2025/11/27/Lau-bo-sa-te-5-1764236984-6307-1764237032.jpg?w=680&h=0&q=100&dpr=2&fit=crop&s=G52fwzqyTTco0Dq4iTEzPg", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu Kimchi Hàn Quốc", 230000, DEFAULT_DESCRIPTION, 
                "https://cdn.netspace.edu.vn/images/2018/10/25/cach-nau-lau-kim-chi-chuan-vi-han-quoc-800.jpg", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu cù lao", 285000, DEFAULT_DESCRIPTION, 
                "https://i.ytimg.com/vi/J-HTqw1-RlU/maxresdefault.jpg", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu cá đuối", 295000, DEFAULT_DESCRIPTION, 
                "https://cdn.tgdd.vn/2021/09/CookRecipe/Avatar/BeFunky-collage(5).jpg", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu uyên ương (2 ngăn)", 330000, DEFAULT_DESCRIPTION, 
                "https://maxvina.vn/mediacenter/media/1/files/khi-su-dung-noi-lau-2-ngan-can-dam-bao-an-toan-trong-qua-trinh-su-dung.png", soupCategory, hotpotDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Lẩu vịt om sấu", 255000, DEFAULT_DESCRIPTION, 
                "https://beptruong.edu.vn/wp-content/uploads/2020/09/lau-vit-om-sau.jpg", soupCategory, hotpotDishType, DEFAULT_STATUS));

        // --- 2.4. THỨC UỐNG (drink) ---
        String drinkCategory = "drink";
        
        dishes.add(new Dish(idCounter++, "Coca-Cola (Lon)", 20000, DEFAULT_DESCRIPTION, 
                "https://pvmarthanoi.com.vn/wp-content/uploads/2023/02/nuoc-ngot-coca-cola-330ml-202001131632421470-500x600.png", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Trà Đào Cam Sả", 45000, DEFAULT_DESCRIPTION, 
                "https://thecupcafevietnam.com/wp-content/uploads/2022/10/tra-dao-cam-sa-1.png", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Nước ép Cam tươi", 50000, DEFAULT_DESCRIPTION, 
                "https://nhahangchayhuongsen.com/image/cache/catalog/san-pham/giai-khat/hs4-800x800.png", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Sữa đậu nành ", 30000, DEFAULT_DESCRIPTION, 
                "https://www.bartender.edu.vn/wp-content/uploads/2020/04/sua-dau-nanh.jpg", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Nước lọc (Chai)", 15000, DEFAULT_DESCRIPTION, 
                "https://nuoclavie.vn/wp-content/uploads/2025/04/cac-loai-nuoc-loc-dong-chai-02.jpg", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Soda chanh tươi", 35000, DEFAULT_DESCRIPTION, 
                "https://product.hstatic.net/200000561069/product/sodachanh-01_4dfe10cb977c49e4ad057301e3eb8470.jpg", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Trà Atiso (Lạnh/Nóng)", 30000, DEFAULT_DESCRIPTION, 
                "https://dalatfarm.net/wp-content/uploads/2020/12/tra-atiso-do-1.jpg", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Trà dâu", 35000, DEFAULT_DESCRIPTION, 
                "https://tralocphat.com.vn/wp-content/uploads/2023/07/Dau-atiso-dau-chanh-day-TLP-1024x841.png", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Trà tắc", 40000, DEFAULT_DESCRIPTION, 
                "https://catalog-assets-asia-southeast1.aeon-vn-prod.e.spresso.com/c3RvcmFnZS5nb29nbGVhcGlzLmNvbQ==/YWVvbnZpZXRuYW0tc3ByZXNzby1wdWJsaWM=/Rk9PRExJTkUgMjAyNA==/QVVH/MDA1MjY2MDk=.jpg", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Trà Lipton đá", 30000, DEFAULT_DESCRIPTION, 
                "https://product.hstatic.net/1000405326/product/dfc44fc49259c6af44fb9c81d04_1024x1024_8a226b6686ef42efb9fdbd3e8ed31666_f79cb1e9a18f4881a261d99b42e3214d.png", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Nước dừa tươi", 40000, DEFAULT_DESCRIPTION, 
                "https://suckhoedoisong.qltns.mediacdn.vn/324455921873985536/2022/8/11/39785hd-1660203876717-16602038768471868376541.jpg", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Trà Vải", 45000, DEFAULT_DESCRIPTION, 
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQarnpFyBpNg8Zsb-SopvkPWb8xpBRgLbFHxQ&s", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Nước chanh dây", 40000, DEFAULT_DESCRIPTION, 
                "https://horecavn.com/wp-content/uploads/2024/05/luc-tra-chanh-day-ly-500ml_20240527104903.png", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Bia Sài Gòn (Chai/Lon)", 25000, DEFAULT_DESCRIPTION, 
                "https://biasaigonmt.com/wp-content/uploads/2018/09/lon_chai_Lager.jpg", drinkCategory, singleDishType, DEFAULT_STATUS));
        dishes.add(new Dish(idCounter++, "Bia Heineken (Chai/Lon)", 35000, DEFAULT_DESCRIPTION, 
                "https://www.vitaminhouse.vn/cdn/shop/files/chenlogovitaminhouse-2024-06-25T115358.136_600x600_crop_center.png?v=1744355673", drinkCategory, singleDishType, DEFAULT_STATUS));
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
