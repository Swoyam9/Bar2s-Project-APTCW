package com.ecommerce.dao;

import com.ecommerce.models.Product;
import com.ecommerce.queries.Queries;
import com.ecommerce.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductDAO {
    private static boolean productImageColumnChecked;

    public List<Product> findAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            ensureProductImageColumn(con);
            try (PreparedStatement ps = con.prepareStatement(Queries.LIST_PRODUCTS);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(mapProduct(rs));
                }
            }
        }
        return products;
    }

    public Product findById(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {
            ensureProductImageColumn(con);
            try (PreparedStatement ps = con.prepareStatement(Queries.FIND_PRODUCT_BY_ID)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next() ? mapProduct(rs) : null;
                }
            }
        }
    }

    public List<Product> findFiltered(String search, String vehicleType) throws SQLException {
        List<Product> products = new ArrayList<>();
        String cleanSearch = search == null || search.trim().isEmpty() ? null : "%" + search.trim().toLowerCase() + "%";
        String cleanVehicleType = vehicleType == null || vehicleType.trim().isEmpty() ? null : vehicleType.trim();
        try (Connection con = DBConnection.getConnection()) {
            ensureProductImageColumn(con);
            try (PreparedStatement ps = con.prepareStatement(Queries.LIST_FILTERED_PRODUCTS)) {
                ps.setString(1, cleanVehicleType);
                ps.setString(2, cleanVehicleType);
                ps.setString(3, cleanSearch);
                ps.setString(4, cleanSearch);
                ps.setString(5, cleanSearch);
                ps.setString(6, cleanSearch);
                ps.setString(7, cleanSearch);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        products.add(mapProduct(rs));
                    }
                }
            }
        }
        return products;
    }

    public boolean create(Product product) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {
            ensureProductImageColumn(con);
            try (PreparedStatement ps = con.prepareStatement(Queries.INSERT_PRODUCT)) {
                fillProductStatement(ps, product);
                ps.setString(9, product.getStoredImageUrl());
                return ps.executeUpdate() == 1;
            }
        }
    }

    public boolean update(Product product) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {
            ensureProductImageColumn(con);
            try (PreparedStatement ps = con.prepareStatement(Queries.UPDATE_PRODUCT)) {
                fillProductStatement(ps, product);
                ps.setInt(9, product.getId());
                return ps.executeUpdate() == 1;
            }
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(Queries.DELETE_PRODUCT)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    public Map<Integer, String> findCategories() throws SQLException {
        Map<Integer, String> categories = new LinkedHashMap<>();
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(Queries.LIST_CATEGORIES)) {
            while (rs.next()) {
                categories.put(rs.getInt("id"), rs.getString("name"));
            }
        }
        return categories;
    }

    private void fillProductStatement(PreparedStatement ps, Product product) throws SQLException {
        ps.setInt(1, product.getCategoryId());
        ps.setString(2, product.getName());
        ps.setString(3, product.getBrand());
        ps.setString(4, product.getPartNumber());
        ps.setString(5, product.getVehicleType());
        ps.setString(6, product.getDescription());
        ps.setBigDecimal(7, product.getPrice());
        ps.setInt(8, product.getStockQuantity());
    }

    private void ensureProductImageColumn(Connection con) throws SQLException {
        if (productImageColumnChecked) {
            return;
        }
        synchronized (ProductDAO.class) {
            if (productImageColumnChecked) {
                return;
            }
            String sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS " +
                    "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'products' AND COLUMN_NAME = 'image_url'";
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                rs.next();
                if (rs.getInt(1) > 0) {
                    productImageColumnChecked = true;
                    return;
                }
            }
            try (Statement st = con.createStatement()) {
                st.executeUpdate("ALTER TABLE products ADD COLUMN image_url VARCHAR(255)");
            }
            productImageColumnChecked = true;
        }
    }

    private Product mapProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setCategoryId(rs.getInt("category_id"));
        product.setCategoryName(rs.getString("category_name"));
        product.setName(rs.getString("name"));
        product.setBrand(rs.getString("brand"));
        product.setPartNumber(rs.getString("part_number"));
        product.setVehicleType(rs.getString("vehicle_type"));
        product.setDescription(rs.getString("description"));
        product.setImageUrl(rs.getString("image_url"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setStockQuantity(rs.getInt("stock_quantity"));
        if (rs.getTimestamp("created_at") != null) {
            product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        if (rs.getTimestamp("updated_at") != null) {
            product.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }
        return product;
    }
}
