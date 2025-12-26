package dao;

import model.Space;
import model.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TableDAO {

    private SpaceDAO spaceDAO = new SpaceDAO();

    // ================== MAP RESULTSET ==================
    private Table mapTable(ResultSet rs) throws Exception {
        Table table = new Table();

        table.setTableId(rs.getInt("TableID"));
        table.setTableName(rs.getString("TableName"));
        table.setSpaceId(rs.getInt("SpaceID"));
        table.setSeats(rs.getInt("Seats"));
        table.setStatus(rs.getString("Status"));

        // Map Space object (OOP)
        Space space = spaceDAO.getSpaceById(table.getSpaceId());
        table.setSpace(space);

        return table;
    }

    // ================== GET ALL ==================
    public List<Table> getAll() {
        List<Table> list = new ArrayList<>();
        String sql = "SELECT * FROM [Table] ORDER BY TableName";

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                list.add(mapTable(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ================== GET BY ID ==================
    public Table getById(int tableId) {
        String sql = "SELECT * FROM [Table] WHERE TableID = ?";

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, tableId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapTable(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ================== GET BY SPACE ==================
    public List<Table> getBySpace(int spaceId) {
        List<Table> list = new ArrayList<>();
        String sql = """
            SELECT *
            FROM [Table]
            WHERE SpaceID = ?
            ORDER BY TableName
        """;

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, spaceId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapTable(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ================== GET AVAILABLE TABLES ==================
    public List<Table> getAvailableTables(int spaceId, int minSeats) {
        List<Table> list = new ArrayList<>();
        String sql = """
            SELECT *
            FROM [Table]
            WHERE SpaceID = ?
              AND Seats >= ?
              AND Status = 'available'
            ORDER BY Seats
        """;

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, spaceId);
            ps.setInt(2, minSeats);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapTable(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ================== UPDATE STATUS ==================
    public boolean updateStatus(int tableId, String status) {
        String sql = "UPDATE [Table] SET Status = ? WHERE TableID = ?";

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, status);
            ps.setInt(2, tableId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================== TEST MAIN ==================
    public static void main(String[] args) {

        TableDAO tableDAO = new TableDAO();

        System.out.println("========== ALL TABLES ==========");
        tableDAO.getAll().forEach(t ->
            System.out.println(
                t.getTableId() + " | "
                + t.getTableName() + " | "
                + t.getSpace().getSpaceId() + " | "
                + t.getSeats() + " seats | "
                + t.getStatus()
            )
        );

        System.out.println("\n========== TABLE BY ID (1) ==========");
        Table t = tableDAO.getById(1);
        if (t != null) {
            System.out.println(
                t.getTableName() + " | "
                + t.getSpace().getSpaceId() + " | "
                + t.getStatus()
            );
        }

        System.out.println("\n========== TABLES BY SPACE (VIP - 2) ==========");
        tableDAO.getBySpace(2).forEach(table ->
            System.out.println(
                table.getTableName() + " | "
                + table.getSeats() + " seats | "
                + table.getStatus()
            )
        );

        System.out.println("\n========== AVAILABLE TABLES (SPACE 1, >= 4 SEATS) ==========");
        tableDAO.getAvailableTables(1, 4).forEach(table ->
            System.out.println(
                table.getTableName() + " | "
                + table.getSeats() + " seats"
            )
        );

        System.out.println("\n========== UPDATE STATUS TEST ==========");
        boolean ok = tableDAO.updateStatus(1, "reserved");
        System.out.println(ok ? "✅ Update OK" : "❌ Update FAIL");
    }
}
