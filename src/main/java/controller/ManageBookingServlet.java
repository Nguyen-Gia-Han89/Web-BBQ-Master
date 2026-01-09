package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BookingDAO;
import model.Booking;

/**
 * Servlet implementation class ManageBookingServlet
 */
@WebServlet("/admin/manage-bookings")
public class ManageBookingServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Gọi DAO lấy tất cả đơn
        BookingDAO dao = new BookingDAO();
        List<Booking> list = dao.getUpcomingBookings();
        
        // 2. Đẩy dữ liệu sang trang JSP
        request.setAttribute("allBookings", list);
        
        // 3. Mở trang manage-bookings.jsp
        request.getRequestDispatcher("/admin/manage-bookings.jsp").forward(request, response);
    }
}
