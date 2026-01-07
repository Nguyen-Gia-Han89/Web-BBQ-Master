package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BookingDAO;
import model.Booking;

/**
 * Servlet implementation class UpdateBookingStatusServlet
 */
@WebServlet("/admin/UpdateBookingStatus")
public class UpdateBookingStatusServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // 1. Lấy tham số từ URL
            int bookingId = Integer.parseInt(request.getParameter("id"));
            String statusStr = request.getParameter("status"); // VD: "COMPLETED" hoặc "CANCELLED"

            // 2. Chuyển String thành Enum BookingStatus
            Booking.BookingStatus newStatus = Booking.BookingStatus.valueOf(statusStr);

            // 3. Gọi DAO cập nhật
            BookingDAO dao = new BookingDAO();
            boolean success = dao.updateStatus(bookingId, newStatus);

            if (success) {
                // Quay lại Servlet để nạp lại dữ liệu mới nhất
                response.sendRedirect(request.getContextPath() + "/admin/dashboard?msg=update_success");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard?msg=update_fail");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("dashboard.jsp?msg=error");
        }
    }
}