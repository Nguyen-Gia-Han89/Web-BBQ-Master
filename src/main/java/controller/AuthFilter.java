package controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {
	    "/admin/*", 
	    "/booking-table", "/booking-table/*", 
	    "/party-booking", "/party-booking/*",
	    "/cart"
	})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        // Lấy thông tin role (nếu đã đăng nhập)
        String userRole = (session != null) ? (String) session.getAttribute("userRole") : null;
        boolean loggedIn = (session != null && session.getAttribute("customer") != null);
        
        String path = req.getRequestURI();

        // 1. Nếu là Admin: CHỈ được vào trang /admin/*
        if ("administrator".equals(userRole)) {
            if (path.contains("/admin/")) {
                chain.doFilter(request, response); // Admin vào đúng chỗ
            } else {
                // Admin đi lạc vào /cart hoặc trang user -> Đuổi về admin dashboard
            	res.sendError(HttpServletResponse.SC_FORBIDDEN, "Admin không có quyền truy cập trang khách hàng!");
            }
            return; 
        }

        // 2. Nếu vào vùng /admin/* mà KHÔNG PHẢI Admin (có thể là khách chưa login hoặc customer)
        if (path.contains("/admin/")) {
        	res.sendError(HttpServletResponse.SC_FORBIDDEN, "Khu vực này chỉ dành cho quản trị viên!");
            return;
        }
        
        // 3. CHẶN KHÁCH VÃNG LAI (Chưa đăng nhập)
        if (!loggedIn) {
            // Lưu lại trang khách muốn vào để sau khi login có thể quay lại (tùy chọn)
            // Redirect về trang chủ hoặc trang login
            res.sendRedirect(req.getContextPath() + "/index.jsp?requireLogin=true");
            return; // Dừng lại không cho vào trang đặt bàn/giỏ hàng
        }
    
        
        // Cho phép cả khách vãng lai và Customer vào bình thường
        chain.doFilter(request, response);
    }
}