package model;

/**
 * Lớp Table đại diện cho bàn trong nhà hàng.
 * Mỗi bàn thuộc một khu vực (Space) và có các trạng thái khác nhau.
 */
public class Table {

    /** Mã bàn */
    private int tableId;

    /** Mã khu vực - khóa ngoại liên kết với Space */
    private int spaceId;

    /** Số ghế tại bàn */
    private int seats;

    /** 
     * Trạng thái bàn:
     * - available: còn trống
     * - reserved: đã đặt trước
     * - occupied: đang sử dụng
     * - maintenance: đang bảo trì
     */
    private String status;

    /** Tham chiếu đến đối tượng Space (quan hệ OOP) */
    private Space space;

    /** Constructor mặc định */
    public Table() {}

    /**
     * Constructor khởi tạo bàn không gắn đối tượng Space
     * @param tableId
     * @param spaceId
     * @param seats
     * @param status
     */
    public Table(int tableId, int spaceId, int seats, String status) {
        this.tableId = tableId;
        this.spaceId = spaceId;
        this.seats = seats;
        this.status = status;
    }

    /**
     * Constructor khởi tạo bàn có gắn đối tượng Space
     * @param tableId
     * @param spaceId
     * @param seats
     * @param status
     * @param space
     */
    public Table(int tableId, int spaceId, int seats, String status, Space space) {
        this.tableId = tableId;
        this.spaceId = spaceId;
        this.seats = seats;
        this.status = status;
        this.space = space;
    }

    // Getters & Setters
    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(int spaceId) {
        this.spaceId = spaceId;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }
}
