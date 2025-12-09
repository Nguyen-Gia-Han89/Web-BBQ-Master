package controller;

import model.Food;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Food> foods = new ArrayList<>();

        foods.add(new Food(1, "Bò nướng sốt BBQ", 95000, "img/bo-nuong.jpg"));
        foods.add(new Food(2, "Ba chỉ heo nướng", 79000, "img/heo-nuong.jpg"));
        foods.add(new Food(3, "Gà nướng mật ong", 85000, "img/ga-nuong.jpg"));
        foods.add(new Food(4, "Lẩu thái chua cay", 159000, "img/lau-thai.jpg"));

        request.setAttribute("foods", foods);
        request.getRequestDispatcher("pages/menu.jsp").forward(request, response);
    }
}
