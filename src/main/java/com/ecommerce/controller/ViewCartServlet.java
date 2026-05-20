package com.ecommerce.controller;

import com.ecommerce.models.CartItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@WebServlet("/cart")
public class ViewCartServlet extends HttpServlet {
    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CartItem> cart = (List<CartItem>) request.getSession().getAttribute("cart");
        if (cart == null) {
            cart = Collections.emptyList();
        }
        BigDecimal total = cart.stream().map(CartItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        request.setAttribute("cart", cart);
        request.setAttribute("total", total);
        request.getRequestDispatcher("/WEB-INF/views/user/cart.jsp").forward(request, response);
    }
}
