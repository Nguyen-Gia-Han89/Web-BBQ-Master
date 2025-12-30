package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Booking;
import model.Booking.BookingStatus;
import model.Booking.BookingType;
import model.BookingDetail;
import model.Customer;
import model.Service;
import model.Table;

import dao.BookingDAO;
import dao.ServiceDAO;
import dao.SpaceDAO;
import dao.TableDAO;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/booking-table")
public class BookingServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		if (session.getAttribute("customer") == null) {
			String referer = request.getHeader("Referer");
			String redirectUrl = (referer != null) ? referer : request.getContextPath() + "/";
			String separator = redirectUrl.contains("?") ? "&" : "?";
			response.sendRedirect(redirectUrl + separator + "requireLogin=true");
			return;
		}

		// Load dữ liệu cho các bước chọn bàn và dịch vụ
		SpaceDAO spaceDAO = new SpaceDAO();
		TableDAO tableDAO = new TableDAO();
		ServiceDAO serviceDAO = new ServiceDAO();

		request.setAttribute("spaces", spaceDAO.getAllSpaces());
		request.setAttribute("availableTables", tableDAO.getAll());
		request.setAttribute("services", serviceDAO.getAll());

		request.getRequestDispatcher("/pages/book-table.jsp").forward(request, response);
	}

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
			// 1. Đọc dữ liệu từ Form
			int guests = Integer.parseInt(request.getParameter("guests"));
			String date = request.getParameter("date");
			String time = request.getParameter("time");
			String note = request.getParameter("note");
			int tableId = Integer.parseInt(request.getParameter("tableId"));
			String serviceIdStr = request.getParameter("serviceId");
			String totalAmountStr = request.getParameter("amount"); // Tổng tiền từ JS ở Tab 3

			// 2. Khởi tạo đối tượng Booking chính
			Booking booking = new Booking();
			booking.setCustomer(customer);
			booking.setNumberOfGuests(guests);
			booking.setBookingTime(LocalDateTime.parse(date + "T" + time));
			booking.setNote(note);
			booking.setStatus(BookingStatus.PENDING);
			booking.setBookingType(BookingType.DINE_IN);

			// Gán bàn
			Table table = new Table();
			table.setTableId(tableId);
			booking.setTable(table);

			// Xử lý Dịch vụ (Fix lỗi Foreign Key ID=0)
			if (serviceIdStr != null && !serviceIdStr.trim().isEmpty() && !serviceIdStr.equals("0")) {
				Service service = new Service();
				service.setServiceID(Integer.parseInt(serviceIdStr));
				booking.setService(service);
			} else {
				booking.setService(null); // Gán null để Database hiểu là không dùng dịch vụ
			}

			// 3. Xử lý danh sách món ăn từ giỏ hàng (Cart) trong Session
			Booking cart = (Booking) session.getAttribute("cart");
			List<BookingDetail> details = new ArrayList<>();

			if (cart != null && cart.getBookingDetails() != null) {
				for (BookingDetail item : cart.getBookingDetails()) {
					item.setBooking(booking); // Thiết lập mối quan hệ cha-con
					details.add(item);
				}
			}
			booking.setBookingDetails(details);

			// 4. Thực hiện lưu vào Database qua DAO
			BookingDAO dao = new BookingDAO();
			int bookingId = dao.insert(booking);

			if (bookingId > 0) {
			    // 1. Đồng bộ mã đơn hàng
			    booking.generateAndSetOrderCode(bookingId); 

			    // 2. Lấy thông tin chi tiết Bàn và Không gian từ DB
			    TableDAO tableDAO = new TableDAO();
			    Table fullTableInfo = tableDAO.getById(tableId); 

			    // 3. ĐỊNH DẠNG NGÀY GIỜ TRƯỚC (Tránh lỗi 500 ở JSP)
			    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy");
			    String formattedTime = booking.getBookingTime().format(formatter);

			    // 4. LƯU VÀO SESSION (Lưu String thay vì Object LocalDateTime)
			    session.setAttribute("ORDER_CODE", booking.getOrderCode());
			    session.setAttribute("BOOKING_TIME_STR", formattedTime); 
			    session.setAttribute("GUESTS", booking.getNumberOfGuests());

			    // 5. Xử lý logic tên Khu vực và Bàn
			    String spaceName = "Tầng 1: Khu thường"; 
			    String tableName = "Nhân viên sắp xếp";

			    if (fullTableInfo != null) {
			        tableName = fullTableInfo.getTableName();
			        if (fullTableInfo.getSpace() != null) {
			            spaceName = fullTableInfo.getSpace().getName();
			        }
			    }

			    session.setAttribute("SPACE_NAME", spaceName);
			    session.setAttribute("TABLE_NAME", tableName);
			    
			    // 6. Xử lý tiền
			    double finalAmount = 0;
			    try {
			        String amountStr = request.getParameter("amount");
			        finalAmount = (amountStr != null) ? Double.parseDouble(amountStr) : 0;
			    } catch (NumberFormatException e) { 
			        finalAmount = 0; 
			    }
			    session.setAttribute("TOTAL_AMOUNT", finalAmount);

			    // 7. Phân luồng chuyển hướng
			    if (finalAmount > 0) {
			        session.setAttribute("PAYMENT_AMOUNT", finalAmount);
			        session.setAttribute("BOOKING_ID", bookingId);
			        response.sendRedirect(request.getContextPath() + "/payment");
			    } else {
			        dao.updateStatus(bookingId, BookingStatus.CONFIRMED);
			        session.removeAttribute("cart");
			        response.sendRedirect(request.getContextPath() + "/pages/booking-success.jsp");
			    }
			} else {
			    throw new Exception("Lỗi lưu dữ liệu đặt bàn.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Lỗi: " + e.getMessage());
			doGet(request, response); // Quay lại trang đặt bàn và hiển thị lỗi
		}
	}
}