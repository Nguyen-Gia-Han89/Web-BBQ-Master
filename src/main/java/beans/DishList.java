package beans;

import java.util.List;
import dao.DishDAO;
import model.Dish;

public class DishList {
    private List<Dish> dishes;

    public DishList() {
        DishDAO dao = new DishDAO();
        dishes = dao.getActiveCombos(); // lấy combo active
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
