package com.ecommerce.controller;

import com.ecommerce.dao.ProductDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet({"/products", "/home", "/admin/products"})
public class ViewProductsServlet extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String search = request.getParameter("search");
            String vehicleType = request.getParameter("vehicleType");
            request.setAttribute("products", productDAO.findFiltered(search, vehicleType));
            request.setAttribute("search", search);
            request.setAttribute("vehicleType", vehicleType);
            if (request.getServletPath().startsWith("/admin")) {
                request.getRequestDispatcher("/WEB-INF/views/admin/products.jsp").forward(request, response);
            } else if (request.getServletPath().equals("/home")) {
                request.getRequestDispatcher("/WEB-INF/views/user/home.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/views/user/products.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Could not load products", e);
        }
    }
}
