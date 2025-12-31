package model;

/**
 * Lớp Service đại diện cho dịch vụ bổ sung trong hệ thống đặt bàn nhà hàng.
 * Ví dụ: Phòng VIP, trang trí tiệc, dịch vụ phục vụ riêng...
 * Chứa thông tin về mã dịch vụ, tên dịch vụ và phí dịch vụ cộng thêm.
 */
public class Service {

    /** Mã dịch vụ (duy nhất) */
    private int serviceID;

    /** Tên dịch vụ */
    private String name;

    /** Phí dịch vụ được cộng vào tổng tiền booking */
    private double extraFee;

    /** Constructor mặc định */
    public Service() {}

    /**
     * Constructor khởi tạo dịch vụ đầy đủ thông tin.
     *
     * @param serviceID Mã dịch vụ
     * @param name Tên dịch vụ
     * @param extraFee Phí dịch vụ
     */
    public Service(int serviceID, String name, double extraFee) {
        this.serviceID = serviceID;
        this.name = name;
        this.extraFee = extraFee;
    }

    // Getters & Setters
    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getExtraFee() {
        return extraFee;
    }

    public void setExtraFee(double extraFee) {
        this.extraFee = extraFee;
    }
}
