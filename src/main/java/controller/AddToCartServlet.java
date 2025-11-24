package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Booking;
import model.BookingDetail;
import model.Dish;

import java.io.IOException;

import beans.DishList;
import dao.DishDAO;

/**
 * Servlet implementation class AddToCartServlet
 */
@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int dishId = Integer.parseInt(request.getParameter("dishId"));
		Dish selectedDish = null;
		
        // Giả sử bạn có DishService để lấy món
        DishList dishList = (DishList) request.getSession().getAttribute("dishList");
        if (dishList != null && dishList.getDishes() != null) {
            for (Dish d : dishList.getDishes()) {
                if (d.getDishId() == (dishId)) {
                    selectedDish = d;
                    break;
                }
            }
        }

        if (selectedDish != null) {
            Booking cart = (Booking) request.getSession().getAttribute("cart");
            if (cart == null) cart = new Booking();
            cart.addDish(selectedDish, 1); // thêm 1 món
            request.getSession().setAttribute("cart", cart);
        }
        
        String referer = request.getHeader("Referer");
        response.sendRedirect(referer != null ? referer : "index.jsp");
    	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
