package controller;

import dao.DishDAO;
import model.Dish;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /* =======================
     * GET: Hiển thị menu
     * ======================= */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Lấy category
        String category = request.getParameter("category");

        // Nếu chưa có category → redirect về ?category=all
        if (category == null || category.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/menu?category=all");
            return;
        }

        // 2. Lấy dữ liệu món ăn
        DishDAO dishDAO = new DishDAO();
        List<Dish> allDishes = dishDAO.getAllDishes();
        List<Dish> result = new ArrayList<>();

        // 3. Lọc theo category
        if ("all".equalsIgnoreCase(category)) {
            result = allDishes;
        } else {
            for (Dish dish : allDishes) {
                if (dish.getCategory() != null &&
                    dish.getCategory().equalsIgnoreCase(category)) {
                    result.add(dish);
                }
            }
        }

        // 4. Đẩy sang JSP
        request.setAttribute("foods", result);
        request.setAttribute("selectedCategory", category);

        request.getRequestDispatcher("/menu/menu.jsp")
               .forward(request, response);
    }

    /* =======================
     * POST: dùng lại GET
     * ======================= */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
