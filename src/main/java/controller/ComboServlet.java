package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Dish;

import java.io.IOException;

import beans.DishList;

/**
 * Servlet implementation class ComboServlet
 */
@WebServlet("/combo")
public class ComboServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ComboServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Lấy session
        HttpSession session = request.getSession();

        // Tạo danh sách DishList và lưu vào session
        DishList dishList = new DishList(); 
        session.setAttribute("dishList", dishList);
        
        // Chuyển tới trang hiển thị combo (ví dụ home.jsp hoặc combo.jsp)
        request.getRequestDispatcher("index.jsp").forward(request, response);

    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
