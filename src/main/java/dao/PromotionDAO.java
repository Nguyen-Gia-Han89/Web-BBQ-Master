package dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.Promotion;

public class PromotionDAO {

    private static final List<Promotion> promotions = new ArrayList<>();

    static {
        Calendar cal = Calendar.getInstance();

        // ===== Ưu đãi 1 =====
        cal.set(2025, Calendar.NOVEMBER, 30);
        Date endDate1 = cal.getTime();
        promotions.add(new Promotion(1,"Happy Hour", "Giảm 20% từ 14h–17h mỗi ngày",20.0,new Date(),endDate1,"https://static.vinwonders.com/production/quan-nuong-nha-trang-1.jpg",""   ));

        // ===== Ưu đãi 2 =====
        cal.set(2025, Calendar.DECEMBER, 31);
        Date endDate2 = cal.getTime();
        promotions.add(new Promotion(2, "Combo Cặp Đôi", "Không gian lãng mạn + menu đặc biệt cho 2 người",30.0,new Date(),endDate2,"https://statics.vincom.com.vn/xu-huong/chi_tiet_xu_huong/buffet-lau-nuong-da-dang-hap-dan-1649053624.jpg",""));

        // ===== Ưu đãi 3 =====
        cal.set(2025, Calendar.DECEMBER, 15);
        Date endDate3 = cal.getTime();
        promotions.add(new Promotion(3, "Combo Gia Đình","Giảm 15% cuối tuần",15.0,new Date(),endDate3,"https://hotdeal.vn/images/uploads/2016/Thang%2011/18/306310/306310-combo-lau-nuong-nha-hang-lau-ngon-124-lang-ha-body-1.jpg",""));
    }

    /* =========================
       LẤY TẤT CẢ ƯU ĐÃI
       ========================= */
    public List<Promotion> getAllPromotions() {
        updatePromotionStatus();
        return promotions;
    }

    /* =========================
       LẤY ƯU ĐÃI THEO ID
       ========================= */
    public Promotion getPromotionById(int id) {
        updatePromotionStatus();
        for (Promotion promo : promotions) {
            if (promo.getPromoId() == id) {
                return promo;
            }
        }
        return null;
    }

    /* =========================
       LẤY ƯU ĐÃI ĐANG DIỄN RA
       ========================= */
    public List<Promotion> getActivePromotions() {
        updatePromotionStatus();
        List<Promotion> list = new ArrayList<>();
        for (Promotion promo : promotions) {
            if ("Active".equals(promo.getStatus())) {
                list.add(promo);
            }
        }
        return list;
    }

    /* =========================
       TỰ ĐỘNG CẬP NHẬT STATUS
       ========================= */
    private void updatePromotionStatus() {
        Date today = new Date();
        for (Promotion promo : promotions) {
            if (promo.getEndDate().after(today)) {
                promo.setStatus("Active");
            } else {
                promo.setStatus("Inactive");
            }
        }
    }
}
