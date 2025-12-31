package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;
import dao.CustomerDAO;

import java.io.IOException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	System.out.println(">>> ĐÃ VÀO SERVLET ĐĂNG KÝ!");
    	
        // 1. Lấy dữ liệu và loại bỏ khoảng trắng dư thừa
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 2. Kiểm tra dữ liệu trống (Validation)
        if (fullName == null || fullName.trim().isEmpty() || 
            email == null || email.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            
            request.setAttribute("registerError", "Vui lòng nhập đầy đủ thông tin!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // 3. Tạo đối tượng Customer
        Customer customer = new Customer();
        customer.setFullName(fullName.trim());
        customer.setPhoneNumber(phone != null ? phone.trim() : "");
        customer.setEmail(email.trim());
        customer.setPasswordHash(password); 

        // 4. Gọi DAO để lưu
        try {
        	// ... đoạn code lấy dữ liệu ...
        	System.out.println("=== THỬ ĐĂNG KÝ NGƯỜI DÙNG MỚI ===");
        	System.out.println("Họ tên: " + fullName);
        	System.out.println("Email: " + email);

        	boolean success = customerDAO.register(customer);

        	if (success) {
        	    System.out.println(">>> KẾT QUẢ: Đăng ký THÀNH CÔNG cho email: " + email);
        	    response.sendRedirect("index.jsp?register=success");
        	} else {
        	    System.out.println(">>> KẾT QUẢ: Đăng ký THẤT BẠI (Email có thể đã tồn tại)");
        	    request.setAttribute("registerError", "Email đã tồn tại!");
        	    request.getRequestDispatcher("index.jsp").forward(request, response);
        	}
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("registerError", "Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}