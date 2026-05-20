package com.ecommerce.controller;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.models.Product;
import com.ecommerce.utils.ValidationUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@MultipartConfig(maxFileSize = 1024 * 1024 * 3)
@WebServlet("/admin/products/add")
public class AddProductServlet extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("categories", productDAO.findCategories());
            request.getRequestDispatcher("/WEB-INF/views/admin/add-product.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Could not load categories", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Product product = buildProduct(request);
            if (!isValid(product)) {
                request.setAttribute("error", "Please enter valid product details.");
                request.setAttribute("categories", productDAO.findCategories());
                request.getRequestDispatcher("/WEB-INF/views/admin/add-product.jsp").forward(request, response);
                return;
            }
            Part image = request.getPart("productImage");
            if (image != null && image.getSize() > 0) {
                String imagePath = saveProductImage(image);
                if (imagePath == null) {
                    request.setAttribute("error", "Please upload JPG, JPEG, PNG, or GIF image only.");
                    request.setAttribute("categories", productDAO.findCategories());
                    request.getRequestDispatcher("/WEB-INF/views/admin/add-product.jsp").forward(request, response);
                    return;
                }
                product.setImageUrl(imagePath);
            }
            productDAO.create(product);
            request.getSession().setAttribute("success", "Product added successfully.");
            response.sendRedirect(request.getContextPath() + "/admin/products");
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Could not add product", e);
        }
    }

    private Product buildProduct(HttpServletRequest request) {
        Product product = new Product();
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

    private String saveProductImage(Part image) throws IOException, ServletException {
        String extension = extensionOf(image.getSubmittedFileName());
        if (!isAllowedImage(extension)) {
            return null;
        }

        String folderPath = getServletContext().getRealPath("/images/products");
        File folder = new File(folderPath);
        if (!folder.exists() && !folder.mkdirs()) {
            throw new ServletException("Could not create product image folder");
        }

        String fileName = "product-" + System.currentTimeMillis() + extension;
        image.write(new File(folder, fileName).getAbsolutePath());
        return "images/products/" + fileName;
    }

    private String extensionOf(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.')).toLowerCase();
    }

    private boolean isAllowedImage(String extension) {
        return ".jpg".equals(extension) || ".jpeg".equals(extension) || ".png".equals(extension) || ".gif".equals(extension);
    }

    private boolean isValid(Product product) {
        return product.getCategoryId() > 0 && !ValidationUtils.isBlank(product.getName()) &&
                !ValidationUtils.isBlank(product.getBrand()) && !ValidationUtils.isBlank(product.getPartNumber()) &&
                ValidationUtils.isValidVehicleType(product.getVehicleType()) &&
                ValidationUtils.isPositivePrice(product.getPrice()) && ValidationUtils.isNonNegative(product.getStockQuantity());
    }

}
