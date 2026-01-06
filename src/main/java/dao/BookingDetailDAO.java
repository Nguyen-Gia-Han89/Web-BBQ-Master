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

    // Map ResultSet
    private BookingDetail mapBookingDetail(ResultSet rs) throws Exception {
        BookingDetail bd = new BookingDetail();

        Booking booking = new Booking();
        booking.setBookingId(rs.getInt("BookingID"));
        bd.setBooking(booking);

        Dish dish = new Dish();
        dish.setDishId(rs.getInt("DishID"));
        dish.setName(rs.getString("DishName"));
        bd.setDish(dish);

        bd.setQuantity(rs.getInt("Quantity"));
        bd.setPrice(rs.getDouble("PriceAtOrder")); // PriceAtOrder lưu trong DB

        return bd;
    }

    public List<BookingDetail> getByBookingId(int bookingId) {
        List<BookingDetail> list = new ArrayList<>();
        String sql = """
            SELECT bd.BookingID, bd.DishID, bd.Quantity, bd.PriceAtOrder, d.Name AS DishName
            FROM BookingDetail bd
            JOIN Dish d ON bd.DishID = d.DishID
            WHERE bd.BookingID = ?
        """;

        try (Connection con = DBCPDataSource.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
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

    public boolean insert(int bookingId, BookingDetail detail) {
        String sql = """
            INSERT INTO BookingDetail (BookingID, DishID, Quantity, PriceAtOrder)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection con = DBCPDataSource.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ps.setInt(2, detail.getDish().getDishId());
            ps.setInt(3, detail.getQuantity());
            ps.setDouble(4, detail.getPrice());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteByBookingId(int bookingId) {
        String sql = "DELETE FROM BookingDetail WHERE BookingID = ?";
        try (Connection con = DBCPDataSource.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteDish(int bookingId, int dishId) {
        String sql = "DELETE FROM BookingDetail WHERE BookingID = ? AND DishID = ?";
        try (Connection con = DBCPDataSource.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
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
		dao.getByBookingId(1).forEach(bd -> System.out.println(bd.getDishName() + " | SL: " + bd.getQuantity()
				+ " | Giá: " + bd.getPrice() + " | Tổng: " + bd.getTotal()));

	}
}
