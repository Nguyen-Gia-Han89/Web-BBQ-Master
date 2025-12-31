package controller;

import com.vnpay.common.Config;
import dao.BookingDAO;
import model.Booking.BookingStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/vnpay_return")
public class VNPayReturnServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Thu thập tất cả tham số từ VNPAY gửi về
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        // 2. Lấy chữ ký bảo mật từ VNPAY gửi về
        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        
        // Loại bỏ các tham số không dùng để tính toán Hash (SecureHash và SecureHashType)
        fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");

        // 3. Tính toán lại chữ ký từ dữ liệu nhận được để so khớp
        String signValue = Config.hashAllFields(fields);

        // 4. Kiểm tra logic
        if (signValue.equals(vnp_SecureHash)) {
            // Chữ ký hợp lệ -> Kiểm tra kết quả thanh toán
            String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
            String bookingId = request.getParameter("vnp_TxnRef");

            if ("00".equals(vnp_ResponseCode)) {
                // THANH TOÁN THÀNH CÔNG
                BookingDAO dao = new BookingDAO();
                dao.updateStatus(Integer.parseInt(bookingId), BookingStatus.CONFIRMED);
                
                // Xóa giỏ hàng và các session liên quan
                request.getSession().removeAttribute("cart");
                request.getSession().removeAttribute("BOOKING_ID");
                request.getSession().removeAttribute("PAYMENT_AMOUNT");

                response.sendRedirect(request.getContextPath() + "/pages/booking-success.jsp");
            } else {
                // THANH TOÁN THẤT BẠI (Người dùng hủy hoặc lỗi thẻ)
                response.sendRedirect(request.getContextPath() + "/booking-fail.jsp?code=" + vnp_ResponseCode);
            }
        } else {
            // CHỮ KÝ SAI (Dữ liệu có thể đã bị can thiệp)
            request.setAttribute("error", "Dữ liệu giao dịch không hợp lệ (Signature mismatch)");
            request.getRequestDispatcher("booking-fail.jsp").forward(request, response);
        }
    }
}