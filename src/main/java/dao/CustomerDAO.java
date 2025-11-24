package dao;

import java.util.ArrayList;
import java.util.List;
import model.Customer;

public class CustomerDAO {

    private static List<Customer> customers = new ArrayList<>();

    static {
        // Thêm 1 tài khoản test
        customers.add(new Customer(1, "Nguyen Van A", "0123456789", "a@gmail.com", "123"));
    }

    // Đăng ký
    public boolean register(Customer customer) {
        for (Customer c : customers) {
            if (c.getEmail().equalsIgnoreCase(customer.getEmail())) {
                return false; // Email tồn tại
            }
        }

        customers.add(customer);
        return true;
    }

    // Đăng nhập bằng email + password
    public Customer login(String email, String password) {
        for (Customer c : customers) {
            if (c.getEmail().equalsIgnoreCase(email) && c.getPassword().equals(password)) {
                return c;
            }
        }
        return null;
    }

    public List<Customer> getAll() {
        return customers;
    }
}
