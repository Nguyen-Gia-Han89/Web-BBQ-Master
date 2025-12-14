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
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       // 🔥 FIX NULL CATEGORY (BẮT BUỘC)
       String category = request.getParameter("category");
       if (category == null || category.isEmpty()) {
           category = "all";
       }
       // 🔥 LẤY DATA TỪ DAO
       DishDAO dao = new DishDAO();
       List<Dish> allFoods = dao.getAllDishes();
       List<Dish> result = new ArrayList<>();
       if (category.equals("all")) {
           result = allFoods;
       } else {
           for (Dish d : allFoods) {
               if (d.getCategory().equalsIgnoreCase(category)) {
                   result.add(d);
               }
           }
       }
       // 🧪 DEBUG
       System.out.println("MENU DEBUG - allFoods size = " + allFoods.size());
       System.out.println("MENU DEBUG - category = " + category);
       System.out.println("MENU DEBUG - result size = " + result.size());
       // 🔥 ĐẨY DATA SANG JSP
       request.setAttribute("foods", result);
       request.setAttribute("selectedCategory", category);
       request.getRequestDispatcher("/pages/menu.jsp").forward(request, response);
   }
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       doGet(request, response);
   }
}

