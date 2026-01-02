package controller;

import dao.CustomerDAO;
import model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final CustomerDAO customerDAO = new CustomerDAO();

    /* =======================
     * POST: Xử lý đăng nhập
     * ======================= */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Customer customer = customerDAO.login(email, password);

        if (customer != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("customer", customer);

            // Đăng nhập thành công → về trang chủ
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            // Đăng nhập thất bại
            request.setAttribute("error", "Email hoặc mật khẩu không đúng");
            request.getRequestDispatcher("index.jsp")
                   .forward(request, response);
        }
    }
}
