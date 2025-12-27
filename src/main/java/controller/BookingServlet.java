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
import model.Dish;
import model.Service;
import model.Space;
import model.Table;

import dao.BookingDAO;
import dao.DishDAO;
import dao.ServiceDAO;
import dao.SpaceDAO;
import dao.TableDAO;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/booking-table")
public class BookingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        if (session.getAttribute("customer") == null) {
            // Lấy trang trước đó (thường là /cart.jsp hoặc /menu)
            String referer = request.getHeader("Referer");
            String redirectUrl = (referer != null) ? referer : request.getContextPath() + "/";

            // Thêm tham số requireLogin vào URL
            String separator = redirectUrl.contains("?") ? "&" : "?";
            response.sendRedirect(redirectUrl + separator + "requireLogin=true");
            return;
        }

        // Khởi tạo các DAO
        SpaceDAO spaceDAO = new SpaceDAO();
        TableDAO tableDAO = new TableDAO();
        ServiceDAO serviceDAO = new ServiceDAO();

        // Đưa dữ liệu cơ bản cho việc đặt bàn
        request.setAttribute("spaces", spaceDAO.getAllSpaces());
        request.setAttribute("availableTables", tableDAO.getAll());
        request.setAttribute("services", serviceDAO.getAll());

        // Chuyển hướng tới trang đặt bàn
        request.getRequestDispatcher("/pages/book-table.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // 1. Lấy thông tin cơ bản từ Form
            int guests = Integer.parseInt(request.getParameter("guests"));
            String date = request.getParameter("date");
            String time = request.getParameter("time");
            String note = request.getParameter("note");
            int tableId = Integer.parseInt(request.getParameter("tableId"));
            String serviceIdStr = request.getParameter("serviceId");

            // 2. Tạo đối tượng Booking
            Booking booking = new Booking();
            booking.setCustomer(customer);
            booking.setNumberOfGuests(guests);
            booking.setBookingTime(LocalDateTime.parse(date + "T" + time));
            booking.setNote(note);
            booking.setStatus(BookingStatus.PENDING);
            booking.setBookingType(BookingType.DINE_IN);

            Table table = new Table();
            table.setTableId(tableId);
            booking.setTable(table);

            if (serviceIdStr != null && !serviceIdStr.isEmpty()) {
                Service service = new Service();
                service.setServiceID(Integer.parseInt(serviceIdStr));
                booking.setService(service);
            }

            // 3. LẤY TẤT CẢ MÓN ĂN TỪ GIỎ HÀNG (SESSION)
            Booking cart = (Booking) session.getAttribute("cart");
            List<BookingDetail> details = new ArrayList<>();

            if (cart != null && cart.getBookingDetails() != null) {
                for (BookingDetail item : cart.getBookingDetails()) {
                    // Liên kết mỗi chi tiết với đối tượng booking cha
                    item.setBooking(booking);
                    details.add(item);
                }
            }
            booking.setBookingDetails(details);

            // 4. Lưu vào Database
            BookingDAO dao = new BookingDAO();
            int bookingId = dao.insert(booking);

            if (bookingId > 0) {
                session.removeAttribute("cart"); // Xóa giỏ hàng sau khi đặt thành công
                response.sendRedirect("booking-success.jsp");
            } else {
                throw new Exception("Lỗi lưu database");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            doGet(request, response);
        }
    }
}