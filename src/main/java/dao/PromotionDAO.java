package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Promotion;

public class PromotionDAO {

    // ================== MAP RESULTSET ==================
    private Promotion mapPromotion(ResultSet rs) throws Exception {
        Promotion p = new Promotion();
        p.setPromoId(rs.getInt("PromoID"));
        p.setPromoName(rs.getString("PromoName"));
        p.setDescription(rs.getString("Description"));
        p.setDiscountPercent(rs.getDouble("DiscountPercent"));
        p.setStartDate(rs.getDate("StartDate"));
        p.setEndDate(rs.getDate("EndDate"));
        p.setImageUrl(rs.getString("ImageURL"));
        p.setStatus(rs.getString("Status"));
        return p;
    }

    /**
     * Lấy tất cả khuyến mãi
     */
    public List<Promotion> getAllPromotions() {
        List<Promotion> list = new ArrayList<>();
        String sql = "SELECT * FROM Promotion";

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                list.add(mapPromotion(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy các khuyến mãi đang Active
     */
    public List<Promotion> getActivePromotions() {
        List<Promotion> list = new ArrayList<>();
        String sql = "SELECT * FROM Promotion WHERE Status = 'Active'";

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                list.add(mapPromotion(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy chi tiết 1 ưu đãi theo ID
     */
    public Promotion getPromotionById(int id) {
        String sql = "SELECT * FROM Promotion WHERE PromoID = ?";

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapPromotion(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ================== TEST MAIN ==================
    public static void main(String[] args) {
        PromotionDAO dao = new PromotionDAO();

        System.out.println("=== ALL PROMOTIONS ===");
        dao.getAllPromotions().forEach(p ->
            System.out.println(p.getPromoId() + " - " + p.getPromoName())
        );

        System.out.println("\n=== ACTIVE PROMOTIONS ===");
        dao.getActivePromotions().forEach(p ->
            System.out.println(p.getPromoName() + " | " + p.getDiscountPercent() + "%")
        );

        System.out.println("\n=== PROMOTION BY ID (1) ===");
        Promotion p = dao.getPromotionById(1);
        if (p != null) {
            System.out.println(p.getPromoName() + " | " + p.getStatus());
        }
    }
}
