package com.ecommerce.controller;

import com.ecommerce.dao.OrderDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/orders/status")
public class UpdateOrderStatusServlet extends HttpServlet {
    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            String status = request.getParameter("status");
            if (orderDAO.updateStatus(orderId, status)) {
                request.getSession().setAttribute("success", "Order status updated successfully.");
            } else {
                request.getSession().setAttribute("error", "Please choose a valid order status.");
            }
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Could not update order status", e);
        }
    }
}
