package controller;

import com.bbqmaster.util.MailService;
import com.vnpay.common.Config;
import dao.BookingDAO;
import model.Booking;
import model.Booking.BookingStatus;
import model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/vnpay_return")
public class VNPayReturnServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Thu thập tất cả tham số từ VNPAY gửi về
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                fields.put(fieldName, fieldValue);
            }
        }

        // 2. Lấy chữ ký bảo mật từ VNPAY gửi về
        String vnp_SecureHash = request.getParameter("vnp_SecureHash");

        // Loại bỏ các tham số không dùng để tính toán Hash
        fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");

        // 3. Tính toán lại chữ ký từ dữ liệu nhận được để so khớp
        String signValue = Config.hashAllFields(fields);

        // 4. Kiểm tra logic
        if (signValue.equals(vnp_SecureHash)) {
            // Chữ ký hợp lệ → kiểm tra kết quả thanh toán
            String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
            String bookingId = request.getParameter("vnp_TxnRef");

            if ("00".equals(vnp_ResponseCode)) {
            		int id = Integer.parseInt(bookingId);
                // THANH TOÁN THÀNH CÔNG
                BookingDAO dao = new BookingDAO();
                dao.updateStatus(Integer.parseInt(bookingId), BookingStatus.CONFIRMED);

                // 2. LẤY BOOKING TỪ DB
                Booking booking = dao.getById(id);
                Customer customer = booking.getCustomer();
                
                System.out.println("Booking: " + booking);
                System.out.println("Customer: " + booking.getCustomer().toString());
                System.out.println("Email: " + customer.getEmail());

                // 3. GỬI MAIL (CHỈ 1 LẦN – ĐÚNG CHỖ)
                MailService.sendBookingEmail(booking, customer.getEmail());
                
                // Xóa giỏ hàng và các session liên quan
                request.getSession().removeAttribute("cart");
                request.getSession().removeAttribute("BOOKING_ID");
                request.getSession().removeAttribute("PAYMENT_AMOUNT");
               
                response.sendRedirect(request.getContextPath() + "/booking-table/booking-success.jsp");
                } else {
                // THANH TOÁN THẤT BẠI (người dùng hủy hoặc lỗi thẻ)
            		response.sendRedirect(
            		    request.getContextPath() + "/booking-table/booking-fail.jsp?code=" + vnp_ResponseCode
            		);

            }
        } else {
            // CHỮ KÝ SAI (dữ liệu có thể đã bị can thiệp)
            request.setAttribute(
                    "error",
                    "Dữ liệu giao dịch không hợp lệ (Signature mismatch)"
            );
            request.getRequestDispatcher("/booking-table/booking-fail.jsp").forward(request, response);
        }
    }
}
