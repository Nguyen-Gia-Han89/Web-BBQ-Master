package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
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

        Customer c = new Customer();
        c.setCustomerID(rs.getInt("CustomerID"));
        c.setFullName(rs.getString("FullName"));
        b.setCustomer(c);

        Table t = new Table();
        t.setTableId(rs.getInt("TableID"));
        t.setTableName(rs.getString("TableName"));
        b.setTable(t);

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
     * Lấy tất cả Booking
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
                    return mapBooking(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Tạo Booking mới
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
            con.setAutoCommit(false); // Bắt đầu Transaction để đảm bảo tính toàn vẹn

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

                            // 1. TẠO ORDER_CODE (VÍ DỤ: BBQ-20251230-1)
                            String datePart = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
                            String orderCode = "BBQ-" + datePart + "-" + newId;

                            // 2. UPDATE NGƯỢC LẠI VÀO DB
                            try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdateCode)) {
                                psUpdate.setString(1, orderCode);
                                psUpdate.setInt(2, newId);
                                psUpdate.executeUpdate();
                            }

                            // 3. LƯU CHI TIẾT MÓN ĂN
                            insertBookingDetails(con, newId, booking.getBookingDetails());

                            // Gán ngược orderCode vào đối tượng booking để Servlet có thể lấy ra session
                            booking.setOrderCode(orderCode); 

                            con.commit(); // Thành công hết thì mới lưu thật vào DB
                            return newId;
                        }
                    }
                }
            } catch (Exception e) {
                con.rollback(); // Lỗi bất cứ bước nào thì hủy hết (tránh dữ liệu rác)
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
     * Cập nhật tổng tiền Booking
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
     * Cập nhật trạng thái Booking
     */
    public boolean updateStatus(int bookingId, Booking.BookingStatus status) {
        String sql = "UPDATE Booking SET Status = ? WHERE BookingID = ?";

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, status.name());
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
