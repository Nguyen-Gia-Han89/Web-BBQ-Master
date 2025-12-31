package model;

/**
 * Lớp Dish đại diện cho món ăn trong hệ thống nhà hàng/đặt bàn.
 * Chứa thông tin về tên món, giá, mô tả, hình ảnh, loại món và trạng thái.
 */
public class Dish {

    /** Mã món ăn (duy nhất) */
    private int dishId;

    /** Tên món ăn */
    private String name;

    /** Giá món ăn */
    private double price;

    /** Mô tả chi tiết về món */
    private String description;

    /** Đường dẫn hình ảnh món ăn */
    private String imageUrl;

    /** Danh mục món ăn (VD: BBQ, Hải sản, Đồ uống...) */
    private String category;

    /** Loại món: single / combo / set menu */
    private String dishType;

    /** Trạng thái món: active / inactive / out_of_stock */
    private String status;

    /** Constructor mặc định */
    public Dish() {}

    /**
     * Constructor khởi tạo món ăn đầy đủ thông tin.
     *
     * @param dishId     Mã món
     * @param name       Tên món
     * @param price      Giá món
     * @param description Mô tả
     * @param imageUrl   Link ảnh món
     * @param category   Danh mục
     * @param dishType   Loại món (single/combo/set)
     * @param status     Trạng thái món
     */
    public Dish(int dishId, String name, double price, String description,
                String imageUrl, String category, String dishType, String status) {
        this.dishId = dishId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.dishType = dishType;
        this.status = status;
    }

    // Getters & Setters
    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Kiểm tra món có đang hoạt động/cho phép đặt hay không.
     *
     * @return true nếu món đang active
     */
    public boolean isAvailable() {
        return "active".equalsIgnoreCase(status);
    }

    /**
     * Kiểm tra món có phải dạng combo hay không.
     *
     * @return true nếu dishType = combo
     */
    public boolean isCombo() {
        return "combo".equalsIgnoreCase(dishType);
    }

    /**
     * Kiểm tra món có phải set menu hay không.
     *
     * @return true nếu dishType = set
     */
    public boolean isSetMenu() {
        return "set".equalsIgnoreCase(dishType);
    }
}
