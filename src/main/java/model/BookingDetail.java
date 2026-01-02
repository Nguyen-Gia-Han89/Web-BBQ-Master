package model;

/**
 * Lớp BookingDetail đại diện cho chi tiết từng món ăn
 * trong một đơn Booking.
 *
 * Booking có thể là:
 * - Đặt bàn (DINE_IN)
 * - Đặt tiệc (PARTY)
 *
 * Mỗi BookingDetail tương ứng với một món ăn trong thực đơn,
 * bao gồm số lượng, giá tại thời điểm đặt và tổng tiền.
 */
public class BookingDetail {

    /** Đơn đặt (đặt bàn hoặc đặt tiệc) mà chi tiết này thuộc về */
    private Booking booking;

    /** Món ăn được khách chọn */
    private Dish dish;

    /** Số lượng món được đặt (tiệc có thể số lượng lớn) */
    private int quantity;

    /** Giá món tại thời điểm đặt */
    private double price;

    /** Tổng tiền = price * quantity */
    private double total;

    /** Ghi chú cho món (ít cay, không hành, dành cho tiệc...) */
    private String note;

    /** Constructor mặc định */
    public BookingDetail() {}

    /**
     * Constructor khởi tạo chi tiết món ăn
     *
     * @param dish     Món ăn
     * @param quantity Số lượng
     * @param price    Giá tại thời điểm đặt
     */
    public BookingDetail(Dish dish, int quantity, double price) {
        this.dish = dish;
        this.quantity = quantity;
        this.price = price;
        this.total = price * quantity;
    }

    // ===== GETTERS & SETTERS =====

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
        if (this.price == 0 && dish != null) {
            this.price = dish.getPrice();
        }
        recalculateTotal();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        recalculateTotal();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        recalculateTotal();
    }

    public double getTotal() {
        return total;
    }

    private void recalculateTotal() {
        this.total = this.price * this.quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // ===== TIỆN ÍCH CHO JSP =====
    public String getDishName() {
        return dish != null ? dish.getName() : "";
    }

    public String getDishImage() {
        return dish != null ? dish.getImageUrl() : "";
    }

	public void setTotal(double double1) {
		// TODO Auto-generated method stub
		
	}
}
