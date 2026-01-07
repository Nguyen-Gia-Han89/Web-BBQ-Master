package model;

/**
 * Lá»›p Customer Ä‘áº¡i diá»‡n cho thÃ´ng tin cá»§a khÃ¡ch hÃ ng.
 * LÆ°u trá»¯ cÃ¡c thuá»™c tÃ­nh cÆ¡ báº£n nhÆ° há»� tÃªn, sá»‘ Ä‘iá»‡n thoáº¡i, email vÃ  máº­t kháº©u.
 * Ä�Æ°á»£c sá»­ dá»¥ng Ä‘á»ƒ quáº£n lÃ½ tÃ i khoáº£n khÃ¡ch hÃ ng trong há»‡ thá»‘ng Ä‘áº·t bÃ n/nhÃ  hÃ ng.
 */
public class Customer {

    /** MÃ£ khÃ¡ch hÃ ng (duy nháº¥t trong há»‡ thá»‘ng) */
    private int customerID;

    /** Há»� vÃ  tÃªn khÃ¡ch hÃ ng */
    private String fullName;

    /** Sá»‘ Ä‘iá»‡n thoáº¡i liÃªn há»‡ */
    private String phoneNumber;

    /** Ä�á»‹a chá»‰ email Ä‘Äƒng kÃ½ */
    private String email;
    
    private String passwordHash; // CHá»ˆ DÃ™NG CÃ�I NÃ€Y

    private String role;
    /** Constructor máº·c Ä‘á»‹nh */
    public Customer() {}

    /**
     * Constructor khá»Ÿi táº¡o khÃ¡ch hÃ ng Ä‘áº§y Ä‘á»§ thÃ´ng tin
     *
     * @param customerID  MÃ£ khÃ¡ch hÃ ng
     * @param fullName    Há»� tÃªn khÃ¡ch hÃ ng
     * @param phoneNumber Sá»‘ Ä‘iá»‡n thoáº¡i
     * @param email       Email Ä‘Äƒng kÃ½
     * @param password    Máº­t kháº©u tÃ i khoáº£n
     */
    public Customer(String fullName, String phoneNumber, String email, String passwordHash) {;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passwordHash = passwordHash;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
