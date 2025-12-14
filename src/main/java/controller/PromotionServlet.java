package controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import beans.PromotionList;
import dao.PromotionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Promotion;

@WebServlet("/promotions")
public class PromotionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private PromotionDAO promoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        promoDAO = new PromotionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Lấy danh sách ưu đãi
        List<Promotion> promotions = promoDAO.getAllPromotions();

        // Lọc ưu đãi còn hiệu lực (ngày hiện tại <= endDate)
        Date today = new Date(); // ngày hiện tại
        promotions.removeIf(p -> p.getEndDate().before(today));

        // Lưu vào PromotionList
        PromotionList promoList = new PromotionList();
        promoList.setPromotions(promotions);

        session.setAttribute("promoList", promoList);

        // Forward đến trang hiển thị (index.jsp)
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
