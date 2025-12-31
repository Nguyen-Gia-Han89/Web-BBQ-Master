package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Service;

public class ServiceDAO {

    private Service mapService(ResultSet rs) throws Exception {
        Service s = new Service();
        s.setServiceID(rs.getInt("ServiceID"));
        s.setName(rs.getString("Name"));
        s.setExtraFee(rs.getDouble("ExtraFee"));
        return s;
    }

    public List<Service> getAll() {
        List<Service> list = new ArrayList<>();
        String sql = """
            SELECT ServiceID, Name, ExtraFee
            FROM Service
        """;

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                list.add(mapService(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Service getServiceById(int id) {
        String sql = """
            SELECT ServiceID, Name, ExtraFee
            FROM Service
            WHERE ServiceID = ?
        """;

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapService(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(Service service) {
        String sql = """
            INSERT INTO Service(Name, ExtraFee)
            VALUES (?, ?)
        """;

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, service.getName());
            ps.setDouble(2, service.getExtraFee());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Service service) {
        String sql = """
            UPDATE Service
            SET Name = ?, ExtraFee = ?
            WHERE ServiceID = ?
        """;

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, service.getName());
            ps.setDouble(2, service.getExtraFee());
            ps.setInt(3, service.getServiceID());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM Service WHERE ServiceID = ?";

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        ServiceDAO dao = new ServiceDAO();

        System.out.println("=== ALL SERVICES ===");
        dao.getAll().forEach(s ->
            System.out.println(s.getServiceID() + " - " + s.getName() + " - " + s.getExtraFee())
        );
    }
}
