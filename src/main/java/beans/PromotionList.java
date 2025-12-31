package beans;

import java.util.List;
import model.Promotion;
import dao.PromotionDAO;

public class PromotionList {
    private List<Promotion> promotions;

    public PromotionList() {
        PromotionDAO dao = new PromotionDAO();
        promotions = dao.getAllPromotions(); // gọi DAO để lấy danh sách từ DB
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }
}
