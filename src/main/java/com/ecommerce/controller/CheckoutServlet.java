package com.ecommerce.controller;

import com.ecommerce.models.CartItem;
import com.ecommerce.models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CartItem> cart = (List<CartItem>) request.getSession().getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            request.getSession().setAttribute("error", "Your cart is empty.");
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }
        User user = (User) request.getSession().getAttribute("authUser");
        BigDecimal total = cart.stream().map(CartItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        request.setAttribute("total", total);
        request.setAttribute("defaultAddress", user.getAddress());
        request.getRequestDispatcher("/WEB-INF/views/user/checkout.jsp").forward(request, response);
    }
}
