package com.ecommerce.controller;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.models.Product;
import com.ecommerce.utils.ValidationUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet("/admin/products/edit")
public class UpdateProductServlet extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Product product = productDAO.findById(id);
            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            request.setAttribute("product", product);
            request.setAttribute("categories", productDAO.findCategories());
            request.getRequestDispatcher("/WEB-INF/views/admin/edit-product.jsp").forward(request, response);
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Could not load product", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Product product = buildProduct(request);
            if (!isValid(product)) {
                request.setAttribute("error", "Please enter valid product details.");
                request.setAttribute("product", product);
                request.setAttribute("categories", productDAO.findCategories());
                request.getRequestDispatcher("/WEB-INF/views/admin/edit-product.jsp").forward(request, response);
                return;
            }
            productDAO.update(product);
            request.getSession().setAttribute("success", "Product updated successfully.");
            response.sendRedirect(request.getContextPath() + "/admin/products");
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Could not update product", e);
        }
    }

    private Product buildProduct(HttpServletRequest request) {
        Product product = new Product();
        product.setId(Integer.parseInt(request.getParameter("id")));
        product.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
        product.setName(request.getParameter("name"));
        product.setBrand(request.getParameter("brand"));
        product.setPartNumber(request.getParameter("partNumber"));
        product.setVehicleType(request.getParameter("vehicleType"));
        product.setDescription(request.getParameter("description"));
        product.setPrice(new BigDecimal(request.getParameter("price")));
        product.setStockQuantity(Integer.parseInt(request.getParameter("stockQuantity")));
        return product;
    }

    private boolean isValid(Product product) {
        return product.getId() > 0 && product.getCategoryId() > 0 && !ValidationUtils.isBlank(product.getName()) &&
                !ValidationUtils.isBlank(product.getBrand()) && !ValidationUtils.isBlank(product.getPartNumber()) &&
                ValidationUtils.isValidVehicleType(product.getVehicleType()) &&
                ValidationUtils.isPositivePrice(product.getPrice()) && ValidationUtils.isNonNegative(product.getStockQuantity());
    }

}
