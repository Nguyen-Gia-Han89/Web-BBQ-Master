package controller;

import dao.PromotionDAO;
import model.Promotion;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private PromotionDAO promotionDAO = new PromotionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Lấy danh sách khuyến mãi thực tế từ DB
        List<Promotion> list = promotionDAO.getAllPromotions();
        
        // 2. Gắn vào request với tên "promotions" để EL ở index.jsp đọc được
        request.setAttribute("promotions", list);
        
        // 3. Chuyển hướng sang trang index.jsp
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}