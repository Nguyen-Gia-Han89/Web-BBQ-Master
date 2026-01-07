package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Booking;
import model.Customer;
import model.Service;
import model.Table;

public class BookingDAO {

    // ================== MAP RESULTSET ==================
    private Booking mapBooking(ResultSet rs) throws Exception {
        Booking b = new Booking();

        b.setBookingId(rs.getInt("BookingID"));

        CustomerDAO customerDAO = new CustomerDAO();
        Customer c = customerDAO.getCustomerById(rs.getInt("CustomerID"));
        b.setCustomer(c);


        
        int tableId = rs.getInt("TableID");
        if (!rs.wasNull() && tableId > 0) {
            Table t = new Table();
            t.setTableId(tableId);
            t.setTableName(rs.getString("TableName"));
            b.setTable(t);
        } else {
            b.setTable(null);
        }

        if (rs.getInt("ServiceID") > 0) {
            Service s = new Service();
            s.setServiceID(rs.getInt("ServiceID"));
            s.setName(rs.getString("ServiceName"));
            s.setExtraFee(rs.getDouble("ExtraFee"));
            b.setService(s);
        }

        b.setBookingTime(rs.getTimestamp("BookingTime").toLocalDateTime());
        b.setNumberOfGuests(rs.getInt("NumberOfGuests"));
        b.setNote(rs.getString("Note"));
        b.setTotalAmount(rs.getDouble("TotalAmount"));
        b.setStatus(Booking.BookingStatus.valueOf(rs.getString("Status")));
        b.setBookingType(Booking.BookingType.valueOf(rs.getString("BookingType")));

        return b;
    }

    /**
     * Lấy tất cả danh sách đặt bàn
     */
    public List<Booking> getAll() {
        List<Booking> list = new ArrayList<>();

        String sql = """
            SELECT b.*, c.FullName,
                   t.TableName,
                   s.ServiceID, s.Name AS ServiceName, s.ExtraFee
            FROM Booking b
            JOIN Customer c ON b.CustomerID = c.CustomerID
            LEFT JOIN [Table] t ON b.TableID = t.TableID
            LEFT JOIN Service s ON b.ServiceID = s.ServiceID
            ORDER BY b.BookingTime DESC
        """;

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                list.add(mapBooking(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy Booking theo ID
     */
    public Booking getById(int bookingId) {
        String sql = """
            SELECT b.*, c.FullName,
                   t.TableName,
                   s.ServiceID, s.Name AS ServiceName, s.ExtraFee
            FROM Booking b
            JOIN Customer c ON b.CustomerID = c.CustomerID
            LEFT JOIN [Table] t ON b.TableID = t.TableID
            LEFT JOIN Service s ON b.ServiceID = s.ServiceID
            WHERE b.BookingID = ?
        """;

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, bookingId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Booking b = mapBooking(rs);

                    // 1. Load chi tiết món
                    b.setBookingDetails(new BookingDetailDAO().getByBookingId(bookingId));

                    // 2. OrderCode
                    b.setOrderCode(rs.getString("OrderCode")); // chắc chắn OrderCode đã lưu trong DB

                    return b;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Tạo booking mới
     */
    public int insert(Booking booking) {
        String sqlInsert = """
            INSERT INTO Booking 
            (CustomerID, TableID, ServiceID, BookingTime, 
             NumberOfGuests, Note, TotalAmount, Status, BookingType) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        String sqlUpdateCode = "UPDATE Booking SET OrderCode = ? WHERE BookingID = ?";

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection()
        ) {
            con.setAutoCommit(false); 

            try (PreparedStatement ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, booking.getCustomer().getCustomerID());

                if (booking.getTable() != null && booking.getTable().getTableId() > 0) {
                    ps.setInt(2, booking.getTable().getTableId());
                } else {
                    ps.setNull(2, Types.INTEGER);
                }

                if (booking.getService() != null && booking.getService().getServiceID() > 0) {
                    ps.setInt(3, booking.getService().getServiceID());
                } else {
                    ps.setNull(3, Types.INTEGER);
                }

                ps.setTimestamp(4, Timestamp.valueOf(booking.getBookingTime()));
                ps.setInt(5, booking.getNumberOfGuests());
                ps.setString(6, booking.getNote());
                ps.setDouble(7, booking.getTotalAmount());
                ps.setString(8, booking.getStatus().name());
                ps.setString(9, booking.getBookingType().name());

                int affectedRows = ps.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            int newId = rs.getInt(1);

                            // 1. Tạo ORDER_CODE (Ví dụ: BBQ-20251230-1)
                            String datePart = new SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
                            String orderCode = "BBQ-" + datePart + "-" + newId;

                            // 2. UPDATE NGÆ¯á»¢C Láº I VÃ€O DB
                            try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdateCode)) {
                                psUpdate.setString(1, orderCode);
                                psUpdate.setInt(2, newId);
                                psUpdate.executeUpdate();
                            }

                            // 3. LÆ¯U CHI TIáº¾T MÃ“N Ä‚N
                            insertBookingDetails(con, newId, booking.getBookingDetails());

                            // GÃ¡n ngÆ°á»£c orderCode vÃ o Ä‘á»‘i tÆ°á»£ng booking Ä‘á»ƒ Servlet cÃ³ thá»ƒ láº¥y ra session
                            booking.setOrderCode(orderCode); 

                            con.commit(); // ThÃ nh cÃ´ng háº¿t thÃ¬ má»›i lÆ°u tháº­t vÃ o DB
                            return newId;
                        }
                    }
                }
            } catch (Exception e) {
                con.rollback(); // Lá»—i báº¥t cá»© bÆ°á»›c nÃ o thÃ¬ há»§y háº¿t (trÃ¡nh dá»¯ liá»‡u rÃ¡c)
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void insertBookingDetails(Connection con, int bookingId, List<model.BookingDetail> details) throws Exception {
        if (details == null || details.isEmpty()) return;

        String sql = "INSERT INTO BookingDetail (BookingID, DishID, Quantity, PriceAtOrder, Total) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (model.BookingDetail item : details) {
                ps.setInt(1, bookingId);
                ps.setInt(2, item.getDish().getDishId());
                ps.setInt(3, item.getQuantity());
                ps.setDouble(4, item.getPrice());  
                ps.setDouble(5, item.getTotal()); 
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
    /**
     * Cập nhật tổng tiền
     */
    public boolean updateTotalAmount(int bookingId, double totalAmount) {
        String sql = "UPDATE Booking SET TotalAmount = ? WHERE BookingID = ?";

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setDouble(1, totalAmount);
            ps.setInt(2, bookingId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Cập nhật trạng thái Booking sử dụng Enum BookingStatus
     */
    public boolean updateStatus(int bookingId, Booking.BookingStatus status) {
        String sql = "UPDATE Booking SET Status = ? WHERE BookingID = ?";

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            // Chuyển Enum thành String để lưu vào Database (VD: "COMPLETED")
            ps.setString(1, status.name());
            ps.setInt(2, bookingId);
            
            int rows = ps.executeUpdate();
            System.out.println("✅ Đã cập nhật đơn #" + bookingId + " sang: " + status.name());
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

 // Lấy N đơn hàng mới nhất cho Dashboard
    public List<Booking> getRecentBookings(int limit) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT TOP (?) b.*, c.FullName, t.TableName, s.Name AS ServiceName, s.ExtraFee " +
                     "FROM Booking b JOIN Customer c ON b.CustomerID = c.CustomerID " +
                     "LEFT JOIN [Table] t ON b.TableID = t.TableID " +
                     "LEFT JOIN Service s ON b.ServiceID = s.ServiceID " +
                     "ORDER BY b.BookingTime DESC";

        try (Connection con = DBCPDataSource.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapBooking(rs));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // ================== THỐNG KÊ (DASHBOARD) ==================

    public int countTotalBookings() {
        String sql = "SELECT COUNT(*) FROM Booking";
        try (Connection con = DBCPDataSource.getDataSource().getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public double getTotalRevenue() {
        String sql = "SELECT SUM(TotalAmount) FROM Booking WHERE Status = 'COMPLETED'";
        try (Connection con = DBCPDataSource.getDataSource().getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0.0;
    }
    
    public boolean cancelBooking(int bookingId) {
        // Chúng ta sử dụng Enum BookingStatus.CANCELLED đã định nghĩa trong model Booking
        String sql = "UPDATE Booking SET Status = ? WHERE BookingID = ?";
        
        try (Connection con = DBCPDataSource.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, Booking.BookingStatus.CANCELLED.name());
            ps.setInt(2, bookingId);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // ================== TEST MAIN ==================
    public static void main(String[] args) {
        BookingDAO dao = new BookingDAO();

        System.out.println("=== ALL BOOKINGS ===");
        dao.getAll().forEach(b ->
            System.out.println(
                "Booking #" + b.getBookingId() +
                " | " + b.getCustomer().getFullName() +
                " | " + b.getStatus()
            )
        );
    }
}
