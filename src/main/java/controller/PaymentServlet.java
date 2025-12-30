package controller;

import com.vnpay.common.Config;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Double amountVal = (Double) session.getAttribute("PAYMENT_AMOUNT");
        Integer bookingId = (Integer) session.getAttribute("BOOKING_ID");

        // 1. Kiểm tra dữ liệu đầu vào
        if (amountVal == null || bookingId == null || amountVal < 5000) {
            System.out.println("Lỗi: Số tiền không hợp lệ hoặc thiếu Booking ID");
            response.sendRedirect("booking-fail.jsp");
            return;
        }

        // 2. Thiết lập các tham số cơ bản cho VNPAY
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", Config.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf((long) (amountVal * 100))); // Nhân 100 theo quy định
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", String.valueOf(bookingId));
        vnp_Params.put("vnp_OrderInfo", "Thanh toan BBQ Master don hang #" + bookingId);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", Config.getIpAddress(request));

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        vnp_Params.put("vnp_CreateDate", formatter.format(cld.getTime()));

        // 3. Sắp xếp danh sách tham số theo bảng chữ cái (A-Z)
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        // 4. Xây dựng chuỗi Query và HashData
        StringBuilder query = new StringBuilder();
        StringBuilder hashData = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();

        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);

            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Encode Key và Value
                String encodedKey = URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString());
                String encodedValue = URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString());

                // Xây dựng Query String (Dùng cho URL thực tế)
                query.append(encodedKey).append('=').append(encodedValue);

                // Xây dựng Hash Data (Dùng để tạo chữ ký)
                hashData.append(fieldName).append('=').append(encodedValue);

                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        // 5. Tạo chữ ký Secure Hash bằng HMAC SHA512
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        
        // 6. Tạo URL cuối cùng để Redirect
        String paymentUrl = Config.vnp_PayUrl + "?" + query.toString() + "&vnp_SecureHash=" + vnp_SecureHash;

        System.out.println("Redirecting to VNPAY: " + paymentUrl);
        response.sendRedirect(paymentUrl);
    }
}