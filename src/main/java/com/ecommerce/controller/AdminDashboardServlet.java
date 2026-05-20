package com.ecommerce.controller;

import com.ecommerce.dao.OrderDAO;
import com.ecommerce.dao.ProductDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();
    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("products", productDAO.findAll());
            request.setAttribute("orders", orderDAO.findAll());
            request.setAttribute("revenue", orderDAO.getTotalRevenue());
            request.setAttribute("topSellingProduct", orderDAO.getTopSellingProduct());
            request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Could not load dashboard", e);
        }
    }
}
