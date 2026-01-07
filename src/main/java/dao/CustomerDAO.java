package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        // KhÃ´ng map password khi Ä‘á»�c danh sÃ¡ch
        return c;
    }

    /**
     * Láº¥y táº¥t cáº£ khÃ¡ch hÃ ng
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
     * Láº¥y khÃ¡ch hÃ ng theo ID
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
            SELECT c.CustomerID, c.FullName, c.Email, c.PhoneNumber, r.RoleName
            FROM Customer c
            LEFT JOIN User_Roles r ON c.Email = r.Email
            WHERE c.Email = ? AND c.PasswordHash = ?
        """;

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer c = mapCustomer(rs);
                    c.setRole(rs.getString("RoleName")); // Lấy quyền từ DB
                    return c;
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
        String checkSql = "SELECT 1 FROM Customer WHERE Email = ? OR PhoneNumber = ?";
        String insertCustSql = "INSERT INTO Customer (FullName, PhoneNumber, Email, PasswordHash) VALUES (?, ?, ?, ?)";
        String insertRoleSql = "INSERT INTO User_Roles (Email, RoleName) VALUES (?, ?)";

        Connection con = null;
        try {
            con = DBCPDataSource.getDataSource().getConnection();
            // Bật chế độ Transaction (Tắt tự động lưu)
            con.setAutoCommit(false);

            // 1. Kiểm tra trùng lặp
            try (PreparedStatement psCheck = con.prepareStatement(checkSql)) {
                psCheck.setString(1, customer.getEmail());
                psCheck.setString(2, customer.getPhoneNumber());
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("❌ Email hoặc số điện thoại đã tồn tại!");
                        return false; 
                    }
                }
            }

            // 2. Insert vào bảng Customer
            try (PreparedStatement psCust = con.prepareStatement(insertCustSql)) {
                psCust.setString(1, customer.getFullName());
                psCust.setString(2, customer.getPhoneNumber());
                psCust.setString(3, customer.getEmail());
                psCust.setString(4, customer.getPasswordHash());
                psCust.executeUpdate();
            }

            // 3. Insert vào bảng User_Roles (Gán quyền mặc định là registered-user)
            try (PreparedStatement psRole = con.prepareStatement(insertRoleSql)) {
                psRole.setString(1, customer.getEmail());
                psRole.setString(2, "registered-user"); // <--- Role mặc định cho khách mới
                psRole.executeUpdate();
            }

            // Nếu mọi thứ ok, lưu tất cả thay đổi vào Database
            con.commit();
            System.out.println("✅ Đăng ký và gán quyền thành công cho: " + customer.getEmail());
            return true;

        } catch (Exception e) {
            if (con != null) {
                try { con.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
        } finally {
            if (con != null) {
                try { con.setAutoCommit(true); con.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
        return false;
    }
    
    public boolean updateProfile(Customer customer) {

        String checkSql = "UPDATE Customer SET FullName = ?, PhoneNumber = ? WHERE CustomerID = ?";

        try (Connection con = DBCPDataSource.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(checkSql)) {

            ps.setString(1, customer.getFullName());
            ps.setString(2, customer.getPhoneNumber());
            ps.setInt(3, customer.getCustomerID());

            int rows = ps.executeUpdate();
            System.out.println("Update Profile rows affected = " + rows);

            // âœ… DÃ’NG QUYáº¾T Ä�á»ŠNH
            return rows > 0;

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
//      System.out.println("\n=== CUSTOMER BY ID (1) ===");
//        Customer c = dao.getCustomerById(1);
//        if (c != null) {
//            System.out.println(c.getFullName() + " | " + c.getEmail());
//        }
//
        System.out.println("\n=== LOGIN TEST ===");
        Customer login = dao.login("a@gmail.com", "123");
        System.out.println(login != null ? "Login OK" : "Login Fail");
//        
//        System.out.println("\n=== Register TEST ===");
//        Customer c1 = new Customer("Tran", "0231452513", "tran@gmail.com", "123456");
//        boolean register = dao.register(c1);
//        System.out.println(register ? "Register OK" : "Register Fail");
      
       
//        System.out.println("\n=== EDIT PROFILE TEST ===");
//
//        Customer c = new Customer();
//        c.setCustomerID(1);
//        c.setFullName("Test Update Java");
//        c.setPhoneNumber("0912345678");
//
//        boolean result = dao.updateProfile(c);
//
//        System.out.println(result ? "Edit Profile OK" : "Edit Profile FAIL");
    }
}

	
	

