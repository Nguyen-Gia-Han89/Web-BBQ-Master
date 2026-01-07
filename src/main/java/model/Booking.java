package model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Lá»›p Ä‘áº¡i diá»‡n cho thÃ´ng tin Ä‘áº·t bÃ n/Ä‘áº·t tiá»‡c táº¡i nhÃ  hÃ ng. Bao gá»“m thÃ´ng tin
 * khÃ¡ch hÃ ng, thá»�i gian Ä‘áº·t, sá»‘ lÆ°á»£ng khÃ¡ch, loáº¡i hÃ¬nh Ä‘áº·t bÃ n, dá»‹ch vá»¥ Ä‘i kÃ¨m,
 * chi tiáº¿t mÃ³n Äƒn vÃ  tá»•ng chi phÃ­.
 */
public class Booking {
	/**
	 * Tráº¡ng thÃ¡i cá»§a Ä‘Æ¡n Ä‘áº·t bÃ n: - PENDING: Chá»� xÃ¡c nháº­n - CONFIRMED: Ä�Ã£ xÃ¡c nháº­n
	 * - CANCELLED: Ä�Ã£ há»§y - COMPLETED: HoÃ n thÃ nh
	 */
	public enum BookingStatus {
		PENDING, CONFIRMED, CANCELLED, COMPLETED
	}

	/**
	 * Loáº¡i hÃ¬nh Ä‘áº·t bÃ n: - DINE_IN: Ä‚n táº¡i nhÃ  hÃ ng - PARTY: Ä�áº·t tiá»‡c/sinh nháº­t
	 */
	public enum BookingType {
		DINE_IN, PARTY
	}

	/** MÃ£ Ä‘áº·t bÃ n */
	private int bookingId;

	/** KhÃ¡ch hÃ ng thá»±c hiá»‡n Ä‘áº·t bÃ n */
	private Customer customer;

	/** Thá»�i gian Ä‘áº·t bÃ n */
	private LocalDateTime bookingTime;

	/** Sá»‘ lÆ°á»£ng khÃ¡ch */
	private int numberOfGuests;

	/** Ghi chÃº kÃ¨m theo cá»§a khÃ¡ch hÃ ng */
	private String note;

	/** Tráº¡ng thÃ¡i Ä‘áº·t bÃ n */
	private BookingStatus status;

	/** Loáº¡i hÃ¬nh Ä‘áº·t bÃ n */
	private BookingType bookingType;

	/** Danh sÃ¡ch chi tiáº¿t mÃ³n Äƒn/Ä‘á»“ uá»‘ng */
	private List<BookingDetail> bookingDetails;

	/** BÃ n Ä‘Æ°á»£c chá»�n */
	private Table table;

	/** Dá»‹ch vá»¥ Ä‘i kÃ¨m (nhÆ° trang trÃ­, Ã¢m nháº¡c, VIP) */
	private Service service;

	/** Tá»•ng sá»‘ tiá»�n cáº§n thanh toÃ¡n */
	private double totalAmount;

	/** MÃ£ Ä‘Æ¡n hÃ ng hiá»ƒn thá»‹ cho khÃ¡ch hÃ ng (VÃ­ dá»¥: BBQ-20251230-1) */
	private String orderCode;

	/**
	 * Constructor máº·c Ä‘á»‹nh. Khá»Ÿi táº¡o danh sÃ¡ch chi tiáº¿t mÃ³n Äƒn rá»—ng vÃ  tráº¡ng thÃ¡i
	 * máº·c Ä‘á»‹nh lÃ  PENDING.
	 */
	public Booking() {
		this.bookingDetails = new ArrayList<>();
		this.status = BookingStatus.PENDING;
	}

	/**
	 * Constructor Ä‘áº§y Ä‘á»§ tham sá»‘
	 */
	public Booking(int bookingId, String orderCode, Customer customer, LocalDateTime bookingTime, int numberOfGuests,
			String note, BookingStatus status, BookingType bookingType, List<BookingDetail> bookingDetails, Table table,
			Service service, double totalAmount) {
		this.bookingId = bookingId;
		this.orderCode = orderCode; // ThÃªm má»›i
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
	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public LocalDateTime getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(LocalDateTime bookingTime) {
		this.bookingTime = bookingTime;
	}

	public int getNumberOfGuests() {
		return numberOfGuests;
	}

	public void setNumberOfGuests(int numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public BookingType getBookingType() {
		return bookingType;
	}

	public void setBookingType(BookingType bookingType) {
		this.bookingType = bookingType;
	}

	public List<BookingDetail> getBookingDetails() {
		return bookingDetails;
	}

	public void setBookingDetails(List<BookingDetail> bookingDetails) {
		this.bookingDetails = bookingDetails;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	/**
     * Táº¡o mÃ£ Ä‘Æ¡n hÃ ng tá»± Ä‘á»™ng dá»±a trÃªn ngÃ y hiá»‡n táº¡i vÃ  ID
     * @param id ID láº¥y tá»« database sau khi insert
     * @return chuá»—i BBQ-YYYYMMDD-ID
     */
    public void generateAndSetOrderCode(int id) {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.orderCode = (this.bookingType == BookingType.PARTY ? "EVT-" : "BBQ-") + datePart + "-" + id;
    }

	/**
	 * ThÃªm mÃ³n Äƒn/Ä‘á»“ uá»‘ng vÃ o danh sÃ¡ch chi tiáº¿t Ä‘áº·t bÃ n, sau Ä‘Ã³ cáº­p nháº­t tá»•ng
	 * tiá»�n.
	 * 
	 * @param detail chi tiáº¿t mÃ³n Äƒn/Ä‘á»“ uá»‘ng
	 */
    public void addDetail(BookingDetail detail) {
        if (detail != null) {
            detail.setBooking(this);
            bookingDetails.add(detail);
            calculateTotalAmount();
        }
    }


	/**
	 * TÃ­nh tá»•ng chi phÃ­ = tá»•ng tiá»�n mÃ³n Äƒn + phÃ­ dá»‹ch vá»¥ (náº¿u cÃ³)
	 */
    public void calculateTotalAmount() {
        // 1. Tính tổng tiền món ăn từ giỏ hàng
        double dishTotal = bookingDetails.stream().mapToDouble(BookingDetail::getTotal).sum();
        
        // 2. Lấy phí dịch vụ đi kèm (ví dụ: Trang trí 300k)
        double serviceFee = (service != null) ? service.getExtraFee() : 0;
        
        // 3. Cộng phí mặc định nếu là Đặt tiệc (Phí tổ chức 500k)
        double partyFee = 0;
        if (this.bookingType == BookingType.PARTY) {
            partyFee = 500000;
        }
        
        // Tổng cuối cùng
        this.totalAmount = dishTotal + serviceFee + partyFee;
    }

	/**
	 * Kiá»ƒm tra xem Ä‘Æ¡n Ä‘áº·t bÃ n Ä‘Ã£ Ä‘Æ°á»£c xÃ¡c nháº­n hay chÆ°a
	 *
	 * @return true náº¿u tráº¡ng thÃ¡i CONFIRMED
	 */
	public boolean isConfirmed() {
		return status == BookingStatus.CONFIRMED;
	}

	public BookingDetail[] getBookingDetailsArray() {
		return bookingDetails.toArray(new BookingDetail[0]);
	}

	public void addDish(Dish dish, int quantity) {
		if (dish == null || quantity <= 0)
			return;

		for (BookingDetail detail : bookingDetails) {
			if (detail.getDish().getDishId() == dish.getDishId()) {
				detail.setQuantity(detail.getQuantity() + quantity);
				calculateTotalAmount();
				return;
			}
		}

		BookingDetail newDetail = new BookingDetail();
		newDetail.setBooking(this);
		newDetail.setDish(dish);
		newDetail.setQuantity(quantity);
		newDetail.setPrice(dish.getPrice());
		bookingDetails.add(newDetail);


		calculateTotalAmount();
	}

	public int getTotalQuantity() {
		int total = 0;
		for (BookingDetail d : bookingDetails) {
			total += d.getQuantity();
		}
		return total;
	}

	public void removeDish(Dish selectedDish) {
		if (selectedDish == null)
			return;
		// Loáº¡i bá»� táº¥t cáº£ chi tiáº¿t cÃ³ dishId báº±ng vá»›i mÃ³n Ä‘Æ°á»£c chá»�n
		bookingDetails.removeIf(detail -> detail.getDish().getDishId() == selectedDish.getDishId());
		// Cáº­p nháº­t láº¡i tá»•ng tiá»�n sau khi xÃ³a
		calculateTotalAmount();

	}

	// Láº¥y BookingDetail theo dishId
	public BookingDetail findDetailByDishId(int dishId) {
		for (BookingDetail detail : bookingDetails) {
			if (detail.getDish().getDishId() == dishId)
				return detail;
		}
		return null;
	}

	// alias cho servlet cÅ©
	public BookingDetail getDetailByDishId(int dishId) {
		return findDetailByDishId(dishId);
	}

	// Set trá»±c tiáº¿p sá»‘ lÆ°á»£ng
	public void setQuantity(Dish dish, int quantity) {
		if (dish == null)
			return;
		for (BookingDetail detail : bookingDetails) {
			if (detail.getDish().getDishId() == dish.getDishId()) {
				detail.setQuantity(quantity);
				calculateTotalAmount();
				return;
			}
		}
		// Náº¿u chÆ°a cÃ³ mÃ³n nÃ y thÃ¬ thÃªm má»›i
		if (quantity > 0)
			addDish(dish, quantity);
	}

	public void removeDetailByDishId(int dishId) {
		if (bookingDetails == null || bookingDetails.isEmpty()) {
			return;
		}
		// Duyá»‡t qua danh sÃ¡ch vÃ  xÃ³a pháº§n tá»­ cÃ³ dishId trÃ¹ng khá»›p
		bookingDetails.removeIf(detail -> detail.getDish().getDishId() == dishId);
	}

	/**
	 * Tráº£ vá»� tá»•ng tiá»�n dÆ°á»›i dáº¡ng chuá»—i Ä‘á»‹nh dáº¡ng tiá»�n tá»‡ Viá»‡t Nam (VÃ­ dá»¥:
	 * 1.200.000Ä‘)
	 */
	public String getFormattedTotalAmount() {
		return String.format("%,.0fÄ‘", totalAmount);
	}

	/**
	 * Tráº£ vá»� thá»�i gian Ä‘áº·t bÃ n dÆ°á»›i dáº¡ng chuá»—i dá»… Ä‘á»�c (VÃ­ dá»¥: 18:30 - 30/12/2025)
	 */
	public String getFormattedBookingTime() {
		if (bookingTime == null)
			return "";
		DateTimeFormatter formatter = java.time.format.DateTimeFormatter
				.ofPattern("HH:mm - dd/MM/yyyy");
		return bookingTime.format(formatter);
	}
	
	public java.util.Date getBookingTimeAsDate() {
	    return Timestamp.valueOf(this.bookingTime);
	}

}
