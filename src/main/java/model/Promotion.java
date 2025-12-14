package model;

import java.util.Date;

/**
 * Lớp đại diện cho một chương trình khuyến mãi trong hệ thống.
 * Mỗi khuyến mãi bao gồm thông tin như tên, mô tả, phần trăm giảm giá,
 * thời gian áp dụng, hình ảnh hiển thị và trạng thái hiện tại.
 *
 * Trạng thái có thể là:
 * - "Active": Đang diễn ra
 * - "Expired": Đã kết thúc
 * - "Upcoming": Sắp diễn ra
 */
public class Promotion {
	
	/** Mã khuyến mãi (duy nhất) */
    private int promoId;

    /** Tên chương trình khuyến mãi */
    private String promoName;

    /** Mô tả chi tiết về chương trình */
    private String description;

    /** Phần trăm giảm giá */
    private double discountPercent;

    /** Ngày bắt đầu áp dụng */
    private Date startDate;

    /** Ngày kết thúc áp dụng */
    private Date endDate;

    /** Đường dẫn ảnh hiển thị cho khuyến mãi */
    private String imageUrl;

    /** Trạng thái chương trình: Active / Expired / Upcoming */
    private String status;

    /**
     * Constructor mặc định
     */
    public Promotion() {}
    
    /**
     * Constructor khởi tạo đầy đủ thông tin khuyến mãi
     * @param promoId
     * @param promoName
     * @param description
     * @param discountPercent
     * @param startDate
     * @param endDate
     * @param imageUrl
     * @param status
     */
    public Promotion(int promoId, String promoName, String description, double discountPercent,
                     Date startDate, Date endDate, String imageUrl, String status) {
        this.promoId = promoId;
        this.promoName = promoName;
        this.description = description;
        this.discountPercent = discountPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    // Getters and setters
    public int getPromoId() {
        return promoId;
    }

    public void setPromoId(int promoId) {
        this.promoId = promoId;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "Promotion [promoId=" + promoId + ", promoName=" + promoName + ", description=" + description
				+ ", discountPercent=" + discountPercent + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", imageUrl=" + imageUrl + ", status=" + status + "]";
	}


}

