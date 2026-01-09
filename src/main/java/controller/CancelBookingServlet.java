package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BookingDAO;

/**
 * Servlet implementation class CancelBookingServlet
 */
@WebServlet("/cancel-booking")
public class CancelBookingServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        BookingDAO dao = new BookingDAO();
        
        // Gọi hàm update trạng thái thành CANCELLED
        boolean success = dao.cancelBooking(id);
        
        if (success) {
            // Quay lại trang lịch sử với thông báo thành công
            response.sendRedirect("booking-history?message=cancelled");
        } else {
            response.sendRedirect("booking-history?error=fail");
        }
    }
}