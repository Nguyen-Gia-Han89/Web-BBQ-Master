package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.DishDAO;
import model.Dish;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. LẤY CATEGORY
        String category = request.getParameter("category");

        // 🔥 THAY ĐỔI: Nếu không có tham số nào, CHUYỂN HƯỚNG SANG /menu?category=all
        if (category == null || category.trim().isEmpty()) {
            // Xây dựng URL chuyển hướng: /ten_ung_dung/menu?category=all
            String redirectURL = request.getContextPath() + "/menu?category=all";
            
            // Thực hiện chuyển hướng (Redirect - Server ra lệnh trình duyệt tải trang mới)
            response.sendRedirect(redirectURL); 
            
            // Dừng việc xử lý tiếp theo của doGet
            return; 
        }

        // --- LOGIC DƯỚI ĐÂY CHỈ THỰC HIỆN KHI CÓ THAM SỐ (ví dụ: category=all) ---
        
        // 2. LẤY DATA
        DishDAO dao = new DishDAO();
        List<Dish> allFoods = dao.getAllDishes();
        List<Dish> result = new ArrayList<>();

        // 3. FILTER
        if ("all".equalsIgnoreCase(category)) {
            // Nếu là 'all', lấy tất cả
            result = allFoods;
        } else {
            // Nếu là danh mục cụ thể, tiến hành lọc
            for (Dish d : allFoods) {
                if (d.getCategory() != null &&
                    d.getCategory().equalsIgnoreCase(category)) {
                    result.add(d);
                }
            }
        }

        // 🧪 DEBUG (giữ lại để test)
        System.out.println("MENU DEBUG - allFoods size = " + allFoods.size());
        System.out.println("MENU DEBUG - category = " + category);
        System.out.println("MENU DEBUG - result size = " + result.size());

        // 4. ĐẨY SANG JSP
        request.setAttribute("foods", result);
        request.setAttribute("selectedCategory", category);

        request.getRequestDispatcher("/pages/menu.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
