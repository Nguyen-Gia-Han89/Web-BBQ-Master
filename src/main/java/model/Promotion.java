package model;


import java.util.Date; 

public class Promotion {
    private int promoId;
    private String promoName;
    private String description;
    private double discountPercent;
    private Date startDate; 
    private Date endDate;   
    private String imageUrl;
    private String status;

    public Promotion() {}

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

