package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Dish;

public class DishDAO {

    // ================== MAP RESULTSET ==================
    private Dish mapDish(ResultSet rs) throws Exception {
        Dish d = new Dish();
        d.setDishId(rs.getInt("DishID"));
        d.setName(rs.getString("Name"));
        d.setPrice(rs.getDouble("Price"));
        d.setDescription(rs.getString("Description"));
        d.setImageUrl(rs.getString("ImageURL"));
        d.setCategory(rs.getString("Category"));
        d.setDishType(rs.getString("DishType"));
        d.setStatus(rs.getString("Status"));
        return d;
    }

    /**
     * Lấy tất cả món ăn đang hoạt động (bao gồm combo, pizza, món lẻ, lẩu).
     */
    public List<Dish> getAllDishes() {
        List<Dish> list = new ArrayList<>();
        String sql = "SELECT * FROM Dish WHERE Status = 'active'";

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                list.add(mapDish(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy danh sách các Combo / Hotpot đang hoạt động.
     */
    public List<Dish> getActiveCombos() {
        List<Dish> list = new ArrayList<>();
        String sql = """
            SELECT * FROM Dish
            WHERE Status = 'active'
              AND (DishType = 'combo' OR DishType = 'hotpot')
        """;

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                list.add(mapDish(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy 1 món ăn theo ID.
     */
    public Dish getDishById(int id) {
        String sql = "SELECT * FROM Dish WHERE DishID = ? AND Status = 'active'";

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapDish(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ================== TEST MAIN ==================
    public static void main(String[] args) {
        DishDAO dao = new DishDAO();

        System.out.println("=== ALL DISHES ===");
        dao.getAllDishes().forEach(d ->
            System.out.println(d.getDishId() + " - " + d.getName())
        );

        System.out.println("\n=== COMBOS ===");
        dao.getActiveCombos().forEach(d ->
            System.out.println(d.getDishId() + " - " + d.getName())
        );

        System.out.println("\n=== DISH BY ID (1) ===");
        Dish d = dao.getDishById(1);
        if (d != null) {
            System.out.println(d.getName() + " | " + d.getPrice());
        }
    }
}
