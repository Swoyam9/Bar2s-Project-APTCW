package com.ecommerce.controller;

import com.ecommerce.models.CartItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cart/remove")
public class RemoveFromCartServlet extends HttpServlet {
    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            List<CartItem> cart = (List<CartItem>) request.getSession().getAttribute("cart");
            if (cart != null) {
                cart.removeIf(item -> item.getProduct().getId() == productId);
            }
            request.getSession().setAttribute("success", "Item removed from cart.");
            response.sendRedirect(request.getContextPath() + "/cart");
        } catch (NumberFormatException e) {
            throw new ServletException("Could not remove cart item", e);
        }
    }
}
