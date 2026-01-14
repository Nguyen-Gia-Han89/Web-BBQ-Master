package com.bbqmaster.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {
    public static String hashPassword(String password) {
        try {
        		// 1. Khởi tạo thuật toán SHA-256 (Mã hóa một chiều cực kỳ an toàn)
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
         // 2. Băm mật khẩu văn bản thuần túy thành mảng các byte
            byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            
            // Chuyển đổi byte sang chuỗi Hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
            		// Đảm bảo mỗi byte được chuyển thành 2 ký tự Hex (ví dụ: 0 -> "00", 15 -> "0f")
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}