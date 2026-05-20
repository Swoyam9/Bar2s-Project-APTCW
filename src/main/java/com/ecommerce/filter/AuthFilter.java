package com.ecommerce.filter;

import com.ecommerce.dao.UserDAO;
import com.ecommerce.models.User;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    private final UserDAO userDAO = new UserDAO();

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();

        if (isPublic(path)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("authUser");
        if (user == null) {
            user = restoreFromCookie(request);
            if (user != null) {
                session.setAttribute("authUser", user);
            }
        }

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (path.startsWith("/admin") && !user.isAdmin()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    private boolean isPublic(String path) {
        return path.equals("/") || path.equals("/index.jsp") || path.equals("/login") || path.equals("/register") ||
                path.startsWith("/css/") ||
                path.startsWith("/images/") ||
                path.startsWith("/components/");
    }

    private User restoreFromCookie(HttpServletRequest request) throws ServletException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if ("rememberToken".equals(cookie.getName())) {
                try {
                    return userDAO.findByRememberToken(cookie.getValue());
                } catch (SQLException e) {
                    throw new ServletException("Could not restore remember-me session", e);
                }
            }
        }
        return null;
    }
}
