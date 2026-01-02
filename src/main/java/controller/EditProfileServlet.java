package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

import dao.CustomerDAO;
import model.Customer;

@WebServlet("/edit-profile")
public class EditProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("customer") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        request.getRequestDispatcher("/pages/edit-profile.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("customer") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Customer customer = (Customer) session.getAttribute("customer");

        String fullName = request.getParameter("fullName");
        String phoneNumber = request.getParameter("phoneNumber");

        if (fullName == null || fullName.trim().isEmpty()) {
            request.setAttribute("error", "Họ và tên không được để trống");
            request.getRequestDispatcher("/pages/edit-profile.jsp").forward(request, response);
            return;
        }

        customer.setFullName(fullName);
        customer.setPhoneNumber(phoneNumber);

        CustomerDAO dao = new CustomerDAO();
        boolean success = dao.updateProfile(customer);

        if (success) {
            session.setAttribute("customer", customer);
            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            request.setAttribute("error", "Cập nhật thất bại");
            request.getRequestDispatcher("/pages/edit-profile.jsp").forward(request, response);
        }
    }
}
