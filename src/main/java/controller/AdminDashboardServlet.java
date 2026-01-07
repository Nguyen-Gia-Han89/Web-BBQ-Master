package controller;

import dao.BookingDAO;
import dao.CustomerDAO;
import model.Booking;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private final BookingDAO bookingDAO = new BookingDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // 1. Lấy dữ liệu thống kê từ Database thông qua DAO
        int totalCustomers = customerDAO.getAll().size();
        int totalBookings = bookingDAO.countTotalBookings();
        double revenue = bookingDAO.getTotalRevenue();

        // 2. Lấy danh sách 5 đơn hàng mới nhất
        List<Booking> recentBookings = bookingDAO.getRecentBookings(5);

        // 3. Đưa dữ liệu vào request attribute để JSP có thể đọc được
        request.setAttribute("totalCustomers", totalCustomers);
        request.setAttribute("totalBookings", totalBookings);
        request.setAttribute("revenue", revenue);
        request.setAttribute("recentBookings", recentBookings);

        // 4. Chuyển hướng (Forward) sang file JSP để hiển thị giao diện
        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
    }
}