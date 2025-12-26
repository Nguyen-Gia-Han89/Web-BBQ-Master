package dao;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBCPDataSource {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        ds.setUrl(
            "jdbc:sqlserver://LAPTOP-9O2AT2GT\\HANA:1433;" +
            "databaseName=RestaurantBookingDB;" +
            "encrypt=true;trustServerCertificate=true"
        );

        ds.setUsername("sa");
        ds.setPassword("123456");

        ds.setInitialSize(3);
        ds.setMaxTotal(10);
    }

    public static BasicDataSource getDataSource() {
        return ds;
    }
}
