package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp đại diện cho thông tin đặt bàn/đặt tiệc tại nhà hàng.
 * Bao gồm thông tin khách hàng, thời gian đặt, số lượng khách,
 * loại hình đặt bàn, dịch vụ đi kèm, chi tiết món ăn và tổng chi phí.
 */
public class Booking {
	/**
     * Trạng thái của đơn đặt bàn:
     * - PENDING: Chờ xác nhận
     * - CONFIRMED: Đã xác nhận
     * - CANCELLED: Đã hủy
     * - COMPLETED: Hoàn thành
     */
    public enum BookingStatus {
        PENDING, CONFIRMED, CANCELLED, COMPLETED
    }

    /**
     * Loại hình đặt bàn:
     * - DINE_IN: Ăn tại nhà hàng
     * - PARTY: Đặt tiệc/sinh nhật
     */
    public enum BookingType {
        DINE_IN, PARTY
    }

    /** Mã đặt bàn */
    private int bookingId;

    /** Khách hàng thực hiện đặt bàn */
    private Customer customer;

    /** Thời gian đặt bàn */
    private LocalDateTime bookingTime;

    /** Số lượng khách */
    private int numberOfGuests;

    /** Ghi chú kèm theo của khách hàng */
    private String note;

    /** Trạng thái đặt bàn */
    private BookingStatus status;

    /** Loại hình đặt bàn */
    private BookingType bookingType;

    /** Danh sách chi tiết món ăn/đồ uống */
    private List<BookingDetail> bookingDetails;

    /** Bàn được chọn */
    private Table table;

    /** Dịch vụ đi kèm (như trang trí, âm nhạc, VIP) */
    private Service service;

    /** Tổng số tiền cần thanh toán */
    private double totalAmount;

    /**
     * Constructor mặc định.
     * Khởi tạo danh sách chi tiết món ăn rỗng và trạng thái mặc định là PENDING.
     */
    public Booking() {
        this.bookingDetails = new ArrayList<>();
        this.status = BookingStatus.PENDING;
    }
    
    /**
     * Constructor đầy đủ tham số
     */
    public Booking(int bookingId, Customer customer, LocalDateTime bookingTime,
                   int numberOfGuests, String note, BookingStatus status,
                   BookingType bookingType, List<BookingDetail> bookingDetails,
                   Table table, Service service, double totalAmount) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.bookingTime = bookingTime;
        this.numberOfGuests = numberOfGuests;
        this.note = note;
        this.status = status;
        this.bookingType = bookingType;
        this.bookingDetails = bookingDetails != null ? bookingDetails : new ArrayList<>();
        this.table = table;
        this.service = service;
        this.totalAmount = totalAmount;
    }

    // Getters & Setters
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }

    public int getNumberOfGuests() { return numberOfGuests; }
    public void setNumberOfGuests(int numberOfGuests) { this.numberOfGuests = numberOfGuests; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }

    public BookingType getBookingType() { return bookingType; }
    public void setBookingType(BookingType bookingType) { this.bookingType = bookingType; }

    public List<BookingDetail> getBookingDetails() { return bookingDetails; }
    public void setBookingDetails(List<BookingDetail> bookingDetails) { this.bookingDetails = bookingDetails; }

    public Table getTable() { return table; }
    public void setTable(Table table) { this.table = table; }

    public Service getService() { return service; }
    public void setService(Service service) { this.service = service; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    /**
     * Thêm món ăn/đồ uống vào danh sách chi tiết đặt bàn,
     * sau đó cập nhật tổng tiền.
     * @param detail chi tiết món ăn/đồ uống
     */
    public void addDetail(BookingDetail detail) {
        if (detail != null) {
            bookingDetails.add(detail);
            calculateTotalAmount();
        }
    }
    
    /**
     * Tính tổng chi phí = tổng tiền món ăn + phí dịch vụ (nếu có)
     */
    public void calculateTotalAmount() {
        double dishTotal = bookingDetails.stream().mapToDouble(BookingDetail::getTotal).sum();
        double serviceFee = service != null ? service.getExtraFee() : 0;
        this.totalAmount = dishTotal + serviceFee;
    }

    /**
     * Kiểm tra xem đơn đặt bàn đã được xác nhận hay chưa
     *
     * @return true nếu trạng thái CONFIRMED
     */
    public boolean isConfirmed() { return status == BookingStatus.CONFIRMED; }

    public BookingDetail[] getBookingDetailsArray() {
        return bookingDetails.toArray(new BookingDetail[0]);
    }
    
    public void addDish(Dish dish, int quantity) {
        if (dish == null || quantity <= 0) return;

        // Kiểm tra món đã tồn tại chưa
        for (BookingDetail detail : bookingDetails) {
            if (detail.getDish().getDishId() == dish.getDishId()) {
                // Nếu đã có, cộng dồn số lượng
                detail.setQuantity(detail.getQuantity() + quantity);
                return;
            }
        }

        // Nếu chưa có, tạo mới BookingDetail
        BookingDetail newDetail = new BookingDetail();
        newDetail.setDish(dish);
        newDetail.setQuantity(quantity);
        newDetail.setPrice(dish.getPrice()); // gán giá hiện tại
        bookingDetails.add(newDetail);
    }

    public int getTotalQuantity() {
        int total = 0;
        for (BookingDetail d : bookingDetails) {
            total += d.getQuantity();
        }
        return total;
    }


}
