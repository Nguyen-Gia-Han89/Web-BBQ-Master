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
import model.Customer;
import model.Service;
import model.Space;
import model.Table;

import dao.BookingDAO;
import dao.ServiceDAO;
import dao.SpaceDAO;
import dao.TableDAO;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // DAO
        SpaceDAO spaceDAO = new SpaceDAO();
        TableDAO tableDAO = new TableDAO();
        ServiceDAO serviceDAO = new ServiceDAO();

        // Lấy dữ liệu
        List<Space> spaces = spaceDAO.getAllSpaces();
        List<Table> availableTables = tableDAO.getAll(); 
        List<Service> services = serviceDAO.getAll();

        // Đưa sang JSP
        request.setAttribute("spaces", spaces);
        request.setAttribute("availableTables", availableTables);
        request.setAttribute("services", services);

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

        int guests = Integer.parseInt(request.getParameter("guests"));
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        String note = request.getParameter("note");

        Integer tableId = Integer.parseInt(request.getParameter("tableId"));
        String serviceIdStr = request.getParameter("serviceId");

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setNumberOfGuests(guests);
        booking.setBookingTime(LocalDateTime.parse(date + "T" + time));
        booking.setNote(note);
        booking.setStatus(BookingStatus.PENDING);
        booking.setBookingType(BookingType.DINE_IN);

        if (tableId != null) {
            Table table = new Table();
            table.setTableId(tableId);
            booking.setTable(table);
        }

        if (serviceIdStr != null && !serviceIdStr.isEmpty()) {
            Service s = new Service();
            s.setServiceID(Integer.parseInt(serviceIdStr));
            booking.setService(s);
        }

        BookingDAO dao = new BookingDAO();
        int bookingId = dao.insert(booking);

        if (bookingId > 0) {
            response.sendRedirect("booking-success.jsp");
        } else {
            request.setAttribute("error", "Đặt bàn thất bại");
            doGet(request, response);
        }
    }
}
