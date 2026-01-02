package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Customer;

import java.io.IOException;

import dao.CustomerDAO;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        Customer customer = (session != null)
                ? (Customer) session.getAttribute("customer")
                : null;

        if (customer != null) {
            request.getRequestDispatcher("/pages/profile.jsp")
                   .forward(request, response);
        } else {
        	response.sendRedirect(request.getContextPath() + "/index.jsp");

        }
    }
}


