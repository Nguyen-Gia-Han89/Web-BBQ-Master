package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Space;

public class SpaceDAO {

    // ================== MAP RESULTSET ==================
    private Space mapSpace(ResultSet rs) throws Exception {
        Space s = new Space();
        s.setSpaceId(rs.getInt("SpaceID"));
        s.setName(rs.getString("SpaceName"));
        s.setDescription(rs.getString("Description"));
        s.setImageUrl(rs.getString("ImageURL"));
        return s;
    }

    /**
     * Lấy tất cả khu vực (Space)
     */
    public List<Space> getAllSpaces() {
        List<Space> list = new ArrayList<>();
        String sql = "SELECT * FROM Space";

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                list.add(mapSpace(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy 1 khu vực theo ID
     */
    public Space getSpaceById(int id) {
        String sql = "SELECT * FROM Space WHERE SpaceID = ?";

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapSpace(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ================== TEST MAIN ==================
    public static void main(String[] args) {
        SpaceDAO dao = new SpaceDAO();

        System.out.println("=== ALL SPACES ===");
        dao.getAllSpaces().forEach(s ->
            System.out.println(s.getSpaceId() + " - " + s.getName())
        );

        System.out.println("\n=== SPACE BY ID (1) ===");
        Space s = dao.getSpaceById(1);
        if (s != null) {
            System.out.println(s.getName() + " | " + s.getDescription());
        }
    }
}
