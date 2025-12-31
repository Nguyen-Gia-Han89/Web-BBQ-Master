package dao;

import org.apache.commons.dbcp2.BasicDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBCPDataSource {
    private static BasicDataSource ds = new BasicDataSource();

    static {
        Properties props = new Properties();
        // Thử đọc bằng class loader của class hiện tại
        try (InputStream is = DBCPDataSource.class.getClassLoader().getResourceAsStream("db.properties")) {
            
            if (is == null) {
                // Nếu vẫn null, báo lỗi rõ ràng để bạn biết lỗi ở đâu
                throw new RuntimeException("Lỗi: Không tìm thấy file db.properties trong thư mục resources hoặc src!");
            }
            
            props.load(is);
            
            ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ds.setUrl(props.getProperty("db.url"));
            ds.setUsername(props.getProperty("db.user"));
            ds.setPassword(props.getProperty("db.pass"));
            
            // Cấu hình pool (Đảm bảo file properties có các key này)
            ds.setMinIdle(Integer.parseInt(props.getProperty("db.minIdle", "5")));
            ds.setMaxTotal(Integer.parseInt(props.getProperty("db.maxActive", "10")));
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khởi tạo DataSource: " + e.getMessage());
        }
    }

    public static BasicDataSource getDataSource() {
        return ds;
    }
}