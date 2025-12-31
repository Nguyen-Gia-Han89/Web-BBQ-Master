package controller;

import java.io.IOException;
import java.util.Date; 
import java.time.LocalDate;
import java.util.List;

import dao.PromotionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Promotion;

@WebServlet("/promotions")
public class PromotionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private PromotionDAO promoDAO;

    @Override
    public void init() throws ServletException {
        promoDAO = new PromotionDAO();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Promotion> promotions = promoDAO.getAllPromotions();

        // Lọc ưu đãi còn hiệu lực bằng java.util.Date
        Date today = new Date();
        promotions.removeIf(p -> p.getEndDate() != null && p.getEndDate().before(today));

        // Đảm bảo tên attribute khớp với biến items trong c:forEach ở file JSP
        request.setAttribute("promotions", promotions); 

        request.getRequestDispatcher("/pages/promotion.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
