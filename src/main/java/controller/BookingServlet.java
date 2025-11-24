package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Booking;
import model.Customer;
import model.Dish;
import model.Service;
import model.Table;
import model.BookingDetail;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class BookingServlet
 */
@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		String name = request.getParameter("name");
		HttpSession session = request.getSession();
	
		// Thông tin khách hàng đã đăng nhập
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            response.sendRedirect("login.jsp"); 
            return;
        }
        
        // Lấy thông tin từ form
        int guests = Integer.parseInt(request.getParameter("guests"));
        String note = request.getParameter("note");
        String tableParam = request.getParameter("selectedTable");
        String serviceName = request.getParameter("service");
        String combo = request.getParameter("combo");
        String time = request.getParameter("time");
        String date = request.getParameter("date");

        // Tạo Booking mới
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setNumberOfGuests(guests);
        booking.setNote(note);
        booking.setBookingTime(LocalDateTime.now()); // thời gian tạo đơn
        booking.setStatus(Booking.BookingStatus.PENDING);
        booking.setBookingType(Booking.BookingType.DINE_IN);

        if (tableParam != null && !tableParam.isEmpty()) {
            int tableId = Integer.parseInt(tableParam);
            // Gán bàn (nếu có Table class)
            if (tableId <= 0) {
                Table table = new Table();
                table.setTableId(tableId);
                booking.setTable(table);
            }
        }

        // Gán dịch vụ (nếu có)
        if (serviceName != null && !serviceName.isEmpty()) {
            Service service = new Service();
            service.setName(serviceName);
            service.setExtraFee(0); // có thể gán phí nếu cần
            booking.setService(service);
        }

        // Gán combo (nếu có)
        if (combo != null && !combo.isEmpty()) {
            // Tạo BookingDetail từ combo
            Dish dish = new Dish();
            dish.setName(combo);
            dish.setPrice(0); // nếu có giá combo, gán ở đây
            booking.addDish(dish, 1);
        }

        // Lưu vào session list bookings
        List<Booking> bookings = (List<Booking>) session.getAttribute("bookings");
        if (bookings == null) bookings = new ArrayList<>();
        bookings.add(booking);
        session.setAttribute("bookings", bookings);

        // Chuyển sang trang xác nhận
        response.sendRedirect("booking-success.jsp");
    }
}
