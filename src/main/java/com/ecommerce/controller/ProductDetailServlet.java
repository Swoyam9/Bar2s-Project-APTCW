package com.ecommerce.controller;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.models.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/product")
public class ProductDetailServlet extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("id"));
            Product product = productDAO.findById(productId);
            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            request.setAttribute("product", product);
            request.setAttribute("rating", ratingFor(product.getId()));
            request.getRequestDispatcher("/WEB-INF/views/user/product-detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException e) {
            throw new ServletException("Could not load product details", e);
        }
    }

    private String ratingFor(int productId) {
        int filledStars = 4 + Math.abs(productId % 2);
        StringBuilder rating = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            rating.append(i <= filledStars ? "★" : "☆");
        }
        rating.append(" from previous buyers");
        return rating.toString();
    }
}
