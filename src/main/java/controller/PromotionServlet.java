package controller;

import dao.PromotionDAO;
import model.Promotion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/khuyen-mai") // Đổi tên để tránh xung đột thư mục
public class PromotionServlet extends HttpServlet {

    private PromotionDAO promotionDAO = new PromotionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Lấy toàn bộ danh sách, không lọc bỏ cái nào cả
        List<Promotion> promotions = promotionDAO.getAllPromotions();

        // 2. Đẩy dữ liệu vào Request
        request.setAttribute("promotions", promotions);
        
        // Log kiểm tra số lượng thực tế trong DB
        System.out.println(">>> Tổng số ưu đãi trong DB: " + (promotions != null ? promotions.size() : 0));

        // 3. Kiểm tra xem người dùng đang vào trang nào để forward cho đúng
        String path = request.getServletPath();
        if ("/home".equals(path)) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/promotions/promotion.jsp").forward(request, response);
        }
    }
}