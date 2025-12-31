package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Booking;
import model.BookingDetail;
import model.Dish;

public class BookingDetailDAO {

    // ================== MAP RESULTSET ==================
    private BookingDetail mapBookingDetail(ResultSet rs) throws Exception {
        BookingDetail bd = new BookingDetail();

        // Booking
        Booking booking = new Booking();
        booking.setBookingId(rs.getInt("BookingID"));
        bd.setBooking(booking);

        // Dish
        Dish dish = new Dish();
        dish.setDishId(rs.getInt("DishID"));
        dish.setName(rs.getString("DishName"));
        dish.setImageUrl(rs.getString("ImageURL"));
        bd.setDish(dish);

        bd.setQuantity(rs.getInt("Quantity"));
        bd.setPrice(rs.getDouble("PriceAtOrder"));
        bd.setTotal(rs.getDouble("Total"));

        return bd;
    }

    /**
     * Lấy danh sách món theo BookingID
     */
    public List<BookingDetail> getByBookingId(int bookingId) {
        List<BookingDetail> list = new ArrayList<>();

        String sql = """
            SELECT bd.BookingID, bd.DishID,
                   d.Name AS DishName, d.ImageURL,
                   bd.Quantity, bd.PriceAtOrder, bd.Total
            FROM BookingDetail bd
            JOIN Dish d ON bd.DishID = d.DishID
            WHERE bd.BookingID = ?
        """;

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, bookingId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapBookingDetail(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Thêm món vào Booking
     */
    public boolean insert(int bookingId, BookingDetail detail) {
        String sql = """
            INSERT INTO BookingDetail
            (BookingID, DishID, Quantity, PriceAtOrder, Total)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, bookingId);
            ps.setInt(2, detail.getDish().getDishId());
            ps.setInt(3, detail.getQuantity());
            ps.setDouble(4, detail.getPrice());
            ps.setDouble(5, detail.getTotal());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Xóa toàn bộ món của Booking
     */
    public boolean deleteByBookingId(int bookingId) {
        String sql = "DELETE FROM BookingDetail WHERE BookingID = ?";

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, bookingId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Xóa 1 món trong Booking
     */
    public boolean deleteDish(int bookingId, int dishId) {
        String sql = """
            DELETE FROM BookingDetail
            WHERE BookingID = ? AND DishID = ?
        """;

        try (
            Connection con = DBCPDataSource.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, bookingId);
            ps.setInt(2, dishId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================== TEST MAIN ==================
    public static void main(String[] args) {
        BookingDetailDAO dao = new BookingDetailDAO();

        System.out.println("=== BOOKING DETAILS (BookingID = 1) ===");
        dao.getByBookingId(1).forEach(bd ->
            System.out.println(
                bd.getDishName() +
                " | SL: " + bd.getQuantity() +
                " | Giá: " + bd.getPrice() +
                " | Tổng: " + bd.getTotal()
            )
        );
    }
}
