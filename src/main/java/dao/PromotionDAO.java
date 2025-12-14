package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

import model.Promotion;

public class PromotionDAO {

    // Giả lập dữ liệu (nếu chưa có database)
    private static List<Promotion> promotions = new ArrayList<>();

    static {
        promotions.add(new Promotion(1, "Happy Hour", "Giảm 20% từ 14h–17h mỗi ngày", 20.0, createDate(2025, 11, 1), createDate(2025, 11, 30), "https://images.unsplash.com/photo-1551218808-94e220e084d2", "Active"));
        promotions.add(new Promotion(2, "Combo Cặp Đôi", "Không gian lãng mạn + menu đặc biệt cho 2 người", 30.0, createDate(2025, 11, 1), createDate(2025, 12, 15), "https://images.unsplash.com/photo-1551218808-94e220e084d2", "Active" ));
        promotions.add(new Promotion(3, "Combo Gia Đình", "Giảm 15% cuối tuần", 15.0, createDate(2025, 11, 1), createDate(2025, 12, 31), "https://images.unsplash.com/photo-1551218808-94e220e084d2", "Inactive")); 
        }

    // Hàm tiện lợi để tạo Date
    private static Date createDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, 0, 0, 0); // month-1 vì Calendar tháng 0-11
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // Lấy tất cả ưu đãi
    public List<Promotion> getAllPromotions() {
        return promotions;
    }

    // Lấy chi tiết 1 ưu đãi theo ID
    public Promotion getPromotionById(int id) {
        for (Promotion promo : promotions) {
            if (promo.getPromoId() == id) {
                return promo;
            }
        }
        return null; // Nếu không tìm thấy
    }
}
