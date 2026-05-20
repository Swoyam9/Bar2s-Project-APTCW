package com.ecommerce.controller;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.models.CartItem;
import com.ecommerce.models.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cart/add")
public class AddToCartServlet extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Math.max(1, Integer.parseInt(request.getParameter("quantity")));
            Product product = productDAO.findById(productId);
            if (product == null || product.getStockQuantity() < quantity) {
                request.getSession().setAttribute("error", "Product is unavailable or stock is insufficient.");
                response.sendRedirect(request.getContextPath() + "/products");
                return;
            }

            List<CartItem> cart = getCart(request.getSession());
            CartItem existing = cart.stream()
                    .filter(item -> item.getProduct().getId() == productId)
                    .findFirst()
                    .orElse(null);
            if (existing == null) {
                cart.add(new CartItem(product, quantity));
            } else {
                existing.setQuantity(Math.min(product.getStockQuantity(), existing.getQuantity() + quantity));
            }

            request.getSession().setAttribute("success", "Item added to cart.");
            response.sendRedirect(request.getContextPath() + "/cart");
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Could not add item to cart", e);
        }
    }

    @SuppressWarnings("unchecked")
    private List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}
