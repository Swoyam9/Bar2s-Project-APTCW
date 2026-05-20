package com.ecommerce.controller;

import com.ecommerce.dao.UserDAO;
import com.ecommerce.models.User;
import com.ecommerce.utils.PasswordUtils;
import com.ecommerce.utils.ValidationUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        if (!ValidationUtils.isValidEmail(email) || ValidationUtils.isBlank(password)) {
            request.setAttribute("error", "Please enter a valid email and password.");
            doGet(request, response);
            return;
        }

        try {
            User user = userDAO.findByEmail(email);
            if (user == null || !PasswordUtils.verifyPassword(password, user.getPasswordSalt(), user.getPasswordHash())) {
                request.setAttribute("error", "Invalid email or password.");
                doGet(request, response);
                return;
            }

            request.getSession().setAttribute("authUser", user);
            if ("on".equals(remember)) {
                String token = PasswordUtils.randomToken();
                userDAO.saveRememberToken(user.getId(), token);
                Cookie cookie = new Cookie("rememberToken", token);
                cookie.setMaxAge(7 * 24 * 60 * 60);
                cookie.setHttpOnly(true);
                cookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
                response.addCookie(cookie);
            }

            response.sendRedirect(request.getContextPath() + (user.isAdmin() ? "/admin/dashboard" : "/home"));
        } catch (SQLException e) {
            throw new ServletException("Login failed", e);
        }
    }
}
