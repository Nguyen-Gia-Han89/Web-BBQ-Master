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
                System.err.println("⚠ Không tìm thấy file db.properties tại src/main/java/");
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
            message.setSubject("Xác nhận đặt bàn - BBQ Master", "UTF-8");

            String code = booking.getOrderCode();
            String time = booking.getFormattedBookingTime();
            String table = booking.getTable() != null ? booking.getTable().getTableName() : "Nhân viên sắp xếp";
            String space = (booking.getTable() != null && booking.getTable().getSpace() != null)
                            ? booking.getTable().getSpace().getName() : "Khu thường";
            int guests = booking.getNumberOfGuests();

            double totalAmount = 0;
            String serviceSection = "";
            Service service = booking.getService();
            if (service != null) {
                totalAmount += service.getExtraFee();
                serviceSection = """
                <p style='margin-bottom:20px;'><b>Dịch vụ đi kèm:</b> %s – <b>%,.0f đ</b></p>
                """.formatted(service.getName(), service.getExtraFee());
            }

            String foodSection = "";
            List<BookingDetail> details = booking.getBookingDetails();
            if (details != null && !details.isEmpty()) {
                StringBuilder rows = new StringBuilder();
                for (BookingDetail d : details) {
                    double itemTotal = d.getQuantity() * d.getPrice();
                    totalAmount += itemTotal;
                    rows.append("""
                        <tr style="border-bottom: 1px solid #eee;">
                          <td style="padding: 12px;">%s</td>
                          <td align="center">%d</td>
                          <td align="right">%,.0f đ</td>
                          <td align="right" style="font-weight:bold;">%,.0f đ</td>
                        </tr>
                    """.formatted(d.getDish().getName(), d.getQuantity(), d.getPrice(), itemTotal));
                }

                double deposit = totalAmount * 0.3;
                double remaining = totalAmount - deposit;

                foodSection = """
                <h3 style="color:#e67e22; border-bottom:2px solid #e67e22; padding-bottom:5px;">Chi tiết thực đơn</h3>
                <table width="100%%" style="border-collapse:collapse; margin-bottom:20px;">
                  <thead>
                    <tr style="background:#f8f9fa;"><th align="left" style="padding:10px;">Món</th><th>SL</th><th align="right">Giá</th><th align="right">Tổng</th></tr>
                  </thead>
                  <tbody>%s</tbody>
                </table>
                <div style="background:#fff9f4; padding:15px; border-radius:8px; border:1px dashed #e67e22;">
                    <table width="100%%">
                      <tr><td align="right">Tổng cộng:</td><td align="right"><b>%,.0f đ</b></td></tr>
                      <tr><td align="right" style="color:#d35400;">Cọc (30%%):</td><td align="right" style="color:#d35400;"><b>- %,.0f đ</b></td></tr>
                      <tr style="font-size:18px; color:#c0392b;"><td align="right"><b>Còn lại tại quầy:</b></td><td align="right"><b>%,.0f đ</b></td></tr>
                    </table>
                </div>
                """.formatted(rows.toString(), totalAmount, deposit, remaining);
            }

            String html = """
            <html>
            <body style="font-family:Arial; background:#f4f4f4; padding:20px;">
              <div style="max-width:600px; margin:auto; background:#fff; padding:30px; border-radius:12px; box-shadow:0 4px 10px rgba(0,0,0,0.1);">
                <div style="background:#e67e22; padding:20px; text-align:center; color:white; border-radius:8px 8px 0 0;">
                  <h2 style="margin:0;">BBQ MASTER</h2>
                  <p style="margin:0;">Xác nhận đặt bàn thành công</p>
                </div>
                <div style="padding:20px;">
                  <p>Chào quý khách, dưới đây là thông tin đơn hàng <b>#%s</b>:</p>
                  <div style="background:#fdf2e9; padding:15px; margin-bottom:20px;">
                    <b>Thời gian:</b> %s <br>
                    <b>Khu vực:</b> %s | <b>Bàn:</b> %s <br>
                    <b>Số khách:</b> %d người
                  </div>
                  %s %s
                  <p style="text-align:center; margin-top:30px; color:#2e7d32; font-weight:bold;">Trạng thái: Đang chờ khách</p>
                </div>
              </div>
            </body>
            </html>
            """.formatted(code, time, space, table, guests, serviceSection, foodSection);

            message.setContent(html, "text/html; charset=UTF-8");
            Transport.send(message);
        } catch (Exception e) { e.printStackTrace(); }
    }
}