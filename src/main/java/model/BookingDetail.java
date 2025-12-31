package model;

/**
 * Lớp BookingDetail đại diện cho thông tin chi tiết của từng món ăn
 * trong một đơn đặt bàn (Booking). Mỗi BookingDetail tương ứng với một món
 * được order, bao gồm món ăn, số lượng, giá tại thời điểm đặt và tổng tiền.
 */
public class BookingDetail {

    /** Đơn đặt bàn mà chi tiết này thuộc về */
    private Booking booking;

    /** Món ăn được khách chọn */
    private Dish dish;

    /** Số lượng món được đặt */
    private int quantity;

    /** Giá món tại thời điểm đặt (đảm bảo không thay đổi khi giá món thay đổi sau này) */
    private double price;

    /** Tổng tiền = price * quantity */
    private double total;

    /**
     * Constructor mặc định.
     */
    public BookingDetail() {}

    /**
     * Constructor khởi tạo chi tiết đặt món
     *
     * @param booking  Đơn đặt bàn
     * @param dish     Món ăn
     * @param quantity Số lượng món
     */
    public BookingDetail(Dish dish, int quantity, double price) {
        this.dish = dish;
        this.quantity = quantity;
        this.price = price;
        this.total = price * quantity;
    }

    // Getters & Setters
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
        if (this.price == 0) {  
            this.price = dish.getPrice();
        }
        this.total = this.price * this.quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.total = this.price * quantity;
    }

    public void setPrice(double price) {
		this.price = price;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getPrice() {
        return price;
    }

    public double getTotal() {
        return total;
    }
    
    public String getDishName() { return dish.getName(); }
    public String getDishImage() { return dish.getImageUrl(); }

}
