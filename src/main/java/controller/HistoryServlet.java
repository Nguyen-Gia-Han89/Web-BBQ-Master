package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BookingDAO;
import model.Booking;
import model.Customer;

/**
 * Servlet implementation class HistoryServlet
 */
@WebServlet("/booking-history")
public class HistoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
	    	request.setCharacterEncoding("UTF-8");
	    	response.setCharacterEncoding("UTF-8");
	    	
	    	HttpSession session = request.getSession();
        Customer c = (Customer) session.getAttribute("customer");
        
        if (c != null) {
            BookingDAO dao = new BookingDAO();
            List<Booking> history = dao.getBookingHistory(c.getCustomerID());
            request.setAttribute("history", history);
            request.getRequestDispatcher("/pages/booking-history.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}