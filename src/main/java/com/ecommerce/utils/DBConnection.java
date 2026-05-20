package com.ecommerce.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/auto_spare_parts_db?useSSL=false&serverTimezone=UTC";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "";

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver not found", e);
        }

        String url = firstNonBlank(System.getenv("DB_URL"), System.getProperty("DB_URL"), DEFAULT_URL);
        String user = firstNonBlank(System.getenv("DB_USER"), System.getProperty("DB_USER"), DEFAULT_USER);
        String password = firstNonBlank(System.getenv("DB_PASSWORD"), System.getProperty("DB_PASSWORD"), DEFAULT_PASSWORD);
        return DriverManager.getConnection(url, user, password);
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.trim().isEmpty()) {
                return value;
            }
        }
        return "";
    }
}
