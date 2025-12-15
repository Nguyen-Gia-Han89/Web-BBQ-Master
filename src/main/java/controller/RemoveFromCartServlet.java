package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Booking;

import java.io.IOException;

/**
 * Servlet implementation class RemoveFromCartServlet
 */
@WebServlet("/remove-cart")
public class RemoveFromCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Booking cart = (Booking) session.getAttribute("cart");

        if (cart != null) {
            try {
                int dishId = Integer.parseInt(request.getParameter("dishId"));
                cart.removeDetailByDishId(dishId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/pages/cart.jsp");
    }
}