package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp Space đại diện cho khu vực trong nhà hàng (ví dụ: khu VIP, sân vườn, phòng lạnh...).
 * Mỗi khu vực có thể gồm nhiều bàn. Dùng để phân chia không gian đặt bàn cho khách.
 */
public class Space {

    /** Mã khu vực */
    private int spaceId;

    /** Tên khu vực (VD: VIP Room, Garden Area, Family Zone) */
    private String name;

    /** Mô tả chi tiết khu vực */
    private String description;

    /** URL hình ảnh đại diện cho khu vực */
    private String imageUrl;

    /** Danh sách bàn thuộc khu vực */
    private List<Table> tables;

    /** Constructor mặc định — khởi tạo danh sách bàn rỗng */
    public Space() {
        tables = new ArrayList<>();
    }

    /**
     * Khởi tạo khu vực không kèm danh sách bàn
     * @param spaceId
     * @param name
     * @param description
     * @param imageUrl
     */

    public Space(int spaceId, String name, String description, String imageUrl) {
        this.spaceId = spaceId;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.tables = new ArrayList<>();
    }

    /**
     * Khởi tạo khu vực kèm danh sách bàn
     * @param spaceId
     * @param name
     * @param description
     * @param imageUrl
     * @param tables
     */
    public Space(int spaceId, String name, String description, String imageUrl, List<Table> tables) {
        this.spaceId = spaceId;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.tables = tables;
    }

    // Getters & Setters
    public int getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(int spaceId) {
        this.spaceId = spaceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    /**
     * Thêm bàn vào khu vực
     * @param table bàn cần thêm
     */
    public void addTable(Table table) {
        tables.add(table);
    }
}
