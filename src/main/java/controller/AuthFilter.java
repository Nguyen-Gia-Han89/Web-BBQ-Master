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
	    "/party-booking", "/party-booking/*"
	})public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        // 1. Kiểm tra xem đã đăng nhập chưa
        boolean loggedIn = (session != null && session.getAttribute("customer") != null);
        String userRole = (session != null) ? (String) session.getAttribute("userRole") : null;

        String path = req.getRequestURI();

        if (!loggedIn) {
            // Nếu chưa đăng nhập, đá về trang login
        	res.sendRedirect(req.getContextPath() + "/index.jsp?requireLogin=true");        
        	} else {
            // 2. Nếu vào vùng Admin, phải kiểm tra Role
            if (path.contains("/admin/") && !"administrator".equals(userRole)) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập vùng này!");
            } else {
                // Đã đăng nhập và đúng quyền, cho đi tiếp
                chain.doFilter(request, response);
            }
        }
    }
}