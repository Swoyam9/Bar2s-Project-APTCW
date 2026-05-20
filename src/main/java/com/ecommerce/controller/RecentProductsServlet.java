package com.ecommerce.controller;

import com.ecommerce.dao.ProductDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/recent-products")
public class RecentProductsServlet extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("products", productDAO.findAll());
            request.getRequestDispatcher("/WEB-INF/views/admin/recent-products.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Could not load recent products", e);
        }
    }
}
