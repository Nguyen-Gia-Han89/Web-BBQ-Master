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

    private int parseIntSafe(String s, int defaultVal) {
        try { return Integer.parseInt(s); } catch (Exception e) { return defaultVal; }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();

        int dishId = parseIntSafe(request.getParameter("dishId"), -1);
        if (dishId < 0) {
            redirectBack(request, response);
            return;
        }

        Dish selectedDish = findDish(dishId);
        if (selectedDish != null) {
            Booking cart = (Booking) session.getAttribute("cart");
            if (cart == null) cart = new Booking();
            cart.addDish(selectedDish, 1); // thêm 1 món
            session.setAttribute("cart", cart);
        }
        
        String ajax = request.getParameter("ajax");
        if ("true".equals(ajax)) {
            response.setContentType("application/json");
            Booking cart = (Booking) session.getAttribute("cart");
            int count = (cart != null) ? cart.getTotalQuantity() : 0;
            response.getWriter().write("{\"count\": " + count + "}");
            return;
        }
        redirectBack(request, response);
    }

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();

        int dishId = parseIntSafe(request.getParameter("dishId"), -1);
        String action = request.getParameter("action");
        if (action == null) action = "add";
        int quantity = parseIntSafe(request.getParameter("quantity"), 1);

        if (dishId < 0) {
            redirectBack(request, response);
            return;
        }

        Dish selectedDish = findDish(dishId);
        if (selectedDish == null) {
            redirectBack(request, response);
            return;
        }

        Booking cart = (Booking) session.getAttribute("cart");
        if (cart == null) cart = new Booking();

        if ("plus".equals(action)) {
            cart.addDish(selectedDish, 1);
        } else if ("minus".equals(action)) {
            cart.addDish(selectedDish, -1);
            BookingDetail bd = cart.getDetailByDishId(dishId);
            if (bd != null && bd.getQuantity() <= 0) {
                cart.removeDish(selectedDish);
            }
        } else if ("remove".equals(action)) {
            cart.removeDish(selectedDish);
        } else if ("set".equals(action)) { // optional: set trực tiếp số lượng
            if (quantity <= 0) cart.removeDish(selectedDish);
            else cart.setQuantity(selectedDish, quantity);
        } else {
            // mặc định add 1
            cart.addDish(selectedDish, 1);
        }

        session.setAttribute("cart", cart);

        String ajax = request.getParameter("ajax");
        if ("true".equals(ajax)) {
            response.setContentType("application/json");
            int count = (cart != null) ? cart.getTotalQuantity() : 0;
            response.getWriter().write("{\"count\": " + count + "}");
            return;
        }
        redirectBack(request, response);
    }

    private void redirectBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String referer = request.getHeader("Referer");
        String cartPage = request.getContextPath() + "/pages/cart.jsp";
        response.sendRedirect(referer != null ? referer : cartPage);
    }

    private Dish findDish(int dishId) {
        DishDAO dao = new DishDAO();
        return dao.getDishById(dishId);
    }
  
}
