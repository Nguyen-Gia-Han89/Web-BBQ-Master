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
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // 1. Yêu cầu Tomcat xác thực thông qua JDBCRealm đã cấu hình
            request.login(email, password);

            System.out.println("Login thành công cho: " + email);
            System.out.println("Quyền Administrator: " + request.isUserInRole("administrator"));
            
            // 2. Nếu không có Exception, nghĩa là đăng nhập thành công. 
            // Lấy lại đối tượng Customer từ DB để lưu vào Session nếu cần hiển thị tên.
            Customer customer = customerDAO.login(email, password);
            request.getSession().setAttribute("customer", customer);

            // 3. Kiểm tra quyền và chuyển hướng đúng Servlet
            if (request.isUserInRole("administrator")) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }

        } catch (ServletException e) {
        		System.out.println("Đăng nhập thất bại: " + e.getMessage());
        		// 4. Nếu sai tài khoản, request.login sẽ ném ra Exception
            request.setAttribute("error", "Email hoặc mật khẩu không đúng");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
