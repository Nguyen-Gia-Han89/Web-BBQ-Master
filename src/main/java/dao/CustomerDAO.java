package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Customer;

public class CustomerDAO {

    // ================== MAP RESULTSET ==================
    private Customer mapCustomer(ResultSet rs) throws Exception {
        Customer c = new Customer();
        c.setCustomerID(rs.getInt("CustomerID"));
        c.setFullName(rs.getString("FullName"));
        c.setEmail(rs.getString("Email"));
        c.setPhoneNumber(rs.getString("PhoneNumber"));
        // Không map password khi đọc danh sách
        return c;
    }

    /**
     * Lấy tất cả khách hàng
     */
    public List<Customer> getAll() {
        List<Customer> list = new ArrayList<>();
        String sql = """
            SELECT CustomerID, FullName, Email, PhoneNumber
            FROM Customer
        """;

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                list.add(mapCustomer(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy khách hàng theo ID
     */
    public Customer getCustomerById(int id) {
        String sql = """
            SELECT CustomerID, FullName, Email, PhoneNumber
            FROM Customer
            WHERE CustomerID = ?
        """;

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapCustomer(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Login
     */
    public Customer login(String email, String password) {
        String sql = """
            SELECT CustomerID, FullName, Email, PhoneNumber
            FROM Customer
            WHERE Email = ? AND PasswordHash = ?
        """;

        try (
            Connection con = DBCPDataSource.getDataSource(). getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapCustomer(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Register
     */
    public boolean register(Customer customer) {
        String checkSql = """
            SELECT 1 FROM Customer 
            WHERE Email = ? OR PhoneNumber = ?
        """;

        String insertSql = """
            INSERT INTO Customer (FullName, PhoneNumber, Email, PasswordHash)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection con = DBCPDataSource.getDataSource().getConnection()) {

            // 1️⃣ Kiểm tra trùng Email hoặc PhoneNumber
            try (PreparedStatement ps = con.prepareStatement(checkSql)) {
                ps.setString(1, customer.getEmail());
                ps.setString(2, customer.getPhoneNumber());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("❌ Email hoặc số điện thoại đã tồn tại!");
                        return false;
                    }
                }
            }

            // 2️⃣ Insert dữ liệu mới
            try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                ps.setString(1, customer.getFullName());
                ps.setString(2, customer.getPhoneNumber());
                ps.setString(3, customer.getEmail());
                ps.setString(4, customer.getPasswordHash());

                int rows = ps.executeUpdate();
                System.out.println("✅ Insert thành công, số dòng thêm: " + rows);
                return rows > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    
    // ================== TEST MAIN ==================
    public static void main(String[] args) {
        CustomerDAO dao = new CustomerDAO();

        System.out.println("=== ALL CUSTOMERS ===");
        dao.getAll().forEach(c ->
            System.out.println(c.getCustomerID() + " - " + c.getFullName())
        );
//
//        System.out.println("\n=== CUSTOMER BY ID (1) ===");
//        Customer c = dao.getCustomerById(1);
//        if (c != null) {
//            System.out.println(c.getFullName() + " | " + c.getEmail());
//        }
//
//        System.out.println("\n=== LOGIN TEST ===");
//        Customer login = dao.login("test@gmail.com", "123456");
//        System.out.println(login != null ? "Login OK" : "Login Fail");
//        
//        System.out.println("\n=== Register TEST ===");
//        Customer c1 = new Customer("Tran", "0231452513", "tran@gmail.com", "123456");
//        boolean register = dao.register(c1);
//        System.out.println(register ? "Register OK" : "Register Fail");
    }
}
