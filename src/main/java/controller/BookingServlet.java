package controller;

import dao.BookingDAO;
import dao.ServiceDAO;
import dao.SpaceDAO;
import dao.TableDAO;
import model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.bbqmaster.util.MailService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet({ "/booking-table", "/party-booking" })
public class BookingServlet extends HttpServlet {

	/*
	 * ======================= GET: Hiển thị form đặt bàn =======================
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
	    
	    // Lấy thông tin từ URL
	    String date = request.getParameter("date");
	    String time = request.getParameter("time");

	    // Nếu lần đầu truy cập (chưa có param)
	    if (date == null) date = java.time.LocalDate.now().toString();
	    if (time == null) time = "10:00";

	    // 1. Lấy trạng thái bàn từ Database
	    TableDAO tableDAO = new TableDAO();
	    List<Table> allTables = tableDAO.getAllWithStatus(date, time);
	    
	    // 2. Đẩy dữ liệu ra Request
	    request.setAttribute("availableTables", allTables);
	    request.setAttribute("spaces", new SpaceDAO().getAllSpaces());
	    request.setAttribute("services", new ServiceDAO().getAll());
	    
	    // Giữ lại giá trị để JSP hiển thị đúng ngày/giờ vừa chọn
	    request.setAttribute("selectedDate", date);
	    request.setAttribute("selectedTime", time);

	    String path = request.getServletPath();
	    
	    if ("/party-booking".equals(path)) {
	        // Nếu là đặt tiệc, mở trang tiệc (hoặc trang đặt bàn nhưng với giao diện khác)
	        request.getRequestDispatcher("/booking-party/book-party.jsp").forward(request, response);
	    } else {
	        // Mặc định mở trang đặt bàn thường
	        request.getRequestDispatcher("/booking-table/book-table.jsp").forward(request, response);
	    }
	}

	/*
	 * ======================= POST: Xử lý đặt bàn =======================
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    HttpSession session = request.getSession();
	    Customer customer = (Customer) session.getAttribute("customer");

	    if (customer == null) {
	        response.sendRedirect(request.getContextPath() + "/login.jsp");
	        return;
	    }

	    try {
	        /* -------- 1. Đọc dữ liệu form -------- */
	        int guests = Integer.parseInt(request.getParameter("guests"));
	        String tableIdStr = request.getParameter("tableId");
	        String date = request.getParameter("date");
	        String time = request.getParameter("time");
	        String note = request.getParameter("note");
	        String serviceIdStr = request.getParameter("serviceId");

	        if (date == null || time == null) throw new Exception("Vui lòng chọn ngày và giờ!");
	        LocalDateTime bookingTime = LocalDateTime.parse(date + "T" + time);

	        /* -------- 2. Khởi tạo đối tượng Booking -------- */
	        Booking booking = new Booking();
	        booking.setCustomer(customer);
	        booking.setNumberOfGuests(guests);
	        booking.setBookingTime(bookingTime);
	        booking.setNote(note);
	        booking.setStatus(Booking.BookingStatus.PENDING);

	        // Xác định loại Booking TRƯỚC khi tính tiền
	        String path = request.getServletPath();
	        boolean isParty = path.equals("/party-booking");
	        booking.setBookingType(isParty ? Booking.BookingType.PARTY : Booking.BookingType.DINE_IN);

	        // Lấy dịch vụ (nếu có)
	        Service service = null;
	        if (serviceIdStr != null && !serviceIdStr.isBlank()) {
	            service = new ServiceDAO().getServiceById(Integer.parseInt(serviceIdStr));
	            booking.setService(service);
	        }

	        // Xử lý bàn (chỉ cho đặt bàn thường)
	        Table fullTable = null;
	        if (!isParty && tableIdStr != null && !tableIdStr.trim().isEmpty()) {
	            fullTable = new TableDAO().getById(Integer.parseInt(tableIdStr));
	            booking.setTable(fullTable);
	        }

	        /* -------- 3. Lấy món ăn & TÍNH TIỀN CHUẨN -------- */
	        Booking cart = (Booking) session.getAttribute("cart");
	        List<BookingDetail> details = new ArrayList<>();
	        if (cart != null && cart.getBookingDetails() != null) {
	            for (BookingDetail d : cart.getBookingDetails()) {
	                d.setBooking(booking);
	                details.add(d);
	            }
	        }
	        booking.setBookingDetails(details);
	        
	        // GỌI DUY NHẤT 1 LẦN Ở ĐÂY - Model sẽ tự cộng 500k nếu là PARTY
	        booking.calculateTotalAmount(); 
	        double totalAmount = booking.getTotalAmount();

	        /* -------- 4. Lưu Database -------- */
	        BookingDAO bookingDAO = new BookingDAO();
	        int bookingId = bookingDAO.insert(booking);
	        if (bookingId <= 0) throw new Exception("Không thể tạo đơn đặt bàn");

	        /* -------- 5. Thiết lập Session để hiển thị ở trang tiếp theo -------- */
	        session.setAttribute("ORDER_CODE", booking.getOrderCode());
	        session.setAttribute("TOTAL_AMOUNT", totalAmount);
	        session.setAttribute("BOOKING_TIME_STR", bookingTime.format(DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy")));
	        session.setAttribute("GUESTS", guests);
	        session.setAttribute("TABLE_NAME", fullTable != null ? fullTable.getTableName() : "Nhân viên sắp xếp");
	        session.setAttribute("SPACE_NAME", (fullTable != null && fullTable.getSpace() != null) ? fullTable.getSpace().getName() : "Khu vực tiệc");
	        session.setAttribute("ORDER_CODE", booking.getOrderCode());
	        
	        // Thông tin phí để hiển thị minh bạch
	        if (isParty) {
	            session.setAttribute("SERVICE_NAME", "Phí tổ chức tiệc (Mặc định)");
	            session.setAttribute("PARTY_FEE", 500000);
	        } else {
	            session.setAttribute("SERVICE_NAME", service != null ? service.getName() : "Tự nướng");
	        }

	        /* -------- 6. Điều hướng Thanh toán hoặc Hoàn tất -------- */
	        if (isParty || totalAmount > 0) {
	            // Lấy số tiền cọc người dùng đã thấy trên giao diện JSP
	            String depositStr = request.getParameter("amount"); 
	            double amountToPay = (depositStr != null) ? Double.parseDouble(depositStr) : totalAmount;

	            session.setAttribute("PAYMENT_AMOUNT", amountToPay); // Đây là số sẽ nhân 100 ở PaymentServlet
	            session.setAttribute("BOOKING_ID", bookingId);
	            session.setAttribute("PAYMENT_TYPE", isParty ? "DEPOSIT" : "ORDER");
	            
	            session.removeAttribute("cart"); 
	            response.sendRedirect(request.getContextPath() + "/payment");
	        } else {
	            bookingDAO.updateStatus(bookingId, Booking.BookingStatus.CONFIRMED);
	            session.removeAttribute("cart");
	            MailService.sendBookingEmail(booking, customer.getEmail());
	            response.sendRedirect(request.getContextPath() + "/booking-table/booking-success.jsp");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("error", e.getMessage());
	        doGet(request, response);
	    }
	}
}
