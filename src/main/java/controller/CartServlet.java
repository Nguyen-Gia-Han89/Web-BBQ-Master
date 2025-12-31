package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.Booking;
import model.BookingDetail;
import model.Dish;
import dao.DishDAO;

import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private int parseIntSafe(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/pages/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession();
        Booking cart = (Booking) session.getAttribute("cart");
        if (cart == null) cart = new Booking();

        int dishId = parseIntSafe(req.getParameter("dishId"), -1);
        String action = req.getParameter("action");
        int quantity = parseIntSafe(req.getParameter("quantity"), 1);

        if (dishId < 0 || action == null) {
            redirectBack(req, resp);
            return;
        }

        Dish dish = new DishDAO().getDishById(dishId);
        if (dish == null) {
            redirectBack(req, resp);
            return;
        }

        BookingDetail item = cart.findDetailByDishId(dishId);

        switch (action) {
            case "add":
            case "plus":
                cart.addDish(dish, 1);
                break;

            case "minus":
                if (item != null) {
                    int newQty = item.getQuantity() - 1;
                    if (newQty <= 0) cart.removeDish(dish);
                    else item.setQuantity(newQty);
                }
                break;

            case "set":
                if (quantity <= 0) cart.removeDish(dish);
                else cart.setQuantity(dish, quantity);
                break;

            case "remove":
                cart.removeDish(dish);
                break;
        }

        cart.calculateTotalAmount();
        session.setAttribute("cart", cart);

        redirectBack(req, resp);
    }

    private void redirectBack(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String referer = req.getHeader("Referer");
        resp.sendRedirect(referer != null ? referer : req.getContextPath() + "/cart");
    }
}
