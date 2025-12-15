package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Booking;
import model.BookingDetail;

import java.io.IOException;

@WebServlet("/update-cart")
public class UpdateCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Booking cart = (Booking) session.getAttribute("cart");

        if (cart == null) {
            cart = new Booking();
            session.setAttribute("cart", cart);
        }

        try {
            int dishId = Integer.parseInt(request.getParameter("dishId"));
            String action = request.getParameter("action");
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            // Tìm món trong giỏ
            BookingDetail item = cart.findDetailByDishId(dishId);
            if (item != null) {
                if ("plus".equals(action)) {
                    item.setQuantity(item.getQuantity() + 1);
                } else if ("minus".equals(action)) {
                    int newQty = item.getQuantity() - 1;
                    item.setQuantity(Math.max(newQty, 1)); // không cho nhỏ hơn 1
                } else {
                    // Nếu người dùng nhập số lượng trực tiếp
                    item.setQuantity(Math.max(quantity, 1));
                }
                cart.calculateTotalAmount();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // Quay lại trang giỏ hàng
        response.sendRedirect(request.getContextPath() + "/pages/cart.jsp");
    }
}