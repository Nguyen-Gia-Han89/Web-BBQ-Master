package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
        String sql = """
            INSERT INTO Booking
            (CustomerID, TableID, ServiceID, BookingTime,
             NumberOfGuests, Note, TotalAmount, Status, BookingType)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, booking.getCustomer().getCustomerID());
            ps.setObject(2, booking.getTable() != null ? booking.getTable().getTableId() : null);
            ps.setObject(3, booking.getService() != null ? booking.getService().getServiceID() : null);
            ps.setTimestamp(4, java.sql.Timestamp.valueOf(booking.getBookingTime()));
            ps.setInt(5, booking.getNumberOfGuests());
            ps.setString(6, booking.getNote());
            ps.setDouble(7, booking.getTotalAmount());
            ps.setString(8, booking.getStatus().name());
            ps.setString(9, booking.getBookingType().name());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // BookingID
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
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
