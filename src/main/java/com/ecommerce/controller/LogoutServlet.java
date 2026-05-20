package com.ecommerce.controller;

import com.ecommerce.dao.UserDAO;
import com.ecommerce.models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = (User) request.getSession().getAttribute("authUser");
        if (user != null) {
            try {
                userDAO.clearRememberToken(user.getId());
            } catch (SQLException e) {
                throw new ServletException("Could not clear remember token", e);
            }
        }
        request.getSession().invalidate();
        Cookie cookie = new Cookie("rememberToken", "");
        cookie.setMaxAge(0);
        cookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
        response.addCookie(cookie);
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
