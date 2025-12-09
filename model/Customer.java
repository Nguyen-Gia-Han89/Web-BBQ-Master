package model;

/**
 * Lớp Customer đại diện cho thông tin của khách hàng.
 * Lưu trữ các thuộc tính cơ bản như họ tên, số điện thoại, email và mật khẩu.
 * Được sử dụng để quản lý tài khoản khách hàng trong hệ thống đặt bàn/nhà hàng.
 */
public class Customer {

    /** Mã khách hàng (duy nhất trong hệ thống) */
    private int customerID;

    /** Họ và tên khách hàng */
    private String fullName;

    /** Số điện thoại liên hệ */
    private String phoneNumber;

    /** Địa chỉ email đăng ký */
    private String email;

    /** Mật khẩu tài khoản khách hàng */
    private String password;

    /** Constructor mặc định */
    public Customer() {}

    /**
     * Constructor khởi tạo khách hàng đầy đủ thông tin
     *
     * @param customerID  Mã khách hàng
     * @param fullName    Họ tên khách hàng
     * @param phoneNumber Số điện thoại
     * @param email       Email đăng ký
     * @param password    Mật khẩu tài khoản
     */
    public Customer(int customerID, String fullName, String phoneNumber, String email, String password) {
        this.customerID = customerID;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
