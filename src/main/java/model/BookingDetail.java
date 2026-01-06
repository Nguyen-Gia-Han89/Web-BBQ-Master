package model;

/**
 * Đại diện cho 1 món ăn trong Booking
 */
public class BookingDetail {

    /** Booking chứa món này */
    private Booking booking;

    /** Món ăn */
    private Dish dish;

    /** Số lượng */
    private int quantity;

    /** Giá tại thời điểm đặt */
    private double price;

    /** Tổng tiền = price * quantity */
    private double total;

    /** Ghi chú */
    private String note;

    // ===== CONSTRUCTOR =====
    public BookingDetail() {}

    public BookingDetail(Dish dish, int quantity, double price) {
        this.dish = dish;
        this.quantity = quantity;
        this.price = price;
        recalculateTotal();
    }

    // ===== GETTERS / SETTERS =====
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

	public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // ===== TIỆN ÍCH =====
    private void recalculateTotal() {
        this.total = this.price * this.quantity;
    }

    // Dùng cho JSP / Email
    public String getDishName() {
        return dish != null ? dish.getName() : "";
    }

    public String getDishImage() {
        return dish != null ? dish.getImageUrl() : "";
    }
}
