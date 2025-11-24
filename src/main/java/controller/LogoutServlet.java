package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;

import java.io.IOException;

import dao.CustomerDAO;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private CustomerDAO customerDAO = new CustomerDAO();


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fullName = request.getParameter("fullName");
		String phone = request.getParameter("phoneNumber");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// Tự tạo ID (tăng dần)
		int generatedID = customerDAO.getAll().size() + 1;

		Customer customer = new Customer(generatedID, fullName, phone, email, password);

		if (customerDAO.register(customer)) {
			response.sendRedirect("login.jsp?success=1");
		} else {
			request.setAttribute("error", "Email đã tồn tại!");
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}
	}

}
