package dao;

import java.util.ArrayList;
import java.util.List;
import model.Dish;

public class DishDAO {

    // Giả lập dữ liệu combo
    private static List<Dish> dishes = new ArrayList<>();

    static {
        dishes.add(new Dish(1, "Combo FA", 899000, "Phù hợp cho 4-6 người, gồm nhiều món nướng và lẩu.",
                "https://images.unsplash.com/photo-1543353071-873f17a7a088", "BBQ", "combo", "active"));

        dishes.add(new Dish(2, "Combo Gia Đình", 899000, "Phù hợp cho 4-6 người, gồm nhiều món nướng và lẩu.",
                "https://images.unsplash.com/photo-1543353071-873f17a7a088", "BBQ", "combo", "active"));

        dishes.add(new Dish(3, "Combo Bạn Bè", 499000, "Phù hợp cho 2-4 người, nhiều món nướng hấp dẫn.",
                "https://images.unsplash.com/photo-1543353071-873f17a7a088", "BBQ", "combo", "active"));

        dishes.add(new Dish(4, "Combo Cặp Đôi", 299000, "Thực đơn lãng mạn dành cho 2 người.",
                "https://images.unsplash.com/photo-1543353071-873f17a7a088", "BBQ", "combo", "active"));
        dishes.add(new Dish(5, "Pizza Pepperoni", 150000, "Pizza Pepperoni size M", 
        			"https://file.hstatic.net/1000389344/article/pepperoni_5_1c9ba759196f480eba397d628ac958ed_1024x1024.jpg", "Pizza", "pizza", "active"));
    }

    // Lấy tất cả combo active
    public List<Dish> getActiveCombos() {
        List<Dish> combos = new ArrayList<>();
        for (Dish dish : dishes) {
            if (dish.isCombo() && dish.isAvailable()) {
                combos.add(dish);
            }
        }
        return combos;
    }

    // Lấy 1 combo theo ID
    public Dish getDishById(int id) {
        for (Dish dish : dishes) {
            if (dish.getDishId() == id) {
                return dish;
            }
        }
        return null;
    }
}
