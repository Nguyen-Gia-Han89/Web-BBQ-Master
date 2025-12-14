package beans;

import java.util.List;
import dao.DishDAO;
import model.Dish;

public class MenuListBean {
    private List<Dish> foods; 

    public MenuListBean() {
        // TẢI TOÀN BỘ MÓN ĂN
        DishDAO dao = new DishDAO();
        this.foods = dao.getAllDishes(); 
        
        System.out.println("MenuListBean DEBUG: So luong mon an da tai cho Menu: " + (foods != null ? foods.size() : 0));
    }

    public List<Dish> getFoods() { // Getter phải là getFoods()
        return foods;
    }

    public void setFoods(List<Dish> foods) {
        this.foods = foods;
    }
}