package controller;

import beans.DishList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/combo")
public class ComboServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Chỉ tạo DishList nếu chưa có
        DishList dishList = (DishList) session.getAttribute("dishList");
        if (dishList == null) {
            dishList = new DishList();
            session.setAttribute("dishList", dishList);
        }

        // Forward tới JSP trong folder /combo
        request.getRequestDispatcher("index.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
