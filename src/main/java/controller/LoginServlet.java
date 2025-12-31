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

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	private CustomerDAO userDAO = new CustomerDAO();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		
        Customer customer = userDAO.login(email, password);
        
		// Kiểm tra đăng nhập (ví dụ đơn giản)
        if (customer != null) {
            HttpSession session = request.getSession();
            session.setAttribute("customer", customer); 
            response.sendRedirect("index.jsp");
        } else {
            request.setAttribute("error", "Email hoặc mật khẩu không đúng!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
	}

}
