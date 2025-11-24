package dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Promotion;

public class PromotionDAO {

    // Giả lập dữ liệu (nếu chưa có database)
    private static List<Promotion> promotions = new ArrayList<>();

    static {
        promotions.add(new Promotion(1, "Happy Hour", "Giảm 20% từ 14h–17h mỗi ngày", 20.0, LocalDate.of(2025, 11, 1), LocalDate.of(2025, 11, 30), "https://images.unsplash.com/photo-1551218808-94e220e084d2", "Active"));
        promotions.add(new Promotion(2, "Combo Cặp Đôi", "Không gian lãng mạn + menu đặc biệt cho 2 người", 30.0, LocalDate.of(2025, 11, 1), LocalDate.of(2025, 12, 15), "https://images.unsplash.com/photo-1551218808-94e220e084d2", "Active"));
        promotions.add(new Promotion(3, "Combo Gia Đình", "Giảm 15% cuối tuần", 15.0, LocalDate.of(2025, 11, 1), LocalDate.of(2025, 12, 31), "https://images.unsplash.com/photo-1551218808-94e220e084d2", "Inactive"));
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
