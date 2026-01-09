package controller;

import dao.CustomerDAO;
import model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.bbqmaster.util.PasswordUtil;

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

        System.out.println("--- XÁC THỰC THỦ CÔNG (GIỐNG HÀM MAIN) ---");

        // Gọi trực tiếp DAO giống như bạn đã làm trong hàm main
        Customer customer = customerDAO.login(email, password); 

        if (customer != null) {
            // ĐĂNG NHẬP THÀNH CÔNG
            System.out.println("=> Login OK (giong ham main): " + customer.getFullName());
            
            // Lưu thông tin vào Session để các trang sau biết đã đăng nhập
            HttpSession session = request.getSession();
            session.setAttribute("customer", customer);

            // Vì không dùng Tomcat Realm, ta tự check Role từ DB hoặc mặc định
            // Giả sử bạn muốn check xem có phải Admin không (ví dụ qua email)
            if (email.equals("admin@gmail.com")) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
        } else {
            // ĐĂNG NHẬP THẤT BẠI
            System.out.println("=> Login Fail (giong ham main)");
            request.setAttribute("error", "Email hoặc mật khẩu không đúng!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}