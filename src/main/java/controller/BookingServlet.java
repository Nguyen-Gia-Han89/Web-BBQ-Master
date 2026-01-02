package controller;

import dao.BookingDAO;
import dao.ServiceDAO;
import dao.SpaceDAO;
import dao.TableDAO;
import model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
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

		HttpSession session = request.getSession(false);

		// Chưa đăng nhập → quay lại trang trước + requireLogin
		if (session == null || session.getAttribute("customer") == null) {
			String referer = request.getHeader("Referer");
			String redirect = (referer != null) ? referer + (referer.contains("?") ? "&" : "?") + "requireLogin=true"
					: request.getContextPath() + "/?requireLogin=true";
			response.sendRedirect(redirect);
			return;
		}

		// Load dữ liệu cho JSP
		String path = request.getServletPath();
		request.setAttribute("spaces", new SpaceDAO().getAllSpaces());
		request.setAttribute("availableTables", new TableDAO().getAll());
		request.setAttribute("services", new ServiceDAO().getAll());

		if (path.equals("/party-booking")) {
			// Trang đặt tiệc: Chỉ cần Space, không cần Table lẻ
			request.getRequestDispatcher("/booking-party/book-party.jsp").forward(request, response);
		} else {
			// Trang đặt bàn: Cần đủ Table
			request.setAttribute("availableTables", new TableDAO().getAll());
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
			int tableId = 0; 
			if (tableIdStr != null && !tableIdStr.trim().isEmpty()) {
				tableId = Integer.parseInt(tableIdStr);
			}

			String date = request.getParameter("date");
			String time = request.getParameter("time");
			String note = request.getParameter("note");
			String serviceIdStr = request.getParameter("serviceId");
			String amountStr = request.getParameter("amount");

			// Kiểm tra date và time trước khi parse để tránh lỗi LocalDateTime
			if (date == null || time == null) {
				throw new Exception("Vui lòng chọn ngày và giờ!");
			}
			LocalDateTime bookingTime = LocalDateTime.parse(date + "T" + time);

			/* -------- 2. Tạo Booking -------- */
			Booking booking = new Booking();
			booking.setCustomer(customer);
			booking.setNumberOfGuests(guests);
			booking.setBookingTime(bookingTime);
			booking.setNote(note);
			booking.setStatus(Booking.BookingStatus.PENDING);

			// Tự động xác định loại Booking dựa trên URL
			String path = request.getServletPath();
			if (path.equals("/party-booking")) {
				booking.setBookingType(Booking.BookingType.PARTY); // Giả sử bạn có enum PARTY
			} else {
				booking.setBookingType(Booking.BookingType.DINE_IN);
			}

			// Xử lý Bàn: Chỉ set nếu tableId > 0
			if (tableId > 0) {
				Table table = new Table();
				table.setTableId(tableId);
				booking.setTable(table);
			} else {
				booking.setTable(null); // Đặt tiệc thì không có bàn lẻ
			}

			/* -------- 3. Lấy món ăn từ cart -------- */
			Booking cart = (Booking) session.getAttribute("cart");
			List<BookingDetail> details = new ArrayList<>();

			if (cart != null && cart.getBookingDetails() != null) {
				for (BookingDetail d : cart.getBookingDetails()) {
					d.setBooking(booking);
					details.add(d);
				}
			}
			booking.setBookingDetails(details);

			/* -------- 4. Lưu DB -------- */
			BookingDAO bookingDAO = new BookingDAO();
			int bookingId = bookingDAO.insert(booking);

			if (bookingId <= 0) {
				throw new Exception("Không thể tạo đơn đặt bàn");
			}

			/* -------- 5. Hậu xử lý -------- */
			booking.generateAndSetOrderCode(bookingId);

			Table fullTable = new TableDAO().getById(tableId);

			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy");
			String bookingTimeStr = bookingTime.format(fmt);

			session.setAttribute("ORDER_CODE", booking.getOrderCode());
			session.setAttribute("BOOKING_TIME_STR", bookingTimeStr);
			session.setAttribute("GUESTS", guests);

			session.setAttribute("TABLE_NAME", fullTable != null ? fullTable.getTableName() : "Nhân viên sắp xếp");

			session.setAttribute("SPACE_NAME",
					(fullTable != null && fullTable.getSpace() != null) ? fullTable.getSpace().getName()
							: "Tầng 1 - Khu thường");

			/* -------- 6. Thanh toán -------- */
			double totalAmount = 0;
			try {
				totalAmount = Double.parseDouble(amountStr);
			} catch (Exception ignored) {
			}

			session.setAttribute("TOTAL_AMOUNT", totalAmount);

			if (totalAmount > 0) {
				session.setAttribute("PAYMENT_AMOUNT", totalAmount);
				session.setAttribute("BOOKING_ID", bookingId);
				response.sendRedirect(request.getContextPath() + "/payment");
			} else {
				bookingDAO.updateStatus(bookingId, Booking.BookingStatus.CONFIRMED);
				session.removeAttribute("cart");
				response.sendRedirect(request.getContextPath() + "/booking-table/booking-success.jsp");
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
			doGet(request, response);
		}
	}
}
