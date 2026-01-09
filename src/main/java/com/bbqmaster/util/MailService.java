package com.bbqmaster.util;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import model.Booking;
import model.BookingDetail;
import model.Service;

public class MailService {

    private static String FROM_EMAIL;
    private static String APP_PASSWORD;

    // Tự động nạp cấu hình từ file db.properties
    static {
        try (InputStream input = MailService.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties prop = new Properties();
            if (input != null) {
                prop.load(input);
                FROM_EMAIL = prop.getProperty("mail.username");
                APP_PASSWORD = prop.getProperty("mail.password");
            } else {
                System.err.println("⚠ Không tìm thấy file db.properties tại src/main/resources/");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendBookingEmail(Booking booking, String recipientEmail) {
        if (FROM_EMAIL == null || APP_PASSWORD == null) {
            System.err.println("❌ Lỗi: Chưa cấu hình Email/Password trong db.properties");
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session mailSession = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(FROM_EMAIL, "BBQ Master"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Xác nhận đặt bàn thành công - BBQ Master", "UTF-8");

            // --- 1. Thông tin cơ bản ---
            String code = booking.getOrderCode();
            String time = booking.getFormattedBookingTime();
            String table = booking.getTable() != null ? booking.getTable().getTableName() : "Nhân viên sắp xếp";
            String space = (booking.getTable() != null && booking.getTable().getSpace() != null)
                            ? booking.getTable().getSpace().getName() : "Khu vực tiệc";
            int guests = booking.getNumberOfGuests();

            // --- 2. Xử lý phần Dịch vụ & Phí ---
            double totalAmount = booking.getTotalAmount();
            String serviceSection = "";
            Service service = booking.getService();

            if (booking.getBookingType() == Booking.BookingType.PARTY) {
                String serviceName = (service != null) ? "Gói tiệc: " + service.getName() : "Phí tổ chức tiệc mặc định";
                double partyFee = (service != null) ? (service.getExtraFee() + 500000) : 500000;
                
                serviceSection = """
                    <div style="margin-bottom: 15px; border-left: 4px solid #e67e22; padding-left: 10px;">
                        <p style="margin: 0;"><b>Loại hình:</b> Đặt tiệc liên hoan</p>
                        <p style="margin: 0;"><b>Dịch vụ:</b> %s – <b style="color:#e67e22;">%,.0f đ</b></p>
                    </div>
                    """.formatted(serviceName, partyFee);
            } else {
                String serviceName = (service != null) ? service.getName() : "Tự nướng";
                double fee = (service != null) ? service.getExtraFee() : 0;
                serviceSection = """
                    <div style="margin-bottom: 15px; border-left: 4px solid #3498db; padding-left: 10px;">
                        <p style="margin: 0;"><b>Loại hình:</b> Ăn tại chỗ (Dine-in)</p>
                        <p style="margin: 0;"><b>Dịch vụ đi kèm:</b> %s – <b>%,.0f đ</b></p>
                    </div>
                    """.formatted(serviceName, fee);
            }

            // --- 3. Xử lý danh sách món ăn & Tiền cọc ---
            // LOGIC CỌC: Tiệc 50%, Thường 30%
            double depositRate = (booking.getBookingType() == Booking.BookingType.PARTY) ? 0.5 : 0.3;
            double depositAmount = totalAmount * depositRate;
            double remaining = totalAmount - depositAmount;

            String foodSection = "";
            List<BookingDetail> details = booking.getBookingDetails();
            
            if (details != null && !details.isEmpty()) {
                StringBuilder rows = new StringBuilder();
                for (BookingDetail d : details) {
                    rows.append("""
                        <tr style="border-bottom: 1px solid #eee;">
                          <td style="padding: 10px;">%s</td>
                          <td align="center">%d</td>
                          <td align="right">%,.0f đ</td>
                          <td align="right" style="font-weight:bold;">%,.0f đ</td>
                        </tr>
                    """.formatted(d.getDish().getName(), d.getQuantity(), d.getPrice(), d.getTotal()));
                }

                foodSection = """
                <h3 style="color:#e67e22; border-bottom:1px solid #ddd; padding-bottom:5px;">Chi tiết thực đơn</h3>
                <table width="100%%" style="border-collapse:collapse; margin-bottom:20px;">
                  <thead>
                    <tr style="background:#f8f9fa;">
                        <th align="left" style="padding:10px;">Món</th>
                        <th>SL</th>
                        <th align="right">Giá</th>
                        <th align="right">Tổng</th>
                    </tr>
                  </thead>
                  <tbody>%s</tbody>
                </table>
                """.formatted(rows.toString());
            }

            // --- 4. Tổng kết tài chính ---
            String summarySection = """
                <div style="background:#fff9f4; padding:15px; border-radius:8px; border:1px dashed #e67e22;">
                    <table width="100%%" style="font-size: 14px; line-height: 2;">
                      <tr><td align="right">Tổng cộng (đã gồm phí):</td><td align="right" width="120"><b>%,.0f đ</b></td></tr>
                      <tr><td align="right" style="color:#e67e22;">Tiền cọc đã thanh toán (%.0f%%):</td><td align="right" style="color:#e67e22;"><b>- %,.0f đ</b></td></tr>
                      <tr style="font-size:18px; color:#c0392b;">
                        <td align="right"><b>Còn lại tại nhà hàng:</b></td>
                        <td align="right"><b>%,.0f đ</b></td>
                      </tr>
                    </table>
                </div>
                """.formatted(totalAmount, depositRate * 100, depositAmount, remaining);

            // --- 5. Lắp ráp HTML cuối cùng ---
            String html = """
            <html>
            <body style="font-family:'Segoe UI', Arial, sans-serif; background:#f4f4f4; padding:20px; color: #333;">
              <div style="max-width:600px; margin:auto; background:#fff; padding:30px; border-radius:12px; box-shadow:0 4px 15px rgba(0,0,0,0.1);">
                <div style="text-align:center; padding-bottom: 20px;">
                   <h1 style="color:#e67e22; margin:0;">BBQ MASTER</h1>
                   <p style="color:#7f8c8d; margin:5px 0;">Hương vị nướng thượng hạng</p>
                </div>
                
                <div style="background:#e67e22; padding:15px; text-align:center; color:white; border-radius:8px;">
                  <h2 style="margin:0;">Xác nhận đặt bàn thành công</h2>
                  <p style="margin:0; font-size:18px;">Mã đơn hàng: <b>#%s</b></p>
                </div>

                <div style="padding: 20px 0;">
                  <p>Xin chào quý khách,</p>
                  <p>Cảm ơn quý khách đã tin tưởng lựa chọn <b>BBQ Master</b>. Đơn đặt bàn của quý khách đã được hệ thống xác nhận thành công.</p>
                  
                  <div style="background:#fdf2e9; padding:15px; border-radius:8px; margin-bottom:20px;">
                    <table width="100%%">
                      <tr><td width="100"><b>Thời gian:</b></td><td>%s</td></tr>
                      <tr><td><b>Vị trí:</b></td><td>%s (Bàn: %s)</td></tr>
                      <tr><td><b>Số lượng:</b></td><td>%d người</td></tr>
                    </table>
                  </div>

                  %s 
                  %s
                  %s

                  <p style="font-size: 13px; color: #7f8c8d; font-style: italic; margin-top: 20px;">
                    * Quý khách vui lòng đến đúng giờ đã hẹn. Sau 15 phút nếu không có mặt, nhà hàng xin phép hủy bàn để phục vụ khách hàng khác. Trong trường hợp khách hàng không đến, tiền đặt cọc sẽ không được hoàn trả.
                  </p>
                </div>
                
                <div style="border-top: 1px solid #eee; padding-top: 20px; text-align: center; color: #95a5a6; font-size: 12px;">
                  <p>Hotline: 1900 6789 | Email: support@bbqmaster.com</p>
                  <p>Địa chỉ: 123 Đường Nướng, Quận 1, TP. Hồ Chí Minh</p>
                </div>
              </div>
            </body>
            </html>
            """.formatted(code, time, space, table, guests, serviceSection, foodSection, summarySection);

            message.setContent(html, "text/html; charset=UTF-8");
            Transport.send(message);
            System.out.println("✅ Email xác nhận đã được gửi đến: " + recipientEmail);

        } catch (Exception e) {
            System.err.println("❌ Lỗi gửi email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}