package controller;

import com.vnpay.common.Config;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/booking-fail.jsp");
            return;
        }

        Double amount = (Double) session.getAttribute("PAYMENT_AMOUNT");
        Integer bookingId = (Integer) session.getAttribute("BOOKING_ID");

        /* =======================
         * 1. Validate dữ liệu
         * ======================= */
        if (amount == null || bookingId == null || amount < 5000) {
            System.out.println("PAYMENT ERROR: Invalid amount or bookingId");
            response.sendRedirect(request.getContextPath() + "/booking-table/booking-fail.jsp");
            return;
        }

        /* =======================
         * 2. Tạo params VNPAY
         * ======================= */
        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", Config.vnp_TmnCode);
        vnpParams.put("vnp_Amount", String.valueOf((long) (amount * 100)));
        vnpParams.put("vnp_CurrCode", "VND");
        vnpParams.put("vnp_TxnRef", String.valueOf(bookingId));
        vnpParams.put("vnp_OrderInfo", "Thanh toan BBQ Master don hang #" + bookingId);
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnpParams.put("vnp_IpAddr", Config.getIpAddress(request));

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        vnpParams.put("vnp_CreateDate", sdf.format(calendar.getTime()));

        /* =======================
         * 3. Sort + build query
         * ======================= */
        List<String> fieldNames = new ArrayList<>(vnpParams.keySet());
        Collections.sort(fieldNames);

        StringBuilder query = new StringBuilder();
        StringBuilder hashData = new StringBuilder();

        for (Iterator<String> it = fieldNames.iterator(); it.hasNext(); ) {
            String key = it.next();
            String value = vnpParams.get(key);

            if (value != null && !value.isEmpty()) {
                String encKey = URLEncoder.encode(key, StandardCharsets.US_ASCII);
                String encValue = URLEncoder.encode(value, StandardCharsets.US_ASCII);

                query.append(encKey).append('=').append(encValue);
                hashData.append(key).append('=').append(encValue);

                if (it.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        /* =======================
         * 4. Secure hash
         * ======================= */
        String secureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());

        String paymentUrl = Config.vnp_PayUrl
                + "?" + query
                + "&vnp_SecureHash=" + secureHash;

        System.out.println("VNPAY REDIRECT: " + paymentUrl);

        response.sendRedirect(paymentUrl);
    }
}
