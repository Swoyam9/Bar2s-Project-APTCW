package com.ecommerce.controller;

import com.ecommerce.dao.UserDAO;
import com.ecommerce.models.User;
import com.ecommerce.utils.PasswordUtils;
import com.ecommerce.utils.ValidationUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (ValidationUtils.isBlank(fullName) || !ValidationUtils.isValidEmail(email) ||
                !ValidationUtils.isValidPhone(phone) || !ValidationUtils.isStrongEnoughPassword(password) ||
                !password.equals(confirmPassword)) {
            request.setAttribute("error", "Please complete the form correctly. Password must contain a capital letter, small letter, number, and special character.");
            doGet(request, response);
            return;
        }

        try {
            if (userDAO.findByEmail(email) != null) {
                request.setAttribute("error", "An account with this email already exists.");
                doGet(request, response);
                return;
            }

            String salt = PasswordUtils.generateSalt();
            User user = new User();
            user.setFullName(fullName);
            user.setEmail(email);
            user.setPhone(phone);
            user.setAddress(address);
            user.setPasswordSalt(salt);
            user.setPasswordHash(PasswordUtils.hashPassword(password, salt));
            user.setRole("USER");

            userDAO.register(user);
            request.setAttribute("success", "Registration successful. Please log in.");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Registration failed", e);
        }
    }

}
