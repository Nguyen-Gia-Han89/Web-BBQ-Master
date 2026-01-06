package com.bbqmaster.util;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static Properties prop = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println("Xin lỗi, không tìm thấy file db.properties");
            } else {
                prop.load(input);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
}