package controller;

import dao.PromotionDAO;
import model.Promotion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/promotions")
public class PromotionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private PromotionDAO promotionDAO;

    @Override
    public void init() throws ServletException {
        promotionDAO = new PromotionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Promotion> promotions = promotionDAO.getAllPromotions();

        // Lọc các khuyến mãi đã hết hạn
        Date today = new Date();
        promotions.removeIf(p ->
                p.getEndDate() != null && p.getEndDate().before(today)
        );

        request.setAttribute("promotions", promotions);

        request.getRequestDispatcher("/promotions/promotion.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
