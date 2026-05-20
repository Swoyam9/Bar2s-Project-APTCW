package com.ecommerce.controller;

import com.ecommerce.dao.UserDAO;
import com.ecommerce.models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/users/delete")
public class DeleteUserServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("id"));
            User currentUser = (User) request.getSession().getAttribute("authUser");
            if (currentUser != null && currentUser.getId() == userId) {
                request.getSession().setAttribute("error", "Admin account cannot be deleted.");
                response.sendRedirect(request.getContextPath() + "/admin/users");
                return;
            }
            if (userDAO.deleteUser(userId)) {
                request.getSession().setAttribute("success", "User deleted successfully.");
            } else {
                request.getSession().setAttribute("error", "Admin account cannot be deleted.");
            }
            response.sendRedirect(request.getContextPath() + "/admin/users");
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Could not delete user", e);
        }
    }
}
